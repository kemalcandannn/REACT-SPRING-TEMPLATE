package com.my_company.constants.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Language {
    TR, EN;

    public static Language getLanguage(String value) {
        return Arrays
                .stream(Language.values())
                .filter(language -> language.name().equals(value))
                .findFirst()
                .orElse(null);
    }

    public static String getLinkExpireText(Language language) {
        String linkExpireTextTr = "Bu bağlantı %d dakika içinde geçersiz olacaktır.";
        String linkExpireTextEn = "This link will expire in %d minutes.";
        if (TR.equals(language)) {
            return linkExpireTextTr;
        } else if (EN.equals(language)) {
            return linkExpireTextEn;
        }

        return linkExpireTextEn;
    }

    public static String getPasswordResetMailSubject(Language language) {
        String passwordResetMailSubjectTr = "🔐 Parola Sıfırlama Talebi";
        String passwordResetMailSubjectEn = "🔐 Password Reset Request";

        if (TR.equals(language)) {
            return passwordResetMailSubjectTr;
        } else if (EN.equals(language)) {
            return passwordResetMailSubjectEn;
        }

        return passwordResetMailSubjectEn;
    }

    public static String getPasswordResetMail(Language language) {
        String passwordResetMailTr = """
                Merhaba,
                
                Parolanızı sıfırlamak için aşağıdaki bağlantıya tıklayın:
                %s
                
                %s
                
                Eğer bu isteği siz yapmadıysanız, bu e-postayı yok sayabilirsiniz.
                
                Saygılarımızla,
                MyCompany Destek Ekibi
                """;
        String passwordResetMailEn = """
                Hello,
                
                Click the link below to reset your password:
                %s
                
                %s
                
                If you did not request this, you can ignore this email.
                
                Best regards,
                MyCompany Support Team
                """;

        if (TR.equals(language)) {
            return passwordResetMailTr;
        } else if (EN.equals(language)) {
            return passwordResetMailEn;
        }

        return passwordResetMailEn;
    }

    public static String getAccountVerificationMailSubject(Language language) {
        String accountVerificationMailSubjectTr = "✅ Hesabınızı Doğrulayın";
        String accountVerificationMailSubjectEn = "🔐 Verify Your Account";

        if (TR.equals(language)) {
            return accountVerificationMailSubjectTr;
        } else if (EN.equals(language)) {
            return accountVerificationMailSubjectEn;
        }

        return accountVerificationMailSubjectEn;
    }

    public static String getAccountVerificationMail(Language language) {
        String accountVerificationMailTr = """
                Merhaba,
                
                Hesabınızı aktifleştirmek için aşağıdaki bağlantıya tıklayın:
                %s
                
                %s
                
                Teşekkürler,
                MyCompany Ekibi
                """;
        String accountVerificationMailEn = """
                Hello,
                
                Click the link below to activate your account:
                %s
                
                %s
                
                Thank you,
                MyCompany Team
                """;

        if (TR.equals(language)) {
            return accountVerificationMailTr;
        } else if (EN.equals(language)) {
            return accountVerificationMailEn;
        }

        return accountVerificationMailEn;
    }
}