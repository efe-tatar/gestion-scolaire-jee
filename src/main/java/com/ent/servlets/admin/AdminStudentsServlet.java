package com.ent.servlets.admin;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.ent.entities.Group;
import com.ent.entities.Student;
import com.ent.services.StudentService;
import com.ent.utils.HibernateUtil;

/**
 * Servlet implementation class AdminStudentsServlet
 */
@WebServlet("/adminportal/students")
public class AdminStudentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminStudentsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// check user role
		
		if( request.getParameter("id") != null ) {
			EntityManager manager = HibernateUtil.getEntityManager();
			
			Student student = new StudentService(HibernateUtil.getEntityManager()).getStudentByUuid(request.getParameter("id"));
			request.setAttribute("student", student);
			
			List<Group> potentialGroups = HibernateUtil
				.deriveQuery("SELECT g FROM Group g WHERE g.course IN (SELECT e.course FROM Enrollment e WHERE e.student = :student)", manager)
				.setParameter("student", student)
				.getResultList();
			request.setAttribute("potentialGroups", potentialGroups);
			
			request.getRequestDispatcher("/views/admin/student/student.jsp").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("/views/admin/student/students.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
