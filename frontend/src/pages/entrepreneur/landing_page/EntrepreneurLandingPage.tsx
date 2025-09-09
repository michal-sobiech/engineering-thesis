import { Flex } from "@chakra-ui/react"
import { EnterprisesScrollableList } from "./EntreprisesScrollableList"

export const EntrepreneurLandingPage = () => {
    return <Flex
        direction="column">
        <EnterprisesScrollableList />
    </Flex>
}