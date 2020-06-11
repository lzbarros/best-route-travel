package com.bexs.travel.framework;

import com.bexs.travel.application.usecases.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;
import com.bexs.travel.domain.exceptions.RegisterAlreadyExistsException;
import com.bexs.travel.domain.exceptions.RegisterNotFoundException;
import com.bexs.travel.application.usecases.RouteFacade;
import com.bexs.travel.framework.interfaces.rest.RouteController;
import com.bexs.travel.framework.presenters.TravelRoutePresenter;
import com.bexs.travel.framework.requests.RouteRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Deque;
import java.util.LinkedList;

@RunWith(MockitoJUnitRunner.class)
public class RouteControllerTest {

    @InjectMocks
    RouteController routeController;

    @Mock
    RouteFacade routeFacade;

    @Mock
    TravelRoutePresenter travelRoutePresenter;

    @Test
    public void shouldReturnABestRoute() {
        String expectedBestRoute = "GFD > HJK > OIU > $77";
        Deque<String> linkedList = new LinkedList<>();
        linkedList.addFirst("OIU");
        linkedList.addFirst("GFD");
        linkedList.addFirst("HJK");

        TravelRoute travelRoute = new TravelRoute(linkedList, 10L);

        Mockito.when(this.routeFacade.findBestRoute("GFD", "OIU")).thenReturn(travelRoute);
        Mockito.when(this.travelRoutePresenter.getTravelRoutePresenter(travelRoute)).thenReturn(expectedBestRoute);

        ResponseEntity bestRoute = this.routeController.findBestRoute("GFD", "OIU");

        Assert.assertEquals(HttpStatus.OK, bestRoute.getStatusCode());
        Assert.assertEquals(expectedBestRoute, bestRoute.getBody());
    }

    @Test
    public void shouldNotReturnABestRoute() {
        String expectedBestRoute = "GFD > HJK > OIU > $77";

        ResponseEntity bestRoute = this.routeController.findBestRoute("GFD", "OIU");

        Assert.assertEquals(HttpStatus.OK, bestRoute.getStatusCode());
        Assert.assertNotEquals(expectedBestRoute, bestRoute.getBody());
    }

    @Test
    public void shouldReturnRegisterNotFoundException() {

        Mockito.when(this.routeFacade.findBestRoute("GFT", "OIU")).thenThrow(new RegisterNotFoundException("Route [GFD-OIU] not found"));

        ResponseEntity bestRoute = this.routeController.findBestRoute("GFT", "OIU");

        Assert.assertEquals(HttpStatus.NOT_FOUND, bestRoute.getStatusCode());
        Assert.assertEquals("Route [GFD-OIU] not found", bestRoute.getBody());
    }

    @Test
    public void shouldSaveARoute() {
        RouteRequest routeRequest = new RouteRequest("GFT", "OIU", 77L);

        Mockito.when(this.routeFacade.save("GFT", "OIU", 77L)).thenReturn(new Route("GFT", "OIU", 77L));

        ResponseEntity responseEntity = this.routeController.save(routeRequest);

        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void shouldNotSaveARoute() {
        RouteRequest routeRequest = new RouteRequest("GFT", "OIU", 77L);

        Mockito.when(this.routeFacade.save("GFT", "OIU", 77L)).thenThrow(new RegisterAlreadyExistsException("Route [GFT-OIU] already exists"));

        ResponseEntity responseEntity = this.routeController.save(routeRequest);

        Assert.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

}
