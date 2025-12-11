export enum ServiceCathegory {
    BEAUTY_AND_WELLNESS = "BEAUTY_AND_WELLNESS",
    HAIRDRESSER = "HAIRDRESSER",
    BARBER = "BARBER",

    FAMILY_MEDICINE = "FAMILY_MEDICINE",
    INTERNIST = "INTERNIST",
    PEDIATRICS = "PEDIATRICS",
    PSYCHIATRY = "PSYCHIATRY",
    STOMATOGLOGY = "STOMATOGLOGY",
    GYNECOLOGY = "GYNECOLOGY",

    HANDYMAN = "HANDYMAN",
    PLUMBER = "PLUMBER",
    ELECTRICICAN = "ELECTRICICAN",
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

export function isServiceCathegory(value: string): value is ServiceCathegory {
    return Object.values(ServiceCathegory).includes(value as ServiceCathegory);
}