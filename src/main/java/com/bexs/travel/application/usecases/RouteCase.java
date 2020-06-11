package com.bexs.travel.application.usecases;

import com.bexs.travel.application.usecases.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;
import com.bexs.travel.domain.exceptions.RegisterAlreadyExistsException;
import com.bexs.travel.domain.exceptions.RegisterNotFoundException;
import com.bexs.travel.domain.services.IRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.List;

@Named("routeCase")
public class RouteCase implements IRouteCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteCase.class);

    @Inject
    private IRouteService routeService;

    private static final String ROUTE_FROM_NOT_FOUND_MESSAGE = "Route from [%s] not found";
    private static final String ROUTE_TO_NOT_FOUND_MESSAGE = "Route to [%s] not found";

    public RouteCase(@NotNull final IRouteService routeService) {
        this.routeService = routeService;
    }

    @Override
    public Route save(@NotNull final String routeFrom, @NotNull final String routeTo, @NotNull final Long value) {
        List<Route> routeList = routeService.findRouteFromAndRouteTo(routeFrom, routeTo);

        if (!routeList.isEmpty()) {
            LOGGER.warn("Route [{}-{}] already exists", routeFrom, routeTo);
            throw new RegisterAlreadyExistsException(String.format("Route [%s-%s] already exists", routeFrom, routeTo));
        }

        Route route = new Route(routeFrom, routeTo, value);

        return routeService.save(route);
    }

    @Override
    public TravelRoute findBestRoute(@NotNull final String routeFrom, @NotNull final String routeTo) {
        LOGGER.info("Searching for the best route: {}-{}", routeFrom, routeTo);

        // Find all routes from routeFrom
        final List<Route> routeList = routeService.findRouteFrom(routeFrom);
        // Find all routes from routeFrom
        final List<Route> routeListTo = routeService.findRouteTo(routeTo);

        // Validate if the routeFrom and routeTo exists
        routeValidate(routeFrom, routeTo, routeList, routeListTo);

        // Find the best route
        TravelRoute travelRoute = this.routeService.findBestRoute(routeFrom, routeTo, routeList);

        if (travelRoute.getRoute() == null) {
            LOGGER.warn("Route [{}-{}] not found", routeFrom, routeTo);
            throw new RegisterNotFoundException(String.format("Route [%s-%s] not found", routeFrom, routeTo));
        }

        return travelRoute;
    }

    private void routeValidate(@NotNull final String routeFrom, @NotNull final String routeTo, @NotNull final List<Route> routeList, @NotNull final List<Route> routeListTo) {
        LOGGER.info("Validating route: {}-{}", routeFrom, routeTo);

        if (routeList.isEmpty()) {
            String exceptionMessage = String.format(ROUTE_FROM_NOT_FOUND_MESSAGE, routeFrom);
            LOGGER.warn("{}", exceptionMessage);
            throw new RegisterNotFoundException(exceptionMessage);
        }

        if (routeListTo.isEmpty()) {
            String exceptionMessage = String.format(ROUTE_TO_NOT_FOUND_MESSAGE, routeTo);
            LOGGER.warn("{}", exceptionMessage);
            throw new RegisterNotFoundException(exceptionMessage);
        }
    }
}
