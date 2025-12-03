package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/new")
public class NewPassword extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String email = req.getParameter("email");
        String npass = req.getParameter("npass");
        String cpass = req.getParameter("cpass");
      
        String from = "devworkspace803@gmail.com";
        String messag=" ";
        String subj=" ";
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/User","****","*****");
        PreparedStatement ps = con.prepareStatement("SELECT username FROM users WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
             if (rs.next()) {
                
                if (npass == null || cpass == null) {
                    subj="Error!";
                  messag="please Enter new password";
                } else {
                
                    if (!npass.equals(cpass)) {
                      subj="Error!";
                      messag="Password not matched";
                    } else {
                        PreparedStatement ps1 = con.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
                        ps1.setString(1, npass);
                        ps1.setString(2, email);
                        int updated = ps1.executeUpdate();
                            if (updated > 0) {
                       subj="update succesfully";
                           messag="Changed password successfully";
                            out.println("<div style='text-align:center;'><a href='index.html'>Go to Login</a></div>");
                        } else {
                           subj="Failed!";
                           messag="Fail to change password";
                        }
                    }
                }
            }
            Mail.sendEmail(messag,subj,email,from);
             con.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
}
