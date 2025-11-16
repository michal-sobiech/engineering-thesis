import { createContext } from "react";
import { UseStateSetter } from "../../../utils/use-state";
import { AdminReportsPageUnresolvedEnterpriseReport, AdminReportsPageUnresolvedReviewReport, AdminReportsPageUnresolvedServiceReport } from "./AdminReportsPageUnresolvedReport";

export interface AdminReportsPageContextValue {
    unresolvedEnterpriseReports: AdminReportsPageUnresolvedEnterpriseReport[],
    setUnresolvedEnterpriseReports: UseStateSetter<AdminReportsPageUnresolvedEnterpriseReport[]>,
    unresolvedServiceReports: AdminReportsPageUnresolvedServiceReport[],
    setUnresolvedServiceReports: UseStateSetter<AdminReportsPageUnresolvedServiceReport[]>,
    unresolvedReviewReports: AdminReportsPageUnresolvedReviewReport[],
    setUnresolvedReviewReports: UseStateSetter<AdminReportsPageUnresolvedReviewReport[]>,
}

export const AdminReportsPageContext = createContext<AdminReportsPageContextValue | null>(null);