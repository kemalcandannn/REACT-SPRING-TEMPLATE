import type { PROVIDER, STATUS } from "../../../constants/Enumerations";

export interface User {
    username: string;
    passwordValidUntil: Date;
    provider: PROVIDER;
    providerId: string;
    status: STATUS;
    createdAt: Date;

    authorities: string[]
}