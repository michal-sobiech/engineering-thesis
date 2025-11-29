import { Box, Center, Text } from "@chakra-ui/react";
import { LocalDate } from "@js-joda/core";
import { FC } from "react";
import { isPostalCode } from "../../utils/postal-code";
import { toastError } from "../../utils/toast";
import { UseStateSetter } from "../../utils/use-state";
import { LocalDatePicker } from "../LocalDatePicker";
import { StandardButton } from "../StandardButton";
import { StandardFlex } from "../StandardFlex";
import { StandardLabeledContainer } from "../StandardLabeledContainer";
import { StandardPanel } from "../StandardPanel";
import { StandardTextField } from "../StandardTextField";

export interface PayoutInfoPageProps {
    submit: () => Promise<void>;

    dateOfBirth: LocalDate | null;
    country: string;
    city: string;
    street: string;
    postalCode: string;
    iban: string;

    setDateOfBirth: UseStateSetter<LocalDate | null>;
    setCountry: UseStateSetter<string>;
    setStreet: UseStateSetter<string>;
    setPostalCode: UseStateSetter<string>;
    setCity: UseStateSetter<string>;
    setIban: UseStateSetter<string>;
}

export const PayoutInfoPage: FC<PayoutInfoPageProps> = ({
    submit,

    dateOfBirth,
    country,
    city,
    street,
    postalCode,
    iban,

    setDateOfBirth,
    setCountry,
    setCity,
    setStreet,
    setPostalCode,
    setIban,
}) => {

    const onClick = () => {
        if (dateOfBirth === null) {
            toastError("Enter date of birht");
            return;
        }

        if (dateOfBirth > LocalDate.now()) {
            toastError("Invalid date of birth");
            return;
        }

        if (country === "") {
            toastError("Enter country");
            return;
        }

        if (city === "") {
            toastError("Enter city");
            return;
        }

        if (street === "") {
            toastError("Enter street");
            return;
        }

        if (postalCode === "") {
            toastError("Enter postal code");
            return;
        }

        if (!isPostalCode(postalCode)) {
            toastError("Invalid postal code");
            return;
        }

        if (iban === "") {
            toastError("Enter IBAN");
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
                    <StandardLabeledContainer label="Date of birth">
                        <LocalDatePicker date={dateOfBirth} setDate={setDateOfBirth} />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="Country">
                        <StandardTextField text={country} setText={setCountry} />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="City">
                        <StandardTextField text={city} setText={setCity} />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="Street">
                        <StandardTextField text={street} setText={setStreet} />
                    </StandardLabeledContainer>
                    <StandardLabeledContainer label="Postal code">
                        <StandardTextField text={postalCode} setText={setPostalCode} />
                    </StandardLabeledContainer>
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