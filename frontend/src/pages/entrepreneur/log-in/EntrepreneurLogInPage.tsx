import { IndependentEndUserLogInPage } from "../../../common/independent-end-user-log-in/IndependentEndUserLogInPage"
import { routes } from "../../../router/routes"

export const EntrepreneurLogInPage = () => {
    return <IndependentEndUserLogInPage userGroup={"ENTREPRENEUR"} landingPageUrl={routes.entrepreneurLandingPage} />
}