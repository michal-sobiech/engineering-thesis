import { IndependentEndUserLogInPage } from "../../common/independent-end-user-log-in/IndependentEndUserLogInPage"
import { routes } from "../../router/routes"

export const CustomerLogInPage = () => {
    return <IndependentEndUserLogInPage userGroup={"CUSTOMER"} landingPageUrl={routes.serviceSearch} />
}