import { routes } from "../../router/routes";
import { PostPaymentPage } from "./PostPaymentPage";

export const PostAppointmentPaymentPage = () => {
    return <PostPaymentPage redirectUrl={routes.customerLandingPage} />;
}