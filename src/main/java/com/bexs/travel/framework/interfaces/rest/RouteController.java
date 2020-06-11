package com.bexs.travel.framework.interfaces.rest;

import com.bexs.travel.domain.entities.Route;
import com.bexs.travel.domain.exceptions.RegisterAlreadyExistsException;
import com.bexs.travel.domain.exceptions.RegisterNotFoundException;
import com.bexs.travel.application.usecases.IRouteFacade;
import com.bexs.travel.framework.presenters.ITravelRoutePresenter;
import com.bexs.travel.framework.requests.RouteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

@RestController
@RequestMapping("travel/routes")
public class RouteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteController.class);

    @Inject
    private IRouteFacade routeFacade;

    @Inject
    private ITravelRoutePresenter travelRoutePresenter;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid RouteRequest routeRequest) {
        LOGGER.info("Receiving data to save a new route: {}", routeRequest);

        try {
            Route route = routeFacade.save(routeRequest.getRouteFrom(), routeRequest.getRouteTo(), routeRequest.getValue());

            return new ResponseEntity<>(route, HttpStatus.CREATED);
        } catch (RegisterAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{routeFrom}/{routeTo}")
    public ResponseEntity<String> findBestRoute(@PathVariable("routeFrom") String routeFrom, @PathVariable("routeTo") String routeTo) {
        LOGGER.info("Receiving data to find the best route: {}-{}", routeFrom, routeTo);

        try {
            String bestRoute = travelRoutePresenter.getTravelRoutePresenter(routeFacade.findBestRoute(routeFrom, routeTo));

            return ResponseEntity.ok(bestRoute);
        } catch (RegisterNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
