package sessionfactory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class GetSessionFactory {
	static  SessionFactory sf = null;
	
	static{
		Configuration cfg = new Configuration().configure();
		StandardServiceRegistryBuilder srb = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		StandardServiceRegistry sr = srb.build();
		sf = cfg.buildSessionFactory(sr);
	}

	public static SessionFactory getInstance(){
		return sf;
	}
}
