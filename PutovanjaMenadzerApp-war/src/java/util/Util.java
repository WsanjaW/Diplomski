/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Properties;
import java.util.Random;
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

    public static String generisiBoju() {
        Random r = new Random();
        int randRed = r.nextInt(256);
        int randGreen = r.nextInt(256);
        int randBlue = r.nextInt(256);
        
        String hexRed = Integer.toHexString(randRed);
        String hexGreen = Integer.toHexString(randGreen);
        String hexBlue = Integer.toHexString(randBlue);
        
        return "#" + hexRed + hexGreen + hexBlue;
        
//        /* get random red, green, and blue from 0 to 255 */
//        int randomred = Math.floor(Math.random() * 255);
//        var randomgreen = Math.floor(Math.random() * 255);
//        var randomblue = Math.floor(Math.random() * 255);
//
//        /* convert each decimal number to hexadecimal */
//        var hred = new String(randomred.toString(16));
//        var hgreen = new String(randomgreen.toString(16));
//        var hblue = new String(randomblue.toString(16));
//
//        /* pad with 0 if necessary 
//         (e.g. make sure to output 05 instead of just 5) */
//        hred = String('00'+hred
//        ).slice(-2);
//        hgreen = String('00'+hgreen
//        ).slice(-2);
//        hblue = String('00'+hblue
//        ).slice(-2);
//
//        return '#' + hred + hgreen + hblue;
    }
}
