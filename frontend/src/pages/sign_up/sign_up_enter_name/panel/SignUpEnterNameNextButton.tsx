import { StandardButton } from "../../../../common/StandardButton";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { signUpWizardContext } from "../../wizard/SignUpWizardContext";

export const SignUpEnterNameNextButton = () => {
    const { incrementStep } = useContextOrThrow(signUpWizardContext);

    return <StandardButton
        text="Next"
        onClick={() => incrementStep()}
    />;
}