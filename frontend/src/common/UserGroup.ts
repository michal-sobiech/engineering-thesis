export const userGroups = ["ENTREPRENEUR", "CUSTOMER", "EMPLOYEE", "REGULAR_ADMIN", "HEAD_ADMIN"] as const;

export type UserGroup = (typeof userGroups)[number];

export type IndependentEndUserGroup = Extract<UserGroup, "ENTREPRENEUR" | "CUSTOMER">;

export function isUserGroup(value: string): value is UserGroup {
    return (userGroups as readonly string[]).includes(value);
}