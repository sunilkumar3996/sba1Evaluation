package com.iiht.evaluation.coronokit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.iiht.evaluation.coronokit.model.ProductMaster;



public class ProductMasterDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public ProductMasterDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("**** Driver class loaded *****");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			System.out.println("**** url: "+jdbcURL+" user: "+jdbcUsername+" password: "+jdbcPassword);
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
		System.out.println("***** Connected **********");
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	// add DAO methods as per requirements
	
	public List<ProductMaster> getProductRecords() throws ClassNotFoundException, SQLException {
		String sql = "select * from products";
		this.connect();
		
		Statement stmt = this.jdbcConnection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		// map it to model
		List<ProductMaster> products = new ArrayList<ProductMaster>();
		while(rs.next()) {
			ProductMaster employee = new ProductMaster(rs.getInt("id"), 
											 rs.getString("name"), 
											 String.valueOf(rs.getInt("cost")), 
											 rs.getString("description"));
			products.add(employee);		
		}
		
		rs.close();
		stmt.close();
		this.disconnect();
		
		return products;
	}
	
	public boolean addNewProduct(String name, String description, String cost) throws ClassNotFoundException, SQLException {
		String sql = "insert into products (name,cost,description) values(?,?,?)";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setInt(2, Integer.parseInt(cost));
		pstmt.setString(3, description);
		
		boolean added = pstmt.executeUpdate() > 0;
		
		pstmt.close();
		this.disconnect();
		return added;
	}
	
	public ProductMaster getProductRecord(String id) throws ClassNotFoundException, SQLException {
		String sql = "select * from products where id="+id;
		this.connect();
		
		Statement stmt = this.jdbcConnection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		ProductMaster product = null;
		
		// map it to model
		if(rs.next()) {
			product = new ProductMaster(rs.getInt("id"), 
											 rs.getString("name"), 
											 String.valueOf(rs.getInt("cost")), 
											 rs.getString("description"));
		}
		
		rs.close();
		stmt.close();
		this.disconnect();
		
		return product;
	}
	
	public boolean updateProduct(String id, String name, String cost, String description) throws ClassNotFoundException, SQLException {
		String sql = "update products set name=?, cost=?, description=? where id=?";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setInt(2, Integer.parseInt(cost));
		pstmt.setString(3, description);
		pstmt.setInt(4, Integer.parseInt(id));
		
		boolean added = pstmt.executeUpdate() > 0;
		
		pstmt.close();
		this.disconnect();
		return added;
	}
	
public boolean deleteProduct(String id) throws ClassNotFoundException, SQLException {
		
		String sql = "delete from products where id=?";
		this.connect();
		
		PreparedStatement pstmt = this.jdbcConnection.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(id));
		
		boolean deleted = pstmt.executeUpdate() > 0;
		
		pstmt.close();
		this.disconnect();
		return deleted;
	}
}