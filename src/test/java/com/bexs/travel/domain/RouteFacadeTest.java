package com.bexs.travel.domain;

import com.bexs.travel.application.usecases.IRouteCase;
import com.bexs.travel.application.usecases.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;
import com.bexs.travel.application.usecases.RouteFacade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Deque;
import java.util.LinkedList;

@RunWith(MockitoJUnitRunner.class)
public class RouteFacadeTest {

    @InjectMocks
    RouteFacade routeFacade;

    @Mock
    IRouteCase routeCase;

    @Test
    public void shouldReturnABestRoute() {
        Route route = new Route("HGT", "JET", 10L);
        Deque<String> linkedList = new LinkedList<>();
        linkedList.addFirst(route.getRouteFrom());
        linkedList.addFirst(route.getRouteTo());

        TravelRoute expectedTravelRoute = new TravelRoute(linkedList, 10L);

        Mockito.when(this.routeCase.findBestRoute(route.getRouteFrom(), route.getRouteTo())).thenReturn(expectedTravelRoute);

        TravelRoute travelRoute = this.routeFacade.findBestRoute(route.getRouteFrom(), route.getRouteTo());

        Assert.assertEquals(expectedTravelRoute, travelRoute);
    }

    @Test
    public void shouldSaveARoute() {
        Route expectedRoute = new Route("HGT", "JET", 10L);

        Mockito.when(this.routeCase.save(ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyLong())).thenReturn(expectedRoute);

        Route route = this.routeFacade.save("HGT", "JET", 10L);

        Assert.assertEquals(expectedRoute, route);
    }


}
