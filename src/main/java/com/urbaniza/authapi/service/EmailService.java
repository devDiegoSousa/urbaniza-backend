package com.urbaniza.authapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmationEmail(String toEmail, String token) {
        String confirmationUrl = "http://localhost:8081/auth/confirm-email?token=" + token;
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toEmail);
        mail.setSubject("Confirme seu email no Urbaniza!");
        mail.setText("Clique no link para confirmar: " + confirmationUrl);
        mailSender.send(mail);
    }
}
