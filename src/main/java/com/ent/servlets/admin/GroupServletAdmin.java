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
import com.ent.entities.Teacher;
import com.ent.entities.Subject;
import com.ent.services.CourseService;
import com.ent.services.GroupService;
import com.ent.services.TeacherService;
import com.ent.utils.HibernateUtil;

/**
 * Servlet implementation class GroupServletAdmin
 */
@WebServlet("/adminportal/groups")
public class GroupServletAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupServletAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if( request.getParameter("id") != null ) {
			EntityManager manager = HibernateUtil.getEntityManager();
			
			Group group = new GroupService(manager).getGroupByUuid(request.getParameter("id"));
			request.setAttribute("group", group);
			
			List<Teacher> teachers = new TeacherService(manager).getAllTeachers();
			request.setAttribute("teachers", teachers);
			
			List<Subject> subjects = 
					HibernateUtil.deriveQuery("SELECT s FROM Subject s WHERE s.course = :course", manager)
					.setParameter("course", group.getCourse())
					.getResultList();
			request.setAttribute("subjects", subjects);
					
			request.getRequestDispatcher("/views/admin/group/groupshow.jsp").forward(request, response);
			return;
		}
		
		request.setAttribute("courses", new CourseService(HibernateUtil.getEntityManager()).getAllCourses());
		request.getRequestDispatcher("/views/admin/group/groupindex.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
