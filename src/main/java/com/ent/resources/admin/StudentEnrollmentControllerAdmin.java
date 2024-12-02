package com.ent.resources.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ent.entities.Course;
import com.ent.entities.Enrollment;
import com.ent.services.CourseService;
import com.ent.services.EnrollmentService;
import com.ent.services.StudentService;
import com.ent.utils.DataTableUtil;
import com.ent.utils.HibernateUtil;
import com.ent.utils.ServerSideRenderingHelper;
import com.ent.validators.admin.EnrollmentStoreValidator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class StudentEnrollmentControllerAdmin {

	public static void index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String studentId = request.getParameter("studentId");
		
		String queryString = "SELECT e FROM Enrollment e WHERE e.student.uuid = :studentUuid";
		
		EntityManager manager = HibernateUtil.getEntityManager();
		TypedQuery<Enrollment> studentQuery = manager.createQuery(queryString, Enrollment.class);
		
		studentQuery.setParameter("studentUuid", studentId);
		
		List<Enrollment> enrollmentList = studentQuery.getResultList();
		
		request.setAttribute("enrollmentList", enrollmentList);
		request.setAttribute("courses", new CourseService(manager).getAllCourses());
		request.setAttribute("studentId", studentId);

		HttpServletResponseWrapper wrapper = ServerSideRenderingHelper.getResponseWrapper(response);
		request.getRequestDispatcher("/views/admin/student/tables/enrollments.jsp").include(request, wrapper);
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, wrapper.toString());
		
		response.getWriter().write(jsonFormattedResponse);
	}
	
	public static void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	}
	
	public static void store(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		EnrollmentStoreValidator validator = new EnrollmentStoreValidator(request);
		boolean valid = validator.validate();
		if (!valid) {
			String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedError((Map<String, String>)request.getAttribute("errorMessages"));
			response.getWriter().write(jsonFormattedResponse);
			return;
		}
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		// check uniqueness
		String queryString = "SELECT e FROM Enrollment e WHERE e.course.uuid = :course AND e.student.uuid = :student";
		Query query = HibernateUtil.deriveQuery(queryString, manager);
		query.setParameter("course", request.getParameter("course"));
		query.setParameter("student", request.getParameter("studentId"));
		
		if(query.getResultList().size() > 0) {
			String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedError("Student is already enrolled in this Course.");
			response.getWriter().write(jsonFormattedResponse);
			return;
		}
		
		CourseService courseService = new CourseService(manager);
		
		Enrollment enrollment = new Enrollment();
		enrollment.setUuid(UUID.randomUUID().toString());
		enrollment.setCourse(courseService.getCourseByUuid(request.getParameter("course")));
		enrollment.setStudent(new StudentService(manager).getStudentByUuid(request.getParameter("studentId")));
		try {
			enrollment.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("startDate")));
			enrollment.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("endDate")));
		} catch (ParseException e) {
		}
		
		new EnrollmentService(manager).saveEnrollment(enrollment);
		
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, "");
		response.getWriter().write(jsonFormattedResponse);
		
	}
	
}
