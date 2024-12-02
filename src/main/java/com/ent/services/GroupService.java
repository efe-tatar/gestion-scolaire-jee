package com.ent.services;

import java.util.List;

import com.ent.entities.Group;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

public class GroupService {

    private final EntityManager entityManager;

    // Constructor to inject EntityManager
    public GroupService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Create or Update a Group
    public Group saveGroup(Group group) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (group.getId() == null) {
                entityManager.persist(group); // Insert
            } else {
                group = entityManager.merge(group); // Update
            }
            transaction.commit();
            return group;
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }

    // Retrieve a Group by ID
    public Group getGroupById(Long id) {
        return entityManager.find(Group.class, id);
    }

    public Group getGroupByUuid(String uuid) {
        TypedQuery<Group> query = entityManager.createQuery("SELECT u FROM Group u where u.uuid = :uuid", Group.class);
        query.setParameter("uuid", uuid);
        return query.getSingleResult();
    }

    // Retrieve all Groups
    public List<Group> getAllGroups() {
        TypedQuery<Group> query = entityManager.createQuery("SELECT g FROM Group g", Group.class);
        return query.getResultList();
    }

    // Delete a Group by ID
    public void deleteGroupById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Group group = entityManager.find(Group.class, id);
            if (group != null) {
                entityManager.remove(group);
            }
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            throw e;
        }
    }
}
