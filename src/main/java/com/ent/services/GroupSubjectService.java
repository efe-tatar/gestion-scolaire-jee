package com.ent.services;

import java.util.List;

import com.ent.entities.GroupSubject;
import com.ent.entities.Teacher;
import com.ent.utils.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class GroupSubjectService {

    private final EntityManager entityManager;

    // Constructor to inject EntityManager
    public GroupSubjectService(EntityManager manager) {
        this.entityManager = manager;
    }

    // Create or Update a GroupSubject
    public GroupSubject saveGroupSubject(GroupSubject groupClass) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (groupClass.getId() == null) {
                entityManager.persist(groupClass); // Insert
            } else {
                groupClass = entityManager.merge(groupClass); // Update
            }
            transaction.commit();
            return groupClass;
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    // Retrieve a GroupSubject by ID
    public GroupSubject getGroupSubjectById(Long id) {
        return entityManager.find(GroupSubject.class, id);
    }
    
    public GroupSubject getGroupSubjectByUuid(String uuid) {
    	String queryString = "SELECT u FROM GroupSubject u where u.uuid = :uuid";
        
        TypedQuery<GroupSubject> query = HibernateUtil.getEntityManagerFactory().createEntityManager()
				.createQuery(queryString, GroupSubject.class);
        
        query.setParameter("uuid", uuid);
        System.out.println(uuid);
        
        return query.getSingleResult();
    }

    public List<GroupSubject> getGroupSubjectsByTeacher(Teacher teacher) {
        TypedQuery<GroupSubject> query = entityManager.createQuery("SELECT u FROM GroupSubject u where u.teacher = :teacher", GroupSubject.class);
        query.setParameter("teacher", teacher);
        return query.getResultList();
    }

    // Retrieve all GroupSubjects
    public List<GroupSubject> getAllGroupSubjects() {
        TypedQuery<GroupSubject> query = entityManager.createQuery("SELECT u FROM GroupSubject u", GroupSubject.class);
        return query.getResultList();
    }

    // Delete a GroupSubject by ID
    public void deleteGroupSubjectById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            GroupSubject groupClass = entityManager.find(GroupSubject.class, id);
            if (groupClass != null) {
                entityManager.remove(groupClass);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }
}
