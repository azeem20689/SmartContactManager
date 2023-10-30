package com.smartcontact.service;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@org.springframework.stereotype.Service
public class Service {

	public boolean sendEmail(String to, String from, String subject, String message) {
		boolean flag = false;
		String username = "jazeem20689java";
		String password = "jywn jhtr inui rdxp";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", true);

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message mm = new MimeMessage(session);
			mm.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mm.setFrom(new InternetAddress(from));
			mm.setSubject(subject);
			mm.setText(message);
			Transport.send(mm);
			flag = true;
			return flag;

		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}

	}
}
