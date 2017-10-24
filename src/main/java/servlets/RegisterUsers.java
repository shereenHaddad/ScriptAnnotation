package com.hello.world.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hello.world.beans.User;
import com.hello.world.DAO.DAOFactory;
import com.hello.world.DAO.UserDAO;

/**
 * Servlet implementation class RegisterUsers
 */
@WebServlet("/RegisterUsers")
public class RegisterUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUsers() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean status = true;
		User user = new User();
		String msg = "";
		if (request.getParameter("firstName") != null && request.getParameter("firstName").trim().length() > 0) {

			String fName = request.getParameter("firstName");

			user.setFirstName(fName);

			if (fName.length() > 35) {
				msg = "First Name Can be Up to 35 charachters";
				status = false;
			} else {
				
				String regex = "^[a-zA-Z]+$";
				 
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(fName);
				if(!matcher.matches()){
					msg = msg + " invalid First Name Format";
					status = false;
				}
			}
		} else {
			msg = "Invalid First Name";
			status = false;
		}

		if (request.getParameter("lastName") != null && request.getParameter("lastName").trim().length() > 0) {
			user.setLastName(request.getParameter("lastName"));

			if (request.getParameter("lastName").length() > 35) {
				msg = "Last Name Can be Up to 35 charachters";
				status = false;
			}

			String lName = request.getParameter("lastName");

			user.setLastName(lName);

			if (lName.length() > 35) {
				msg = "Last Name Can be Up to 35 charachters";
				status = false;
			} else {
				
				String regex = "^[a-zA-Z]+$";
				 
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(lName);
				if(!matcher.matches()){
					msg = msg + " invalid Last Name Format";
					status = false;
				}
			}

		} else {
			msg = msg + " Invalid Last Name";
			status = false;
		}

		if (request.getParameter("address1") != null && request.getParameter("address1").trim().length() > 0) {
			user.setAddress1(request.getParameter("address1"));
		} else {
			msg = msg + " Invalid Address1";
			status = false;
		}

		if (request.getParameter("address2") != null && request.getParameter("address2").trim().length() > 0) {
			user.setAddress2(request.getParameter("address2"));
		}

		if (request.getParameter("city") != null && request.getParameter("city").trim().length() > 0) {
			user.setCity(request.getParameter("city"));
		} else {
			msg = msg + " city";
			status = false;
		}

		if (request.getParameter("zipCode") != null && request.getParameter("zipCode").trim().length() > 0) {
			user.setZipCode(request.getParameter("zipCode"));

			String zip = request.getParameter("zipCode");
			zip = zip.replaceAll(" ", "");
			zip = zip.replaceAll("-", "");
			
			if (!(zip.length() == 5 || zip.length() == 9)) {
				msg = msg + " ZipCode length must be 5 or 9 digits";
				status = false;
			}else {
				
				String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
				 
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(request.getParameter("zipCode"));
				if(!matcher.matches()){
					msg = msg + " invalid ZipCode Format";
					status = false;
				}
			}
		} else {
			msg = msg + " Invalid ZipCode";
			status = false;
		}

		if (request.getParameter("state") != null && request.getParameter("state").trim().length() > 0) {
			user.setState(request.getParameter("state"));
		} else {
			msg = msg + " Invalid State";
			status = false;
		}

		if (request.getParameter("country") != null && request.getParameter("country").trim().length() > 0) {
			user.setCountry(request.getParameter("country"));

			if (!request.getParameter("country").equalsIgnoreCase("United States")) {
				msg = msg + " Registration is only valid in United States";

			}
		} else {
			msg = msg + " Invalid Country";
			status = false;
		}

		user.setMsg(msg);
		request.setAttribute("user", user);

		if (status) {
			DAOFactory db = DAOFactory.getInstance();
			if (db == null) {
				user.setMsg("failed to Connect to DAO Factory !!!");
				getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
			} else {

				user.setMsg("DAOFactory successfully obtained: " + db);
				UserDAO userDAO = db.getUserDAO();
				user.setMsg("UserDAO successfully obtained: " + userDAO);

				/*
				 * userDAO.createTable(); user.setMsg("table created: " );
				 * getServletContext().getRequestDispatcher("/confirm.jsp").
				 * forward(request, response);
				 */
				if (userDAO.userExists(user.getFirstName(), user.getLastName(), user.getZipCode())) {
					user.setMsg("User Already Exists !!!");
					getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
				}

				else {
					user.setRegDate(new Date());
					userDAO.create(user);
					user.setMsg("Your Registration was Successful .. You can review your profile here ");
					getServletContext().getRequestDispatcher("/confirm.jsp").forward(request, response);
				}
			}
		} else {
			getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
		}
	}

}
