import { useState } from "react";
import { FcGoogle } from "react-icons/fc";
import { FaFacebook, FaApple } from "react-icons/fa";
import { useLanguage } from "../../contexts/language/LanguageContext";
import { useAuthentication } from "../../contexts/authentication/AuthenticationContext";
import { useNavigate } from "react-router-dom";
import "./style/Authorization.css";
import BaseApiAxios from "../../helpers/BaseApiAxios";
import { useParameters } from "../../contexts/parameters/ParametersContext";

const Login: React.FC = () => {
    const { getLabel } = useLanguage();
    const { login } = useAuthentication();
    const { initParameters } = useParameters();
    const navigate = useNavigate();

    const [emailOrUsername, setEmailOrUsername] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError("");

        try {
            const response = await BaseApiAxios.post("api/v1/authentication/login", {
                username: emailOrUsername,
                password: password
            });

            if (!response?.data?.success) {
                setError(response?.data?.errorMessage ?? getLabel("unknownErrorOccured"));
                return;
            }

            const token = response.data.data.token;
            processLogin(token);
        } catch (err: any) {
            if (!err?.response?.data?.success) {
                if (err?.response?.data?.errorCode == "TOKEN_EXPIRED") {
                    setError(getLabel("tokenHasExpired"));
                } else if (err?.response?.data?.errorCode == "INCORRECT_USERNAME_OR_PASSWORD") {
                    setError(getLabel("incorrectUsernameOrPassword"));
                } else {
                    setError(getLabel("loginFailedCheckYourCredentials"));
                }
            } else {
                setError(getLabel("loginFailedCheckYourCredentials"));
            }
        }

        setLoading(false);
    };

    const handleSocialLogin = async (provider: "google" | "facebook" | "apple") => {
        setLoading(true);
        setError("");

        try {
            console.log(`${provider} ile giriş yapılıyor`);
            await new Promise((resolve) => setTimeout(resolve, 1000));
            processLogin("TEST");
        } catch {
            setError(`${provider} ile giriş yapılamadı.`);
        }

        setLoading(false);
    };

    const processLogin = (token: string) => {
        login(token);
        initParameters();
    }

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
                                placeholder={getLabel("emailOrUsername")}
                                value={emailOrUsername}
                                onChange={(e) => setEmailOrUsername(e.target.value)}
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
                            <FaApple className="logo" /> {getLabel("loginWithApple")}
                        </button>

                        <button
                            onClick={() => handleSocialLogin("facebook")}
                            disabled={loading}>
                            <FaFacebook className="logo" /> {getLabel("loginWithFacebook")}
                        </button>

                        <button
                            onClick={() => handleSocialLogin("google")}
                            disabled={loading}>
                            <FcGoogle className="logo" /> {getLabel("loginWithGoogle")}
                        </button>
                    </div>

                    <p>
                        {getLabel("dontHaveAnAccount")}
                        <a onClick={(e: React.MouseEvent<HTMLAnchorElement>) => {
                            e.preventDefault();
                            navigate("/signUp");
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