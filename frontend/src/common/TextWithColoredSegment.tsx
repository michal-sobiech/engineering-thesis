import { Text, TextProps } from "@chakra-ui/react";
import { SystemProperties } from "@chakra-ui/react/dist/types/styled-system/generated/system.gen";
import { FC } from "react";

export type TextWithColoredSegmentProps = TextProps & {
    textBeforeSegment?: string;
    segmentText?: string;
    textAfterSegment?: string;
    segmentColor?: SystemProperties["color"];
    segmentWeight?: SystemProperties["fontWeight"];
}

export const TextWithColoredSegment: FC<TextWithColoredSegmentProps> = ({ textBeforeSegment, segmentText, textAfterSegment, segmentColor, segmentWeight, ...otherProps }) => {
    return <Text {...otherProps}>
        {textBeforeSegment}
        <Text as="span" color={segmentColor} fontWeight={segmentWeight} >
            {segmentText}
        </Text>
        {textAfterSegment}
    </Text>
}