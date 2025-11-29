import { Box, Center, Input, Text } from "@chakra-ui/react";
import { FC } from "react";
import { toast } from "react-toastify";
import { toastError } from "../../utils/toast";
import { UseStateSetter } from "../../utils/use-state";
import { StandardButton } from "../StandardButton";
import { StandardFlex } from "../StandardFlex";
import { StandardPanel } from "../StandardPanel";

export interface SignUpEnterNamePageProps {
    submit: () => void;
    firstName: string;
    setFirstName: UseStateSetter<string>;
    lastName: string;
    setLastName: UseStateSetter<string>;
}

export const SignUpEnterNamePage: FC<SignUpEnterNamePageProps> = ({ submit, firstName, setFirstName, lastName, setLastName }) => {
    const handleFirstNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFirstName(event.target.value);
    };

    const handleLastNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setLastName(event.target.value);
    };

    const onNextClick = async () => {
        if (firstName === "") {
            toastError("Please enter your first name")
            return;
        }

        if (lastName === "") {
            toastError("Please enter your last name");
            return;
        }

        toast.dismiss();
        submit();
    }

    return <Center height="100%">
        <Box width="40%">
            <StandardPanel>
                <StandardFlex>
                    <Text textAlign="center">
                        Enter your name
                    </Text>
                    <Input
                        type="text"
                        placeholder="First name"
                        value={firstName}
                        onChange={handleFirstNameChange}
                        required
                    />
                    <Input
                        type="text"
                        placeholder="Last name"
                        value={lastName}
                        onChange={handleLastNameChange}
                        required
                    />
                    <StandardButton onClick={onNextClick}>
                        Next
                    </StandardButton>
                </StandardFlex>
            </StandardPanel>
        </Box>
    </Center>;
};