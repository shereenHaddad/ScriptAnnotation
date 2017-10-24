package a.b.c;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ColorMe
 */
@WebServlet("/ColorMe")
public class ColorMe extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ColorMe() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HashMap<String, String> abc = new HashMap<String, String>();
		response.setContentType("text/plain; charset=utf-8");

		// connect to database
		try {
			String host = "127.8.167.130";
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amoool", "root", "root");
			/* Connection con =
			 DriverManager.getConnection("jdbc:mysql://"+host+"/myasapp",
			 "adminTKnvAxx", "6kcWL-WhALLs");*/
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();

			String stm1 = "select distinct word , color  from words  left outer join categories on words.categoryId = categories.id where articleId = "
					+ request.getParameter("fid");
			System.out.println(stm1);
			ResultSet rs = stmt.executeQuery(stm1);

			String last = "";
			String colors = "";
			while (rs.next())

			{

				if (rs.getString(1).equalsIgnoreCase(last)) {

					colors = colors + ",#" + rs.getString(2) + "";
					System.out.println(rs.getString(1) + " -1- " + colors.length() + "  " + colors);
				} else {

					if (last.length() > 0) {

						if (colors.length() == 8) {
							colors = colors + colors;
						}
						abc.put(last, colors);
						System.out.println(rs.getString(1) + " -2-" + colors.length() + "  " + colors);
					}

					last = rs.getString(1);
					colors = ",#" + rs.getString(2) + "";
				}

			}

			// System.out.println(rs.getString(1) + " " + rs.getString(2));

			if (colors.length() == 8) {
				colors = colors + colors;
			}

			System.out.println(last + " -3- " + colors.length() + "  " + colors);
			abc.put(last, colors);

			rs.close();
			con.close();
		} catch (

		Exception e) {
			System.out.println(e);
		}

		//
		System.out.println(abc);
		String c = "";
		for (String s : abc.keySet()) {
			c = c + s + "=" + abc.get(s) + ";";

		}
		if (c.length() > 0)
			c = c.substring(0, c.length() - 1);
		PrintWriter out = response.getWriter();
		out.print( c);

	}

}
