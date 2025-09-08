import { Id, toast } from "react-toastify";

export function toastError(message: string): Id {
    return toast.error(message, { autoClose: 10000 });
}

export function toastSuccess(message: string): Id {
    return toast.error(message, { autoClose: 10000 });
}