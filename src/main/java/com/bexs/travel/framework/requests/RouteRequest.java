package com.bexs.travel.framework.requests;

import com.bexs.travel.domain.entities.Route;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RouteRequest extends Route {
    public RouteRequest(String routeFrom, String routeTo, Long value) {
        super(routeFrom, routeTo, value);
    }

    @NotEmpty(message = "Route from cannot be empty")
    @Size(max = 3, min = 3, message = "size must be between 3 and 3")
    @NotBlank
    @Override
    public String getRouteFrom() {
        return super.getRouteFrom();
    }
    @NotEmpty(message = "Route to cannot be empty")
    @Size(max = 3, min = 3, message = "size must be between 3 and 3")
    @NotBlank
    @Override
    public String getRouteTo() {
        return super.getRouteTo();
    }
    @Min(value = 1, message = "Route value must be equal to or greater than 1")
    @Override
    public Long getValue() {
        return super.getValue();
    }
}
