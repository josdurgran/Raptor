package core;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SimpleSSLMail {

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final int SMTP_HOST_PORT = 465;
	private static final String SMTP_AUTH_USER = "josdurgran@gmail.com";
	private static final String SMTP_AUTH_PWD = "juanpablo2";

	public void test() throws Exception {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.user", "chuidiang@gmail.com");
		props.setProperty("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(true);

		BodyPart texto = new MimeBodyPart();
		texto.setText("Texto del mensaje");

		//BodyPart adjunto = new MimeBodyPart();
		//adjunto.setDataHandler(new DataHandler(new FileDataSource(
			//	"d:/futbol.gif")));
		//adjunto.setFileName("futbol.gif");

		MimeMultipart multiParte = new MimeMultipart();

		multiParte.addBodyPart(texto);
		//multiParte.addBodyPart(adjunto);

		MimeMessage message = new MimeMessage(session);

		// Se rellena el From
		message.setFrom(new InternetAddress("josdurgran@hotmail.com"));

		// Se rellenan los destinatarios
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				SMTP_AUTH_USER));

		// Se rellena el subject
		message.setSubject("Hola");

		// Se mete el texto y la foto adjunta.
		message.setContent(multiParte);

		Transport t = session.getTransport("smtp");
		t.connect(SMTP_AUTH_USER, SMTP_AUTH_PWD);
		t.sendMessage(message, message.getAllRecipients());
		t.close();

	}

}
