package com.example.demo.service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


public class MailService {

    public void sendMail(String recipient){
        System.out.println("prepare msg");
        Properties prop = new Properties();

        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        String myAccount = "degroennekokke@gmail.com";
        String myPassword = "groenkok1";

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, myPassword);
            }
        });

        Message message = prepareMessage(session, myAccount, recipient);
        try {
            Transport.send(message);
            System.out.println("message sent...");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private static Message prepareMessage(Session session, String myAccount, String recipient) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("De Grønne Kokke - Vi har modtaget din information");
            message.setText("Hej,\nVi har modtaget din kontaktinformation og kontakter dig hurtigst muligt!");
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }



}
