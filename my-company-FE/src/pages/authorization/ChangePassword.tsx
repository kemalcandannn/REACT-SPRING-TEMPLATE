import { useState } from "react";
import { useLanguage } from "../../contexts/language/LanguageContext";
import { useAuthentication } from "../../contexts/authentication/AuthenticationContext";
import BaseApiAxios from "../../helpers/BaseApiAxios";
import "./style/Authorization.css";
import { ERROR_CODE } from "../../constants/Utils";

const ChangePassword: React.FC = () => {
    const { getLabel } = useLanguage();
    const { logout } = useAuthentication();

    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");
        setSuccess("");
        setLoading(true);

        try {
            await BaseApiAxios.post("api/v1/authentication/change-password", {
                oldPassword,
                newPassword,
                confirmPassword
            });

            setSuccess(getLabel("passwordChangedSuccessfully"));
            setOldPassword("");
            setNewPassword("");
            setConfirmPassword("");
        } catch (err: any) {
            if (err?.response?.data?.errorCode === ERROR_CODE.INCORRECT_OLD_PASSWORD) {
                setError(getLabel("incorrectOldPassword"));
            } else if (err?.response?.data?.errorCode === ERROR_CODE.NEW_PASSWORD_DOES_NOT_CONFIRM) {
                setError(getLabel("incorrectOldPassword"));
            } else {
                setError(err?.response?.data?.errorMessage ?? getLabel("unknownErrorOccured"));
            }
        }

        setLoading(false);
    };

    return (
        <div className="login-body">
            <div className="login-root">
                <h2>{getLabel("changePasswordTitle")}</h2>

                <form onSubmit={handleSubmit}>
                    <div>
                        <input
                            type="password"
                            placeholder={getLabel("oldPassword")}
                            value={oldPassword}
                            onChange={(e) => setOldPassword(e.target.value)}
                            required
                        />
                    </div>

                    <div>
                        <input
                            type="password"
                            placeholder={getLabel("newPassword")}
                            value={newPassword}
                            onChange={(e) => setNewPassword(e.target.value)}
                            required
                        />
                    </div>

                    <div>
                        <input
                            type="password"
                            placeholder={getLabel("confirmNewPassword")}
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            required
                        />
                    </div>

                    <div>
                        <button type="submit" disabled={loading}>
                            {loading ? getLabel("loading") : getLabel("changePassword")}
                        </button>
                    </div>

                    {error && <div className="error-text">{error}</div>}
                    {success && <div className="success-text">{success}</div>}
                </form>

                <p style={{ marginTop: "20px" }}>
                    <a
                        onClick={(e: React.MouseEvent<HTMLAnchorElement>) => {
                            e.preventDefault();
                            logout();
                        }}
                        href="#"
                    >
                        {getLabel("logout")}
                    </a>
                </p>
            </div>
        </div>
    );
};

export default ChangePassword;
