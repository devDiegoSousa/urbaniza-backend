package com.urbaniza.authapi;

import com.urbaniza.authapi.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito
public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender; // Mock do JavaMailSender

    @InjectMocks
    private EmailService emailService; // Injeta o mock no EmailService

    @Test
    void testSendConfirmationEmail() {
        // Dados de teste
        String toEmail = "usuario@example.com";
        String token = "abc123-token";

        // Executa o método a ser testado
        emailService.sendConfirmationEmail(toEmail, token);

        // Verifica se o mailSender.send() foi chamado com os parâmetros corretos
        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}