import { FC } from "react";
import { useReportsApi } from "../../../api/reports-api";
import { ReportButton } from "../template/ReportButton";

export interface ReportEnterpriseButtonProps {
    enterpriseId: number,
}

export const ReportEnterpriseButton: FC<ReportEnterpriseButtonProps> = ({ enterpriseId }) => {
    const reportsApi = useReportsApi();

    const submitReport = (text: string) => {
        return reportsApi.createReport({
            reportSubjectType: "ENTERPRISE",
            reportSubjectId: enterpriseId,
            text,
        });
    };

    return <ReportButton submitReport={submitReport} />;
}