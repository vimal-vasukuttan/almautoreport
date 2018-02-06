package gen.rep.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

/*
 * Service class for mailing operations
 * @author Vimal V
 */
@Service
public class MailingService {
	static final String username = "sender@gmail.com";
	static final String password = "sender_password";
	
	public static void sendMail(String defects) {
	Properties pp = new Properties();
	pp.setProperty("mail.smtp.auth", "true");
	pp.put("mail.smtp.auth", "true");
	pp.put("mail.smtp.starttls.enable", "true");
	pp.put("mail.smtp.host", "smtp.gmail.com");
	pp.put("mail.smtp.port", "587");
	
	// check the authentication
	Session session = Session.getInstance(pp, new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	});

	try {

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("sender@gmail.com"));

		// recipients email address
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("receiver@gmail.com"));

		// add the Subject of email
		message.setSubject("Defects This Week");

		// message body
		message.setText(defects);

		Transport.send(message);


	} catch (MessagingException e) {
		throw new RuntimeException(e);

	}
	}
}
