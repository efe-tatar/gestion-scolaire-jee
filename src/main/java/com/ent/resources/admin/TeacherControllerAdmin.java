package com.ent.resources.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ent.entities.Student;
import com.ent.entities.Teacher;
import com.ent.entities.User;
import com.ent.services.CourseService;
import com.ent.services.GroupService;
import com.ent.services.SubjectService;
import com.ent.services.TeacherService;
import com.ent.services.UserService;
import com.ent.utils.DataTableUtil;
import com.ent.utils.HibernateUtil;
import com.ent.utils.PasswordUtil;
import com.ent.utils.ServerSideRenderingHelper;
import com.ent.validators.admin.SubjectStoreValidator;
import com.ent.validators.admin.TeacherStoreValidator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class TeacherControllerAdmin {
	
	public static void index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String nameFilter = request.getParameter("name_filter");
		String sortBy = request.getParameter("sort_order");
		String genderFilter = request.getParameter("gender_filter");
		
		String queryString = "SELECT t FROM Teacher t WHERE 1=1";
		
		if( nameFilter != null && !nameFilter.isBlank() ) {
			queryString += " AND (t.user.firstName like :nameFilter OR t.user.surname like :nameFilter)";
		}
		if( genderFilter != null && !genderFilter.isBlank() ) {
			queryString += " AND t.user.gender = :genderFilter";
		}
		if( sortBy != null && !sortBy.isBlank() ) {
			if(sortBy.equals("sur_desc")) {
				queryString += " ORDER BY t.user.surname DESC";
			} else {				
				queryString += " ORDER BY t.user.surname ASC";
			}
		}
		
		EntityManager manager = HibernateUtil.getEntityManager();
		TypedQuery<Teacher> teacherQuery = manager.createQuery(queryString, Teacher.class);
		
		if( nameFilter != null && !nameFilter.isBlank() ) {
			teacherQuery.setParameter("nameFilter", "%" + nameFilter + "%");
		}
		if( genderFilter != null && !genderFilter.isBlank() ) {
			teacherQuery.setParameter("genderFilter", genderFilter);
		}
		
		List<Teacher> teachers = teacherQuery.getResultList();
		
		DataTableUtil<Teacher> tableManager = new DataTableUtil<Teacher>(request);
		
		List<Teacher> sublist = tableManager.paginate(teachers);
		request.setAttribute("teachers", sublist);

		HttpServletResponseWrapper wrapper = ServerSideRenderingHelper.getResponseWrapper(response);
		request.getRequestDispatcher("/views/admin/teacher/tables/teachers.jsp").include(request, wrapper);
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, wrapper.toString());
		
		response.getWriter().write(jsonFormattedResponse);
		
	}
	
	public static void show(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	}
	
	public static void store(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		TeacherStoreValidator validator = new TeacherStoreValidator(request);
		boolean valid = validator.validate();
		if (!valid) {
			String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedError((Map<String, String>)request.getAttribute("errorMessages"));
			response.getWriter().write(jsonFormattedResponse);
			return;
		}
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		User user = new User();
		user.setUuid(UUID.randomUUID().toString());
		user.setFirstName(request.getParameter("name"));
		user.setSurname(request.getParameter("surname"));
		try {
			user.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateOfBirth")));
		} catch (ParseException e) {
			// :(
		}
		user.setEmail(request.getParameter("email"));
		user.setPassword(PasswordUtil.generatePassword());
		user.setGender(request.getParameter("gender"));
		
		new UserService(manager).saveUser(user);
		
		Teacher teacher = new Teacher();
		teacher.setUuid(UUID.randomUUID().toString());
		teacher.setUser(user);
		
		new TeacherService(manager).saveTeacher(teacher);
		
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, "");
		response.getWriter().write(jsonFormattedResponse);
		
	}

}
