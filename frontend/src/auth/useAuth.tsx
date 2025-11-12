import { useContextOrThrow } from "../hooks/useContextOrThrow";
import { AuthContext } from "./AuthContext";

export const useAuth = () => {
    const { auth, setAuth } = useContextOrThrow(AuthContext);

    return { auth, setAuth };
};