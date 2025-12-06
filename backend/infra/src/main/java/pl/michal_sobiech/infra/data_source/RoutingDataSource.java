package pl.michal_sobiech.infra.data_source;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<Route> context = new ThreadLocal<>();

    public static void setRoute(Route route) {
        context.set(route);
    }

    public static Route getRoute() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getRoute();
    }
}