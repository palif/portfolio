package ui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bl.Look;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("signup.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SecurePassword sp = SecurePassword.getInstance();
		String username = request.getParameter("username");
		String password = sp.getHashSHA256(request.getParameter("password").trim());
		HttpSession session = request.getSession();
		System.out.println("User:" + username + ", pwd: " + password);
		
		Look look = new Look();
		
		if(look.trySignup(username, password) && password != null) {
			response.sendRedirect("login.jsp");
		} else {
			session.setAttribute("errorSignup", "Username already exist, choose a new one");
			response.sendRedirect("signup.jsp");
		}
		
	}
	

}
