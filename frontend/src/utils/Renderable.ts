import { ReactNode } from "react";

export type Renderable = Exclude<ReactNode, boolean | null | undefined>;