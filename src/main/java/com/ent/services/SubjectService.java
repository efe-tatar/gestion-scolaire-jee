package com.ent.services;

import java.util.List;

import com.ent.entities.Subject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class SubjectService {

    private final EntityManager entityManager;

    // Constructor to inject EntityManager
    public SubjectService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Create or Update a Subject
    public Subject saveSubject(Subject subject) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (subject.getId() == null) {
                entityManager.persist(subject); // Insert
            } else {
                subject = entityManager.merge(subject); // Update
            }
            transaction.commit();
            return subject;
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    // Retrieve a Subject by ID
    public Subject getSubjectById(Long id) {
        return entityManager.find(Subject.class, id);
    }
    
    public Subject getSubjectByUuid(String uuid) {
        TypedQuery<Subject> query = entityManager.createQuery("SELECT u FROM Subject u where u.uuid = :uuid", Subject.class);
        query.setParameter("uuid", uuid);
        return query.getSingleResult();
    }

    // Retrieve all Subjects
    public List<Subject> getAllSubjects() {
        TypedQuery<Subject> query = entityManager.createQuery("SELECT c FROM Subject c", Subject.class);
        return query.getResultList();
    }

    // Delete a Subject by ID
    public void deleteSubjectById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Subject subject = entityManager.find(Subject.class, id);
            if (subject != null) {
                entityManager.remove(subject);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }
}
