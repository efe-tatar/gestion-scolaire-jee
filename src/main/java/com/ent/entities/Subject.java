package com.ent.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "uuid", nullable = false, unique = true)
	private String uuid;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;

    @ManyToOne
    @JoinColumn(name = "id_course", nullable = false, referencedColumnName = "id")
    private Course course;
    
    @ManyToMany
    @JoinTable(
        name = "group_subjects",
        joinColumns = @JoinColumn(name = "id_subject"),
        inverseJoinColumns = @JoinColumn(name = "id_group")
    )
    private List<Group> groups;

    public Subject() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    public List<Group> getGroups() {
    	return groups;
    }
    
    public void setGroups(List<Group> groups) {
    	this.groups = groups;
    }
    
}
