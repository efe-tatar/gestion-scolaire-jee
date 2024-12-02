package com.ent.services;

import java.util.List;

import com.ent.entities.Grade;
import com.ent.entities.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class GradeService {

    private final EntityManager entityManager;

    // Constructor to inject EntityManager
    public GradeService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Create or Update a Grade
    public Grade saveGrade(Grade grade) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (grade.getId() == null) {
                entityManager.persist(grade); // Insert
            } else {
                grade = entityManager.merge(grade); // Update
            }
            transaction.commit();
            return grade;
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    // Retrieve a Grade by ID
    public Grade getGradeById(Long id) {
        return entityManager.find(Grade.class, id);
    }
    
    public Grade getGradeByUuid(String uuid) {
        TypedQuery<Grade> query = entityManager.createQuery("SELECT u FROM Grade u where u.uuid = :uuid", Grade.class);
        query.setParameter("uuid", uuid);
        return query.getSingleResult();
    }

    // Retrieve all Grades
    public List<Grade> getAllGrades() {
        TypedQuery<Grade> query = entityManager.createQuery("SELECT g FROM Grade g", Grade.class);
        return query.getResultList();
    }

    // Delete a Grade by ID
    public void deleteGradeById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Grade grade = entityManager.find(Grade.class, id);
            if (grade != null) {
                entityManager.remove(grade);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }
}
