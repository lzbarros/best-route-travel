package com.bexs.travel.domain.entities;

import java.util.Objects;

public class Route {
    private final String routeFrom;
    private final String routeTo;
    private final Long value;

    public Route(String routeFrom, String routeTo, Long value) {
        this.routeFrom = routeFrom;
        this.routeTo = routeTo;
        this.value = value;
    }

    public String getRouteFrom() {
        return routeFrom;
    }

    public String getRouteTo() {
        return routeTo;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Route{");
        sb.append("routeFrom='").append(routeFrom).append('\'');
        sb.append(", routeTo='").append(routeTo).append('\'');
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return routeFrom.equals(route.routeFrom) &&
                routeTo.equals(route.routeTo) &&
                value.equals(route.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeFrom, routeTo, value);
    }
}
