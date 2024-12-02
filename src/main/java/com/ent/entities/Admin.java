package com.ent.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin {
	
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
	
	// Constructor
	public Admin() {}
	
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

}
