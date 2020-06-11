package com.bexs.travel.domain.services;

import com.bexs.travel.application.infrastructure.databaseconnector.IDatabaseConnector;
import com.bexs.travel.domain.entities.Route;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Named("routeRepository")
public class RouteRepository implements IRouteRepository {

    @Inject
    private IDatabaseConnector databaseConnector;

    public RouteRepository(@NotNull final IDatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public Route save(@NotNull final Route route) {
        return databaseConnector.save(route);
    }

    @Override
    public List<Route> findRouteFrom(@NotNull final String route) {
        List<Route> routeList = databaseConnector.find();
        routeList = routeList.stream().filter(r -> r.getRouteFrom().equalsIgnoreCase(route)).collect(Collectors.toList());

        return routeList;
    }

    @Override
    public List<Route> findRouteTo(@NotNull final String route) {
        List<Route> routeList = databaseConnector.find();
        routeList = routeList.stream().filter(r -> r.getRouteTo().equalsIgnoreCase(route)).collect(Collectors.toList());

        return routeList;
    }

    @Override
    public List<Route> findRouteFromAndRouteTo(@NotNull final String routeFrom, @NotNull final String routeTo) {
        List<Route> routeList = databaseConnector.find();

        routeList = routeList.stream().filter(r -> r.getRouteFrom().equalsIgnoreCase(routeFrom) && r.getRouteTo().equalsIgnoreCase(routeTo)).collect(Collectors.toList());

        return routeList;
    }
}
