package a.b.c;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddCategories
 */
@WebServlet("/AddCategories")
public class AddCategories extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddCategories() {
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

		HttpSession session = request.getSession();
		HashSet<String> savedCats = (HashSet<String>) session.getAttribute("savedCats");
		PrintWriter out = response.getWriter();
		if (savedCats.contains(request.getParameter("newCat").toString())) {
			out.print("Failed : Category " + request.getParameter("newCat").toString() + " already Exist");
		} else {

			// connect to database
			try {
				String host = "127.8.167.130";
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amoool", "root", "root");
				/*Connection con = DriverManager.getConnection("jdbc:mysql://"
				 + host + "/myasapp", "adminTKnvAxx",
				 "6kcWL-WhALLs");*/
				// here sonoo is database name, root is username and password
				Statement stmt = con.createStatement();
				String stm2 = "insert into categories (category , color ,name) values ('"
						+ request.getParameter("newCat").toLowerCase() + "','" + request.getParameter("colorMe") + "' , '" +session.getAttribute("userName") +"')";
				stmt.executeUpdate(stm2);

				con.close();
			} catch (

			Exception e) {
				System.out.println(e);
			}

			//
			out.println("<table id='t"+request.getParameter("newCat").toLowerCase()+"'><tr><td width=70%><span  style='color:#"+request.getParameter("colorMe") +";'  width=100% onclick='getCat(&#39;"+request.getParameter("newCat").toLowerCase()+"&#39;);'>"+request.getParameter("newCat").toLowerCase()+"</span></td><td onclick=addSub('"+request.getParameter("newCat").toLowerCase()+"');>Sub</td><td onclick=removeCat('"+request.getParameter("newCat").toLowerCase()+"');> Del</td></tr></table>");

		}
	}
}
