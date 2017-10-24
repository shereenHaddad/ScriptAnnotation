package com.hello.world.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hello.world.DAO.DAOFactory;
import com.hello.world.DAO.UserDAO;

/**
 * Servlet implementation class EmptyHWTable
 */
@WebServlet("/EmptyHWTable")
public class EmptyHWTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmptyHWTable() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAOFactory db = DAOFactory.getInstance();
		PrintWriter out = response.getWriter();
		if (db == null) {
			out.print("failed to connect");
			getServletContext().getRequestDispatcher("/userReports.jsp").forward(request, response);
		} else {

			UserDAO userDAO = db.getUserDAO();

			userDAO.EmptyTable();
			
			out.print("Table is Empty");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
