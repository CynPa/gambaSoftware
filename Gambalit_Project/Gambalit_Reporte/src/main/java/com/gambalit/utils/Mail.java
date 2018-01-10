package com.gambalit.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
private final Properties properties = new Properties();
	
	private String password;
 
	private Session session;
 
	private void init() {
 
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port",25);
		//properties.put("mail.smtp.mail.sender","emisor@gmail.com");
		properties.put("mail.from","elvistorrest98@gmail.com");
		properties.put("mail.user", "elvistorrest98@gmail.com");
		properties.put("mail.password", "cynthiatorres31");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
 
		session = Session.getDefaultInstance(properties);
	}
	
	 public static void main(String[] args) {
		Mail s=new Mail();
		s.sendEmail();
	}
	public void sendEmail(){

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("elvistorrest98@gmail.com","cynthiatorres31");
				}
			});

		try {
			String certificatesTrustStorePath = "C:/Program Files/Java/jre1.8.0_151/lib/security/cacerts";
			System.setProperty("javax.net.ssl.trustStore", certificatesTrustStorePath);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("elvistorrest98@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("elvistorrest98@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler," +
					"\n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		/*final String username = "elvistorrest98@gmail.com";
		final String password = "cynthiatorres31";

		Properties props = new Properties();
		//props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.debug", "true");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("elvistorrest98@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("elvis.torrest@hotmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			System.out.println(""+e.toString());
		}
		*/
	}
}
