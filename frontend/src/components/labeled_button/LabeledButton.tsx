import { useState } from "react";
import { config } from "../../config/config";
import { joinUrl } from "../../utils/UrlUtils";
import Button from "./_internal/button/Button";
import Label from "./_internal/label/Label";


const createUrl = (): URL => {
    return joinUrl(config.backendUrl, "sign_up");
};

const onClick = () => {
    fetch(createUrl())
        .then(response => response.status);
};

const LabeledButton = () => {
    const [label, setLabel] = useState<string>("");

    return <div>
        <Button onClick={onClick} />
        <Label text={label} />
    </div>;
}

export default LabeledButton;