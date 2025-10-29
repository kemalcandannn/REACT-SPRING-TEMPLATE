import { STATUS, PROVIDER } from "../../../constants/Enumerations";

export interface User {
    username: string;
    passwordValidUntil: Date;
    provider: PROVIDER;
    providerId: string;
    status: STATUS;
    createdAt: Date;

    roleList: string[]
    menuList: string[]
}