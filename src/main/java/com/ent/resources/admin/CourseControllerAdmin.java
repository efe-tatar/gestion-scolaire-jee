package com.ent.resources.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ent.entities.Course;
import com.ent.services.CourseService;
import com.ent.utils.DataTableUtil;
import com.ent.utils.HibernateUtil;
import com.ent.utils.ServerSideRenderingHelper;
import com.ent.validators.admin.CourseStoreValidator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class CourseControllerAdmin {
	
	public static void index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String nameFilter = request.getParameter("name_filter");
		
		String queryString = "SELECT c FROM Course c WHERE 1=1";
		
		if( nameFilter != null && !nameFilter.isBlank() ) {
			queryString += " AND c.name like :nameFilter";
		}
		
		EntityManager manager = HibernateUtil.getEntityManager();
		TypedQuery<Course> studentQuery = manager.createQuery(queryString, Course.class);
		
		if( nameFilter != null && !nameFilter.isBlank() ) {
			studentQuery.setParameter("nameFilter", "%" + nameFilter + "%");
		}
		
		List<Course> courses = studentQuery.getResultList();
		
		DataTableUtil<Course> tableManager = new DataTableUtil<Course>(request);
		
		List<Course> sublist = tableManager.paginate(courses);
		request.setAttribute("courses", sublist);

		HttpServletResponseWrapper wrapper = ServerSideRenderingHelper.getResponseWrapper(response);
		request.getRequestDispatcher("/views/admin/course/tables/courses.jsp").include(request, wrapper);
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, wrapper.toString());
		
		response.getWriter().write(jsonFormattedResponse);
		
	}
	
	public static void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	}
	
	public static void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	}
	
	public static void store(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		CourseStoreValidator validator = new CourseStoreValidator(request);
		boolean valid = validator.validate();
		if (!valid) {
			String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedError((Map<String, String>)request.getAttribute("errorMessages"));
			response.getWriter().write(jsonFormattedResponse);
			return;
		}
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		Course course = new Course();
		course.setName(request.getParameter("name"));
		course.setUuid(UUID.randomUUID().toString());
		
		new CourseService(manager).saveCourse(course);
		
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, "");
		response.getWriter().write(jsonFormattedResponse);
	}

}
