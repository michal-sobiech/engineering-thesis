import { Id, toast, ToastOptions } from "react-toastify";

export function toastError(message: string, options?: ToastOptions<unknown>): Id {
    const finalOptions: ToastOptions<unknown> = {
        autoClose: 10000,
        ...(options ?? {})
    };

    return toast.error(message, finalOptions);
}

export function toastSuccess(message: string): Id {
    return toast.success(message, { autoClose: 10000 });
}