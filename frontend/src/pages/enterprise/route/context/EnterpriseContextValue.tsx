import { VoidCallback } from "../../../../common/VoidCallback";
import { Location } from "../../../../GENERATED-api";

export interface EnterpriseContextValue {
    enterpriseName: string;
    setEnterpriseName: VoidCallback<string>;
    enterpriseDescription: string | null;
    setEnterpriseDescription: VoidCallback<string>;
    enterpriseLocation: Location | null;
    setEnterpriseLocation: VoidCallback<Location | null>;
}