import { useState } from "react";
import { FcGoogle } from "react-icons/fc";
import { FaFacebook, FaApple } from "react-icons/fa";
import { useLanguage } from "../../contexts/language/LanguageContext";
import { useAuthentication } from "../../contexts/authentication/AuthenticationContext";
import { useNavigate } from "react-router-dom";
import "./style/Authorization.css";
import BaseApiAxios from "../../helpers/BaseApiAxios";
import { ERROR_CODE } from "../../constants/Utils";
import { useApiErrorHandler } from "../../helpers/ApiErrorHandler";

const SignUp: React.FC = () => {
    const { getLabel } = useLanguage();
    const { login } = useAuthentication();
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
            const response = await BaseApiAxios.post("api/v1/authentication/local-sign-up", {
                username: username,
                password: password
            });

            const token = response.data.data.token;
            login(token);
        } catch (err: any) {
            setError(handleApiError(err));
        }

        setLoading(false);
    };

    const handleSocialLogin = async (provider: "google" | "facebook" | "apple") => {
        setLoading(true);
        setError("");

        try {
            console.log(`${provider} ile kayıt olunuyor`);
            await new Promise((resolve) => setTimeout(resolve, 1000));
            login("TEST");
            alert(`${provider} ile kayıt olundu!`);
        } catch {
            setError(`${provider} ile kayıt olunamadı.`);
        }

        setLoading(false);
    };

    return (
        <>
            <div className="login-body">
                <div className="login-root">
                    <h2>
                        {getLabel("signUpTitle")}
                    </h2>

                    <form onSubmit={handleSubmit}>
                        <div>
                            <input
                                type="email"
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
                                {loading ? getLabel("loading") : getLabel("signUp")}
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
                            <FaApple className="logo" /> {getLabel("signUpWithApple")}
                        </button>

                        <button
                            onClick={() => handleSocialLogin("facebook")}
                            disabled={loading}>
                            <FaFacebook className="logo" /> {getLabel("signUpWithFacebook")}
                        </button>

                        <button
                            onClick={() => handleSocialLogin("google")}
                            disabled={loading}>
                            <FcGoogle className="logo" /> {getLabel("signUpWithGoogle")}
                        </button>
                    </div>

                    <p>
                        {getLabel("alreadyHaveAnAccount")}
                        <a onClick={(e: React.MouseEvent<HTMLAnchorElement>) => {
                            e.preventDefault();
                            navigate("/login");
                        }}>
                            {getLabel("login")}
                        </a>
                    </p>
                </div>
            </div>
        </>
    );
};

export default SignUp;