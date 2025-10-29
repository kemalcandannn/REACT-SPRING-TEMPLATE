import { useState } from "react";
import { FcGoogle } from "react-icons/fc";
import { FaFacebook, FaApple } from "react-icons/fa";
import { useLanguage } from "../../contexts/language/LanguageContext";
import { useAuthentication } from "../../contexts/authentication/AuthenticationContext";
import { useNavigate } from "react-router-dom";
import "./style/Authorization.css";
import BaseApiAxios from "../../helpers/BaseApiAxios";
import { useApiErrorHandler } from "../../helpers/ApiErrorHandler";
import { NAVIGATE_PATHS, SERVICE_PATHS } from "../../constants/Paths";

const Login: React.FC = () => {
    const { getLabel } = useLanguage();
    const { fillToken } = useAuthentication();
    const { handleApiError } = useApiErrorHandler();
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError("");

        try {
            const response = await BaseApiAxios.post(SERVICE_PATHS.API_V1_AUTHENTICATION_LOGIN, {
                username: username,
                password: password
            });

            const token = response.data.data.token;
            fillToken(token);
        } catch (err: any) {
            setError(handleApiError(err));
        }

        setLoading(false);
    };

    const handleSocialLogin = async (provider: "google" | "facebook" | "apple") => {
        setLoading(true);
        setError("");

        try {
            console.log(`${provider} ile giriş yapılıyor`);
            await new Promise((resolve) => setTimeout(resolve, 1000));
            fillToken("TEST");
        } catch {
            setError(`${provider} ile giriş yapılamadı.`);
        }

        setLoading(false);
    };

    return (
        <>
            <div className="login-body">
                <div className="login-root">
                    <h2>
                        {getLabel("loginTitle")}
                    </h2>

                    <form onSubmit={handleSubmit}>
                        <div>
                            <input
                                type="text"
                                placeholder={getLabel("username")}
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                required
                            />
                        </div>
                        <div>
                            <input
                                type="password"
                                placeholder={getLabel("password")}
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                        </div>

                        <div>
                            <button
                                type="submit"
                                disabled={loading}>
                                {loading ? getLabel("loading") : getLabel("login")}
                            </button>
                        </div>

                        {/* 🔹 Parolanızı mı unuttunuz bağlantısı */}
                        <div className="forgot-password">
                            <a href="#"
                                onClick={(e: React.MouseEvent<HTMLAnchorElement>) => {
                                    e.preventDefault();
                                    navigate(NAVIGATE_PATHS.FORGOT_PASSWORD); // veya istediğin route
                                }}>
                                {getLabel("forgotPassword")}
                            </a>
                        </div>

                        {error &&
                            <div className="error-text">
                                {error}
                            </div>}
                    </form>

                    <hr />
                    {getLabel("or")}
                    <hr />

                    <div>
                        <button
                            onClick={() => handleSocialLogin("apple")}
                            disabled={loading}>
                            <FaApple className="logo" /> {getLabel("continueWithApple")}
                        </button>

                        <button
                            onClick={() => handleSocialLogin("facebook")}
                            disabled={loading}>
                            <FaFacebook className="logo" /> {getLabel("continueWithFacebook")}
                        </button>

                        <button
                            onClick={() => handleSocialLogin("google")}
                            disabled={loading}>
                            <FcGoogle className="logo" /> {getLabel("continueWithGoogle")}
                        </button>
                    </div>

                    <p>
                        {getLabel("dontHaveAnAccount")}
                        <a href="#"
                            onClick={(e: React.MouseEvent<HTMLAnchorElement>) => {
                                e.preventDefault();
                                navigate(NAVIGATE_PATHS.SIGN_UP);
                            }}>
                            {getLabel("signUp")}
                        </a>
                    </p>
                </div>
            </div>
        </>
    );
};

export default Login;