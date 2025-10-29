import { IndependentEndUserLogInPage } from "../../../common/log_in/LogInPage"
import { routes } from "../../../router/routes"

export const EntrepreneurLogInPage = () => {
    return <IndependentEndUserLogInPage userGroup={"ENTREPRENEUR"} landingPageUrl={routes.entrepreneurLandingPage} />
}