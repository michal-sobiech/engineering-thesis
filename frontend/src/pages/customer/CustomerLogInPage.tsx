import { IndependentEndUserLogInPage } from "../../common/log_in/LogInPage"
import { routes } from "../../router/routes"

export const CustomerLogInPage = () => {
    return <IndependentEndUserLogInPage landingPageUrl={routes.customerLandingPage} />
}