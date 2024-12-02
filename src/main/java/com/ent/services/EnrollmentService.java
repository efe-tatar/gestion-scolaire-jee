package com.ent.services;

import java.util.List;

import com.ent.entities.Enrollment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class EnrollmentService {

    private final EntityManager entityManager;

    // Constructor to inject EntityManager
    public EnrollmentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Create or Update an Enrollment
    public Enrollment saveEnrollment(Enrollment enrollment) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (enrollment.getId() == null) {
                entityManager.persist(enrollment); // Insert
            } else {
                enrollment = entityManager.merge(enrollment); // Update
            }
            transaction.commit();
            return enrollment;
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    // Retrieve an Enrollment by ID
    public Enrollment getEnrollmentById(Long id) {
        return entityManager.find(Enrollment.class, id);
    }
    
    public Enrollment getEnrollmentByUuid(String uuid) {
        TypedQuery<Enrollment> query = entityManager.createQuery("SELECT u FROM Enrollment u where u.uuid = :uuid", Enrollment.class);
        query.setParameter("uuid", uuid);
        return query.getSingleResult();
    }

    // Retrieve all Enrollments
    public List<Enrollment> getAllEnrollments() {
        TypedQuery<Enrollment> query = entityManager.createQuery("SELECT c FROM Enrollment c", Enrollment.class);
        return query.getResultList();
    }

    // Delete an Enrollment by ID
    public void deleteEnrollmentById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Enrollment enrollment = entityManager.find(Enrollment.class, id);
            if (enrollment != null) {
                entityManager.remove(enrollment);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }
}
