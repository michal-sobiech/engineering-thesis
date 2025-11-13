import { Flex } from "@chakra-ui/react";
import { useNavigate } from "react-router";
import { FullSpaceCenter } from "../../common/FullSpaceCenter";
import { StandardButton } from "../../common/StandardButton";
import { TextWithColoredSegment } from "../../common/TextWithColoredSegment";
import { routes } from "../../router/routes";

export const MainPageEntrepreneurPanel = () => {
    const navigate = useNavigate();

    const onClick = () => {
        navigate(routes.entrepreneurSignUpOrLogIn)
    }

    return <FullSpaceCenter>
        <Flex direction="column" gap="5px">
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