package login;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.*;

  
@WebServlet("/neww")
public class  NewUser extends HttpServlet  {  
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		String eid=req.getParameter("email");
		String pas=req.getParameter("password");
		String cpas=req.getParameter("confirm");
		String uname=req.getParameter("name");
		
	        String from = "devworkspace803@gmail.com";
	        String messag=" ";
	        String subj=" ";
	        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/User","****","*****");
			PreparedStatement ps=con.prepareStatement("SELECT email FROM users WHERE email = ?");
			ps.setString(1,eid);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
		
				subj="Error!";
			    messag="You are already have an account";
			}
          else { 
			   if(pas==cpas)
			   {
				   subj="Error";
				   messag="Recheck password";
			   }
			   else {
				   
				      PreparedStatement ps1=con.prepareStatement("INSERT INTO users (email,username,password)VALUES(?,?,?)");
			           ps1.setString(1,eid);
			           ps1.setString(2,uname);
			           ps1.setString(3,cpas);
			          
			           int rows = ps1.executeUpdate();
			        
			           if(rows>0)
			           {
			        	  subj="Succesfully Registered";
			        	  messag=" Congratulations "+" " +uname +" Welcome in Intellihire!";
			        }
		   }
		}
		  Mail.sendEmail(messag,subj,eid,from);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
}
