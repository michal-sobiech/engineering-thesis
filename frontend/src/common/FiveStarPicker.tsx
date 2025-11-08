import { HStack, Icon, IconButton } from "@chakra-ui/react";
import { FC } from "react";
import { FaStar } from "react-icons/fa";
import { UseStateSetter } from "../utils/useState";

export interface FiveStarPickerProps {
    numStarsOutOf5: number | null,
    setNumStarsOutOf5: UseStateSetter<number | null>;
}

export const FiveStarPicker: FC<FiveStarPickerProps> = ({ numStarsOutOf5, setNumStarsOutOf5 }) => {
    const startButtons = [1, 2, 3, 4, 5].map(createStarButton);

    function createStarButton(rating: number) {
        const isFull = numStarsOutOf5 !== null && rating <= numStarsOutOf5;

        return <IconButton
            key={rating}
            colorScheme={isFull ? "primary.gold" : "primary.darkGray"}>
            <Icon as={FaStar as any} />
        </IconButton>
    }

    return <HStack>
        {startButtons}
    </HStack>;
}