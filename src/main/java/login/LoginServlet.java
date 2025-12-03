package login;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;
 @WebServlet("/log")
public class  LoginServlet extends HttpServlet  {  
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		String eid=req.getParameter("email");
		String pas=req.getParameter("password");
		   String from = "devworkspace803@gmail.com";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/User","****","*****");
		    PreparedStatement ps=con.prepareStatement("Select username from users where email=? AND password=?");
			ps.setString(1,eid);
			ps.setString(2, pas);
			ResultSet rs=ps.executeQuery();
			if (rs.next()) {
		        String name = rs.getString("username");
		        out.println("<h3>Welcome, " + name + "!</h3>");
		        String messag="Welcome Again!  "+" "+name;
		        Mail.sendEmail(messag,"Welcome note",eid,from);
		        
		    } else {
		        out.println("<h3>Invalid Email ID or Password.</h3>");}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
}
