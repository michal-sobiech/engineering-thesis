import { FC } from "react";
import { useReportsApi } from "../../api/reports-api";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../utils/result";
import { toastError, toastSuccess } from "../../utils/toast";
import { ReportButton } from "./ReportButton";

export interface ReportReviewButtonProps {
    reviewId: number,
}

export const ReportReviewButton: FC<ReportReviewButtonProps> = ({ reviewId }) => {
    const reportsApi = useReportsApi();

    const onClick = async () => {
        const promise = reportsApi.createReport({
            reportSubjectType: "REVIEW",
            reportSubjectId: reviewId,
        });
        const result = await errorErrResultAsyncFromPromise(promise);
        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }
        toastSuccess("Reported review!");
    }

    return <ReportButton onClick={onClick} />
}