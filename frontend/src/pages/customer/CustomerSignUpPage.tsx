import { ResultAsync } from "neverthrow";
import { useCustomersApi } from "../../api/customers-api";
import { IndependentEndUserSignUpWizard } from "../../common/sign-up-page/wizard/SignUpWizard";
import { CreateIndependentEndUserRequest } from "../../GENERATED-api";
import { routes } from "../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../utils/result";

export const CustomerSignUpPage = () => {
    const customersApi = useCustomersApi();

    function createUser(request: CreateIndependentEndUserRequest): ResultAsync<void, Error> {
        const promise = customersApi.createCustomer(request);
        return errorErrResultAsyncFromPromise(promise).map(() => { });
    }

    return <IndependentEndUserSignUpWizard logInPageUrl={routes.customerLogIn} createUser={createUser} />;
}