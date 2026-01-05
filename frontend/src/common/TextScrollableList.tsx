import { Text } from "@chakra-ui/react";
import { FC } from "react";
import { ScrollableList } from "./ScrollableList";

interface Props {
    items: string[];
}

export const TextScrollableList: FC<Props> = ({ items }) => {
    const itemComponents = items.map(createItemComponent);

    return <ScrollableList>
        {itemComponents}
    </ScrollableList>;
}

function createItemComponent(item: string) {
    return <Text fontSize="xs">{item}</Text>;
}