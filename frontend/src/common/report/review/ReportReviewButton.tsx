import { FC } from "react";
import { useReportsApi } from "../../../api/reports-api";
import { ReportButton } from "../template/ReportButton";

export interface ReportReviewButtonProps {
    reviewId: number,
}

export const ReportReviewButton: FC<ReportReviewButtonProps> = ({ reviewId }) => {
    const reportsApi = useReportsApi();

    const sumbitReport = async (text: string) => {
        reportsApi.createReport({
            reportSubjectType: "REVIEW",
            reportSubjectId: reviewId,
            text,
        });
    }

    return <ReportButton submitReport={sumbitReport} />;
}