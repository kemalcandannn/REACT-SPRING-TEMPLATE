import React, { useEffect } from "react";
import { BiLogOut, BiTestTube } from "react-icons/bi";
import { useLanguage } from "../contexts/language/LanguageContext";
import { useAuthentication } from "../contexts/authentication/AuthenticationContext";
import { useNavigate } from "react-router-dom";
import { NAVIGATE_PATHS } from "../constants/Paths";

const Dashboard: React.FC = ({ }) => {
    const { getLabel } = useLanguage();
    const { logout } = useAuthentication();
    const navigate = useNavigate();

    useEffect(() => {
        test();
    }, []);

    const test = () => {
        console.log("TEST BUTONUNA BASILDI");
    };

    return (
        <>
            <div style={{ margin: 0, display: "flex", placeItems: "center", minWidth: "320px", minHeight: "100vh" }}>
                <div style={{ margin: "0 auto", padding: "2rem", textAlign: "center" }}>
                    {getLabel("welcomeToHomePage")}

                    <div>
                        <button
                            onClick={() => navigate(NAVIGATE_PATHS.CHANGE_PASSWORD)}>
                            <BiLogOut className="logo" /> {"PAROLA DEĞİŞTİR"}
                        </button>
                    </div>

                    <div>
                        <button
                            onClick={test}>
                            <BiTestTube className="logo" /> {"TEST"}
                        </button>
                    </div>

                    <div>
                        <button
                            onClick={logout}>
                            <BiLogOut className="logo" /> {getLabel("logout")}
                        </button>
                    </div>
                </div>
            </div>
        </>
    );
};

export default Dashboard;