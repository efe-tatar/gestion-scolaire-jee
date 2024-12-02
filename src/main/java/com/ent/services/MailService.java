package com.ent.services;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailService {

    private final String username;
    private final String password;

    public MailService(String username, String password) {
        // On utilise les paramètres passés pour initialiser les champs.
        this.username = username;
        this.password = password;
    }

    public void sendEmail(String from, String to, String subject, String content) throws MessagingException {
        // Configuration des propriétés SMTP
        String host = "smtp.gmail.com";
        String port = "587";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Création de la session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Création du message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(content);

        // Envoi du message
        Transport.send(message);
    }
}