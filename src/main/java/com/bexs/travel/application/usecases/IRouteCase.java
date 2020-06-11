package com.bexs.travel.application.usecases;

import com.bexs.travel.application.usecases.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;

import javax.validation.constraints.NotNull;

public interface IRouteCase {
    Route save(@NotNull final String routeFrom, @NotNull final String routeTo, @NotNull final Long value);

    TravelRoute findBestRoute(@NotNull final String routeFrom, @NotNull final String routeTo);
}
