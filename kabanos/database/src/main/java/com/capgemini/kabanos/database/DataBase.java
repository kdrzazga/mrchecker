package com.capgemini.kabanos.database;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.capgemini.kabanos.common.domain.Implementation;
import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.database.utils.HibernateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DataBase {
	public DataBase() {
	}

	public boolean savePrepositions(List<Preposition> prepositions) {
		boolean result = false;
		Session session = null;
		Transaction tran = null;

		/*
		 * objects must point to a proper reference- once to a new created object and once to a object 
		 * taken form the database. if a predecessor is present in the DB then the object must point to a BDobject
		 * if it point to a wrong object the a "transient" error is thrown
		 */
		Map<String, Preposition> prepositionMap = new HashMap<>();

		try {
			session = HibernateUtils.getSessionFactory().openSession();
			tran = session.beginTransaction();

			for(Preposition prep : prepositions) {
				Preposition fromDB = this.getPreposition(prep.getFormattedLoggerStep(), session);

				prepositionMap.put(prep.getFormattedLoggerStep(), fromDB != null ? fromDB : prep);

				Set<Preposition> newPredecesors = new HashSet<>();				
				for(Preposition pred : prep.getPredecessors()) {
					newPredecesors.add(prepositionMap.getOrDefault(pred.getFormattedLoggerStep(), pred));
				}
				prep.setPredecessors(newPredecesors);

				if(fromDB == null) {
					session.save(prep);
					for (Implementation impl : prep.getImplementations()) {
						session.save(impl);
					}
				}
				else {
					fromDB.addTotalNumber(prep.getTotalNumber());
					fromDB.addImplementations(prep.getImplementations());
					fromDB.addPredecessors(prep.getPredecessors());
					session.update(fromDB);
					
					for (Implementation impl : fromDB.getImplementations()) {
						if(impl.getOccurrences() == 1)
							session.save(impl);
						else 
							session.update(impl);
					}
				}
			}
						
			tran.commit();
			System.out.println("Saved: " + prepositions.size());
			result = true;
		} catch (Exception e) {
			result = false;
			if (tran != null) {
				System.out.println("Performing rollback");
				tran.rollback();
			}
			System.out.println(e);
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}
	
	
	public boolean savePreposition(Preposition preposition) {
		List<Preposition> list = new ArrayList<Preposition>();
		list.add(preposition);
		return this.savePrepositions(list);
	}

	private Preposition getPreposition(String loggerStep, Session session) {
		Criteria criteria = session.createCriteria(Preposition.class);
		criteria.add(Restrictions.eq("formattedLoggerStep", loggerStep));
		return (Preposition) criteria.uniqueResult();
	}
	
	public Preposition getPreposition(String formattedLoggerStep) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Preposition result = this.getPreposition(formattedLoggerStep, session);		
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Preposition> getAllPrepositions() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Preposition.class);

		List<Preposition> result = criteria.list();
		
		if (session != null)
			session.close();
		
		return result;
	}
}
