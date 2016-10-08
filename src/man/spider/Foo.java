package man.spider;

import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Foo
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/Foo" })
public class Foo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;

    /**
     * Default constructor. 
     */
    public Foo() {
        // TODO Auto-generated constructor stub
    	try {
    		Class.forName("org.postgresql.Driver"); 
			conn = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/testdb",
					"teste",
					"123"
					);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		// escreve o texto no cliente
		out.println("<html>");
		out.println("<body>");
		out.println("Primeira porra => " + request.getContextPath());
		out.println("</body>");
		out.println("</html>");
	}
	 */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		// escreve o texto no html
		try {
			Statement stmt = this.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM teste");
			
			out.println("<html>");
			out.println("<body>");
			out.println("<form method=\"POST\" action=\"/servlets-dev-web/Foo\">");
			out.println("<input type=\"text\" name=\"nome\" placeholder=\"nome\">");
			out.println("<input type=\"submit\" value=\"Submit\">");
			out.println("</form>");
			out.println("<table>");
			out.println("<tr><th>ID</th><th>Nome</th></tr>");
			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>");
				out.println(rs.getString("ID"));
				out.println("</td>");
				out.println("<td>");
				out.println(rs.getString("nome"));
				out.println("</td>");
				out.println("</tr>");
			}
			out.println("</table>");
			out.println("</body>");
			out.println("</html>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// add to database
		try {
			PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO teste(nome) VALUES(?)");
			stmt.setString(1, request.getParameter("nome"));
			stmt.execute();
			System.out.println("inserted!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return data
		doGet(request, response);
	}

}
