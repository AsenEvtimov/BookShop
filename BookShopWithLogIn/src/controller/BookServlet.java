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
		case "showSearchForm":
			showSearchForm(request, response);
			break;
		case "searchBook":
			searchBook(request, response);
			break;
		case "buy":
			buy(request, response);			
			break;
		case "addQuantityToCart":
			changeQuantityInCart(+1, request, response);
			break;
		case "removeQuantityFromCart":
			changeQuantityInCart(-1, request, response);
			break;
		case "viewCart":
			viewCart(request, response);
			break;
		case "clearCart":
			clearCart(request, response);
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

	private void buy(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		System.out.printf("buyLinkPressed: request.bookId: %s, session.bookId: %s, username: %s\n",
				request.getParameter("bookId"), session.getAttribute("bookId"), session.getAttribute("username"));
		
		if (request.getParameter("bookId") !=null) {
			
			if (session.getAttribute("userName") == null) {
				
				session.setAttribute("bookId", request.getParameter("bookId"));
				response.sendRedirect("LoginServlet?action=viewLoginForm");
			}else {
				System.out.println("There is a bookId and there is a username, so add to cart.......");
				addToCart(request.getParameter("bookId"), request, response);
			}
		}else {
			System.out.printf("BookId from the session object: %s and username: %s\n",
					session.getAttribute("bookId"), session.getAttribute("username"));
			addToCart(session.getAttribute("bookId").toString(), request, response);
		}
						
	}
	
	protected void addToCart(String strBookId, HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
			
		int bookId = Integer.parseInt(strBookId);
		Book book = bookDAO.getBookById(bookId);
		System.out.printf("\nBook for id %d is: %s", bookId, book);
		
		int qty = 1;
		if (session.getAttribute("cart")==null) {
			session.setAttribute("cart", new HashMap<Book,Integer>());
		}

		HashMap<Book, Integer> cart = (HashMap<Book, Integer>)session.getAttribute("cart");
		
		if (cart.containsKey(book)){
			qty = cart.get(book)+1;
		}		
		cart.put(book, qty);		
		System.out.println("Book added to cart");
		
		response.sendRedirect("BookServlet?action=viewAll");				
	}

	private void changeQuantityInCart(int newQuantity, 
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int bookId = Integer.parseInt(request.getParameter("bookId"));
		Book book = bookDAO.getBookById(bookId);
		Map<Book, Integer> cart = (Map<Book, Integer>)
				request.getSession().getAttribute("cart");
		// When you add a book to the Map, if the book is there already
		// it will not be added again but the quantity will change.
		// newQuantity will be +1 for increasing the quantity and -1
		// for decreasing the quantity
		cart.put(book, cart.get(book) + newQuantity);
		/*
		 * If the new quantity becomes zero, remove that book from
		 * the cart.
		 * cart.get(book) will always get the quantity for that book.
		 */
		if (cart.get(book) == 0) {
			cart.remove(book);
		}
		System.out.println("Quantity changed in the cart for " + book);
		response.sendRedirect("BookServlet?action=viewCart");
	}

	private void showSearchForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("\\WEB-INF\\view\\searchBook.jsp");
		dispatcher.forward(request, response);
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

		int bookId = Integer.parseInt(request.getParameter("bookId"));
		Book book = bookDAO.getBookById(bookId);
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
		System.out.println("New book: " + book);
		bookDAO.insertBook(book);


		response.sendRedirect("BookServlet?action=viewAll");
	}

	private void deleteBook(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		int bookId = Integer.parseInt(request.getParameter("bookId"));
		System.out.println("book to delete: " + bookId);
		bookDAO.deleteBook(bookId);

		response.sendRedirect("BookServlet?action=viewAll");
	}

}
