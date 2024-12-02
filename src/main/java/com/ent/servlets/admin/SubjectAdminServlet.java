package com.ent.servlets.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ent.services.CourseService;
import com.ent.utils.HibernateUtil;

/**
 * Servlet implementation class SubjectAdminServlet
 */
@WebServlet("/adminportal/subjects")
public class SubjectAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubjectAdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if( request.getParameter("id") != null ) {
			request.getRequestDispatcher("/views/admin/subject/subjectshow.jsp").forward(request, response);
			return;
		}
		
		request.setAttribute("courses", new CourseService(HibernateUtil.getEntityManager()).getAllCourses());
		request.getRequestDispatcher("/views/admin/subject/subjectindex.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
