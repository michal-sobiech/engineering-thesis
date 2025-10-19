import { FC } from "react";
import { LinkText } from "./LinkText";
import { ScrollableList } from "./ScrollableList";

export interface LinkScrollableListItem {
    label: string;
    url: string;
}

export interface LinkScrollableListProps {
    items: LinkScrollableListItem[];
}

export const LinkScrollableList: FC<LinkScrollableListProps> = ({ items }) => {
    const itemComponents = items.map(createItemComponent);

    return <ScrollableList>
        {itemComponents}
    </ScrollableList>;
}

function createItemComponent(item: LinkScrollableListItem) {
    return <LinkText url={item.url} fontSize="xs">
        {item.label}
    </LinkText>
}