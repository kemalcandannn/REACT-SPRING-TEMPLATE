export const STATUS = {
    ACTIVE: "ACTIVE",
    PASSIVE: "PASSIVE",
} as const;
export type STATUS = typeof STATUS[keyof typeof STATUS];

export const PROVIDER = {
    LOCAL: "LOCAL",
    GOOGLE: "GOOGLE",
    FACEBOOK: "FACEBOOK",
    APPLE: "APPLE"
} as const;
export type PROVIDER = typeof PROVIDER[keyof typeof PROVIDER];