//export const LANGUGAGES = ["tr", "en", "de", "ru", "ar"];
export const LANGUGAGES = ["tr"];

export type Language = typeof LANGUGAGES[number];

export interface Labels {
    alreadyHaveAnAccount: string;
    dontHaveAnAccount: string;
    email: string;
    emailOrUsername: string;
    incorrectUsernameOrPassword: string;
    loading: string;
    login: string;
    loginFailedCheckYourCredentials: string;
    loginTitle: string;
    loginWithApple: string;
    loginWithFacebook: string;
    loginWithGoogle: string;
    logout: string;
    or: string;
    password: string;
    signUp: string;
    signUpTitle: string;
    signUpWithApple: string;
    signUpWithFacebook: string;
    signUpWithGoogle: string;
    tokenHasExpired: string;
    unknownErrorOccured: string;
    username: string;
    welcomeToHomePage: string;
}


export const labelsData: Record<Language, Labels> = {
    tr: {
        alreadyHaveAnAccount: "Zaten bir hesabınız var mı? ",
        dontHaveAnAccount: "Hesabın yok mu? ",
        email: "E-Posta",
        emailOrUsername: "E-Posta veya Kullanıcı Adı",
        incorrectUsernameOrPassword: "Kullanıcı adı veya Parola Hatalı, girdiğiniz bilgileri kontrol ediniz.",
        loading: "Yükleniyor...",
        login: "Giriş Yap",
        loginFailedCheckYourCredentials: "Giriş yapılamadı. Bilgilerinizi kontrol edin.",
        loginTitle: "Giriş Yap",
        loginWithApple: "Apple ile giriş",
        loginWithFacebook: "Facebook ile giriş",
        loginWithGoogle: "Google ile giriş",
        logout: "Çıkış Yap",
        or: "veya",
        password: "Parola",
        signUp: "Kaydol",
        signUpTitle: "Kaydol",
        signUpWithApple: "Apple ile Kaydol",
        signUpWithFacebook: "Facebook ile Kaydol",
        signUpWithGoogle: "Google ile Kaydol",
        tokenHasExpired: "Token'ın süresi dolmuş. Lütfen Çıkış/Giriş yapıp, tekrar deneyiniz.",
        unknownErrorOccured: "Bilinmeyen bir hata oluştu, sistem yönetinizle iletişime geçiniz.",
        username: "Kullanıcı Adı",
        welcomeToHomePage: "Anasayfa'ya Hoşgeldiniz",
    }
};