import { Flex } from "@chakra-ui/react";
import { FC, useState } from "react";
import { Position } from "../../../utils/Position";
import { toastError, toastSuccess } from "../../../utils/toast";
import { StandardBox } from "../../StandardBox";
import { StandardButton } from "../../StandardButton";
import { StandardFlex } from "../../StandardFlex";
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

    const onSubmitClick = () => {
        if (text === "") {
            toastError("Enter report description");
            return;
        }
        submitReport(text);
        toastSuccess("Report submitted!");
    };

    return <StandardBox
        position="fixed"
        left={position.x}
        top={position.y}
        zIndex={9999}>
        <StandardFlex>
            <StandardTextArea text={text} setText={setText} />
            <Flex direction="row" gap="5px">
                <StandardButton
                    onClick={onDiscardClick}
                    delayMs={0}
                    width="100%"
                    backgroundColor="primary.darkRed">
                    Discard
                </StandardButton>
                <StandardButton
                    backgroundColor="primary.lightGreen"
                    onClick={onSubmitClick}>
                    Submit
                </StandardButton>
            </Flex>
        </StandardFlex>
    </StandardBox >;
}