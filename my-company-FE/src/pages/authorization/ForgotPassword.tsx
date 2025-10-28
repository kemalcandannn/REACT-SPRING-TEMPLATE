import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLanguage } from "../../contexts/language/LanguageContext";
import BaseApiAxios from "../../helpers/BaseApiAxios";
import "./style/Authorization.css";

const ForgotPassword: React.FC = () => {
    const { getLabel } = useLanguage();
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setMessage("");
        setError("");

        try {
            const response = await BaseApiAxios.post("api/v1/authentication/forgot-password", {
                email: email
            });

            if (response?.data?.success) {
                setMessage(getLabel("passwordResetEmailSent"));
            } else {
                setError(response?.data?.errorMessage ?? getLabel("unknownErrorOccured"));
            }
        } catch (err: any) {
            setError(getLabel("unknownErrorOccured"));
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-body">
            <div className="login-root">
                <h2>{getLabel("forgotPasswordTitle")}</h2>

                <form onSubmit={handleSubmit}>
                    <div>
                        <input
                            type="email"
                            placeholder={getLabel("email")}
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <div>
                        <button type="submit" disabled={loading}>
                            {loading ? getLabel("loading") : getLabel("resetPassword")}
                        </button>
                    </div>

                    {message && <div className="success-text">{message}</div>}
                    {error && <div className="error-text">{error}</div>}
                </form>

                <p>
                    <a
                        href="#"
                        onClick={(e) => {
                            e.preventDefault();
                            navigate("/login");
                        }}
                    >
                        {getLabel("backToLogin")}
                    </a>
                </p>
            </div>
        </div>
    );
};

export default ForgotPassword;
