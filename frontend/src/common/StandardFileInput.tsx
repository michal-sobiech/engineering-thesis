import { Flex, Input } from '@chakra-ui/react';
import { ChangeEvent, FC, useRef } from 'react';
import { StandardButton } from './StandardButton';
import { StandardNoneditableTextField } from './StandardNoneditableTextField';
import { VoidCallback } from './VoidCallback';

export interface StandardFileInputProps {
    text: string;
    setText: VoidCallback<string>;
    setFile: VoidCallback<File>;
}

export const StandardFileInput: FC<StandardFileInputProps> = ({ text, setText, setFile }) => {
    const inputRef = useRef<HTMLInputElement>(null);

    const onChangeFileClick = (event: ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];
        if (file !== undefined) {
            setFile(file);
            setText(file.name);
        }
    }

    return <Flex gap="5px" direction="row">
        <Input
            ref={inputRef}
            type="file"
            onChange={onChangeFileClick}
            display="none"
        />
        <StandardNoneditableTextField text={text} placeholder={"No file chosen"} />
        <StandardButton onClick={() => inputRef.current?.click()}>
            Choose file
        </StandardButton>
    </Flex>;
}