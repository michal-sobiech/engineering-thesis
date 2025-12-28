import { FC } from "react";
import { useReportsApi } from "../../../api/reports-api";
import { ReportButton } from "../template/ReportButton";

export interface ReportEnterpriseServiceButtonProps {
    enterpriseServiceId: number,
}

export const ReportEnterpriseServiceButton: FC<ReportEnterpriseServiceButtonProps> = ({ enterpriseServiceId }) => {
    const reportsApi = useReportsApi();

    const submitReport = (text: string) => {
        return reportsApi.createReport({
            reportSubjectType: "ENTERPRISE_SERVICE",
            reportSubjectId: enterpriseServiceId,
            text,
        });
    };

    return <ReportButton submitReport={submitReport} />;
}