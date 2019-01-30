package org.plu.RESTSpringBoot.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {

    private static final String EMAIL_OWNER = "web2018projekatjul@gmail.com";

    private static final String ACTIVATION_SUBJECT = "Activation link";
    private static final String ACTIVATION_MESSAGE = "Thanks for signing up! Follow this link to activate your account:\r\n"
            + "http://localhost:8080/users/auth/registrationConfirm?token=";

    public EmailUtil() {

    }

    public static void sendMail(String fromEmail, String toEmail, String subject, String messageToSend) {

        Properties props = System.getProperties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_OWNER, EMAIL_OWNER_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromEmail));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            message.setSubject(subject);

            message.setText(messageToSend);

            Transport.send(message);
            System.out.println("Sent message successfully...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static String sendVerificationMail(String toEmail) {
        String token = generateToken(toEmail);

        sendMail(EMAIL_OWNER, toEmail, ACTIVATION_SUBJECT,ACTIVATION_MESSAGE + token);
        return token;
    }

    public static String generateToken(String userEmail) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(userEmail.getBytes());

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // String encryptedString = new String(messageDigest.digest());
        String encryptedString = new Random(50).nextInt() + userEmail;
        return encryptedString;
    }

    private static final String EMAIL_OWNER_PASSWORD = "MikaPera21";

}
