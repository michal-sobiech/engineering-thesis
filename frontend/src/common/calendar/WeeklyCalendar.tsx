import { FC } from "react";
import { Calendar, CalendarProps } from "react-big-calendar";
import "react-big-calendar/lib/css/react-big-calendar.css";

export const WeeklyCalendar: FC<CalendarProps> = (props) => {
    const { localizer, ...nonRequiredProps } = props;

    const finalRequiredProps: CalendarProps = {
        localizer: props.localizer ?? localizer
    };

    const finalOptionalProps: CalendarProps = {
        views: ["week"],
        defaultView: "week",
        selectable: true,
        toolbar: false,
        components: {
            header: ({ label }) => <span>{label.split(" ")[1]}</span>
        },
        ...props
    };

    const finalProps: CalendarProps = {
        ...finalRequiredProps,
        ...finalOptionalProps
    };

    return <Calendar {...finalProps} />;
}