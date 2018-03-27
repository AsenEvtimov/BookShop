package controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Book;
import model.LoginInfo;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private LogInDAO loginDao;

	public LoginServlet() {
		loginDao = new LogInDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null)
			action = "login";
		System.out.println("action: " + action);

		switch(action) {
		case "login":
			login(request, response);
			break;
		case "logOut":
			logOut(request, response);
			break;
		case "newUserForm":
			viewNewUserForm(request, response);
			break;
		case "createUser":
			insertNewLogin(request, response);
			break;
		case "viewLoginForm":
			viewLoginForm(request, response);
			break;
		default:
			response.sendRedirect("BookServlet?action=viewAll");
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void logOut(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		request.getSession().invalidate();
		response.sendRedirect("BookServlet?action=viewAll");
	}

	private void validLoginSoCheckForBookId(String userName, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		Object bookId = request.getSession().getAttribute("bookId");
		System.out.println("In login(), bookId is " + bookId);
		// Save the username and change the session ID
		request.getSession().setAttribute("userName", userName);
		request.changeSessionId();

		// If the bookId is saved in the HttpSession, this means the buy link
		// was pressed, the user was directed to login, so now direct the response
		// object onto the addToCart() method which will add the book to the cart,
		// now that the user is logged in. 
		System.out.println("In login (after changeSessionId()), bookId is " + bookId);
		if (bookId != null)
			response.sendRedirect("BookServlet?action=buy");
		else // The login link was pressed and not a redirect from the buy link.
			response.sendRedirect("BookServlet?action=viewAll");
	}

	protected void login(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		System.out.printf("Validating usernam %s and password %s\n", userName, password);

		if (loginDao.validateUsernameAndPassword(userName, password)) {

			validLoginSoCheckForBookId(userName, request, response);
		} else {
			String wrongUser = "wrong";
			request.setAttribute("wrongUser", wrongUser);
			request.getRequestDispatcher("\\WEB-INF\\view\\loginForm.jsp").
			forward(request, response);

		}

	}

	private void insertNewLogin(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		String userName = request.getParameter("newUserName");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");

		LoginInfo login = new LoginInfo(userName,password);

		String check = "";

		if (loginDao.checkUsernameExists(userName)) {
			check = "exist";
			request.setAttribute("check", check);
			request.getRequestDispatcher("\\WEB-INF\\view\\newUserForm.jsp").forward(request, response);
			//response.sendRedirect("LoginServlet?action=newUserForm");
			request.getSession().invalidate();
		}
		else if(!loginDao.checkUsernameExists(userName)&&password.equals(confirmPassword)) {
			if (session.getAttribute("userName")==null) {
				session.setAttribute("userName", userName);
			}
			loginDao.insertNewLogin(login);
			System.out.println("New login: " + login);
			System.out.println("User Created");
			response.sendRedirect("BookServlet?action=viewAll");
		}
		else {
			check = "passwordNotMatch";
			request.setAttribute("check", check);
			request.getRequestDispatcher("\\WEB-INF\\view\\newUserForm.jsp").forward(request, response);
			//response.sendRedirect("LoginServlet?action=newUserForm");
			//request.getSession().invalidate();
		}

	}

	private void viewNewUserForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setAttribute("check", "");
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("\\WEB-INF\\view\\newUserForm.jsp");
		dispatcher.forward(request, response);
	}

	protected void viewLoginForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setAttribute("wrongUser", "");
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("\\WEB-INF\\view\\loginForm.jsp");
		dispatcher.forward(request, response);
	}

}
