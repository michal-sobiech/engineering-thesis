import { joinURL as ufoJoinUrl } from "ufo";

function joinUrl(url: URL, segment: string): URL {
    console.log(url, segment);
    return new URL(ufoJoinUrl(url.href, segment));
}

export { joinUrl };
