import { createContext } from "react";
import { UseStateSetter } from "../../../utils/useState";
import { CustomerLandingPagePendingAppointment } from "./CustomerLandingPagePendingAppointment";
import { CustomerLandingPageRejectedAppointment } from "./CustomerLandingPageRejectedAppointment";
import { CustomerLandingPageScheduledAppointment } from "./CustomerLandingPageScheduledAppointment";

export interface CustomerLandingPageContextValue {
    futureScheduledAppointments: CustomerLandingPageScheduledAppointment[],
    setFutureScheduledAppointments: UseStateSetter<CustomerLandingPageScheduledAppointment[]>,
    pastScheduledAppointments: CustomerLandingPageScheduledAppointment[],
    setPastScheduledAppointments: UseStateSetter<CustomerLandingPageScheduledAppointment[]>,
    pendingAppointments: CustomerLandingPagePendingAppointment[],
    setPendingAppointments: UseStateSetter<CustomerLandingPagePendingAppointment[]>,
    rejectedAppointments: CustomerLandingPageRejectedAppointment[],
    setRejectedAppointments: UseStateSetter<CustomerLandingPageRejectedAppointment[]>,
}

export const CustomerLandingPageContext = createContext<CustomerLandingPageContextValue | null>(null);