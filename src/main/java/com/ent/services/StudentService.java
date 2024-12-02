package com.ent.services;

import java.util.List;

import com.ent.entities.GroupSubject;
import com.ent.entities.Student;
import com.ent.entities.Teacher;
import com.ent.utils.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class StudentService {

    private final EntityManager entityManager;

    // Constructor to inject EntityManager
    public StudentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Create or Update a Teacher
    public Student saveStudent(Student student) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (student.getId() == null) {
                entityManager.persist(student); // Insert
            } else {
            	student = entityManager.merge(student); // Update
            }
            transaction.commit();
            return student;
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    // Retrieve a Teacher by ID
    public Student getStudentById(Long id) {
        return entityManager.find(Student.class, id);
    }
    
    public Student getStudentByUuid(String uuid) {
        TypedQuery<Student> query = entityManager.createQuery("SELECT u FROM Student u where u.uuid = :uuid", Student.class);
        query.setParameter("uuid", uuid);
        return query.getSingleResult();
    }

    // Retrieve all Teachers
    public List<Student> getAllStudents() {
        TypedQuery<Student> query = entityManager.createQuery("SELECT u FROM Student u", Student.class);
        return query.getResultList();
    }

    // Delete a Teacher by ID
    public void deleteStudentById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Student student = entityManager.find(Student.class, id);
            if (student != null) {
                entityManager.remove(student);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }
}
