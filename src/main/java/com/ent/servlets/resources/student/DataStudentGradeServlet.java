package com.ent.servlets.resources.student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

import com.ent.resources.student.GradeControllerStudent;
import com.ent.utils.ServerSideRenderingHelper;

/**
 * Servlet implementation class DataStudentGradeServlet
 */
@WebServlet("/data/student/grades")
@MultipartConfig
public class DataStudentGradeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataStudentGradeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession(false);
		
		if(session == null || session.getAttribute("user") == null || session.getAttribute("role") == null || !session.getAttribute("role").equals("student")) {
			String jsonFormattedResponse = ServerSideRenderingHelper.getJsonFormattedError("403");
			response.getWriter().write(jsonFormattedResponse);
			return;
		}
		
		session.setAttribute("user", session.getAttribute("user"));
		
		GradeControllerStudent.index(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
