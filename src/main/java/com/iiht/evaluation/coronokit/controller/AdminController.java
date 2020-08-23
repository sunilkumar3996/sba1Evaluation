package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.model.ProductMaster; 

@WebServlet("/admin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductMasterDao productMasterDao;
	
	
	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {
		System.out.println("**** In Servlet init *******");
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		System.out.println("**** dbcurl:"+jdbcURL);
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config.getServletContext().getInitParameter("jdbcPassword");

		this.productMasterDao = new ProductMasterDao(jdbcURL, jdbcUsername, jdbcPassword);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action =  request.getParameter("action");
		String viewName = "";
		try {
			switch (action) {
			case "login" : 
				viewName = adminLogin(request, response);
				break;
			case "newproduct":
				viewName = showNewProductForm(request, response);
				break;
			case "insertproduct":
				viewName = insertProduct(request, response);
				break;
			case "deleteproduct":
				viewName = deleteProduct(request, response);
				break;
			case "editproduct":
				viewName = showEditProductForm(request, response);
				break;
			case "updateproduct":
				viewName = updateProduct(request, response);
				break;
			case "list":
				viewName = listAllProducts(request, response);
				break;	
			case "logout":
				viewName = adminLogout(request, response);
				break;	
			default : viewName = "notfound.jsp"; break;		
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		RequestDispatcher dispatch = 
					request.getRequestDispatcher(viewName);
		dispatch.forward(request, response);
		
		
	}

	private String adminLogout(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.removeAttribute("username");
		session.invalidate();
		return "index.jsp";
	}

	private String listAllProducts(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// need to retrieve records from DB
		
		List<ProductMaster> products = this.productMasterDao.getProductRecords();
		// put data into request object (to share with view page)
		request.setAttribute("products", products);
		return "listproducts.jsp";
	}

	private String updateProduct(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String id= request.getParameter("id");
		String name= request.getParameter("name");
		String cost = request.getParameter("cost");
		String description = request.getParameter("description");
		
		this.productMasterDao.updateProduct(id, name, cost, description);
		return "admin?action=list";
	}

	private String showEditProductForm(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		ProductMaster product = this.productMasterDao.getProductRecord(request.getParameter("id"));
		// put data into request object (to share with view page)
		request.setAttribute("product", product);
		return "editproduct.jsp";
	}

	private String deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		this.productMasterDao.deleteProduct(id);
		
		return "admin?action=list";
	}

	private String insertProduct(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String name= request.getParameter("pname");
		String des = request.getParameter("pdesc");
		String price = request.getParameter("pcost");
		
		this.productMasterDao.addNewProduct(name, des, price);
		
		return "admin?action=list"; 
	}

	private String showNewProductForm(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub		
		return "newproduct.jsp";
	}

	private String adminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uname = request.getParameter("username"); 
 		String password = request.getParameter("password"); 
 		if(uname.equals("admin") && password.equals("admin")) { 
			// put the data into a container to ship to JSP page 
 			// container : session 			 
			request.getSession().setAttribute("username", uname); 
			
			//return "admin?action=list&msg=Welcome "+uname+" <a href=\"admin?action=logout\">Logout</a>";
			
			return "admin?action=list&msg=Welcome "+uname;
		} 
 		
		return "index.jsp?msg=Invalid Credentials";
	}

	
}