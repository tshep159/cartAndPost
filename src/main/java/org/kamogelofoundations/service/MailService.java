package org.kamogelofoundations.service;


import org.kamogelofoundations.dto.CartOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.url}")
    private String appUrl;

    @Value("${app.email.support}")
    private String supportEmail;

  @Autowired 
   private static MailSender mailSender;
    
    public static void sendMail(String to, String subject, String text) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(to);
            email.setSubject(subject);
            email.setFrom("support@xx.com");
            email.setText(text);
            mailSender.send(email);
            System.out.println("SENT EMAIL: TO=" + to + "|SUBJECT:" + subject + "|TEXT:" + text);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
   public static void sendInvoice(CartOrder o) {
	   
        String url = "";
        String to="tshepho.maema@gmail.com";
        String subject = "Invoice";
        String text = "Your invoice is as follows: "+ o.getProductName() + o.getProductPrice()+ o.getCartGrandTotal();
        sendMail(to, subject, text);
    }
   
   
   public void sendResetPassword(String to, String token) throws Exception {
	   System.out.println("Attempting to asend email");
	   String url = appUrl + "/user/reset-password-change?token=" + token;
    
	   String subject = "Reset Password";
       String text = "Please click the following link to reset your password: " + url;
       System.out.println("sending email");
       System.out.println(text);
       System.out.println(subject);
       System.out.println("Token ====================="+token);
       sendMail(to, subject, text);
   }
//  */  
//    public void sendNewRegistration(String to, String token) {
//        String url = appUrl + "/user/activate?activation=" + token;
//        String subject = "Please activate your account";
//        String text = "Please click the following link to activate your account: " + url;
//        sendMail(to, subject, text);
//    }
//    
//    public void sendNewActivationRequest(String to, String token) {
//        sendNewRegistration(to, token);
//    }
//    
//    public void sendErrorEmail(Exception e, HttpServletRequest req, User user) {
//        String subject = "Application Error: " + req.getRequestURL();
//        String text = "An error occured in your application: " + e + "\r\nFor User:  " + user.getEmail();
//        sendMail(supportEmail, subject, text);
//    }
}
