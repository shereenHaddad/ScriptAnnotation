package com.hello.world.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hello.world.DAO.DAOFactory;
import com.hello.world.DAO.UserDAO;

/**
 * Servlet implementation class GetRegisteredUsers
 */
@WebServlet("/GetRegisteredUsers")
public class GetRegisteredUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetRegisteredUsers() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DAOFactory db = DAOFactory.getInstance();
		PrintWriter out = response.getWriter();
		if (db == null) {
			out.print("failed to connect");
			getServletContext().getRequestDispatcher("/userReports.jsp").forward(request, response);
		} else {

			UserDAO userDAO = db.getUserDAO();

			List users = userDAO.list();
			request.setAttribute("users", users);
			//out.print(users);
			getServletContext().getRequestDispatcher("/userReport.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
