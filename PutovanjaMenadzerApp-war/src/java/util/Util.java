/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Sanja
 */
public class Util {

    public static void posaljiMail(String mail, String kod) {

        final String username = "njtprojekatfon@gmail.com";
        final String password = "njtprojekat";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("aleksandar.buha05@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mail));
            message.setSubject("Registracioni mejl");
            message.setText("Dear Mail Crawler,"
                    + "\n\n http://localhost:8080/PutovanjaMenadzerApp-war/faces/aktivacija.xhtml?key=" + kod);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generisKod() {
        StringBuilder buffer = new StringBuilder();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int charactersLength = characters.length();

        for (int i = 0; i < 9; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }
}
