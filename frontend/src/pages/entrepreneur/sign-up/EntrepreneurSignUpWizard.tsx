import { ResultAsync } from "neverthrow";
import { entrepreneursApi } from "../../../api/entrepreneurs-api";
import { SignUpWizard } from "../../../common/sign-up-page/wizard/SignUpWizard";
import { CreateIndependentEndUserRequest } from "../../../GENERATED-api";
import { routes } from "../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";

export const EntrepreneurSignUpWizard = () => {

    function createUser(request: CreateIndependentEndUserRequest): ResultAsync<void, Error> {
        const promise = entrepreneursApi.createEntrepreneur(request);
        let result = errorErrResultAsyncFromPromise(promise);
        const voidResult = result.map(() => { });
        return voidResult;
    }

    return <SignUpWizard logInPageUrl={routes.entrepreneurLogIn} createUser={createUser} />;
}