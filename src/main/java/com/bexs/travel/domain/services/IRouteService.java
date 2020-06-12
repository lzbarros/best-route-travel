package com.bexs.travel.domain.services;

import com.bexs.travel.domain.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IRouteService {
    Route save(@NotNull final Route route);

    TravelRoute findBestRoute(@NotNull String routeFrom, @NotNull String routeTo);

    List<Route> findRouteFrom(@NotNull final String route);

    List<Route> findRouteTo(@NotNull final String route);

    List<Route> findRouteFromAndRouteTo(@NotNull final String routeFrom, @NotNull final String routeTo);
}
