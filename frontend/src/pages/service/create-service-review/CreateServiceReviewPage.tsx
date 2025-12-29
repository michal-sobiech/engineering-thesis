import { Box, Center, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useServicesApi } from "../../../api/services-api";
import { FiveStarPicker } from "../../../common/FiveStarPicker";
import { useServiceIdFromLoader } from "../../../common/loader/service-id-loader";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardPanel } from "../../../common/StandardPanel";
import { StandardTextArea } from "../../../common/StandardTextArea";
import { GetEnterpriseService200Response } from "../../../GENERATED-api";
import { useNavigateWithToastDismiss } from "../../../hooks/useNavigateWithToastDismiss";
import { routes } from "../../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { toastError } from "../../../utils/toast";

export const CreateServiceReviewPage = () => {
    const servicesApi = useServicesApi();
    const serviceId = useServiceIdFromLoader();
    const navigate = useNavigateWithToastDismiss();

    const [serviceData, setServiceData] = useState<GetEnterpriseService200Response | null>(null);
    const [numStarsOutOf5, setNumStarsOutOf5] = useState<number | null>(null);
    const [reviewText, setReviewText] = useState<string>("");

    useEffect(() => {
        async function loadData() {
            const promise = servicesApi.getEnterpriseService(serviceId);
            const resultAsync = errorErrResultAsyncFromPromise(promise);
            const result = await resultAsync;
            if (result.isErr()) {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
                navigate(routes.mainPage);
                return;
            }
            setServiceData(result.value);
        }
        loadData();
    }, []);

    const onSubmitClick = () => {
        if (numStarsOutOf5 === null) {
            toastError("Choose a star rating");
            return;
        }

        if (reviewText === "") {
            toastError("Describe your experience");
            return;
        }

        servicesApi.createEnterpriseServiceReview(serviceId, {
            numStarsOutOf5,
            text: reviewText,
        });
        navigate(routes.servicePublicPage(serviceId));
    }

    if (serviceData === null) {
        return null;
    } else {
        return <Center height="100%">
            <Box width="80%" height="100%">
                <StandardPanel>
                    <StandardFlex>
                        <Text>
                            Write a review for {serviceData.name}
                        </Text>
                        <FiveStarPicker numStarsOutOf5={numStarsOutOf5} setNumStarsOutOf5={setNumStarsOutOf5} />
                        <StandardTextArea
                            placeholder="Share your experience"
                            text={reviewText}
                            setText={setReviewText}
                        />
                        <StandardButton onClick={onSubmitClick}>
                            Submit review
                        </StandardButton>
                    </StandardFlex>
                </StandardPanel>
            </Box>
        </Center>;
    }
}