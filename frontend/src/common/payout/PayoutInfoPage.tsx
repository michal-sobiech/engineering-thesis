import { Box, Center, Text } from "@chakra-ui/react";
import { FC } from "react";
import { isIban } from "../../utils/iban";
import { toastError } from "../../utils/toast";
import { UseStateSetter } from "../../utils/use-state";
import { StandardButton } from "../StandardButton";
import { StandardFlex } from "../StandardFlex";
import { StandardLabeledContainer } from "../StandardLabeledContainer";
import { StandardPanel } from "../StandardPanel";
import { StandardTextField } from "../StandardTextField";

export interface PayoutInfoPageProps {
    submit: () => Promise<void>;
    iban: string;
    setIban: UseStateSetter<string>;
}

export const PayoutInfoPage: FC<PayoutInfoPageProps> = ({
    submit,
    iban,
    setIban,
}) => {

    const onClick = () => {
        if (iban === "") {
            toastError("Enter IBAN");
            return;
        }

        if (!isIban(iban)) {
            toastError("Invalid IBAN");
            return;
        }

        submit();
    }

    return <Center height="100%">
        <Box width="40%">
            <StandardPanel height="100%">
                <StandardFlex height="100%">
                    <Text textAlign="center">
                        Enter information needed for payment
                    </Text>
                    <StandardLabeledContainer label="IBAN">
                        <StandardTextField text={iban} setText={setIban} />
                    </StandardLabeledContainer>
                    <StandardButton onClick={onClick}>
                        Next
                    </StandardButton>
                </StandardFlex>
            </StandardPanel>
        </Box>
    </Center >;
}