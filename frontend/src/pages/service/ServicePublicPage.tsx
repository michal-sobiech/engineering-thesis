import { Center, Text } from "@chakra-ui/react"
import { StandardFlex } from "../../common/StandardFlex"
import { StandardPanel } from "../../common/StandardPanel"

export const ServicePublicPage = () => {
    return <Center height="100vh">
        <StandardPanel width="80%" height="90vh" padding="20px" >
            <StandardFlex>
                <Text fontSize="3xl">Service</Text>
                <Text>Enterprise</Text>
                {/* < */}
            </StandardFlex>
        </StandardPanel>
    </Center >
}