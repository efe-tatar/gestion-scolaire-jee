package com.ent.servlets.teacher;

import java.io.IOException;
import java.util.List;

import com.ent.entities.GroupSubject;
import com.ent.entities.Teacher;
import com.ent.services.GroupSubjectService;
import com.ent.services.TeacherService;
import com.ent.utils.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/teacherportal")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Teacher teacher = new TeacherService(HibernateUtil.getEntityManager()).getTeacherById((long)1);
		
		if(teacher == null) {
			response.getWriter().append("nope");
			return;
		}
		
		request.getRequestDispatcher("/views/teacher/homepage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
