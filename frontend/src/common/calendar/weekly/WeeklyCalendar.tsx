import { FC } from "react";
import { Calendar, CalendarProps } from "react-big-calendar";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { localizer as defaultLocalizer } from "../../localizer";

export type WeeklyCalendarProps = Omit<CalendarProps, "localizer"> & { localizer?: CalendarProps["localizer"] };

export const WeeklyCalendar: FC<WeeklyCalendarProps> = (props) => {
    const { localizer: propsLocalizer, ...nonRequiredProps } = props;

    const finalRequiredProps: CalendarProps = {
        localizer: propsLocalizer ?? defaultLocalizer,
    };

    const finalOptionalProps: Omit<CalendarProps, "localizer"> = {
        views: ["week"],
        defaultView: "week",
        selectable: true,
        toolbar: false,
        components: {
            header: ({ label }) => <span>{label.split(" ")[1]}</span>
        },
        ...nonRequiredProps,
    };

    const finalProps: CalendarProps = {
        ...finalRequiredProps,
        ...finalOptionalProps,
    };

    return <Calendar {...finalProps} />;
};