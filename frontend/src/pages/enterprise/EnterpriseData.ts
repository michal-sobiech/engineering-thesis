import { Location } from "../../GENERATED-api";

export interface EnterpriseData {
    name: string;
    description: string;
    location: Location;
    logo: File | null;
    backgroundPhoto: File | null;
}
