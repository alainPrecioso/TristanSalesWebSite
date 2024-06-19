package com.aprec.webapp.user.registration.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class EmailService implements EmailSender, EmailReader {

	private static final Logger log = LoggerFactory.getLogger(EmailService.class);
	
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
			log.error("failed to send email", e);
			throw new IllegalStateException("failed to send email");
		}
	}



	@Override
	public String readFileToString(String path) {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(path);
		return asString(resource);
	}

	private String asString(Resource resource) {
		try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
