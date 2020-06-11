package com.bexs.travel.application.usecases;

import com.bexs.travel.application.usecases.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;
import com.bexs.travel.domain.services.IRouteService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

@Named("routeCase")
public class RouteCase implements IRouteCase {

    @Inject
    private IRouteService routeService;

    public RouteCase(@NotNull final IRouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public Route save(@NotNull final String routeFrom, @NotNull final String routeTo, @NotNull final Long value) {
        Route route = new Route(routeFrom, routeTo, value);

        return routeService.save(route);
    }

    @Override
    public TravelRoute findBestRoute(@NotNull final String routeFrom, @NotNull final String routeTo) {
        // Find the best route
        return this.routeService.findBestRoute(routeFrom, routeTo);
    }
}
