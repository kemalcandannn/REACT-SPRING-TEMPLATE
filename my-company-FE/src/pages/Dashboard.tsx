import React, { useEffect, useState } from "react";
import { BiLogOut, BiRightArrow } from "react-icons/bi";
import { useLanguage } from "../contexts/language/LanguageContext";
import { useNavigate } from "react-router-dom";
import { NAVIGATE_PATHS } from "../constants/Paths";
import { useAuthentication } from "../contexts/authentication/AuthenticationContext";
import ChangePassword from "./authorization/ChangePassword";
import { STATUS } from "../constants/Enumerations";

const Dashboard: React.FC = ({ }) => {
    const { getLabel } = useLanguage();
    const { sessionUser } = useAuthentication();
    const navigate = useNavigate();
    const [clickedChangePassword, setClickedChangePassword] = useState<boolean>(false);

    useEffect(() => {
    }, []);

    const test = () => {
        console.log("TEST BUTONUNA BASILDI");
    };

    return <>
        <div style={{ position: "fixed", top: 0, left: 0 }}>
            <button
                onClick={() => {
                    navigate(NAVIGATE_PATHS.LOGOUT);
                }}>
                <BiLogOut className="logo" /> {getLabel("logout")}
            </button>
        </div>

        {sessionUser?.status == STATUS.ACTIVE &&
            (clickedChangePassword ?
                <ChangePassword setClickedChangePassword={setClickedChangePassword} />
                :
                (sessionUser?.passwordValidUntil != null &&
                    new Date(sessionUser?.passwordValidUntil) < new Date()) ?
                    <ChangePassword />
                    :
                    <div style={{ margin: 0, display: "flex", placeItems: "center", minWidth: "320px", minHeight: "100vh" }}>
                        <div style={{ margin: "0 auto", padding: "2rem", textAlign: "center" }}>
                            {getLabel("welcomeToHomePage")}

                            <div style={{ marginTop: 10 }}>
                                <button
                                    onClick={() => {
                                        setClickedChangePassword(true);
                                    }}>
                                    {getLabel("changePassword")} <BiRightArrow className="logo" />
                                </button>
                            </div>

                            <div style={{ marginTop: 10 }}>
                                <button
                                    onClick={test}>
                                    {"TEST"} <BiRightArrow className="logo" />
                                </button>
                            </div>

                        </div>
                    </div>
            )
        }

    </>;
};

export default Dashboard;