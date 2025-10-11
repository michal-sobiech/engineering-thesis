import { Result } from "neverthrow";
import { parseURL, joinURL as ufoJoinUrl } from "ufo";
import { assertDefined } from "./result";

export function joinUrl(url: URL, segment: string): URL {
    console.log(url, segment);
    return new URL(ufoJoinUrl(url.href, segment));
}

export function extractFileName(url: string): Result<string, Error> {
    const { pathname } = parseURL(url);
    const fileName = pathname.split("/").pop();
    return assertDefined(fileName);
}