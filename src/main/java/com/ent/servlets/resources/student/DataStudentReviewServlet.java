package com.ent.servlets.resources.student;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.ent.entities.Course;
import com.ent.entities.Grade;
import com.ent.entities.Student;
import com.ent.entities.User;
import com.ent.services.CourseService;
import com.ent.utils.HibernateUtil;

/**
 * Servlet implementation class DataStudentReviewServlet
 */
@WebServlet("/data/student/review")
@MultipartConfig
public class DataStudentReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataStudentReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("user") == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("student")) {
			return;
		}
		
		User user = (User)session.getAttribute("user");
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		Student student =
			(Student) HibernateUtil
			.deriveQuery("SELECT s FROM Student s WHERE s.user = :user", manager)
			.setParameter("user", user)
			.getSingleResult();
		
		String courseUuid = request.getParameter("course");
		
		List<Grade> grades = HibernateUtil
			.deriveQuery("SELECT g FROM Grade g WHERE g.student = :student AND g.enrollment.course.uuid = :course", manager)
			.setParameter("student", student)
			.setParameter("course", courseUuid)
			.getResultList();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"rapport_etudiant.txt\"");

        PrintWriter writer = response.getWriter();
        
        writer.println(user.getFirstName() + " " + user.getSurname());
        
        Course course = new CourseService(manager).getCourseByUuid(courseUuid);
        
        writer.println(course.getName());
        	
        float avg = 0;
        int ct = 0;
        
        for(Grade g : grades) {
        	
        	Float s1 = g.getSession1(), s2 = g.getSession2();
        	
        	if(s1 != null) {
        		avg += s1;
        		ct++;
        	} else if(s2 != null) {
        		avg += s2;
        		ct++;
        	}
        	
        	writer.println(g.getSubject().getName() + ": " + (s1 != null ? s1 : (s2 != null ? s2 : "") ) );
        }
        
        avg = avg / ((float) ct);
        
        writer.println("average: " + avg);

        writer.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
