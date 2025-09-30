import { Flex } from "@chakra-ui/react";
import { useNavigate } from "react-router";
import { FullSpaceCenter } from "../../common/FullSpaceCenter";
import { StandardButton } from "../../common/StandardButton";
import { TextWithColoredSegment } from "../../common/TextWithColoredSegment";
import { toastError } from "../../utils/toast";

export const MainPageEntrepreneurPanel = () => {
    const navigate = useNavigate();

    const onClick = () => {
        // navigate(routes.entrepreneurLogIn)
        toastError("Not implemented yet");
    }

    return <FullSpaceCenter>
        <Flex direction="vertical">
            <TextWithColoredSegment
                textBeforeSegment="Log in as "
                segmentText="entrepreneur"
                segmentColor="primary.purple"
                segmentWeight="bold"
                fontSize="xxl"
            />
            <StandardButton onClick={onClick}>
                Log in
            </StandardButton>
        </Flex>
    </FullSpaceCenter>;
}