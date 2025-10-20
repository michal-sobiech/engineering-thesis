import { Box } from "@chakra-ui/react";
import { JSX } from "react";
import { ScrollableList } from "../../../common/ScrollableList";
import { StandardConcaveBox } from "../../../common/StandardConcaveBox";
import { StandardPanel } from "../../../common/StandardPanel";
import { ServicesSearchServicesListItem } from "./ServiceSearchServicesListItem";

export const ServiceSearchServicesList = () => {
    // const [services, setServices] = useState<GetEnterpriseServicesResponseItem[]>([]);

    // useEffect(() => {
    //     async function loadServicesData(): Promise<void> {
    //         const promise = enterprisesApi.getEnterpriseServices(enterpriseId);
    //         const result = await errorErrResultAsyncFromPromise(promise);
    //         if (result.isOk()) {
    //             setServices(result.value);
    //         }
    //     }

    //     loadServicesData();
    // }, []);

    const services = [1, 2, 3, 4, 5];

    return <StandardConcaveBox minHeight="300px">
        <ScrollableList minHeight="300px" maxHeight="500px">
            {services.map(createItem)}
        </ScrollableList>
    </StandardConcaveBox>
}

// TODO params
function createItem(): JSX.Element {
    return <Box padding="3px 5px 3px 5px">
        <StandardPanel>
            <ServicesSearchServicesListItem
                serviceName={"Service name"}
                enterpriseName="---"
                time={new Date()}
            />
        </StandardPanel>
    </Box>
}