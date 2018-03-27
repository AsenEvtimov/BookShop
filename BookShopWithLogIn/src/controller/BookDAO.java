package controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Book;

public class BookDAO {

	public List<Book> getAllBooks() {

		List<Book> listOfBooks = new ArrayList<>();

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			Query<Book> query = session.createQuery("FROM Book");
			listOfBooks = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return listOfBooks;
	}

	public void insertBook(Book book) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Integer id = (Integer)session.save(book);
			tx.commit();
			System.out.println("Book added id: " + id);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
		}
	}

	public void deleteBook(int bookID) {		

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction tx = null;		
		try {
			tx = session.beginTransaction();
			Book book = session.get(Book.class, bookID);
			session.delete(book);
			tx.commit();
		} catch (HibernateException e) {
			if (tx !=null) {
				tx.rollback();
				e.printStackTrace();
			}
		}
		finally {
			session.close();
		}
	}

	public Book getBookById(int bookId) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Book book = session.get(Book.class, bookId);
		session.close();
		return book;
	}

	/*	public void updateBook (int bookID, String title, String author, String description, BigDecimal price) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction tx = null;		
		try {
			tx = session.beginTransaction();
			Book book = session.get(Book.class, bookID);
			book.setTitle(title);
			book.setAuthor(author);
			book.setDescription(description);
			book.setPrice(price);
			session.update(book);
			tx.commit();
		} catch (HibernateException e) {
			if (tx !=null) {
				tx.rollback();
			e.printStackTrace();
			}
		}
		finally {
			session.close();
		}
	}*/

	public void updateBook (Book bookToUpdate) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction tx = null;		
		try {
			tx = session.beginTransaction();
			session.update(bookToUpdate);			
			tx.commit();
		} catch (HibernateException e) {
			if (tx !=null) {
				tx.rollback();
				e.printStackTrace();
			}
		}
		finally {
			session.close();
		}
	}
		
	public List<Book> searchBook(String search, String searchType) {
		
		List<Book> listOfBooks = new ArrayList<>();

		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = null;
		
		try {
			if (searchType.equals("title")) {
				hql = "from Book b where b.title like concat('%', :searched, '%')";
			}
			else if (searchType.equals("author")) {
				hql = "from Book b where b.author like concat('%', :searched, '%')";
			}
			Query<Book> query = session.createQuery(hql);
			query.setParameter("searched", search);
			listOfBooks = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return listOfBooks;
	}
}
