import { createSystem, defaultConfig, defineConfig } from "@chakra-ui/react";

const systemConfig = defineConfig({
    theme: {
        tokens: {
            colors: {
                primary: {
                    blue: { value: "#0000CC" },
                    lightBlue: { value: "#0A0AFF" },
                    lightGreen: { value: "#0fd20f" },
                    lightGray: { value: "#bdbdbd" },
                    gold: { value: "#ffe134" },
                    purple: { value: "#9700e4" },
                    darkGray: { value: "#2b2727ff" },
                    basicWhite: { value: "#ffffff" },
                    darkRed: { value: "#c40707" },
                    veryDarkRed: { value: "#8c1313" },
                    veryLightBlue: { value: "#6284ebff" },
                }
            },
            // fontSizes: {
            //     // xs: {
            //     //     value: "clamp(12px, 1.5vw + 4px, 14px)"
            //     // },
            //     xl: {
            //         value: "clamp(16px, 1.5vw, 24px)"
            //     }
            // }
        },
        // breakpoints: {
        //     base: "0px",
        //     sm: "30em",
        //     "md": "100em"
        // }
    }
})

export const system = createSystem(defaultConfig, systemConfig);