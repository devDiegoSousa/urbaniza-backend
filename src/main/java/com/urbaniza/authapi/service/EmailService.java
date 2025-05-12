package com.urbaniza.authapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    @Value("${app.verification-url}")
    private String verificationUrl;

    public void sendVerificationEmail(String destinatario, String token) {
        try {
            String link = verificationUrl + "?token=" + token;
            String mensagem = "Clique para verificar: " + link;

            SimpleMailMessage email = new SimpleMailMessage();
            email.setFrom(remetente);
            email.setTo(destinatario);
            email.setSubject("Verifique seu e-mail");
            email.setText(mensagem);

            javaMailSender.send(email);
            System.out.println("E-mail enviado para: " + destinatario); // Log
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage()); // Log de erro
        }
    }

}
