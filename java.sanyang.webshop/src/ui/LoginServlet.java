package ui;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bl.EnumAuth;
import bl.Look;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Look look;   
	
    public LoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SecurePassword sp = SecurePassword.getInstance();
		String username = req.getParameter("user");
		String password = sp.getHashSHA256(req.getParameter("password").trim());
		look = new Look();
		
		if (look.tryLogin(username, password) == null){
			resp.sendRedirect("login.jsp");
		} else if(look.tryLogin(username, password).equals(EnumAuth.USER)) {
			HttpSession session = req.getSession();
			System.out.println("session Id: " + session.getId());
			session.setAttribute("username", username);
			session.setAttribute("allProduct", look.getAllProduct());
			session.setAttribute("cart", new Cart());
			resp.sendRedirect("webshop.jsp"); 
		} else if(look.tryLogin(username, password).equals(EnumAuth.ADMIN)) {
			HttpSession session = req.getSession();
			session.setAttribute("username", username);
			session.setAttribute("allTransaction", look.getAllTransaction());
			resp.sendRedirect("admin.jsp");
		} else if(look.tryLogin(username, password).equals(EnumAuth.STOCK_PERSONNEL)) {
			HttpSession session = req.getSession();
			session.setAttribute("username", username);
			session.setAttribute("searchResult", look.getProductsById("ID"));
			resp.sendRedirect("stockpersonnel.jsp");
		} 
		
	}

}
