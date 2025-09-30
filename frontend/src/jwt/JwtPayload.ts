import z from "zod";
import { Role, ROLES } from "../utils/Role";

export interface JwtPayload {
    userId: number;
    role: Role;
    expires_at: number;
    issued_at: number;
}

export const JwtPayloadCheck: z.ZodType<JwtPayload> = z.object({
    userId: z.number(),
    role: z.enum(ROLES),
    expires_at: z.number(),
    issued_at: z.number(),
});