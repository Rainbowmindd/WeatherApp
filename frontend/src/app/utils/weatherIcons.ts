import {faCloudSun, faCloudSunRain, faQuestion, faSun} from "@fortawesome/free-solid-svg-icons";

export function mapWeatherCodeToIcon(code: number) {
    if (code === 0) return faSun;
    if (code >= 1 && code <= 3) return faCloudSun;
    if (code >= 50 && code <= 99) return faCloudSunRain;
    return faQuestion;
}
