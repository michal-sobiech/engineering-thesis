import { FlexProps, Text } from "@chakra-ui/react";
import { FC, ReactNode, useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useServicesApi } from "../../../api/services-api";
import { RatingOutOf5Display } from "../../../common/RatingOutOf5Display";
import { ReportReviewButton } from "../../../common/report/review/ReportReviewButton";
import { ScrollableList } from "../../../common/ScrollableList";
import { StandardBox } from "../../../common/StandardBox";
import { StandardFlex } from "../../../common/StandardFlex";
import { routes } from "../../../router/routes";

export type ServicePublicPageReviewList = FlexProps & {
    serviceId: number;
}

export const ServicePublicPageReviewList: FC<ServicePublicPageReviewList> = ({ serviceId, ...props }) => {
    const servicesApi = useServicesApi();
    const navigate = useNavigate();

    const [reviewsData, setReviewsData] = useState<Item[]>([]);

    useEffect(() => {
        servicesApi.getEnterpriseServiceReviews(serviceId)
            .then(response => {
                const items: Item[] = response.map((item, index) => ({
                    reactKey: index,
                    reviewId: item.reviewId,
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

    if (items.length > 0) {
        return <ScrollableList {...props}>
            {items}
        </ScrollableList>;
    } else {
        return <Text>No reviews yet!</Text>;
    }
}

interface Item {
    reactKey: number;
    reviewId: number;
    text: string;
    numStarsOutOf5: number;
}

const Item: FC<Item> = ({ reactKey, reviewId, text, numStarsOutOf5 }) => {
    return <StandardBox key={reactKey}>
        <StandardFlex direction="row" justifyContent="space-between">
            <StandardFlex direction="column">
                <RatingOutOf5Display rating={numStarsOutOf5} />
                <Text>{text}</Text>
            </StandardFlex>
            <StandardFlex direction="column">
                <ReportReviewButton reviewId={reviewId} />
            </StandardFlex>
        </StandardFlex>
    </StandardBox>;
}