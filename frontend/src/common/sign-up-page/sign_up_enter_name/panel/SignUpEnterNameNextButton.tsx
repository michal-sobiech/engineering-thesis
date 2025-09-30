import { toast } from "react-toastify";
import { toastError } from "../../../../utils/toast";
import { useContextOrThrow } from "../../../../utils/useContextOrThrow";
import { StandardButton } from "../../../StandardButton";
import { signUpWizardContext } from "../../wizard/SignUpWizardContext";

export const SignUpEnterNameNextButton = () => {
    const { incrementStep, firstName, lastName } = useContextOrThrow(signUpWizardContext);

    const onClick = async () => {
        if (firstName === "") {
            toastError("Please enter your first name")
            return
        }

        if (lastName === "") {
            toastError("Please enter your last name");
            return
        }

        toast.dismiss();
        incrementStep();
    }

    return <StandardButton onClick={onClick} >
        Next
    </StandardButton>;
}