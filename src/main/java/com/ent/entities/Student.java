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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "uuid", nullable = false, unique = true)
	private String uuid;
	
	@OneToOne
	// hibernate looks for XX_id by default
	@JoinColumn(name = "id_user", nullable = false, referencedColumnName = "id")
	private User user;
	
	@ManyToMany
    @JoinTable(
        name = "group_students",
        joinColumns = @JoinColumn(name = "id_student"),
        inverseJoinColumns = @JoinColumn(name = "id_group")
    )
    private List<Group> groups;
	
	@OneToMany
	@JoinTable(
        name = "enrollments", // Join table name
        joinColumns = @JoinColumn(name = "id_student"),
        inverseJoinColumns = @JoinColumn(name = "id_course")
    )
	private List<Enrollment> enrollments;
	
	// Constructor
	public Student() {}
	
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

	public User getUser() {
	    return user;
	}

	public void setUser(User user) {
	    this.user = user;
	}
	
	public List<Group> getGroups() {
		return groups;
	}
	
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

}
