package com.bexs.travel.domain.services;

import com.bexs.travel.application.usecases.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;
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

    public RouteService(@NotNull final IRouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public Route save(@NotNull final Route route) {
        LOGGER.info("Saving route: {}", route);
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
    public TravelRoute findBestRoute(@NotNull String routeFrom, @NotNull String routeTo, @NotNull List<Route> routeList) {
        // Initialize travel route to store final result with infinite max value.
        final TravelRoute travelRoute = new TravelRoute(Long.MAX_VALUE);
        // Initialize a linked list to store the routes
        Deque<String> linkedList = new LinkedList<>(Arrays.asList(routeFrom));

        // Loop the route list to find out the best route
        for (int i = 0; i < routeList.size(); i++) {
            // Verify if the value route is greater than the value already stored on travel route object (To avoid unnecessary loops)
            if (isValueGreaterThan(routeList, travelRoute, i)) continue;

            // Find the best route to current value
            findBestRoute(routeList.get(i), linkedList, travelRoute, routeTo, 0L);
            // Clean up linked list
            linkedList = new LinkedList<>(Arrays.asList(routeFrom));
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
                if (isValueGreaterThan(routeList, travelRoute, i)) continue;
                // Find the best route to current value
                findBestRoute(routeList.get(i), linkedList, travelRoute, finalRoute, value);
                // Clean up linked list
                linkedList = new LinkedList<>(Arrays.asList(linkedList.getFirst()));
            }
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

    private boolean isValueGreaterThan(@NotNull List<Route> routeList, TravelRoute travelRoute, int i) {
        return routeList.get(i).getValue() > travelRoute.getValue();
    }
}
