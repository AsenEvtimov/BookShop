package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Book;


/**
 * Servlet implementation class BookServlet
 */
@WebServlet(
		description = "This servlet serves all BookShop request", 
		urlPatterns = { 
				"/BookServlet", 
				"/BookShop"
		})
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BookDAO bookDAO;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookServlet() {
		super();
		bookDAO = new BookDAO();
		System.out.println("Constructor called");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		//List<Book> listOfBooks = bookDAO.getAllBooks();
		//System.out.println(listOfBooks);

		String action = request.getParameter("action");

		if (action == null)
			action = "viewAll";
		System.out.println("action: " + action);

		switch(action) {
		case "showInsertForm":
			showInsertForm(request, response);
			break;
		case "showUpdateForm":
			showUpdateForm(request, response);
			break;
		case "insertNewBook":
			insertNewBook(request, response);
			break;
		case "delete":
			deleteBook(request, response);
			break;
		case "updateBook":
			updateBook(request, response);
			break;
		case "showSearchForm":
			showSearchForm(request, response);
			break;
		case "searchBook":
			searchBook(request, response);
			break;
		case "addToCart":
			addToCart(request, response);
			break;
		case "viewCart":
			viewCart(request, response);
			break;
		case "clearCart":
			clearCart(request, response);
			break;
		case "remove":
			addToCart(request, response);
			break;
		default:
			getAllBooks(request, response);
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
	
	private void clearCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.removeAttribute("cart");
		
		response.sendRedirect("BookServlet?action=viewAll");
	}
	
	/*	private void updateBook(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		int bookID = Integer.parseInt(request.getParameter("bookID"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		BigDecimal price = new BigDecimal(request.getParameter("price"));

		Book bookToUpdate = new Book(title,author,description,price);
		System.out.println("Book to update: " + bookToUpdate);

		bookDAO.updateBook(bookID, title, author, description, price);

		response.sendRedirect("BookServlet?action=viewAll");
	}*/
	
	private void viewCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("\\WEB-INF\\view\\viewCart.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void addToCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		int bookId = Integer.parseInt(request.getParameter("bookID"));
		Book book = bookDAO.getBookById(bookId);
		System.out.printf("\nBook for id %d is: %s", bookId, book);
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("cart")==null) {
			session.setAttribute("cart", new HashMap<Book,Integer>());
		}
		
		Map<Book, Integer> cart = (Map<Book, Integer>)session.getAttribute("cart");
		
		int qty = 1;
	if (cart.containsKey(book) && request.getParameter("action").equals("addToCart")) {
		qty = cart.get(book)+1;
	}
	else if (cart.containsKey(book) && request.getParameter("action").equals("remove")) {
		qty = cart.get(book)-1;
			if (qty==0) {
				session.removeAttribute("cart");
			}
	}
		cart.put(book, qty);
		System.out.println("Book added to cart");
		response.sendRedirect("BookServlet?action=viewCart");
	}
	
	private void showSearchForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("\\WEB-INF\\view\\searchBook.jsp");
		dispatcher.forward(request, response);
	}
		
	private void updateBook(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		int bookID = Integer.parseInt(request.getParameter("bookID"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		BigDecimal price = new BigDecimal(request.getParameter("price"));

		Book bookToUpdate = new Book(bookID,title,author,description,price);
		System.out.println("Book to update: " + bookToUpdate);

		bookDAO.updateBook(bookToUpdate);

		response.sendRedirect("BookServlet?action=viewAll");
	}

	private void searchBook(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		String searched = request.getParameter("search");
		String searchType = request.getParameter("searchType");
		System.out.println("Book " + searchType +" to search: " + searched);
		request.setAttribute("search", searched);
		request.setAttribute("searchType", searchType);
		
		List<Book> listOfBooks = bookDAO.searchBook(searched,searchType);		
		request.setAttribute("listOfBooks", listOfBooks);
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("\\WEB-INF\\view\\viewBooks.jsp");
		dispatcher.forward(request, response);
		
		}
	
	private void showInsertForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("\\WEB-INF\\view\\insertBook.jsp");
		dispatcher.forward(request, response);
	}

	private void showUpdateForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		int bookID = Integer.parseInt(request.getParameter("bookID"));
		Book book = bookDAO.getBookById(bookID);
		request.setAttribute("book", book);

		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("\\WEB-INF\\view\\updateBook.jsp");
		dispatcher.forward(request, response);
	}

	private void getAllBooks(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		List<Book> listOfBooks = bookDAO.getAllBooks();
		request.setAttribute("listOfBooks", listOfBooks);
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("\\WEB-INF\\view\\viewBooks.jsp");
		dispatcher.forward(request, response);
	}

	private void insertNewBook(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		BigDecimal price = new BigDecimal(request.getParameter("price"));

		Book book = new Book(title,author,description,price);		
		bookDAO.insertBook(book);
		System.out.println("New book: " + book);

		response.sendRedirect("BookServlet?action=viewAll");
	}

	private void deleteBook(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		int bookID = Integer.parseInt(request.getParameter("bookID"));
		System.out.println("book to delete: " + bookID);
		bookDAO.deleteBook(bookID);

		response.sendRedirect("BookServlet?action=viewAll");
	}

}
