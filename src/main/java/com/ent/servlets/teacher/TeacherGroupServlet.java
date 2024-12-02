package com.ent.servlets.teacher;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ent.entities.GroupSubject;
import com.ent.services.GroupSubjectService;
import com.ent.utils.HibernateUtil;

/**
 * Servlet implementation class TeacherGroupServlet
 */
@WebServlet("/teacherportal/group")
public class TeacherGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherGroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// verifs
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		GroupSubject gs = new GroupSubjectService(manager).getGroupSubjectByUuid(request.getParameter("id"));
		request.setAttribute("gs", gs);
		
		
		request.getRequestDispatcher("/views/teacher/subjectgroup.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
