package pl.michal_sobiech.core.secret;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Secret<T> {

    private final T value;

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "*****";
    }

}
