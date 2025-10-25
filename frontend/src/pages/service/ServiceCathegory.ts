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
} as const satisfies Record<ServiceCathegory, string>;