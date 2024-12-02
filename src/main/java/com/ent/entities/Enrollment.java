package com.ent.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;

@Entity
@Table(name = "enrollments")
public class Enrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "uuid", nullable = false, unique = true)
	private String uuid;
	
	@ManyToOne
	@JoinColumn(name = "id_student", referencedColumnName = "id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "id_course", referencedColumnName = "id")
	private Course course;
	
	@Column(name = "start_date", nullable = false, unique = true)
	@Temporal(jakarta.persistence.TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "end_date", nullable = false, unique = true)
	@Temporal(jakarta.persistence.TemporalType.DATE)
	private Date endDate;
	
	public Enrollment() {}
	
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

	public Student getStudent() {
	    return student;
	}

	public void setStudent(Student student) {
	    this.student = student;
	}

	public Course getCourse() {
	    return course;
	}

	public void setCourse(Course course) {
	    this.course = course;
	}

	public Date getStartDate() {
	    return startDate;
	}

	public void setStartDate(Date startDate) {
	    this.startDate = startDate;
	}

	public Date getEndDate() {
	    return endDate;
	}

	public void setEndDate(Date endDate) {
	    this.endDate = endDate;
	}

}
