import { createContext } from "react";
import { UseStateSetter } from "../../../utils/useState";
import { AdminLandingPageUnresolvedEnterpriseReport, AdminLandingPageUnresolvedReviewReport, AdminLandingPageUnresolvedServiceReport } from "./AdminLandingPageUnresolvedReport";

export interface AdminLandingPageContextValue {
    unresolvedEnterpriseReports: AdminLandingPageUnresolvedEnterpriseReport[],
    setUnresolvedEnterpriseReports: UseStateSetter<AdminLandingPageUnresolvedEnterpriseReport[]>,
    unresolvedServiceReports: AdminLandingPageUnresolvedServiceReport[],
    setUnresolvedServiceReports: UseStateSetter<AdminLandingPageUnresolvedServiceReport[]>,
    unresolvedReviewReports: AdminLandingPageUnresolvedReviewReport[],
    setUnresolvedReviewReports: UseStateSetter<AdminLandingPageUnresolvedReviewReport[]>,
}

export const AdminLandingPageContext = createContext<AdminLandingPageContextValue | null>(null);