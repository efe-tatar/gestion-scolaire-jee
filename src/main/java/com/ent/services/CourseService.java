package com.ent.services;

import java.util.List;

import com.ent.entities.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class CourseService {

    private final EntityManager entityManager;

    // Constructor to inject EntityManager
    public CourseService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Create or Update a Course
    public Course saveCourse(Course course) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (course.getId() == null) {
                entityManager.persist(course); // Insert
            } else {
                course = entityManager.merge(course); // Update
            }
            transaction.commit();
            return course;
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    // Retrieve a Course by ID
    public Course getCourseById(Long id) {
        return entityManager.find(Course.class, id);
    }
    
    public Course getCourseByUuid(String uuid) {
        TypedQuery<Course> query = entityManager.createQuery("SELECT u FROM Course u where u.uuid = :uuid", Course.class);
        query.setParameter("uuid", uuid);
        return query.getSingleResult();
    }

    // Retrieve all Courses
    public List<Course> getAllCourses() {
        TypedQuery<Course> query = entityManager.createQuery("SELECT c FROM Course c", Course.class);
        return query.getResultList();
    }

    // Delete a Course by ID
    public void deleteCourseById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Course course = entityManager.find(Course.class, id);
            if (course != null) {
                entityManager.remove(course);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }
}
