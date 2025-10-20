import { Box, Flex, Input } from "@chakra-ui/react";
import { OpenStreetMapProvider } from "leaflet-geosearch";
import { FC, JSX, useEffect, useState } from "react";
import { UseStateSetter } from "../utils/useState";
import { StandardPanel } from "./StandardPanel";

export interface LocationAutocompleteProps {
    query: string;
    setQuery: UseStateSetter<string>;
}

export const LocationAutocomplete: FC<LocationAutocompleteProps> = ({ query, setQuery }) => {
    const [results, setResults] = useState<any[]>([]);
    const [isFocused, setIsFocused] = useState<boolean>(false);

    const provider = new OpenStreetMapProvider();

    const handleChange = async (value: string) => {
        setQuery(value);
    };

    useEffect(() => {
        const timeout = setTimeout(async () => {
            if (query === "") {
                setResults([]);
                return;
            }
            const searchResults = await provider.search({ query });
            setResults(searchResults);
        }, 200);
        return () => clearTimeout(timeout);
    }, [query]);

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
                setQuery(suggestion.label);
                setResults([]);
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
                value={query}
                onChange={(event) => handleChange(event.target.value)}
                placeholder="-"
                onBlur={onBlur}
                onFocus={() => setIsFocused(true)}
            />
            {results.length > 0 && isFocused && <SuggestionsList suggestions={results} />}
        </Box >
    );
};
