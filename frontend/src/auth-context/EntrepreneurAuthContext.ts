import { JwtToken } from "../utils/jwt";
import { Role } from "./Role";

export interface EntrepreneurAuthContext {
    role: Role.ENTREPRENEUR;
    jwtToken: JwtToken;
    userId: number;
    entrepreneurId: number;
}