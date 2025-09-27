import { createContext } from "react";
import { EnterpriseContextValue } from "./EnterpriseContextValue";

export const EnterpriseContext = createContext<EnterpriseContextValue | null>(null);