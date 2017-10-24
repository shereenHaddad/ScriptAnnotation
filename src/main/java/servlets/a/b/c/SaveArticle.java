package a.b.c;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SaveArticle
 */
@WebServlet("/SaveArticle")
public class SaveArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveArticle() {
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

		int i = 0;
		System.out.println(request.getParameter("artTitle").toString());
		HttpSession session = request.getSession();
		try {

			String unicode = "?useUnicode=yes&characterEncoding=UTF-8";

			String host = "127.8.167.130";
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amoool" + unicode, "root",
					"root");
			// Connection con = DriverManager.getConnection("jdbc:mysql://"+host+"/myasapp",
			// "adminTKnvAxx", "6kcWL-WhALLs");
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();

			String message = request.getParameter("data").toString().replaceAll("\n", "<br>");
			System.out.println(message);
			// String message = request.getParameter("data").toString().replaceAll("\n",
			// "<br>");

			String delim = " \n\r\t;"; // insert here all delimitators
			StringTokenizer st = new StringTokenizer(message, delim);
			String wordSpans = "";
			int c = 0;

			while (st.hasMoreTokens()) {

				wordSpans = wordSpans + "<span id=w" + c + "  onClick=colorSpan(" + c + ");>" + st.nextToken()
						+ "</span>&#32;";

				c++;
			}

			// String value = new
			// String(request.getParameter("artTitle").getBytes("ISO-8859-1"), "UTF-8");

			String stm2 = "insert into contextFiles (articleTitle , body , name) values ('"
					+ request.getParameter("artTitle") + "','" + wordSpans + "' , '"
					+ session.getAttribute("userName").toString() + "')";

			// System.out.println(value);
			stmt.executeUpdate(stm2, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {

				i = rs.getInt(1);
				break;
			}
			rs.close();
			con.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/annotate.jsp").forward(request, response);
		//PrintWriter out = response.getWriter();
		//out.print(i + ":" + request.getParameter("artTitle"));

	}

}
