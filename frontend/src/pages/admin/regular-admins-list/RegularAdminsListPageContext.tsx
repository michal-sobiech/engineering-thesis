import { createContext } from "react";
import { GetRegularAdminResponse } from "../../../GENERATED-api";
import { UseStateSetter } from "../../../utils/use-state";

export interface RegularAdminsListPageContext {
    regularAdmins: GetRegularAdminResponse[],
    setRegularAdmins: UseStateSetter<GetRegularAdminResponse[]>,
}

export const RegularAdminsListPageContext = createContext<RegularAdminsListPageContext | null>(null);