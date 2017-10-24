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

/**
 * Servlet implementation class AddWordToCategory
 */
@WebServlet("/AddWordToCategory")
public class AddWordToCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddWordToCategory() {
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
		PrintWriter out = response.getWriter();
		try {
			String host = "127.8.167.130";
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amoool", "root", "root");
			/* Connection con =
			 DriverManager.getConnection("jdbc:mysql://"+host+"/myasapp",
			 "adminTKnvAxx", "6kcWL-WhALLs");*/
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();

			String allColors = "";

			String stmt1 = "select a.categoryId , b.category , b.color from  words a inner join categories b on a.categoryId = b.id where a.word like '"
					+ request.getParameter("selectedWord") + "' and articleId = "+request.getParameter("fileid");
			ResultSet rs = stmt.executeQuery(stmt1);
			int count = 0;
			while (rs.next()) {
				count++;
				allColors = allColors + ",#" + rs.getString("color");
				if (rs.getString("category").equalsIgnoreCase(request.getParameter("selectedCat").toString())) {
					count = -1;
					break;
				}
			}

			if (count == -1) {
				out.print(request.getParameter("selectedWord").toString() + " already in category "
						+ request.getParameter("selectedCat").toString());
			} else {
				String[] id_color = new String[2];
				String stmt3 = "select id, color from categories where category like '"
						+ request.getParameter("selectedCat").toString() + "'";
				rs = stmt.executeQuery(stmt3);
				while (rs.next()) {
					id_color[0] = "" + rs.getInt("id");
					id_color[1] = rs.getString("color");
				}

				String stmt2 = "insert into words (word , categoryId , articleId) values ('"
						+ request.getParameter("selectedWord").toString() + "' ," + id_color[0] +"," +request.getParameter("fileid") +")";
				stmt.executeUpdate(stmt2);
				if (count == 0) {
					out.print(",#" + id_color[1] + ",#" + id_color[1]);
				} else {
					allColors = allColors + ",#" + id_color[1];
					out.print(allColors);
				}

			}
			rs.close();

			con.close();
		} catch (

		Exception e) {
			System.out.println(e);
		}

	}

}
