package ui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LogoutServlet() {
        super();
    }

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(false);
		
		if (session != null) {
			while(session.getAttributeNames().hasMoreElements()) {
				String s = session.getAttributeNames().nextElement();
				session.setAttribute(s, null);
			}
			System.out.println("Log out, session Id: " + session.getId());
			session.invalidate();
		}
		
		resp.sendRedirect("login.jsp");
		
	}

}
