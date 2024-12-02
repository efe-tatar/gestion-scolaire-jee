package com.ent.resources.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ent.entities.Course;
import com.ent.entities.Group;
import com.ent.services.CourseService;
import com.ent.services.GroupService;
import com.ent.services.StudentService;
import com.ent.utils.DataTableUtil;
import com.ent.utils.HibernateUtil;
import com.ent.utils.ServerSideRenderingHelper;
import com.ent.validators.admin.GroupStoreValidator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class GroupControllerAdmin {
	
	public static void index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String queryString = "SELECT g FROM Group g WHERE 1=1";
		
		String nameFilter = request.getParameter("name_filter");
		if( nameFilter != null && !nameFilter.isBlank() ) {
			queryString += " AND g.name like :nameFilter";
		}
		
		String courseFilter = request.getParameter("course_filter");
		if( courseFilter != null && !courseFilter.isBlank() ) {
			queryString += " AND g.course.uuid = :courseFilter";
		}
		
		String studentFilter = request.getParameter("student_filter");
		if( studentFilter != null && !studentFilter.isBlank() ) {
			queryString += " AND :studentFilter MEMBER OF g.students";
		}
		
		EntityManager manager = HibernateUtil.getEntityManager();
		TypedQuery<Group> studentQuery = manager.createQuery(queryString, Group.class);
		
		if( nameFilter != null && !nameFilter.isBlank() ) {
			studentQuery.setParameter("nameFilter", "%" + nameFilter + "%");
		}
		if( courseFilter != null && !courseFilter.isBlank() ) {
			studentQuery.setParameter("courseFilter", courseFilter);
		}
		if( studentFilter != null && !studentFilter.isBlank() ) {
			studentQuery.setParameter("studentFilter", new StudentService(manager).getStudentByUuid(studentFilter));
		}
		
		List<Group> groups = studentQuery.getResultList();
		
		DataTableUtil<Group> tableManager = new DataTableUtil<Group>(request);
		
		List<Group> sublist = tableManager.paginate(groups);
		request.setAttribute("groups", sublist);

		HttpServletResponseWrapper wrapper = ServerSideRenderingHelper.getResponseWrapper(response);
		request.getRequestDispatcher("/views/admin/group/tables/groups.jsp").include(request, wrapper);
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, wrapper.toString());
		
		response.getWriter().write(jsonFormattedResponse);
		
	}
	
	public static void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	}
	
	public static void store(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		GroupStoreValidator validator = new GroupStoreValidator(request);
		boolean valid = validator.validate();
		if (!valid) {
			String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedError((Map<String, String>)request.getAttribute("errorMessages"));
			response.getWriter().write(jsonFormattedResponse);
			return;
		}
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		Group group = new Group();
		group.setName(request.getParameter("name"));
		group.setCourse(new CourseService(manager).getCourseByUuid(request.getParameter("course")));
		group.setUuid(UUID.randomUUID().toString());
		
		new GroupService(manager).saveGroup(group);
		
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, "");
		response.getWriter().write(jsonFormattedResponse);
		
	}

}
