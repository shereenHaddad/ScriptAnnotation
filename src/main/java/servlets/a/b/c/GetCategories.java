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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GetCategories
 */
@WebServlet("/GetCategories")
public class GetCategories extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetCategories() {
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
		String outp = "";
		String out2 = "";
		String out3="";
		HashSet<String> savedCats = new HashSet<String>();
		int count = 0;
		// connect to database
		try {

			request.setCharacterEncoding("UTF-8");
			Class.forName("com.mysql.jdbc.Driver");
			String host = "127.8.167.130";
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amoool", "root", "root");
			// Connection con =
			// DriverManager.getConnection("jdbc:mysql://"+host+"/myasapp",
			 //"adminTKnvAxx", "6kcWL-WhALLs");
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();

			String stm6 = "show tables";
			ResultSet rs = stmt.executeQuery(stm6);

			while (rs.next()) {
				count++;
			}
			if (count < 4) {

				try {
					stmt.executeUpdate("drop table words");
				} catch (Exception e) {
				}
				try {
					stmt.executeUpdate("drop table categories");
				} catch (Exception e) {
				}
				try {
					stmt.executeUpdate("drop table subcategories");
				} catch (Exception e) {
				}
				try {
					stmt.executeUpdate("drop table contextFiles");
				} catch (Exception e) {
				}
				stmt.executeUpdate(
						"create table categories(id int not null auto_increment , Category varchar (50) not null ,Color varchar(20) not null , name varchar (20) not null, primary Key (id) );");
				stmt.executeUpdate(
						"create table words(id int not null auto_increment ,articleId int,  word varchar (100) not null , categoryId int not null , primary key (id) , FOREIGN KEY (categoryId) REFERENCES categories(id) );");
				stmt.executeUpdate(
						" create table subcategories (id int not null auto_increment , mainCategory int , subCategory varchar(50) not null ,primary key (id) , FOREIGN KEY (mainCategory) REFERENCES categories(id))");
				stmt.executeUpdate(
						"create table contextFiles (id int not null auto_increment, articleTitle varchar(100) ,body TEXT , name varchar (20) not null ,primary key (id) )");

			}

			String stm2 = "select category, color from categories where name like '" + session.getAttribute("userName")
					+ "' order by category asc ";

			rs = stmt.executeQuery(stm2);

			
			while (rs.next()) {
				savedCats.add(rs.getString(1));
				outp = outp + rs.getString(1) + ":" + rs.getString(2) + ",";
			}

			if (outp.length() > 0) {
				outp = outp.substring(0, outp.length() - 1);
			}
			outp = outp + "^";
			count = 0;

			String stm3 = "select id , articleTitle from contextFiles where name like '"
					+ session.getAttribute("userName") + "' order by articleTitle asc ";
			System.out.println(stm3);
			
			
			rs = stmt.executeQuery(stm3);
			while (rs.next()) {
System.out.println(rs.getString(1));
				out2 = out2 + rs.getInt(1) + ":" + rs.getString(2) + ",";
				count++;
			}

			if (out2.length() > 1) {
				out2 = out2.substring(0, out2.length() - 1);
			}
			
			
			////////////////////////////////////////
			
			out2 = out2 + "^";
			count = 0;

			String stm4 = "select a.subCategory , b.category from subcategories a , categories b where a.mainCategory=b.id and b.name like '"+session.getAttribute("userName")+"'";
			
			System.out.println(stm4);
			rs = stmt.executeQuery(stm4);
			while (rs.next()) {

				out3 = out3 + rs.getString(1) + ":" + rs.getString(2) + ",";
				count++;
			}

			if (out3.length() > 1) {
				out3 = out3.substring(0, out3.length() - 1);
			}

			///////////////////////////////////////

			rs.close();
			con.close();
		} catch (

		Exception e) {
			System.out.println(e);
		}

		session.setAttribute("savedCats", savedCats);
		PrintWriter out = response.getWriter();
		System.out.println(outp + out2+out3);
		out.print(outp + out2+out3);

	}

}
