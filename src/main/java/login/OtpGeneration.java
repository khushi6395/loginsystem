package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/gen")
public class OtpGeneration extends HttpServlet {

    // OTP GENERATOR METHOD
    public static String generateOTP(int length) {
        SecureRandom random = new SecureRandom();
        String digits = "0123456789";
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            otp.append(digits.charAt(random.nextInt(digits.length())));
        }

        return otp.toString();
    }
    
    @Override
     protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
   res.setContentType("text/plain");
        PrintWriter out = res.getWriter();
        String from="devworkspace803";
        String to=req.getParameter("email");
        String subj="Otp generated";
        String message=" ";
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		
    		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/User","****","*****");
                PreparedStatement ps = con.prepareStatement("SELECT username FROM users WHERE email = ?");
                ps.setString(1, to);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
              String otp = generateOTP(6);
                  HttpSession session =req.getSession();
                  session.setAttribute("OTP", otp);
                   session.setAttribute("email", to);
                   
                   session.setAttribute("otpExpiry", System.currentTimeMillis() + 60000); 
                  message="your otp is "+ " "+otp+" ."+"it is valid for 60 sec";
                  Mail.sendEmail(message, subj, to, from);
                  res.sendRedirect("otp.html");
                }
                else {
                	subj="Error! ";
                	message="User doesn't exist";
                	Mail.sendEmail(message, subj, to, from);
                }
     
    }
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}

 
  
    }
}

