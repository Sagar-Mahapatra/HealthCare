package in.nareshit.raghu.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MyMailUtil {
	@Autowired
	private JavaMailSender mailSender;

	public boolean send(String to[], String cc[], String bcc[], String subject, String text, Resource files[])
			throws MessagingException {
		boolean sent = false;

		// 1. create one empty message object
		MimeMessage message = mailSender.createMimeMessage();

		// 2. fill details (message, attachmentExist?)
		MimeMessageHelper helper = new MimeMessageHelper(message, files != null && files.length > 0);

		helper.setTo(to);

		if (cc != null)
			helper.setCc(cc);
		if (bcc != null)
			helper.setBcc(bcc);

		helper.setSubject(subject);
		helper.setText(text);

		if (files != null) {
			for (Resource rob : files) {
				helper.addAttachment(rob.getFilename(), rob);
			}
		}

		// 3. send email
		mailSender.send(message);

		sent = true;
		return sent;
	}

	/**
	 * overloaded methods
	 * 
	 * @throws MessagingException
	 */
	public boolean send(String to, String subject, String text, Resource file) throws MessagingException {
		return send(new String[] { to }, null, null, subject, text, file != null ? new Resource[] { file } : null);
	}

	public boolean send(String to, String subject, String text) throws MessagingException {
		return send(to, subject, text, null);
	}
}