import { ResultAsync } from "neverthrow";
import { useEntrepreneursApi } from "../../../api/entrepreneurs-api";
import { IndependentEndUserSignUpWizard } from "../../../common/sign-up-page/wizard/SignUpWizard";
import { CreateIndependentEndUserRequest } from "../../../GENERATED-api";
import { routes } from "../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";

export const EntrepreneurSignUpWizard = () => {
    const entrepreneursApi = useEntrepreneursApi();

    function createUser(request: CreateIndependentEndUserRequest): ResultAsync<void, Error> {
        const promise = entrepreneursApi.createEntrepreneur(request);
        let result = errorErrResultAsyncFromPromise(promise);
        const voidResult = result.map(() => { });
        return voidResult;
    }

    return <IndependentEndUserSignUpWizard logInPageUrl={routes.entrepreneurLogIn} createUser={createUser} />;
}