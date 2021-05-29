package com.example.demo.service;

import com.example.demo.model.Mail;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


public class MailService {

    public void sendMail(Mail mail){
        System.out.println("prepare msg");
        Properties prop = new Properties();

        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");

        String myAccount = "hjemtshirt@gmail.com";
        String myPassword = "123emilh";

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccount, myPassword);
            }
        });

        Message message = prepareMessage(session, myAccount, mail);
        try {
            Transport.send(message);
            System.out.println("message sent...");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private static Message prepareMessage(Session session, String myAccount, Mail mail) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(myAccount));
            message.setSubject(mail.getSubject());
            message.setText("Hej,\n" + mail.getName() + " har kontaktet kundeservice. " + mail.getName() + " kan kontaktes på " + mail.getPhone() + " og " + mail.getMail() + " kunden skriver. \n" + mail.getText());
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }



}
