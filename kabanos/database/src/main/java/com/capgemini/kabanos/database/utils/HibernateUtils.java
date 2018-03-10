package com.capgemini.kabanos.database.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

	
	
	
	private static StandardServiceRegistry registry;
	  private static SessionFactory sessionFactory;

	  public static SessionFactory getSessionFactory() {
	    if (sessionFactory == null) {
	      try {
	        // Create registry
	        
	    	  StandardServiceRegistryBuilder b = new StandardServiceRegistryBuilder()
			            .configure();
	    	  registry = b
	            .build();

	        
	        
	        // Create MetadataSources
	        MetadataSources sources = new MetadataSources(registry);

	        // Create Metadata
	        Metadata metadata = sources.getMetadataBuilder().build();

	        // Create SessionFactory
	        sessionFactory = metadata.getSessionFactoryBuilder().build();

	      } catch (Exception e) {
	        e.printStackTrace();
	        if (registry != null) {
	          StandardServiceRegistryBuilder.destroy(registry);
	        }
	      }
	    }
	    return sessionFactory;
	  }

	  public static void shutdown() {
	    if (registry != null) {
	      StandardServiceRegistryBuilder.destroy(registry);
	    }
	  }
	
	
	
	
	
    private HibernateUtils() {}
//
//    private static final SessionFactory sessionFactory;
//
//    static {
//        try {
//        	
//        	System.out.println();
//        	
//            sessionFactory = new Configuration().configure().buildSessionFactory();           
//        } catch (Throwable ex) {
//            System.err.println("Initial SessionFactory creation failed. " + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
//
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//    
//   public static void shutdown() {
//        getSessionFactory().close();
//    }    
}