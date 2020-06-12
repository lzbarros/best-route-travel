package com.bexs.travel.domain.vo;

import java.util.Deque;
import java.util.Objects;

public class TravelRoute {
    private Deque<String> route;
    private Long value;

    public TravelRoute(Long value) {
        this.value = value;
    }

    public TravelRoute(Deque<String> route, Long value) {
        this.route = route;
        this.value = value;
    }

    public Deque<String> getRoute() {
        return route;
    }

    public Long getValue() {
        return value;
    }

    public void setRoute(Deque<String> route) {
        this.route = route;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TravelRoute that = (TravelRoute) o;
        return Objects.equals(route, that.route) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(route, value);
    }
}
