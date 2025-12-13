import { createContext } from "react";
import { GeoPosition } from "../../../../utils/GeoPosition";
import { StateUpdater } from "../../../../utils/StateUpdater";
import { UseStateSetter } from "../../../../utils/use-state";
import { WeeklyTimeWindow } from "../../../../utils/WeeklyTimeWindow";
import { ServiceCathegory } from "../../ServiceCathegory";

export interface StaffCustomServicePageContextValue {
    enterpriseName: string;
    setEnterpriseName: UseStateSetter<string>;
    serviceName: string;
    setServiceName: UseStateSetter<string>;
    serviceDescription: string;
    setServiceDescription: UseStateSetter<string>;
    address: string | null;
    setAddress: UseStateSetter<string | null>;
    position: GeoPosition | null;
    setPosition: UseStateSetter<GeoPosition | null>;
    timezone: string | null;
    setTimezone: UseStateSetter<string | null>;
    windows: WeeklyTimeWindow[];
    setWindows: StateUpdater<WeeklyTimeWindow[]>;
    price: number | null;
    setPrice: UseStateSetter<number | null>;
    cathegory: ServiceCathegory | null;
    setCathegory: UseStateSetter<ServiceCathegory | null>;
}

export const StaffCustomServicePageContext = createContext<StaffCustomServicePageContextValue | null>(null);