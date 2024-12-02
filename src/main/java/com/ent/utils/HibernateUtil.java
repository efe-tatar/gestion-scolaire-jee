package com.ent.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class HibernateUtil {
    private static EntityManagerFactory entityManagerFactory;

    // Méthode d'initialisation statique de l'EntityManagerFactory
    static {
        try {
            System.out.println("Initialisation de l'EntityManagerFactory...");
            entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
            System.out.println("EntityManagerFactory initialisé avec succès.");
        } catch (Exception e) {
            System.out.println("Erreur d'initialisation de l'EntityManagerFactory : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour obtenir l'EntityManagerFactory
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
    
    public static EntityManager getEntityManager() {
    	return entityManagerFactory.createEntityManager();
    }
    
    public static Query createQuery(String queryString) {
    	return HibernateUtil.getEntityManager().createQuery(queryString);
    }
    
    public static Query deriveQuery(String queryString, EntityManager manager) {
    	return manager.createQuery(queryString);
    }

    // Méthode pour fermer l'EntityManagerFactory
    public static void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
            System.out.println("EntityManagerFactory fermé.");
        }
    }
}
