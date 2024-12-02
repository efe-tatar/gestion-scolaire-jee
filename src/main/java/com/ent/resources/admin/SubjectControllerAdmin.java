package com.ent.resources.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ent.entities.Group;
import com.ent.entities.Subject;
import com.ent.services.CourseService;
import com.ent.services.GroupService;
import com.ent.services.SubjectService;
import com.ent.utils.DataTableUtil;
import com.ent.utils.HibernateUtil;
import com.ent.utils.ServerSideRenderingHelper;
import com.ent.validators.admin.SubjectStoreValidator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class SubjectControllerAdmin {
	
	public static void index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		String nameFilter = request.getParameter("name_filter");
		String courseFilter = request.getParameter("course_filter");
		String groupFilter = request.getParameter("group_filter");
		
		String queryString = "SELECT s FROM Subject s WHERE 1=1";
		
		if( nameFilter != null && !nameFilter.isBlank() ) {
			queryString += " AND s.name like :nameFilter";
		}
		if( courseFilter != null && !courseFilter.isBlank() ) {
			queryString += " AND s.course.uuid = :courseFilter";
		}
		if (groupFilter != null && !groupFilter.isBlank()) {
		    queryString += " AND :groupFilter MEMBER OF s.groups";
		}
		
		TypedQuery<Subject> query = manager.createQuery(queryString, Subject.class);
		
		if( nameFilter != null && !nameFilter.isBlank() ) {
			query.setParameter("nameFilter", "%" + nameFilter + "%");
		}
		if( courseFilter != null && !courseFilter.isBlank() ) {
			query.setParameter("courseFilter", courseFilter);
		}
		if (groupFilter != null && !groupFilter.isBlank()) {
			query.setParameter("groupFilter", new GroupService(manager).getGroupByUuid(groupFilter));
		}
		
		List<Subject> subjects = query.getResultList();
		
		DataTableUtil<Subject> tableManager = new DataTableUtil<Subject>(request);
		
		List<Subject> sublist = tableManager.paginate(subjects);
		request.setAttribute("subjects", sublist);

		HttpServletResponseWrapper wrapper = ServerSideRenderingHelper.getResponseWrapper(response);
		request.getRequestDispatcher("/views/admin/subject/tables/subjects.jsp").include(request, wrapper);
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, wrapper.toString());
		
		response.getWriter().write(jsonFormattedResponse);
		
	}
	
	public static void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	}
	
	public static void store(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		SubjectStoreValidator validator = new SubjectStoreValidator(request);
		boolean valid = validator.validate();
		if (!valid) {
			String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedError((Map<String, String>)request.getAttribute("errorMessages"));
			response.getWriter().write(jsonFormattedResponse);
			return;
		}
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		Subject subject = new Subject();
		subject.setName(request.getParameter("name"));
		subject.setCourse(new CourseService(manager).getCourseByUuid(request.getParameter("course_id")));
		subject.setUuid(UUID.randomUUID().toString());
		
		new SubjectService(manager).saveSubject(subject);
		
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, "");
		response.getWriter().write(jsonFormattedResponse);
		
	}

}
