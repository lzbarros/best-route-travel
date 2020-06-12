package com.bexs.travel.domain.services;

import com.bexs.travel.domain.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;
import com.bexs.travel.domain.exceptions.RegisterAlreadyExistsException;
import com.bexs.travel.domain.exceptions.RegisterNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Named("routeService")
public class RouteService implements IRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteService.class);

    @Inject
    private IRouteRepository routeRepository;

    private static final String ROUTE_FROM_NOT_FOUND_MESSAGE = "Route from [%s] not found";
    private static final String ROUTE_TO_NOT_FOUND_MESSAGE = "Route to [%s] not found";

    public RouteService(@NotNull final IRouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public Route save(@NotNull final Route route) {
        LOGGER.info("Saving route: {}", route);

        List<Route> routeList = findRouteFromAndRouteTo(route.getRouteFrom(), route.getRouteTo());

        if (!routeList.isEmpty()) {
            LOGGER.warn("Route [{}-{}] already exists", route.getRouteFrom(), route.getRouteTo());
            throw new RegisterAlreadyExistsException(String.format("Route [%s-%s] already exists", route.getRouteFrom(), route.getRouteTo()));
        }

        return routeRepository.save(route);
    }

    @Override
    public List<Route> findRouteFrom(@NotNull final String route) {
        LOGGER.info("Searching for route from: {}", route);
        return routeRepository.findRouteFrom(route);
    }

    @Override
    public List<Route> findRouteTo(@NotNull final String route) {
        LOGGER.info("Searching for route to: {}", route);
        return routeRepository.findRouteTo(route);
    }

    @Override
    public List<Route> findRouteFromAndRouteTo(@NotNull final String routeFrom, @NotNull final String routeTo) {
        LOGGER.info("Searching for route from and route to: {}-{}", routeFrom, routeTo);
        return routeRepository.findRouteFromAndRouteTo(routeFrom, routeTo);
    }

    @Override
    public TravelRoute findBestRoute(@NotNull String routeFrom, @NotNull String routeTo) {
        LOGGER.info("Searching for the best route: {}-{}", routeFrom, routeTo);

        // Find all routes from routeFrom
        final List<Route> routeList = findRouteFrom(routeFrom);
        // Find all routes from routeFrom
        final List<Route> routeListTo = findRouteTo(routeTo);
        // Validate if the routeFrom and routeTo exists
        routeValidation(routeFrom, routeTo, routeList, routeListTo);
        // Initialize travel route to store final result with infinite max value.
        final TravelRoute travelRoute = new TravelRoute(Long.MAX_VALUE);
        // Initialize a linked list to store the routes
        Deque<String> linkedList = new LinkedList<>(Arrays.asList(routeFrom));

        // Loop the route list to find out the best route
        for (int i = 0; i < routeList.size(); i++) {
            // Verify if the value route is greater than the value already stored on travel route object (To avoid unnecessary loops)
            if (isValueGreaterThan(routeList.get(i).getValue(), travelRoute.getValue())) continue;
            // Find the best route to current value
            findBestRoute(routeList.get(i), linkedList, travelRoute, routeTo, 0L);
            // Clean up linked list
            linkedList = new LinkedList<>(Arrays.asList(routeFrom));
        }

        if (travelRoute.getRoute() == null) {
            LOGGER.warn("Route [{}-{}] not found", routeFrom, routeTo);
            throw new RegisterNotFoundException(String.format("Route [%s-%s] not found", routeFrom, routeTo));
        }

        return travelRoute;
    }

    private void findBestRoute(@NotNull Route route,
                               @NotNull Deque<String> linkedList, @NotNull final TravelRoute travelRoute, @NotNull final String finalRoute, @NotNull Long value) {
        // Accumulate the route value
        value += route.getValue();

        // Add the routeTo to linked list (Building the best route chain)
        linkedList.add(route.getRouteTo());

        // Verify if the final route was reached
        if (finalRoute.equalsIgnoreCase(route.getRouteTo())) {
            // Verify if the value from current route chain is less than what was stored before on travel route object
            setTravelRouteValues(linkedList, travelRoute, value);
        } else {
            // Find all routes from routeTo
            List<Route> routeList = sorted(findRouteFrom(route.getRouteTo()));

            // Loop the route list to find out the best route (to routeTo)
            for (int i = 0; i < routeList.size(); i++) {
                // Verify if the value route is greater than the value already stored on travel route object (To avoid unnecessary loops)
                if (isValueGreaterThan(routeList.get(i).getValue(), travelRoute.getValue())) continue;
                // Find the best route to current value
                findBestRoute(routeList.get(i), linkedList, travelRoute, finalRoute, value);
                // Clean up linked list
                linkedList = new LinkedList<>(Arrays.asList(linkedList.getFirst()));
            }
        }
    }

    private void routeValidation(@NotNull final String routeFrom, @NotNull final String routeTo, @NotNull final List<Route> routeList, @NotNull final List<Route> routeListTo) {
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

    private void setTravelRouteValues(@NotNull Deque<String> linkedList, @NotNull final TravelRoute travelRoute, @NotNull final Long value) {
        if (travelRoute.getValue() > value) {
            travelRoute.setRoute(linkedList);
            travelRoute.setValue(value);
        }
    }

    private List<Route> sorted(@NotNull final List<Route> routeList) {
        return routeList.stream().sorted(Comparator.comparingLong(Route::getValue)).collect(Collectors.toList());
    }

    private boolean isValueGreaterThan(@NotNull Long value1, Long value2) {
        return value1 > value2;
    }
}
