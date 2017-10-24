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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int count = 0 ;
		HttpSession session = request.getSession();
		try {
			
			request.setCharacterEncoding("UTF-8");
			Class.forName("com.mysql.jdbc.Driver");
			   String host = "127.8.167.130";
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amoool", "root", "root");
		    //Connection con = DriverManager.getConnection("jdbc:mysql://"+host+"/myasapp", "adminTKnvAxx", "6kcWL-WhALLs");
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			int found=0;
			ResultSet rs = stmt.executeQuery("show tables");
			while(rs.next()){
				if(rs.getString(1).equalsIgnoreCase("users")){
					found=1;
				}
			}
			
			if(found==0){
				
				 stmt.executeUpdate("create table users (name varchar (20) not null ,password varchar (20) not null )");
			}
			
			String stm6 ="select * from users where name like '"+request.getParameter("userName").toString()+"' and password like '"+request.getParameter("password").toString()+"'";
			 rs = stmt.executeQuery(stm6);
			while(rs.next()){
				count++;
				session.setAttribute("userName", request.getParameter("userName").toString());
			}
			rs.close();
			con.close();
		} catch (

		Exception e) {
			System.out.println(e);
		}
		
		
		
		PrintWriter out = response.getWriter();
		if(count==0){out.print("false");}
		else out.print("true");
	}

}
