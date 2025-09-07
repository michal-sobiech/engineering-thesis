import { Button } from "@chakra-ui/react";
import { useState } from "react";
import { toast } from "react-toastify";
import { independentEndUsersApi } from "../../../api/independent-end-users-api";
import { useContextOrThrow } from "../../../utils/useContextOrThrow";
import { signUpWizardContext } from "../wizard/SignUpWizardContext";

export const CreateAccountButton = () => {
    const { incrementStep, email, firstName, lastName, password } = useContextOrThrow(signUpWizardContext);
    const [loading, setLoading] = useState<boolean>(false);

    const onClick = async () => {
        setLoading(true);
        try {
            await independentEndUsersApi.createIndependentEndUser({ email, firstName, lastName, password });
            incrementStep();
        } catch {
            toast.error("Couldn't create account. Try again later.", { autoClose: 10000 });
        } finally {
            setLoading(false);
        }
    }

    return <Button
        bg="primary.blue"
        p="5"
        rounded="lg"
        shadow="lg"
        direction="column"
        gap="10px"
        onClick={onClick}>
        Create account
    </Button>;
}
