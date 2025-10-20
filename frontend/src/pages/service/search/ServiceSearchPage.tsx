import { Box, Center, Heading } from "@chakra-ui/react"
import { useState } from "react"
import { StandardFlex } from "../../../common/StandardFlex"
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer"
import { StandardPanel } from "../../../common/StandardPanel"
import { StandardTextField } from "../../../common/StandardTextField"
import { ServiceCathegory, serviceCathegoryLabels } from "../ServiceCathegory"
import { ServiceCathegoryPicker } from "../ServiceCathegoryPicker"

export const ServiceSearchPage = () => {
    const [serviceName, setServiceName] = useState<string>("");
    const [serviceCathegory, setServiceCathegory] = useState<ServiceCathegory | null>(null);

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
                </StandardFlex>
            </StandardPanel>
        </Box>
    </Center>
}