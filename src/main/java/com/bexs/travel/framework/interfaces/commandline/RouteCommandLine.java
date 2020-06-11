package com.bexs.travel.framework.interfaces.commandline;

import com.bexs.travel.application.usecases.IRouteFacade;
import com.bexs.travel.framework.presenters.ITravelRoutePresenter;
import com.bexs.travel.framework.requests.RouteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.validation.constraints.NotNull;

@ShellComponent
@ShellCommandGroup("Routes")
public class RouteCommandLine {

    public static final Logger LOGGER = LoggerFactory.getLogger(RouteCommandLine.class.getName());

    private static final String INCORRECT_FORMAT_ROUTE_MESSAGE = "Incorrect route format. ";
    private static final String ROUTE_FORMAT_HELP_MESSAGE = "Please type \"find-best-route\" and \"Route from\"-\"Route To\" ex.: find-best-route ABC-XYZ";

    @Autowired
    IRouteFacade routeFacade;

    @Autowired
    ITravelRoutePresenter travelRoutePresenter;

    @ShellMethod("Find best route. " + ROUTE_FORMAT_HELP_MESSAGE)
    public String findBestRoute(@ShellOption(help = "Route: eg.: ABC-XYZ") final String route) {
        LOGGER.info("Searching the best route for the route: {}", route);

        RouteRequest routeEntity = extractRoute(route);

        return travelRoutePresenter.getTravelRoutePresenter(routeFacade.findBestRoute(routeEntity.getRouteFrom(), routeEntity.getRouteTo()));
    }

    private RouteRequest extractRoute(@NotNull final String route) {
        LOGGER.info("Extracting route: {}", route);

        String[] routes = routeValidate(route);

        return new RouteRequest(routes[0].toUpperCase(), routes[1].toUpperCase(), 0L);
    }

    private String[] routeValidate(@NotNull final String route) {
        LOGGER.info("Validating route format: {}", route);
        String messageError = INCORRECT_FORMAT_ROUTE_MESSAGE + ROUTE_FORMAT_HELP_MESSAGE;
        String logSchema = "{}. {}";

        boolean routeLessThanSize7 = route.trim().length() != 7;

        //Length
        if (routeLessThanSize7) {
            LOGGER.error("{}", messageError);
            throw new UnsupportedOperationException(messageError);
        }

        String[] routes = route.split("-");

        boolean routeLessThanSize2AfterSplit = routes.length != 2;

        if (routeLessThanSize2AfterSplit) {
            LOGGER.error(logSchema, messageError, "Detail: There is no the \"-\" character");
            throw new UnsupportedOperationException(messageError);
        }

        boolean routeFromLessThanSize3 = routes[0].trim().length() != 3;
        if (routeFromLessThanSize3) {
            LOGGER.error(logSchema, messageError, "Detail: The error is on Route from");
            throw new UnsupportedOperationException(messageError);
        }

        boolean routeToLessThanSize3 = routes[1].trim().length() != 3;
        if (routeToLessThanSize3) {
            LOGGER.error(logSchema, messageError, "Detail: The error is on Route to");
            throw new UnsupportedOperationException(messageError);
        }

        return routes;
    }
}
