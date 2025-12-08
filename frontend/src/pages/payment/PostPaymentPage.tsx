import { Center, Text } from "@chakra-ui/react";
import { FC, useEffect } from "react";
import { redirect, useLoaderData, useNavigate } from "react-router";
import { useAdyenApi } from "../../api/adyen-api";
import { routes } from "../../router/routes";
import { toastError } from "../../utils/toast";

export interface PostPaymentPageProps {
    redirectUrl: string;
}

export const usePostPaymentPageLoaderData = () => {
    return useLoaderData() as { sessionId: string, sessionResultToken: string };
}

export const postPaymentPageLoader = ({ request }: { request: Request }) => {
    const url = new URL(request.url);

    const sessionId = url.searchParams.get("sessionId");
    if (sessionId === null) {
        toastError("Error occured during payment");
        throw redirect(routes.mainPage);
    }

    const sessionResult = url.searchParams.get("sessionResult");
    if (sessionResult === null) {
        toastError("Error occured during payment");
        throw redirect(routes.mainPage);
    }

    console.log(sessionId, sessionResult);

    return { sessionId, sessionResultToken: sessionResult };
};

export const PostPaymentPage: FC<PostPaymentPageProps> = ({ redirectUrl }) => {
    const adyenApi = useAdyenApi();
    const navigate = useNavigate()

    const { sessionId, sessionResultToken } = usePostPaymentPageLoaderData();

    console.log(sessionId, sessionResultToken);

    useEffect(() => {
        async function sendResult() {
            adyenApi.sendAdyenSessionResult({
                sessionId,
                sessionResultToken
            }).then(() => {
                navigate(redirectUrl);
                // console.log("123", redirectUrl);
            }).catch();
        }
        sendResult();
    }, []);

    return <Center height="100%">
        <Text>
            Loading. Please do not close this page.
        </Text>
    </Center>;

}