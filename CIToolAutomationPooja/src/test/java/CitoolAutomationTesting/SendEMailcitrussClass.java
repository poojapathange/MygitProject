
package CitoolAutomationTesting;

import java.io.File;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEMailcitrussClass {
	 
   final String username = "ctvbuild.test@ctv-it.net"; //change to your Gmail username
   final String password = "NewComp123@"; //change to your Gmail password
   final String from = "ctvbuild.test@ctv-it.net"; //change to from email address
   final String to = "pooja.patange@citruss.com"; //change to to email address
   final String msg = "OOPS CITool Automation Testing Failed";
   final String subject = "OOPS CITool Automation Testing Failed";
   public void emailsend(String Filename) 
   {
    
   	Properties props = new Properties();
   	props.put("mail.smtp.auth", true);
   	props.put("mail.smtp.starttls.enable", true);
   	props.put("mail.smtp.host", "mail.gandi.net");
   	props.put("mail.smtp.port", "25");

   	Session session = Session.getInstance(props,
           new javax.mail.Authenticator() {
               protected PasswordAuthentication getPasswordAuthentication() {
                   return new PasswordAuthentication(username, password);
               }
           });

   try {

       Message message = new MimeMessage(session);
       message.setFrom(new InternetAddress(from));
       message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(to));
       message.setSubject(subject);
      message.setText(msg);
  
       //Code for attachment:
       
       MimeBodyPart messageBodyPart = new MimeBodyPart();
       Multipart multipart = new MimeMultipart();
       messageBodyPart = new MimeBodyPart();
       //String file = "./FailedTestsScreenshots/";
       String fileName = Filename;
       
       
       
       try {
       	File att = new File(fileName);
       	messageBodyPart.attachFile(att);
       	
			} 
       catch (Exception e) 
       {
			e.printStackTrace();
		}
       
       multipart.addBodyPart(messageBodyPart);

       message.setContent(multipart);

       System.out.println("Sending");

       Transport.send(message);

       System.out.println("Done - Email sent sucessfully");

   } catch (MessagingException e) {
       e.printStackTrace();
   }
}
}
