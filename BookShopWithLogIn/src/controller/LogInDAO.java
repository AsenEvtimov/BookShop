package controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.LoginInfo;

public class LogInDAO {

	public boolean validateUsernameAndPassword (String userName, String password) {
		
		List<LoginInfo> listOfUsers = new ArrayList<>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();		
		
		try {
			Query<LoginInfo> query = 
				session.createQuery("FROM LoginInfo WHERE userName = :userName "
						+ "and password = :password");
			query.setParameter("userName", userName);
			query.setParameter("password", password);
			listOfUsers = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		System.out.println("Username "+userName+" password "+ password+ " exist " + !listOfUsers.isEmpty());
		System.out.println(listOfUsers);
		return !listOfUsers.isEmpty();
	}
	
	//return true if userName exist
	public boolean checkUsernameExists (String userName) {

		List<LoginInfo> listOfUsers = new ArrayList<>();

		Session session = HibernateUtil.getSessionFactory().openSession();

		try {
			Query<LoginInfo> query = 
				session.createQuery("FROM LoginInfo WHERE userName = :userName");
			query.setParameter("userName", userName);
			listOfUsers = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		System.out.println("Username "+userName+" exist " + !listOfUsers.isEmpty());
		System.out.println(listOfUsers);
		return !listOfUsers.isEmpty();//if user exist will return true
	}
	
	public void insertNewLogin(LoginInfo login) {

		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Integer id = (Integer)session.save(login);
			tx.commit();
			System.out.println("Login added id: " + id);
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		finally {
			session.close();
		}
	}
}
