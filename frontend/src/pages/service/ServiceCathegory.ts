export enum ServiceCathegory {
    BEAUTY_AND_WELLNESS,
    HAIRDRESSER,
    BARBER,

    FAMILY_MEDICINE,
    INTERNIST,
    PEDIATRICS,
    PSYCHIATRY,
    STOMATOGLOGY,
    GYNECOLOGY,

    HANDYMAN,
    PLUMBER,
    ELECTRICICAN,

    COMPUTER_GRAPHICS,
    PROGRAMMER,
    IT_HELP,
}

export const serviceCathegoryLabels = {
    [ServiceCathegory.BEAUTY_AND_WELLNESS]: "Beauty & Wellness",
    [ServiceCathegory.HAIRDRESSER]: "Hairdresser",
    [ServiceCathegory.BARBER]: "Barber",
    [ServiceCathegory.FAMILY_MEDICINE]: "Family medicine",
    [ServiceCathegory.INTERNIST]: "Internist",
    [ServiceCathegory.PEDIATRICS]: "Pediatrics",
    [ServiceCathegory.PSYCHIATRY]: "Psychiatry",
    [ServiceCathegory.STOMATOGLOGY]: "Stomatology",
    [ServiceCathegory.GYNECOLOGY]: "Gynecology",
    [ServiceCathegory.HANDYMAN]: "Handyman",
    [ServiceCathegory.PLUMBER]: "Plumber",
    [ServiceCathegory.ELECTRICICAN]: "Electrician",
    [ServiceCathegory.COMPUTER_GRAPHICS]: "Computer graphics",
    [ServiceCathegory.PROGRAMMER]: "Programmer",
    [ServiceCathegory.IT_HELP]: "IT Help",
} as const satisfies Record<ServiceCathegory, string>;