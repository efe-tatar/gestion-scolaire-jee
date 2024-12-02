package com.ent.entities;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "uuid", nullable = false, unique = true)
	private String uuid;

    @ManyToOne
	// hibernate looks for XX_id by default
	@JoinColumn(name = "id_student", nullable = false, referencedColumnName = "id")
    private Student student;

    @ManyToOne
	// hibernate looks for XX_id by default
	@JoinColumn(name = "id_subject", nullable = false, referencedColumnName = "id")
    private Subject subject;

    @ManyToOne
	// hibernate looks for XX_id by default
	@JoinColumn(name = "id_enrollment", nullable = false, referencedColumnName = "id")
    private Enrollment enrollment;

    @Column(name = "session1", nullable = true)
    private Float session1;

    @Column(name = "session2", nullable = true)
    private Float session2;

    public Grade() {}

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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public Float getSession1() {
        return session1;
    }

    public void setSession1(Float session1) {
        this.session1 = session1;
    }

    public Float getSession2() {
        return session2;
    }

    public void setSession2(Float session2) {
        this.session2 = session2;
    }
    
}
