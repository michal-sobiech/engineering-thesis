import { jwtDecode, JwtDecodeOptions } from "jwt-decode";
import { err, ok, Result } from "neverthrow";
import { ZodType } from "zod";
import { JwtPayload, JwtPayloadCheck } from "./JwtPayload";

export function defaultDecodeJwt(token: string, options?: JwtDecodeOptions): Result<JwtPayload, Error> {
    return decodeJwt(token, JwtPayloadCheck, options);
}

export function decodeJwt<T>(token: string, zodCheck: ZodType<T>, options?: JwtDecodeOptions): Result<T, Error> {
    try {
        const jwtPayloadUnknown = jwtDecode<unknown>(token, options);
        const jwtPayloadChecked: T = zodCheck.parse(jwtPayloadUnknown);
        return ok(jwtPayloadChecked);
    } catch (error) {
        return err(new Error(String(error)));
    }
}