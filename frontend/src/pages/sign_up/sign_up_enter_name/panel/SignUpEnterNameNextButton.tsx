import { toast } from "react-toastify";
import { StandardButton } from "../../../../common/StandardButton";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { signUpWizardContext } from "../../wizard/SignUpWizardContext";

export const SignUpEnterNameNextButton = () => {
    const { incrementStep } = useContextOrThrow(signUpWizardContext);

    const onClick = async () => {
        toast.dismiss();
        incrementStep();
    }

    return <StandardButton onClick={onClick} >
        Next
    </StandardButton>;
}