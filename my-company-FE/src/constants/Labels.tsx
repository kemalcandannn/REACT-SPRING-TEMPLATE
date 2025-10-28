//export const LANGUGAGES = ["tr", "en", "de", "ru", "ar"] as const;
export const LANGUGAGES = ["tr", "en"] as const;

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
    },
    en: {
        alreadyHaveAnAccount: "Already have an account? ",
        dontHaveAnAccount: "Don't have an account? ",
        email: "Email",
        emailOrUsername: "Email or Username",
        incorrectUsernameOrPassword: "Incorrect username or password. Please check your input.",
        loading: "Loading...",
        login: "Login",
        loginFailedCheckYourCredentials: "Login failed. Please check your credentials.",
        loginTitle: "Login",
        loginWithApple: "Login with Apple",
        loginWithFacebook: "Login with Facebook",
        loginWithGoogle: "Login with Google",
        logout: "Logout",
        or: "or",
        password: "Password",
        signUp: "Sign Up",
        signUpTitle: "Sign Up",
        signUpWithApple: "Sign Up with Apple",
        signUpWithFacebook: "Sign Up with Facebook",
        signUpWithGoogle: "Sign Up with Google",
        tokenHasExpired: "Token has expired. Please log out/in and try again.",
        unknownErrorOccured: "An unknown error occurred. Please contact support.",
        username: "Username",
        welcomeToHomePage: "Welcome to the homepage",
    }
};