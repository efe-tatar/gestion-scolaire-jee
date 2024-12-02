package com.ent.services;

import java.util.List;

import com.ent.entities.User;
import com.ent.entities.Admin;
import com.ent.entities.Student;
import com.ent.entities.Teacher;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class UserService {

    private final EntityManager entityManager;

    // Constructor to inject EntityManager
    public UserService(EntityManager manager) {
        this.entityManager = manager;
    }

    // Create or Update a User
    public User saveUser(User user) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (user.getId() == null) {
                entityManager.persist(user); // Insert
            } else {
                user = entityManager.merge(user); // Update
            }
            transaction.commit();
            return user;
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    // Retrieve a User by ID
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    // Retrieve all Users
    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    // Delete a User by ID
    public void deleteUserById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            User user = entityManager.find(User.class, id);
            if (user != null) {
                entityManager.remove(user);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }
    
    public User findUserByEmailAndPassword(String email, String password) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);

        List<User> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    public static String getUserRole(EntityManager entityManager, User user) {
        List<Admin> a = entityManager.createQuery(
                        "SELECT a FROM Admin a WHERE a.user = :userId")
                .setParameter("userId", user)
                .getResultList();
        if (a.size() > 0) {
            return "admin";
        }

        List<Student> s = entityManager.createQuery(
                        "SELECT s FROM Student s WHERE s.user = :userId")
                .setParameter("userId", user)
                .getResultList();
        if (s.size() > 0) {
            return "student";
        }

        List<Teacher> t = entityManager.createQuery(
                        "SELECT t FROM Teacher t WHERE t.user = :userId")
                .setParameter("userId", user)
                .getResultList();
        if (t.size() > 0) {
            return "teacher";
        }

        return "unknown";
    }
    
}
