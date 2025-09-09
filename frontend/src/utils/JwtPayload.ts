import z from "zod";

export interface JwtPayload {
    userId: number;
    expires_at: number;
    issued_at: number;
}

export const JwtPayloadCheck: z.ZodType<JwtPayload> = z.object({
    userId: z.number(),
    expires_at: z.number(),
    issued_at: z.number(),
});