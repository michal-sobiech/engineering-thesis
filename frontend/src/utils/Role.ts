export const ROLES = ["CUSTOMER", "ENTREPRENEUR", "ENTERPRISE_EPLOYEE", "REGULAR_ADMIN", "HEAD_ADMIN"] as const;

export type Role = (typeof ROLES)[number];
