import { Flex } from "@chakra-ui/react";
import { FullSpaceCenter } from "../../common/FullSpaceCenter";
import { TextWithColoredSegment } from "../../common/TextWithColoredSegment";

export const MainPageCustomerPanel = () => {
    return <FullSpaceCenter>
        <Flex direction="vertical">
            <TextWithColoredSegment
                textBeforeSegment="Log in as "
                segmentText="customer"
                segmentColor="primary.gold"
                segmentWeight="bold"
                fontSize="xxl"
            />
        </Flex>
    </FullSpaceCenter>;
}