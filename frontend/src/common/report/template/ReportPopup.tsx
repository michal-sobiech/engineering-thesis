import { Flex } from "@chakra-ui/react";
import { FC, useState } from "react";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error";
import { Position } from "../../../utils/Position";
import { toastError, toastSuccess } from "../../../utils/toast";
import { StandardButton } from "../../StandardButton";
import { StandardFlex } from "../../StandardFlex";
import { StandardPanel } from "../../StandardPanel";
import { StandardTextArea } from "../../StandardTextArea";

export interface ReportPopupProps {
    submitReport: (text: string) => Promise<void>;
    position: Position;
    close: () => void;
}

export const ReportPopup: FC<ReportPopupProps> = ({ submitReport, position, close }) => {
    const [text, setText] = useState<string>("");

    const onDiscardClick = () => {
        close();
    }

    const onSubmitClick = async () => {
        if (text === "") {
            toastError("Enter report description");
            return;
        }
        try {
            await submitReport(text);
            toastSuccess("Report submitted!");
        } catch (error: unknown) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
        }
        close();
    };

    return <StandardPanel
        position="fixed"
        left={position.x}
        top={position.y}
        zIndex={9999}>
        <StandardFlex>
            <StandardTextArea text={text} setText={setText} />
            <Flex direction="row" gap="5px">
                <StandardButton
                    flex={1}
                    onClick={onDiscardClick}
                    delayMs={0}
                    backgroundColor="primary.darkRed">
                    Cancel
                </StandardButton>
                <StandardButton
                    flex={1}
                    backgroundColor="primary.lightGreen"
                    onClick={onSubmitClick}>
                    Submit
                </StandardButton>
            </Flex>
        </StandardFlex>
    </StandardPanel >;
}