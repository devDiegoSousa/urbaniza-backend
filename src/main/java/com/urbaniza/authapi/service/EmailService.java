package com.urbaniza.authapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EmailService {
  @Autowired
  private JavaMailSender mailSender;

  @Async
  public void sendConfirmationEmail(String toEmail, String token) {
    if (!StringUtils.hasText(token)) {
      throw new IllegalArgumentException("Token cannot be null or empty");
    }
    String confirmationUrl = "http://localhost:8282/auth/email/confirm?token=" + token;
    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(toEmail);
    mail.setSubject("Confirme seu email no Urbaniza!");
    mail.setText("Clique no link para confirmar: " + confirmationUrl);
    mailSender.send(mail);
  }

  @Async
  public void sendRecoveryPasswordEmail(String toEmail, String recoveryToken) {

    if (!StringUtils.hasText(toEmail)) {throw new IllegalArgumentException("Email cannot be null or empty");}

    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(toEmail);
    mail.setSubject("Recupere sua senha!");
    mail.setText("Utilize o token a seguir para redefinir sua senha: " + recoveryToken + "\n token válido durante 5 minutos");
    mailSender.send(mail);
  }
}
