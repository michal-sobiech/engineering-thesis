import { Flex, Input } from '@chakra-ui/react';
import { ChangeEvent, FC, useRef } from 'react';
import { StandardButton } from './StandardButton';
import { StandardNoneditableTextField } from './StandardNoneditableTextField';
import { VoidCallback } from './VoidCallback';

export interface StandardFileInputProps {
    fileName: string;
    setFileName: VoidCallback<string>;
    setFile: VoidCallback<File>;
}

export const StandardFileInput: FC<StandardFileInputProps> = ({ fileName, setFileName, setFile }) => {
    const inputRef = useRef<HTMLInputElement>(null);

    const onChangeFileClick = (event: ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];
        if (file !== undefined) {
            setFileName(fileName);
            setFile(file);
        }
    }

    return <Flex gap="5px" direction="row">
        <Input
            ref={inputRef}
            type="file"
            onChange={onChangeFileClick}
            display="none"
        />
        <StandardNoneditableTextField text={fileName} placeholder={"No file chosen"} />
        <StandardButton onClick={() => inputRef.current?.click()}>
            Choose file
        </StandardButton>
    </Flex>;
}