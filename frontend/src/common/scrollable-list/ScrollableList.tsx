import { Box, Flex } from "@chakra-ui/react";
import { ListProps } from "@chakra-ui/react/dist/types/components/breadcrumb/namespace";
import React, { isValidElement, ReactElement } from "react";
import { Renderable } from "../../utils/Renderable";
import { ScrollableListSeparator } from "./ScrollableListSeparator";

export const ScrollableList: React.FC<ListProps> = ({ children, ...props }) => {
    const childrenArray = React.Children.toArray(children);
    const childrenAndSeparators = addSeparators(childrenArray);
    const childrenAndSeparatorsValidElems = childrenAndSeparators.filter(isValidElement);
    const childrenAndSeparatorsWithKeys = addWrapper(childrenAndSeparatorsValidElems);

    return <Flex overflowY="auto" maxHeight="100px" gap="2px" direction="column">
        {childrenAndSeparatorsWithKeys}
    </Flex>;
}

function addSeparators(elements: Renderable[]): Renderable[] {
    const out: Renderable[] = [];

    const lastIndex = elements.length - 1;
    elements.forEach((item, i) => {
        out.push(item);

        if (i != lastIndex) {
            const separator = <ScrollableListSeparator />;
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