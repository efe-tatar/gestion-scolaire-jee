package com.ent.validation;

import com.ent.utils.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class ResourceExistsValidator implements Validator{

	@Override
	public boolean validate(Object attribute, String rule) {
		
		if(attribute == null) return true;
		
		try {
			String[] rules = rule.split(":");
			
			EntityManager manager = HibernateUtil.getEntityManager();
			Query query = manager.createQuery("SELECT u from " + rules[1] + " u WHERE u." + rules[2] + " = :value");
			query.setParameter("value", attribute.toString());

			if(query.getResultList().size() < 1) return false;
			
			return true;
		}
		catch(Exception e) {
			System.err.print(e);
			return false;
		}
		
	}

}
