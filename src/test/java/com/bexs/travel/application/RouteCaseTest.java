package com.bexs.travel.application;

import com.bexs.travel.application.usecases.RouteCase;
import com.bexs.travel.application.usecases.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;
import com.bexs.travel.domain.exceptions.RegisterAlreadyExistsException;
import com.bexs.travel.domain.exceptions.RegisterNotFoundException;
import com.bexs.travel.domain.services.IRouteService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class RouteCaseTest {

    @InjectMocks
    RouteCase routeCase;

    @Mock
    IRouteService routeService;

    @Test
    public void shouldSaveARoute() {
        Route expectedRoute = new Route("FGD", "KJH", 10L);

        Mockito.when(this.routeService.save(ArgumentMatchers.any())).thenReturn(expectedRoute);

        Route route = this.routeCase.save("FGD", "KJH", 10L);

        Assert.assertEquals(expectedRoute, route);
    }

    @Test(expected = RegisterAlreadyExistsException.class)
    public void shouldNotSaveARoute() {
        Route expectedRoute = new Route("FGD", "KJH", 10L);
        List<Route> routeList = new ArrayList<>(Arrays.asList(expectedRoute));

        Mockito.when(this.routeService.findRouteFromAndRouteTo(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(routeList);

        Route route = this.routeCase.save("FGD", "KJH", 10L);

        Assert.assertEquals(expectedRoute, route);
    }

    @Test
    public void shouldReturnBestRoute() {
        Route route1 = new Route("FGD", "KJH", 75L);
        Route route2 = new Route("HIJ", "KJH", 10L);
        Route route3 = new Route("FGD", "HIJ", 10L);
        Deque<String> linkedList = new LinkedList<>();
        linkedList.addFirst("FGD");
        linkedList.addFirst("HIJ");
        linkedList.addFirst("KJH");
        TravelRoute expectedTravelRoute = new TravelRoute(linkedList, 20L);

        List<Route> routeListTo = new ArrayList<>(Arrays.asList(route1, route2));

        Mockito.when(this.routeService.findRouteFrom("FGD")).thenReturn(new ArrayList<>(Arrays.asList(route1, route3)));
        Mockito.when(this.routeService.findRouteTo(ArgumentMatchers.anyString())).thenReturn(routeListTo);
        Mockito.when(this.routeService.findBestRoute("FGD", "KJH", new ArrayList<>(Arrays.asList(route1, route3)))).thenReturn(expectedTravelRoute);

        TravelRoute travelRoute = this.routeCase.findBestRoute("FGD", "KJH");

        Assert.assertEquals(expectedTravelRoute, travelRoute);
    }


    @Test(expected = RegisterNotFoundException.class)
    public void shouldReturnRegisterNotFoundExceptionRouteFrom() {
        Mockito.when(this.routeService.findRouteFrom("FGI")).thenReturn(new ArrayList<>());

        this.routeCase.findBestRoute("FGI", "KJH");

        Assert.fail();
    }

    @Test(expected = RegisterNotFoundException.class)
    public void shouldReturnRegisterNotFoundExceptionRouteTo() {
        Route route1 = new Route("FGD", "ITA", 75L);

        Mockito.when(this.routeService.findRouteFrom("FGD")).thenReturn(new ArrayList<>(Arrays.asList(route1)));
        Mockito.when(this.routeService.findRouteTo("KJH")).thenReturn(new ArrayList<>());

        this.routeCase.findBestRoute("FGD", "KJH");

        Assert.fail();
    }

    @Test(expected = RegisterNotFoundException.class)
    public void shouldReturnRegisterNotFoundExceptionRouteFromAndTo() {
        Route route1 = new Route("FGD", "ITA", 75L);
        Route route2 = new Route("HIJ", "KJH", 10L);
        Route route3 = new Route("FGD", "MAO", 10L);

        List<Route> routeListTo = new ArrayList<>(Arrays.asList(route2));

        Mockito.when(this.routeService.findRouteFrom("FGD")).thenReturn(new ArrayList<>(Arrays.asList(route1, route3)));
        Mockito.when(this.routeService.findRouteTo(ArgumentMatchers.anyString())).thenReturn(routeListTo);
        Mockito.when(this.routeService.findBestRoute(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyList())).thenReturn(new TravelRoute(null, null));

        this.routeCase.findBestRoute("FGD", "KJH");

        Assert.fail();
    }

}
