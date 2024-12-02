package com.ent.resources.student;

import java.io.IOException;
import java.util.List;

import com.ent.entities.Grade;
import com.ent.entities.GroupSubject;
import com.ent.entities.Student;
import com.ent.entities.User;
import com.ent.services.GroupSubjectService;
import com.ent.utils.DataTableUtil;
import com.ent.utils.HibernateUtil;
import com.ent.utils.ServerSideRenderingHelper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.servlet.http.HttpSession;

public class GradeControllerStudent {
	
	public static void index(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		HttpSession session = request.getSession(false);
		User user = (User)session.getAttribute("user");
		
		Student student =
				(Student) HibernateUtil
				.deriveQuery("SELECT s FROM Student s WHERE s.user = :user", manager)
				.setParameter("user", user)
				.getSingleResult();
		
		String queryString = "SELECT g FROM Grade g WHERE 1=1 AND g.student = :student";
		
		String enrollment = request.getParameter("enrollment");
		if( enrollment != null && !enrollment.isBlank() ) {
			queryString += " AND g.enrollment.uuid = :enrollment";
		}
		
		Query query = HibernateUtil
				.getEntityManagerFactory()
				.createEntityManager()
				.createQuery(queryString)
				.setParameter("student", student);
		
		if( enrollment != null && !enrollment.isBlank() ) {
			query.setParameter("enrollment", enrollment);
		}
		
		List<Grade> grades = (List<Grade>)query.getResultList();
		
		DataTableUtil<Grade> tableManager = new DataTableUtil<Grade>(request);
		
		grades = tableManager.paginate(grades);
		
		request.setAttribute("grades", grades);
		
		HttpServletResponseWrapper wrapper = ServerSideRenderingHelper.getResponseWrapper(response);
		request.getRequestDispatcher("/views/student/tables/grades.jsp").include(request, wrapper);
		String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedTable(true, wrapper.toString());
		
		response.getWriter().write(jsonFormattedResponse);
		
	}

}
