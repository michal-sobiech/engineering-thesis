import { VoidCallback } from "../../../../common/VoidCallback";

export interface EnterpriseContextValue {
    enterpriseName: string;
    setEnterpriseName: VoidCallback<string>;
    enterpriseDescription: string | null;
    setEnterpriseDescription: VoidCallback<string>;
    enterpriseLocation: string | null;
    setEnterpriseLocation: VoidCallback<string>;
}