package com.my_company.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend.url}")
    private String frontendBaseUrl;

    public void sendPasswordResetMail(String to, String token) {
        String resetLink = frontendBaseUrl + "/resetPassword?token=" + token;
        String subject = "🔐 Parola Sıfırlama Talebi";
        String body = """
                Merhaba,
                
                Parolanızı sıfırlamak için aşağıdaki bağlantıya tıklayın:
                %s
                
                Bu bağlantı 15 dakika içinde geçersiz olacaktır.
                
                Eğer bu isteği siz yapmadıysanız, bu e-postayı yok sayabilirsiniz.
                
                Saygılarımızla,
                MyCompany Destek Ekibi
                """.formatted(resetLink);

        sendEmail(to, subject, body);
    }

    public void sendAccountVerificationMail(String to, String token) {
        String verificationLink = frontendBaseUrl + "/verify-account?token=" + token;
        String subject = "✅ Hesabınızı Doğrulayın";
        String body = """
                Merhaba,
                
                Hesabınızı aktifleştirmek için aşağıdaki bağlantıya tıklayın:
                %s
                
                Bu bağlantı 24 saat içinde geçersiz olacaktır.
                
                Teşekkürler,
                MyCompany Ekibi
                """.formatted(verificationLink);

        sendEmail(to, subject, body);
    }

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
