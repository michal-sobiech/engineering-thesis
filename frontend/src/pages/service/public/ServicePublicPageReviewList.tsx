import { Text } from "@chakra-ui/react";
import { FC, ReactNode, useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useServicesApi } from "../../../api/services-api";
import { RatingOutOf5Display } from "../../../common/RatingOutOf5Display";
import { ScrollableList } from "../../../common/ScrollableList";
import { StandardBox } from "../../../common/StandardBox";
import { StandardFlex } from "../../../common/StandardFlex";
import { routes } from "../../../router/routes";

export interface ServicePublicPageReviewList {
    serviceId: number;
}

export const ServicePublicPageReviewList: FC<ServicePublicPageReviewList> = ({ serviceId }) => {
    const servicesApi = useServicesApi();
    const navigate = useNavigate();

    const [reviewsData, setReviewsData] = useState<Item[]>([]);

    useEffect(() => {
        servicesApi.getEnterpriseServiceReviews(serviceId)
            .then(response => {
                const items: Item[] = response.map((item, index) => ({
                    reactKey: index,
                    text: item.text,
                    numStarsOutOf5: item.numStarsOutOf5,
                }));
                setReviewsData(items);
            })
            .catch(() => {
                navigate(routes.mainPage);
            });
    }, [])

    const items: ReactNode[] = reviewsData.map(review => <Item {...review} />);

    return <ScrollableList>
        {items}
    </ScrollableList>;
}

interface Item {
    reactKey: number;
    text: string;
    numStarsOutOf5: number;
}

const Item: FC<Item> = ({ reactKey, text, numStarsOutOf5 }) => {
    return <StandardBox key={reactKey}>
        <StandardFlex>
            <RatingOutOf5Display rating={numStarsOutOf5} />
            <Text>{text}</Text>
        </StandardFlex>
    </StandardBox>;
}