import { Text } from "@chakra-ui/react";
import { FC } from "react";

export interface RatingOutOf5DisplayProps {
    rating: number
}

export const RatingOutOf5Display: FC<RatingOutOf5DisplayProps> = ({ rating }) => {
    const ratingFormatted = rating.toFixed(2);

    return <Text>
        {ratingFormatted} / 5
    </Text>;
}