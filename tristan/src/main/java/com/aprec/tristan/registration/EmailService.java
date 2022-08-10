package com.aprec.tristan.registration;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailSender {

	private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	
	@Autowired
	private final JavaMailSender mailSender;
	
	@Value("${from.email.address}")
    private String fromEmailAddress;
	
	
	public EmailService(JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}



	@Async
	@Override
	public void send(String to, String email) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject("Confirmation");
			helper.setFrom(fromEmailAddress);
			mailSender.send(mimeMessage);
		} catch(MessagingException e) {
			LOGGER.error("failed to send email", e);
			throw new IllegalStateException("failed to send email");
		}
	}

}
