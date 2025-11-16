import { createContext } from "react";
import { UseStateSetter } from "../../../../utils/use-state";
import { ManageCustomServicePageFutureScheduledAppointment } from "./ManageCustomServicePageFutureScheduledAppointment";
import { ManageCustomServicePagePendingAppointment } from "./ManageCustomServicePagePendingAppointment";

export interface ManageCustomServicePageContextValue {
    futureScheduledAppointments: ManageCustomServicePageFutureScheduledAppointment[],
    setFutureScheduledAppointments: UseStateSetter<ManageCustomServicePageFutureScheduledAppointment[]>,
    pendingAppointments: ManageCustomServicePagePendingAppointment[],
    setPendingAppointments: UseStateSetter<ManageCustomServicePagePendingAppointment[]>,
}

export const ManageCustomServicePageContext = createContext<ManageCustomServicePageContextValue | null>(null);