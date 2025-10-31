import { type STATUS, type PROVIDER, type LANGUAGE } from "../../../constants/Enumerations";

export interface User {
    username: string;
    passwordValidUntil: Date;
    provider: PROVIDER;
    providerId: string;
    status: STATUS;
    language: LANGUAGE;
    createdAt: Date;

    roleList: string[]
    menuList: string[]
}