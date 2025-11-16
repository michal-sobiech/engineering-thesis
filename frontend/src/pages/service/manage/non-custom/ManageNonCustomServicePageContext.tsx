import { createContext } from "react";
import { UseStateSetter } from "../../../../utils/use-state";
import { ManageNonCustomServicePageFutureScheduledAppointment } from "./ManageNonCustomServicePageFutureScheduledAppointment";

export interface ManageNonCustomServicePageContextValue {
    futureScheduledAppointments: ManageNonCustomServicePageFutureScheduledAppointment[],
    setFutureScheduledAppointments: UseStateSetter<ManageNonCustomServicePageFutureScheduledAppointment[]>,
}

export const ManageNonCustomServicePageContext = createContext<ManageNonCustomServicePageContextValue | null>(null);