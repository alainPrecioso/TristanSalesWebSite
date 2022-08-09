package com.aprec.tristan.registration;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender {

	private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	
//	private final JavaMailSender mailSender;
//	
//	
//	public EmailService(JavaMailSender mailSender) {
//		super();
//		this.mailSender = mailSender;
//	}




	@Override
	public void send(String to, String email) {
//		try {
//			MimeMessage mimeMessage = mailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
//			helper.setText(email, true);
//			helper.setTo(to);
//			helper.setSubject("Confirmation");
//			helper.setFrom("confirmation@tristan.fr");
//		} catch(MessagingException e) {
//			LOGGER.error("failed to send email", e);
//			throw new IllegalStateException("failed to send email");
//		}
	}

}
