import { FC } from "react";
import { useReportsApi } from "../../../api/reports-api";
import { ReportButton } from "../template/ReportButton";

export interface ReportServiceButtonProps {
    serviceId: number,
}

export const ReportServiceButton: FC<ReportServiceButtonProps> = ({ serviceId }) => {
    const reportsApi = useReportsApi();

    const submitReport = (text: string) => {
        return reportsApi.createReport({
            reportSubjectType: "ENTERPRISE_SERVICE",
            reportSubjectId: serviceId,
            text,
        });
    };

    return <ReportButton submitReport={submitReport} />;
}