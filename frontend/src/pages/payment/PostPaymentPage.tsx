import { Center, Text } from "@chakra-ui/react";
import { FC } from "react";
import { useNavigate } from "react-router";
import { useAdyenApi } from "../../api/adyen-api";
import { useStringParam } from "../../hooks/useStringParam";

export interface PostPaymentPageProps {
    redirectUrl: string;
}

export const PostPaymentPage: FC<PostPaymentPageProps> = ({ redirectUrl }) => {
    const adyenApi = useAdyenApi();
    const navigate = useNavigate()

    const sessionId = useStringParam("sessionId");
    const sessionResultToken = useStringParam("sessionResult");

    // useEffect(() => {
    //     async function sendResult() {
    //         adyenApi.sendAdyenSessionResult({
    //             sessionId,
    //             sessionResultToken
    //         }).then(() => {
    //             navigate(redirectUrl);
    //         });
    //     }
    //     sendResult();
    // }, []);

    return <Center height="100%">
        <Text>
            Loading. Please do not close this page.
        </Text>
    </Center>;

}