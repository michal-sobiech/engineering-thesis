export type UserGroup = "ENTREPRENEUR" | "CUSTOMER" | "EMPLOYEE" | "REGULAR_ADMIN" | "HEAD_ADMIN";

export type IndependentEndUserGroup = Extract<UserGroup, "ENTREPRENEUR" | "CUSTOMER">;