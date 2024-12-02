package com.ent.servlets.resources.teacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.ent.resources.teacher.GroupControllerTeacher;

/**
 * Servlet implementation class DataTeacherGroupServlet
 */
@WebServlet("/data/teacher/groups")
@MultipartConfig
public class DataTeacherGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataTeacherGroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// check if teacher
		
		// index
		if(request.getParameter("id") == null) {
			GroupControllerTeacher.index(request, response);
			return;
		}
		
		if(request.getParameter("type") != null && request.getParameter("type").equals("edit")) {
			GroupControllerTeacher.edit(request, response);
			return;
		}
		
		GroupControllerTeacher.show(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// check if teacher
		
		GroupControllerTeacher.update(request, response);
	}

}
