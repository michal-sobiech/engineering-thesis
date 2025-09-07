import { Context, useContext } from "react";
import { assertDefined } from "./AssertUtils";

export function useContextOrThrow<T>(context: Context<T | null>): T {
    const contextValue = useContext<T | null>(context);
    return assertDefined(contextValue);
}