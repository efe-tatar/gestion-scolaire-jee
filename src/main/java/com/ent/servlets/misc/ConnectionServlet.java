package com.ent.servlets.misc;

import java.io.IOException;

import com.ent.entities.User;
import com.ent.services.UserService;
import com.ent.utils.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ConnectionServlet
 */
@WebServlet("/signin")
public class ConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectionServlet() {
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

		request.getRequestDispatcher("/views/misc/signin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
        String password = request.getParameter("password");

        EntityManager em = HibernateUtil.getEntityManager();
        UserService userService = new UserService(em);

        try {
            // Recherche de l'utilisateur dans la base
            User user = userService.findUserByEmailAndPassword(email, password);

            if (user != null) {
                // Déterminer le rôle de l'utilisateur
                String role = UserService.getUserRole(em, user);

                // Ajouter les informations utilisateur à la session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", role);

                // Rediriger en fonction du rôle
                switch (role) {
                    case "admin":
                        response.sendRedirect("/ENT/adminportal");
                        break;
                    case "student":
                        response.sendRedirect("/ENT/studentportal");
                        break;
                    case "teacher":
                        response.sendRedirect("/ENT/teacherportal");
                        break;
                    default:
                        response.sendRedirect("/ENT/signout");
                        break;
                }
            } else {
                request.setAttribute("errorMessage", "Email ou mot de passe incorrect.");
                request.getRequestDispatcher("/views/misc/signin.jsp").forward(request, response);
            }
        } finally {
            em.close();
        }
		
	}

}
