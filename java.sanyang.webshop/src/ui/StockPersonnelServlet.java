package ui;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bl.Look;

@WebServlet("/StockPersonnelServlet")
public class StockPersonnelServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Look look;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String action = (String) req.getParameter("action");
		HttpSession session = req.getSession(false);
		
		look = new Look();
		
		if(action.startsWith("ID")) {
			session.setAttribute("searchResult", look.getProductsById(action));
			resp.sendRedirect("stockpersonnel.jsp");
		}
		
		else if(action.startsWith("DELETE")) {
			String id = action.substring(6);
			if(look.deleteProductById(id)) {
				session.setAttribute("searchResult", look.getProductsById("ID"));
				resp.sendRedirect("stockpersonnel.jsp");
			} else {
				resp.getWriter().println("ERROR delete product with id: " + id);
			}
		}
		
		else if(action.startsWith("ADD")) {
			String productId = req.getParameter("productId").trim();
			String title = req.getParameter("title").trim();
			int price = Integer.parseInt(req.getParameter("price").trim());
			int quantity = Integer.parseInt(req.getParameter("quantity").trim());
			
			Hashtable<String, Object> ht = new Hashtable();
			ht.put("id", productId);
			ht.put("title", title);
			ht.put("price", price);
			ht.put("quantity", quantity);
			System.out.println("/"+productId+title+price+quantity+"/");
			if(look.insertProduct(ht)) {
				session.setAttribute("searchResult", look.getProductsById("ID"));
				resp.sendRedirect("stockpersonnel.jsp");
			} else {
				resp.getWriter().println("ERROR adding product with id: " + productId);
			}
			
			
		}
		
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
}
