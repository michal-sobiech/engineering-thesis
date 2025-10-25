import { Flex } from "@chakra-ui/react";
import { useNavigate } from "react-router";
import { FullSpaceCenter } from "../../common/FullSpaceCenter";
import { StandardButton } from "../../common/StandardButton";
import { TextWithColoredSegment } from "../../common/TextWithColoredSegment";
import { routeTemplates } from "../../router/route-templates";

export const MainPageCustomerPanel = () => {
    const navigate = useNavigate();

    const onClick = () => {
        navigate(routeTemplates.customerSignUpOrLogIn)
    }

    return <FullSpaceCenter>
        <Flex direction="vertical">
            <TextWithColoredSegment
                textBeforeSegment="Log in as "
                segmentText="customer"
                segmentColor="primary.gold"
                segmentWeight="bold"
                fontSize="xxl"
            />
            <StandardButton onClick={onClick}>
                Log in
            </StandardButton>
        </Flex>
    </FullSpaceCenter>;
}