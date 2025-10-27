import { Box, Flex, Input } from "@chakra-ui/react";
import { OpenStreetMapProvider } from "leaflet-geosearch";
import { FC, JSX, useEffect, useState } from "react";
import { Position } from "../utils/Position";
import { UseStateSetter } from "../utils/useState";
import { StandardPanel } from "./StandardPanel";

export interface LocationAutocompleteProps {
    position: Position | null;
    setPosition: UseStateSetter<Position | null>;
    address: string;
    setAddress: UseStateSetter<string>;
}

export const LocationAutocomplete: FC<LocationAutocompleteProps> = ({ position, setPosition, address, setAddress }) => {
    const [searchResults, setSearchResults] = useState<any[]>([]);
    const [isFocused, setIsFocused] = useState<boolean>(false);

    const provider = new OpenStreetMapProvider();

    const handleChange = async (value: string) => {
        setAddress(value);
    };

    useEffect(() => {
        const timeout = setTimeout(async () => {
            if (address === "") {
                setSearchResults([]);
                return;
            }
            const searchResults = await provider.search({ query: address });
            setSearchResults(searchResults);
        }, 200);
        return () => clearTimeout(timeout);
    }, [address]);

    const SuggestionsList = ({ suggestions }: { suggestions: any[] }) => {
        return <StandardPanel zIndex={9999} position="relative">
            <Flex direction="column" gap="2px">
                {suggestions.map(createSuggestionListItem)}
            </Flex>
        </StandardPanel>
    };

    function createSuggestionListItem(suggestion: any): JSX.Element {
        return <Box
            padding="2px 8px 2px 8px"
            key={suggestion.raw.place_id}
            onClick={() => {
                setAddress(suggestion.label);
                setPosition({ x: suggestion.x, y: suggestion.y });
                setSearchResults([]);
            }}
            cursor="pointer"
        >
            {suggestion.label}
        </Box>;
    }

    const onBlur = () => setTimeout(() => {
        setIsFocused(false);
    }, 200);

    return (
        <Box>
            <Input
                value={address}
                onChange={(event) => handleChange(event.target.value)}
                placeholder="-"
                onBlur={onBlur}
                onFocus={() => setIsFocused(true)}
            />
            {searchResults.length > 0 && isFocused && <SuggestionsList suggestions={searchResults} />}
        </Box >
    );
};
