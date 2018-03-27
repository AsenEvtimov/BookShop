package controller;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	
	private HibernateUtil() {}
	
	static {
		Configuration config = new Configuration().configure();
		config.addAnnotatedClass(model.Book.class);
		StandardServiceRegistryBuilder builder = 
			new StandardServiceRegistryBuilder().applySettings(config.getProperties());
		sessionFactory = config.buildSessionFactory(builder.build());
	}

	protected static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
}
