package com.ent.servlets.misc;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;

import com.ent.entities.Teacher;
import com.ent.entities.Student;
import com.ent.entities.User;
import com.ent.services.StudentService;
import com.ent.services.TeacherService;
import com.ent.services.UserService;
import com.ent.utils.HibernateUtil;
import com.ent.utils.PasswordUtil;
import com.ent.utils.ServerSideRenderingHelper;
import com.ent.validators.student.StudentStoreValidator;

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet("/signup")
@MultipartConfig
public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InscriptionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			if(session.getAttribute("user") != null || session.getAttribute("role") != null) {
				response.sendRedirect("/ENT/signout");
				return;
			}
		}

		request.getRequestDispatcher("/views/misc/signup.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StudentStoreValidator validator = new StudentStoreValidator(request);
		boolean valid = validator.validate();
		if (!valid) {
			request.setAttribute("errors", ServerSideRenderingHelper.MapToStringErrors((Map<String, String>)request.getAttribute("errorMessages")));
			doGet(request, response);
			return;
		}
		
		EntityManager manager = HibernateUtil.getEntityManager();
		
		User user = new User();
		user.setUuid(UUID.randomUUID().toString());
		user.setFirstName(request.getParameter("first_name"));
		user.setSurname(request.getParameter("surname"));
		try {
			user.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date_of_birth")));
		} catch (ParseException e) {
			// :(
		}
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		user.setGender(request.getParameter("gender"));
		
		new UserService(manager).saveUser(user);
		
		Student student = new Student();
		student.setUuid(UUID.randomUUID().toString());
		student.setUser(user);
		
		new StudentService(manager).saveStudent(student);
		
		response.sendRedirect("/ENT/signin");
	}

}
