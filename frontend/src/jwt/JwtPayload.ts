import z from "zod";

export const JwtPayloadCheck = z.object({
    sub: z.coerce.number(),
    exp: z.coerce.number(),
    iat: z.coerce.number(),
}).transform(({ sub, exp, iat }) => ({
    userId: sub,
    expiresAt: exp,
    issuedAt: iat,
}));

export type JwtPayload = z.infer<typeof JwtPayloadCheck>;