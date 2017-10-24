package a.b.c;

import java.io.IOException;
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
 * Servlet implementation class HelloWorld
 */
@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorld() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String host = System.getenv("$OPENSHIFT_MYSQL_DB_HOST");
		response.getWriter().append("Served at: ").append(request.getContextPath() +"  "+ host);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		try {
			String host = "127.8.167.130";
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amoool", "root", "root");
			//Connection con = DriverManager.getConnection("jdbc:mysql://"
			/// + host + "/myasapp", "adminTKnvAxx",
			 //"6kcWL-WhALLs");
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			HttpSession session =request.getSession();
			ResultSet rs= stmt.executeQuery("select Id from categories where category like '"+request.getParameter("catName").toLowerCase()+"' and name like '"+session.getAttribute("userName")+"'");
			int cId=0;
			while(rs.next()){
				cId  =rs.getInt(1);
			}
			
			String stm2 = "insert into subcategories (mainCategory , subCategory) values ('"
					+ cId + "','" + request.getParameter("t1"+request.getParameter("catName")) + "' )";
			stmt.executeUpdate(stm2);

			con.close();
		} catch (

		Exception e) {
			System.out.println(e);
		}
	}

}
