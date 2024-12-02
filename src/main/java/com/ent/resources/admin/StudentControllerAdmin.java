package com.ent.resources.admin;

import java.io.IOException;
import java.util.List;

import com.ent.entities.GroupSubject;
import com.ent.entities.Student;
import com.ent.services.GroupService;
import com.ent.utils.HibernateUtil;
import com.ent.utils.DataTableUtil;
import com.ent.utils.ServerSideRenderingHelper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class StudentControllerAdmin {
	
	public static void index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String nameFilter = request.getParameter("name_filter");
		String sortBy = request.getParameter("sort_order");
		String genderFilter = request.getParameter("gender_filter");
		String groupFilter = request.getParameter("group_filter");
		
		String queryString = "SELECT s FROM Student s WHERE 1=1";
		
		if( nameFilter != null && !nameFilter.isBlank() ) {
			queryString += " AND (s.user.firstName like :nameFilter OR s.user.surname like :nameFilter)";
		}
		if( genderFilter != null && !genderFilter.isBlank() ) {
			queryString += " AND s.user.gender = :genderFilter";
		}
		if (groupFilter != null && !groupFilter.isBlank()) {
		    queryString += " AND :groupFilter MEMBER OF s.groups";
		}
		if( sortBy != null && !sortBy.isBlank() ) {
			if(sortBy.equals("sur_desc")) {
				queryString += " ORDER BY s.user.surname DESC";
			} else {				
				queryString += " ORDER BY s.user.surname ASC";
			}
		}
		
		EntityManager manager = HibernateUtil.getEntityManager();
		TypedQuery<Student> studentQuery = manager.createQuery(queryString, Student.class);
		
		if( nameFilter != null && !nameFilter.isBlank() ) {
			studentQuery.setParameter("nameFilter", "%" + nameFilter + "%");
		}
		if( genderFilter != null && !genderFilter.isBlank() ) {
			studentQuery.setParameter("genderFilter", genderFilter);
		}
		if (groupFilter != null && !groupFilter.isBlank()) {
		    studentQuery.setParameter("groupFilter", new GroupService(manager).getGroupByUuid(groupFilter));
		}
		
		List<Student> students = studentQuery.getResultList();
		
		DataTableUtil<Student> tableManager = new DataTableUtil<Student>(request);
		
		List<Student> sublist = tableManager.paginate(students);
		request.setAttribute("students", sublist);

		HttpServletResponseWrapper wrapper = ServerSideRenderingHelper.getResponseWrapper(response);
		request.getRequestDispatcher("/views/admin/student/tables/students.jsp").include(request, wrapper);
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, wrapper.toString());
		
		response.getWriter().write(jsonFormattedResponse);
		
	}
	
	
	public static void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	}
	
	public static void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	}

}
