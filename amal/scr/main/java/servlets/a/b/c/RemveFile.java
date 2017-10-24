package a.b.c;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemveFile
 */
@WebServlet("/RemveFile")
public class RemveFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemveFile() {
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

		try {
			String host = "127.8.167.130";
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amoool", "root", "root");
			 //Connection con = DriverManager.getConnection("jdbc:mysql://" +
			// host + "/myasapp", "adminTKnvAxx", "6kcWL-WhALLs");
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();

			String stm2 = "";
			if (request.getParameter("deleteType").toString().equalsIgnoreCase("c")) {
				stm2 = "delete from words where categoryId in (select Id from categories where category like '"
						+ request.getParameter("cid") + "' )";
				stmt.executeUpdate(stm2);
				stm2 = "delete from categories where category like '" + request.getParameter("cid") + "'";
				stmt.executeUpdate(stm2);
			}else if(request.getParameter("deleteType").toString().equalsIgnoreCase("s")){
				
				stm2 = "delete from subcategories where subCategory like '" + request.getParameter("cid") + "'";
				stmt.executeUpdate(stm2);
			
			}
			else {

				stm2 = "delete from contextFiles where id = " + request.getParameter("fid");
				stmt.executeUpdate(stm2);
			}

			con.close();
		} catch (

		Exception e) {
			System.out.println(e);
		}
	}

}
