import { Calendar, CalendarProps } from "react-big-calendar";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { localizer as defaultLocalizer } from "../../localizer";

export type WeeklyCalendarProps<
    TEvent extends object = Event,
    TResource extends object = object
> = Omit<CalendarProps<TEvent, TResource>, "localizer">
    & { localizer?: CalendarProps<TEvent, TResource>["localizer"] };

export const WeeklyCalendar = <
    TEvent extends object = Event,
    TResource extends object = object
>(props: WeeklyCalendarProps<TEvent, TResource>) => {
    const { localizer: propsLocalizer, ...nonRequiredProps } = props;

    const finalRequiredProps: CalendarProps<TEvent, TResource> = {
        localizer: propsLocalizer ?? defaultLocalizer,
    };

    const finalOptionalProps: Omit<CalendarProps<TEvent, TResource>, "localizer"> = {
        views: ["week"],
        defaultView: "week",
        selectable: true,
        toolbar: false,
        components: {
            header: ({ label }) => <span>{label.split(" ")[1]}</span>
        },
        ...nonRequiredProps,
    };

    const finalProps: CalendarProps<TEvent, TResource> = {
        ...finalRequiredProps,
        ...finalOptionalProps,
    };

    return <Calendar {...finalProps} />;
};