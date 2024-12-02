package com.ent.services;

import java.util.List;

import com.ent.entities.Group;
import com.ent.entities.Teacher;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class TeacherService {

    private final EntityManager entityManager;

    // Constructor to inject EntityManager
    public TeacherService(EntityManager manager) {
        this.entityManager = manager;
    }

    // Create or Update a Teacher
    public Teacher saveTeacher(Teacher teacher) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (teacher.getId() == null) {
                entityManager.persist(teacher); // Insert
            } else {
                teacher = entityManager.merge(teacher); // Update
            }
            transaction.commit();
            return teacher;
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    // Retrieve a Teacher by ID
    public Teacher getTeacherById(Long id) {
        return entityManager.find(Teacher.class, id);
    }
    
    public Teacher getTeacherByUuid(String uuid) {
    	 TypedQuery<Teacher> query = entityManager.createQuery("SELECT t FROM Teacher t where t.uuid = :uuid", Teacher.class);
         query.setParameter("uuid", uuid);
         return query.getSingleResult();
    }

    // Retrieve all Teachers
    public List<Teacher> getAllTeachers() {
        TypedQuery<Teacher> query = entityManager.createQuery("SELECT u FROM Teacher u", Teacher.class);
        return query.getResultList();
    }

    // Delete a Teacher by ID
    public void deleteTeacherById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Teacher teacher = entityManager.find(Teacher.class, id);
            if (teacher != null) {
                entityManager.remove(teacher);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }
}
