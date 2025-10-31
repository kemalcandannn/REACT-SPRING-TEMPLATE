package com.my_company.service.email;

import com.my_company.cache.ParameterCache;
import com.my_company.constants.enums.Language;
import com.my_company.constants.enums.ParameterCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend.url}")
    private String frontendBaseUrl;

    public void sendPasswordResetMail(String to, String token, Language language) {
        String resetLink = frontendBaseUrl + "/resetPassword?token=" + token;
        String subject = Language.getPasswordResetMailSubject(language);

        Integer tokenExpirationMinutes = ParameterCache.getParamValueAsIntegerWithControl(ParameterCode.RESET_PASSWORD_TOKEN_EXPIRATION_CONTROL, ParameterCode.RESET_PASSWORD_TOKEN_EXPIRATION_MINUTES);

        String body = Language.getPasswordResetMail(language)
                .formatted(resetLink,
                        (Objects.isNull(tokenExpirationMinutes) ? "" :
                                String.format(Language.getLinkExpireText(language), tokenExpirationMinutes)));

        sendEmail(to, subject, body);
    }

    public void sendAccountVerificationMail(String to, String token, Language language) {
        String verificationLink = frontendBaseUrl + "/verifyAccount?token=" + token;
        String subject = Language.getAccountVerificationMailSubject(language);

        Integer tokenExpirationMinutes = ParameterCache.getParamValueAsIntegerWithControl(ParameterCode.VERIFY_ACCOUNT_TOKEN_EXPIRATION_CONTROL, ParameterCode.VERIFY_ACCOUNT_TOKEN_EXPIRATION_MINUTES);

        String body = Language.getAccountVerificationMail(language)
                .formatted(verificationLink,
                        (Objects.isNull(tokenExpirationMinutes) ? "" :
                                String.format(Language.getLinkExpireText(language), tokenExpirationMinutes)));

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
