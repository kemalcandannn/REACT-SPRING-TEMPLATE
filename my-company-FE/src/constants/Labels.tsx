//export const LANGUGAGES = ["tr", "en", "de", "ru", "ar"] as const;
export const LANGUGAGES = ["tr"] as const;

export type Language = typeof LANGUGAGES[number];

export interface Labels {
    alreadyHaveAnAccount: string;
    back: string;
    backToLogin: string;
    changePassword: string;
    changePasswordTitle: string;
    checkRequiredField: string;
    confirmNewPassword: string;
    dontHaveAnAccount: string;
    dontHavePermission: string;
    email: string;
    emailOrUsername: string;
    incorrectOldPassword: string;
    forgotPassword: string,
    forgotPasswordTitle: string;
    incorrectUsernameOrPassword: string;
    last3PreviousPasswordDifferent: string;
    loading: string;
    login: string;
    loginFailedCheckYourCredentials: string;
    loginTitle: string;
    loginWithApple: string;
    loginWithFacebook: string;
    loginWithGoogle: string;
    logout: string;
    newPassword: string;
    newPasswordDoesNotConfirm: string;
    oldPassword: string;
    or: string;
    password: string;
    passwordAtLeastCharactersLong: string;
    passwordAtLeastOneDigit: string;
    passwordAtLeastOneLowercase: string;
    passwordAtLeastOneSpecialCharacter: string;
    passwordAtLeastOneUppercase: string;
    passwordChangedSuccessfully: string;
    passwordResetEmailSent: string;
    resetPassword: string;
    requiredField: string;
    signUp: string;
    signUpTitle: string;
    signUpWithApple: string;
    signUpWithFacebook: string;
    signUpWithGoogle: string;
    tokenHasExpired: string;
    tokenValidationError: string;
    unknownErrorOccured: string;
    username: string;
    usernameAlreadyRegistered: string;
    welcomeToHomePage: string;
}

export const labelsData: Record<Language, Labels> = {
    tr: {
        alreadyHaveAnAccount: "Zaten bir hesabınız var mı? ",
        back: "Geri",
        backToLogin: "Giriş ekranına dön",
        changePassword: "Parolayı Güncelle",
        changePasswordTitle: "Parolayı Güncelle",
        checkRequiredField: "Zorunlu alanları kontrol ediniz.",
        confirmNewPassword: "Yeni Parola Doğrula",
        dontHaveAnAccount: "Hesabın yok mu? ",
        dontHavePermission: "Yapmaya çalıştığınız işlemi gerçekleştirme yetkiniz bulunmamaktadır",
        forgotPassword: "Parolanızı mı unuttunuz?",
        forgotPasswordTitle: "Parolanızı mı unuttunuz?",
        email: "E-Posta",
        emailOrUsername: "E-Posta veya Kullanıcı Adı",
        incorrectOldPassword: "Mevcut parolanız hatalı.",
        incorrectUsernameOrPassword: "Kullanıcı adı veya Parola Hatalı, girdiğiniz bilgileri kontrol ediniz.",
        last3PreviousPasswordDifferent: "Yeni girilen parola, son 3 paroladan farklı olmak zorundadır.",
        loading: "Yükleniyor...",
        login: "Giriş Yap",
        loginFailedCheckYourCredentials: "Giriş yapılamadı. Bilgilerinizi kontrol edin.",
        loginTitle: "Giriş Yap",
        loginWithApple: "Apple ile giriş",
        loginWithFacebook: "Facebook ile giriş",
        loginWithGoogle: "Google ile giriş",
        logout: "Çıkış Yap",
        newPassword: "Yeni Parola",
        newPasswordDoesNotConfirm: "Yeni parola doğrulanamadı.",
        oldPassword: "Eski Parola",
        or: "veya",
        password: "Parola",
        passwordAtLeastCharactersLong: "Parola en az [%s] karakter olmalıdır.",
        passwordAtLeastOneDigit: "Parola en az 1 rakam içermelidir",
        passwordAtLeastOneLowercase: "Parola en az 1 küçük harf içermelidir.",
        passwordAtLeastOneSpecialCharacter: "Parola en az 1 özel karakter içermelidir",
        passwordAtLeastOneUppercase: "Parola en az 1 büyük harf içermelidir.",
        passwordChangedSuccessfully: "Parolanız başarıyla güncellendi.",
        passwordResetEmailSent: "Şifre sıfırlama bağlantısı e-posta adresinize gönderildi.",
        resetPassword: "Şifre Sıfırlama Bağlantısı Gönder",
        requiredField: "[[%s]] zorunlu alandır, lütfen kontrol ediniz.",
        signUp: "Kaydol",
        signUpTitle: "Kaydol",
        signUpWithApple: "Apple ile Kaydol",
        signUpWithFacebook: "Facebook ile Kaydol",
        signUpWithGoogle: "Google ile Kaydol",
        tokenHasExpired: "Token'ın süresi dolmuş. Lütfen Çıkış/Giriş yapıp, tekrar deneyiniz.",
        tokenValidationError: "Token doğrulanırken bir hata oluştu.",
        unknownErrorOccured: "Bilinmeyen bir hata oluştu, sistem yönetinizle iletişime geçiniz.",
        username: "Kullanıcı Adı",
        usernameAlreadyRegistered: "Kullanıcı sistemde zaten kayıtlı.",
        welcomeToHomePage: "Anasayfa'ya Hoşgeldiniz",
    }
};

export function isLabelKey(key: string): key is keyof Labels {
    return key in labelsData.tr;
}