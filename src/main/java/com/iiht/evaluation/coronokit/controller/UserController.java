package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.coronokit.dao.KitDao;
import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.OrderSummary;
import com.iiht.evaluation.coronokit.model.ProductMaster;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private KitDao kitDAO;
	private ProductMasterDao productMasterDao;

	public void setKitDAO(KitDao kitDAO) {
		this.kitDAO = kitDAO;
	}

	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config. getServletContext().getInitParameter("jdbcPassword");
		
		this.kitDAO = new KitDao(jdbcURL, jdbcUsername, jdbcPassword);
		this.productMasterDao = new ProductMasterDao(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		String viewName = "";
		try {
			switch (action) {
			case "newuser":
				viewName = showNewUserForm(request, response);
				break;
			case "insertuser":
				viewName = insertNewUser(request, response);
				break;
			case "showproducts":
				viewName = showAllProducts(request, response);
				break;	
			case "addnewitem":
				viewName = addNewItemToKit(request, response);
				break;
			case "deleteitem":
				viewName = deleteItemFromKit(request, response);
				break;
			case "showkit":
				viewName = showKitDetails(request, response);
				break;
			case "placeorder":
				viewName = showPlaceOrderForm(request, response);
				break;
			case "saveorder":
				viewName = saveOrderForDelivery(request, response);
				break;	
			case "ordersummary":
				viewName = showOrderSummary(request, response);
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

	private String showOrderSummary(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "ordersummary.jsp";
	}

	private String saveOrderForDelivery(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String postalcode = request.getParameter("postalCode");
		String country = request.getParameter("country");
		int coronaKitId = (int) Math.round(Math.random()*1000000);
		
		String deliveryAddress = address1+"\n"+address2+"\n"+"City: "+city+"\n"+"State:"+state+"\n"+"Postal Code: "+postalcode+"\n"+"Country: "+country;
		HttpSession session = request.getSession();
		System.out.println("***** In Save order for delivery method ******");
		CoronaKit updateUserKit = (CoronaKit)session.getAttribute("userkit");
		
		System.out.println("***** Retrieved coronakit obect from session ******");
		updateUserKit.setDeliveryAddress(deliveryAddress);
		updateUserKit.setId(coronaKitId);
		updateUserKit.setOrderDate(LocalDate.now().toString());
		updateUserKit.setOrderFinalized(true);
		
		/*List<KitDetail> showkit = (List<KitDetail>)session.getAttribute("shoppingcart");
		int totalAmount = 0;
		System.out.println("***** Retrieved kitdetail obect from session ******");
		for(KitDetail kit : showkit) {
			totalAmount += kit.getQuantity()*kit.getAmount();
		}*/
		//To update quantity of existing cart product if tried to re-add using map
		Map<Integer,KitDetail> showkitmap = (Map<Integer,KitDetail>)session.getAttribute("shoppingcart");
		int totalAmount = 0;
		System.out.println("***** Retrieved kitdetail obect from session ******");
		for(Map.Entry<Integer, KitDetail> kit : showkitmap.entrySet()) {
			totalAmount += ((KitDetail)kit.getValue()).getQuantity()*((KitDetail)kit.getValue()).getAmount();
		}
		System.out.println("***** Setting total amount ******");
		updateUserKit.setTotalAmount(totalAmount);		
		session.setAttribute("userkit", updateUserKit);
		
		List<KitDetail> showkit = new ArrayList<KitDetail>(showkitmap.values());
		OrderSummary oSummary = new OrderSummary(updateUserKit, showkit);
		session.setAttribute("ordersummary", oSummary);
		
		return "user?action=ordersummary";
	}

	private String showPlaceOrderForm(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "placeorder.jsp";
	}

	private String showKitDetails(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "showkit.jsp";
	}

	private String deleteItemFromKit(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();		
		/*List<KitDetail> cart = (List<KitDetail>)session.getAttribute("shoppingcart");
		cart.remove(Integer.parseInt(request.getParameter("id")));
		session.setAttribute("shoppingcart", cart);*/
		//Using map
		Map<Integer,KitDetail> cart = (Map<Integer,KitDetail>)session.getAttribute("shoppingcart");
		cart.remove(Integer.parseInt(request.getParameter("id")));
		session.setAttribute("shoppingcart", cart);
		return "user?action=showkit";
	}

	private String addNewItemToKit(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		String pname = request.getParameter("pname");
		int cost = Integer.parseInt(request.getParameter("cost"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		/*KitDetail kit = new KitDetail(id, id, id, quantity, cost);
		HttpSession session = request.getSession();
		
		List<KitDetail> cart = (List<KitDetail>)session.getAttribute("shoppingcart");
		
		if(cart == null) {
			cart = new ArrayList<KitDetail>();
			session.setAttribute("shoppingcart", cart);
		}
		
		cart.add(kit);*/		
		//Using Map
		HttpSession session = request.getSession();
		Map<Integer,KitDetail> mapcart = (Map<Integer,KitDetail>)session.getAttribute("shoppingcart");
		KitDetail kit = null;
		if(mapcart == null) {
			mapcart = new HashMap<Integer,KitDetail>();
			kit = new KitDetail(id,pname, id, id, quantity, cost);
			//session.setAttribute("shoppingcart", mapcart);
		} else {
			if(mapcart.containsKey(id)) {
				kit = mapcart.get(id);
				int inCartQuantity = kit.getQuantity();
				int updatedQuantity = quantity + inCartQuantity;
				kit.setQuantity(updatedQuantity);
				
			} else {
				kit = new KitDetail(id,pname, id, id, quantity, cost);
			}
		}
		
		mapcart.put(id, kit);
		session.setAttribute("shoppingcart", mapcart);
		System.out.println("****** Added new item: "+mapcart);
		return "user?action=showkit";
	}

	private String showAllProducts(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		// need to retrieve records from DB 
		List<ProductMaster> availproducts = this.productMasterDao.getProductRecords();
		// put data into request object (to share with view page)
		request.setAttribute("availproducts", availproducts);
		return "showproductstoadd.jsp"; 
	}

	private String insertNewUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String personName = request.getParameter("pname");
		String email = request.getParameter("pemail");
		String contactNumber = request.getParameter("pcontact");
		
		CoronaKit coKit = new CoronaKit(0, personName, email, contactNumber, 0, "", "", false);
		HttpSession session = request.getSession();
		session.setAttribute("userkit", coKit);	
		return "user?action=showproducts";
	}

	private String showNewUserForm(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "newuser.jsp";
	}
}