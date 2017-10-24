package a.b.c;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GetArticle
 */
@WebServlet("/GetArticle")
public class GetArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetArticle() {
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
		// connect to database
		

		HttpSession session = request.getSession();
		
		String out = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String host = "127.8.167.130";
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amoool", "root", "root");
			/*Connection con =
			 DriverManager.getConnection("jdbc:mysql://"+host+"/myasapp",
			 "adminTKnvAxx", "6kcWL-WhALLs");*/
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();

			String stm6 = "select body from contextFiles where id= " + request.getParameter("fid").toString()+" and name like '"+ session.getAttribute("userName")+"'";
			ResultSet rs = stmt.executeQuery(stm6);

			while (rs.next()) {
				out = rs.getString(1);
			}
			rs.close();
		} catch (

		Exception e) {
			System.out.println(e);
		}

		PrintWriter outp = response.getWriter();
		outp.print(out);

	}

}
