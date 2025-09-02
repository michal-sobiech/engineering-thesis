import { createSystem, defaultConfig, defineConfig } from "@chakra-ui/react";

const systemConfig = defineConfig({
    theme: {
        tokens: {
            fontSizes: {
                xs: {
                    value: "clamp(12px, 1.5vw, 14px)"
                },
                xl: {
                    value: "clamp(16px, 1.5vw, 24px)"
                }
            }
        },
        breakpoints: {
            base: "0px",
            sm: "30em",
            "md": "100em"
        }
    }
})

export const system = createSystem(defaultConfig, systemConfig);