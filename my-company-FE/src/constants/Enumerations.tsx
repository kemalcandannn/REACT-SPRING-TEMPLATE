const STATUS_VALUE = ["ACTIVE", "PASSIVE"] as const;
export type STATUS = typeof STATUS_VALUE[number];

const PROVIDER_VALUE = ["LOCAL", "GOOGLE", "FACEBOOK", "APPLE"] as const;
export type PROVIDER = typeof PROVIDER_VALUE[number];