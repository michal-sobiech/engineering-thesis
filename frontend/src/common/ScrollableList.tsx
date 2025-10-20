import { Box, Flex, FlexProps, Separator } from "@chakra-ui/react";
import React, { isValidElement, ReactElement } from "react";
import { Renderable } from "../utils/Renderable";

export const ScrollableList: React.FC<FlexProps> = ({ children, ...otherProps }) => {
    const childrenArray = React.Children.toArray(children);
    const childrenAndSeparators = addSeparators(childrenArray);
    const childrenAndSeparatorsValidElems = childrenAndSeparators.filter(isValidElement);
    const childrenAndSeparatorsWithKeys = addWrapper(childrenAndSeparatorsValidElems);

    return <Flex overflowY="auto" maxHeight="100px" gap="2px" direction="column" {...otherProps}>
        {childrenAndSeparatorsWithKeys}
    </Flex>;
}

function addSeparators(elements: Renderable[]): Renderable[] {
    const out: Renderable[] = [];

    const lastIndex = elements.length - 1;
    elements.forEach((item, i) => {
        out.push(item);

        if (i != lastIndex) {
            const separator = <Separator />;
            out.push(separator);
        }
    });

    return out;
}

function addWrapper(elements: ReactElement[]): ReactElement[] {
    return elements.map((element, i) => {
        return <Box
            padding="2px 8px 2px 8px"
            key={i}>
            {element}
        </ Box>;
    });
}