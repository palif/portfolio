package ui;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bl.Look;

@WebServlet("/WebshopServlet")
public class WebshopServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Cart cart = new Cart();
	private Look look;
	
	public WebshopServlet() {
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false); 
		String action = req.getParameter("action");
		action.trim();
		look = new Look();
		
		System.out.println("Action:" + action);
		
		// Finds a product and adds it in the cart
		if(action.startsWith("ID")) {
			String id = action.trim();
			System.out.println("Inside the if ID statement");
			cart = (Cart) session.getAttribute("cart");
			cart.addProduct(look.getProductById(id));
			session.setAttribute("cart", cart);
			resp.sendRedirect("webshop.jsp");
		}
		
		if(action.startsWith("REMOVE")) {
			String id = action.substring(7).trim();
			System.out.println("Inside the if REMOVE statement");
			cart = (Cart) session.getAttribute("cart");
			cart.removeProduct(look.getProductById(id));
			session.setAttribute("cart", cart);
			resp.sendRedirect("cart.jsp");
		}
		
		if(action.startsWith("CHECKOUT")) {
			String user = (String) session.getAttribute("username");
			System.out.println("Inside the if CHECKOUT statement");
			cart = (Cart) session.getAttribute("cart");
			if(look.insertTransaction(cart.getAllProducts(), user)) {
				cart.clear();
				session.setAttribute("allProduct", look.getAllProduct());
				session.setAttribute("cart", cart);
				resp.sendRedirect("webshop.jsp");
			} else {
				session.setAttribute("error", "Error checking out! Double check cart and try again.");
				resp.sendRedirect("webshop");
			}
		}
		
		if(action.startsWith("REDIRECT_HOME")) {
			session.setAttribute("allProduct", look.getAllProduct());
			resp.sendRedirect("webshop.jsp");
		}
		
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		String action = req.getParameter("action");
		resp.sendRedirect("cart.jsp");
	}

}
