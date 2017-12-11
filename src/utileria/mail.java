package utileria;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class mail {	
	
	public void sendMail( String to, String from, String subject, String body ) {
	
	
		final String username = "josdurgran@gmail.com";
		final String password = "juanpablo2";

		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtp.host", "smtp.gmail.com");	
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		//props.put("mail.smtp.starttls.enable", "false");
		//props.put("mail.smtp.host", "smtp.gmail.com");
		//props.put("mail.smtp.port", "465");
		//props.put("mail.smtp.ssl.trust", "smtp.gmail.com");



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
			message.setText(body);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
			
		}
	}  

}
