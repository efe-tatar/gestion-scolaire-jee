package com.ent.servlets.student;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.ent.entities.User;
import com.ent.entities.Enrollment;
import com.ent.entities.Student;

import java.io.IOException;
import java.util.List;

import com.ent.utils.HibernateUtil;

/**
 * Servlet implementation class StudentHomeServlet
 */
@WebServlet("/studentportal")
public class StudentHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentHomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("user") == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("student")) {
			response.sendRedirect("/ENT/signout");
			return;
		}
		
		User user = (User)session.getAttribute("user");
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		Student student =
				(Student) HibernateUtil
				.deriveQuery("SELECT s FROM Student s WHERE s.user = :user", manager)
				.setParameter("user", user)
				.getSingleResult();
		
		List<Enrollment> enrollments =
				HibernateUtil
				.deriveQuery("SELECT e FROM Enrollment e WHERE e.student = :student", manager)
				.setParameter("student", student)
				.getResultList();
		
		request.setAttribute("enrollments", enrollments);
		
		request.getRequestDispatcher("/views/student/home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
