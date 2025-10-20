import { Box, Center, Heading } from "@chakra-ui/react"
import { useState } from "react"
import { LocationAutocomplete } from "../../../common/LocationAutocomplete"
import { StandardFlex } from "../../../common/StandardFlex"
import { StandardHorizontalSeparator } from "../../../common/StandardHorizontalSeparator"
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer"
import { StandardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"
import { ServiceCathegory, serviceCathegoryLabels } from "../ServiceCathegory"
import { ServiceCathegoryPicker } from "../ServiceCathegoryPicker"
import { ServiceSearchServicesList } from "./ServiceSearchServicesList"

export const ServiceSearchPage = () => {
    const [serviceName, setServiceName] = useState<string>("");
    const [serviceCathegory, setServiceCathegory] = useState<ServiceCathegory | null>(null);
    const [query, setQuery] = useState<string>("");

    const serviceCathegoryLabel = serviceCathegory === null
        ? "Choose a cathegory"
        : serviceCathegoryLabels[serviceCathegory];

    return <Center height="100vh">
        <Box width="80vw" height="100%">
            <StandardPanel>
                <StandardFlex>
                    <Heading>
                        Find a service
                    </Heading>
                    <StandardLabeledContainer label="Service name">
                        <StandardTextField text={serviceName} setText={setServiceName} placeholder="Female haircut" />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="Service cathegory">
                        <ServiceCathegoryPicker value={serviceCathegory} setValue={setServiceCathegory} />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="Preferred location">
                        <LocationAutocomplete query={query} setQuery={setQuery} />
                    </StandardLabeledContainer>

                    <StandardHorizontalSeparator />

                    <ServiceSearchServicesList />

                </StandardFlex>
            </StandardPanel>
        </Box>
    </Center>
}

