package login;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Mail {
    public static void main(String[] args) {
        System.out.println("mail java file...");
    }

    public static void sendEmail(String message, String subject, String to, String from) {
     String host = "smtp.gmail.com";
   Properties properties = new Properties();
   // Gmail SMTP settings
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
         Session session = Session.getInstance(properties, new Authenticator() {
             @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // Use APP PASSWORD here
                return new PasswordAuthentication("devworkspace803@gmail.com",
                                                  "**** **** ****");
            }
        });

        session.setDebug(true);
        try {
            MimeMessage m = new MimeMessage(session);
             m.setFrom(new InternetAddress(from));
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(subject);
            m.setText(message);
             Transport.send(m);
            System.out.println("Sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
