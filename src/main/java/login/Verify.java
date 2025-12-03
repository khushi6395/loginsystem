 package login;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/verify")
public class Verify extends HttpServlet {

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String from="devworkspace803@gmail.com";
        String to=(String) request.getSession().getAttribute("email");
        String subj=" ";
        String message=" ";
        String otp1 = request.getParameter("d1");
        String otp2 = request.getParameter("d2");
        String otp3 = request.getParameter("d3");
        String otp4 = request.getParameter("d4");
        String otp5 = request.getParameter("d5");
        String otp6 = request.getParameter("d6");

        String enteredOtp = otp1 + otp2 + otp3 + otp4 + otp5 + otp6;

               String actualOtp = (String) request.getSession().getAttribute("OTP");

             

               long expiry = (long) request.getSession().getAttribute("otpExpiry");

               if (System.currentTimeMillis() > expiry) {
                   subj="Error!";
                   message="OTP expired";
                   
                   Mail.sendEmail(message, subj, to, from);
                   return;
               }
               

        if (enteredOtp.equals(actualOtp)) {
            subj="Otp verified ";
            message="Congratulations ,Your Otp Verified Succesfully!";
            
     Mail.sendEmail(message, subj, to, from);
            response.sendRedirect("newpass.html");
        } else {
        	
            subj="Error!";
            message="Invalid otp!please try again";
            
     Mail.sendEmail(message, subj, to, from);

        }
               
      
       
    }
}
