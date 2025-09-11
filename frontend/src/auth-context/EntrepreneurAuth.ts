import { JwtToken } from "../utils/jwt";
import { Role } from "./Role";

export interface EntrepreneurAuth {
    role: Role.ENTREPRENEUR;
    jwtToken: JwtToken;
    userId: number;
    firstName: string;
    lastName: string;
    entrepreneurId: number;
}