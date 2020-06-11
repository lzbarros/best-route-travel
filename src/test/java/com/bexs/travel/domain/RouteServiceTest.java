package com.bexs.travel.domain;

import com.bexs.travel.application.usecases.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;
import com.bexs.travel.domain.services.IRouteRepository;
import com.bexs.travel.domain.services.RouteService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

    @InjectMocks
    RouteService routeService;

    @Mock
    IRouteRepository routeRepository;

    @Test
    public void shouldSaveARoute() {
        Route expectedRoute = new Route("FGD", "KJH", 10L);
        Mockito.when(this.routeRepository.save(ArgumentMatchers.any())).thenReturn(expectedRoute);

        Route route = this.routeService.save(expectedRoute);

        Assert.assertEquals(expectedRoute, route);
    }

    @Test
    public void shouldReturnAListRouteFrom() {
        List<Route> routeList = new ArrayList<>(Arrays.asList(new Route("GHT", "GTR", 10L)));
        Mockito.when(this.routeRepository.findRouteFrom("GHT")).thenReturn(routeList);

        List<Route> routeListFrom = this.routeService.findRouteFrom("GHT");

        Assert.assertEquals(routeList.size(), routeListFrom.size());
        Assert.assertEquals(routeList.get(0).getRouteFrom(), routeListFrom.get(0).getRouteFrom());
    }

    @Test
    public void shouldNotReturnAListRouteFrom() {
        List<Route> routeList = new ArrayList<>(Arrays.asList(new Route("GHT", "GTR", 10L)));

        List<Route> routeListFrom = this.routeService.findRouteFrom("GHI");

        Assert.assertEquals(0, routeListFrom.size());
    }

    @Test
    public void shouldReturnAListRouteTo() {
        List<Route> routeList = new ArrayList<>(Arrays.asList(new Route("GHT", "GTR", 10L)));
        Mockito.when(this.routeRepository.findRouteTo("GTR")).thenReturn(routeList);

        List<Route> routeListTo = this.routeService.findRouteTo("GTR");

        Assert.assertEquals(routeList.size(), routeListTo.size());
        Assert.assertEquals(routeList.get(0).getRouteFrom(), routeListTo.get(0).getRouteFrom());
    }

    @Test
    public void shouldNotReturnAListRouteTo() {
        List<Route> routeListTo = this.routeService.findRouteTo("GHI");

        Assert.assertEquals(0, routeListTo.size());
    }

    @Test
    public void shouldReturnAListRouteFromAndTo() {
        List<Route> routeList = new ArrayList<>(Arrays.asList(new Route("GHT", "GTR", 10L)));
        Mockito.when(this.routeRepository.findRouteFromAndRouteTo("GHT", "GTR")).thenReturn(routeList);

        List<Route> routeListFromAndTo = this.routeService.findRouteFromAndRouteTo("GHT", "GTR");

        Assert.assertEquals(routeList.size(), routeListFromAndTo.size());
        Assert.assertEquals(routeList.get(0).getRouteFrom(), routeListFromAndTo.get(0).getRouteFrom());
    }

    @Test
    public void shouldNotReturnAListRouteFromAndTo() {
        List<Route> routeListFromAndTo = this.routeService.findRouteFromAndRouteTo("GHT", "GTI");

        Assert.assertEquals(0, routeListFromAndTo.size());
    }

    @Test
    public void shouldReturnBestRoute() {
        Route route1 = new Route("FGD", "KJH", 75L);
        Route route2 = new Route("HIJ", "KJH", 10L);
        Route route3 = new Route("FGD", "HIJ", 10L);
        String expectedRouteResult = "[FGD, HIJ, KJH]";
        Long expectedRouteValueResult = 20L;

        Mockito.when(this.routeRepository.findRouteFrom("HIJ")).thenReturn(new ArrayList<>(Arrays.asList(route2)));

        TravelRoute travelRoute = this.routeService.findBestRoute("FGD", "KJH", new ArrayList<>(Arrays.asList(route1, route3)));

        Assert.assertEquals(expectedRouteResult, travelRoute.getRoute().toString());
        Assert.assertEquals(expectedRouteValueResult, travelRoute.getValue());
    }

    @Test
    public void shouldReturnBestRouteWithMoreThan3Routes() {
        Route route1 = new Route("GRU", "BRC", 10L);
        Route route2 = new Route("BRC", "SCL", 5L);
        Route route3 = new Route("GRU", "CDG", 75L);
        Route route4 = new Route("GRU", "SCL", 20L);
        Route route5 = new Route("GRU", "ORL", 56L);
        Route route6 = new Route("ORL", "BRC", 99L);
        Route route7 = new Route("ORL", "CDG", 5L);
        Route route8 = new Route("SCL", "ORL", 20L);

        String expectedRouteResult = "[GRU, BRC, SCL, ORL, CDG]";
        Long expectedRouteValueResult = 40L;

        Mockito.when(this.routeRepository.findRouteFrom("BRC")).thenReturn(new ArrayList<>(Arrays.asList(route2)));
        Mockito.when(this.routeRepository.findRouteFrom("ORL")).thenReturn(new ArrayList<>(Arrays.asList(route6, route7)));
        Mockito.when(this.routeRepository.findRouteFrom("SCL")).thenReturn(new ArrayList<>(Arrays.asList(route8)));

        TravelRoute travelRoute = this.routeService.findBestRoute("GRU", "CDG", new ArrayList<>(Arrays.asList(route1, route3, route4, route5)));

        Assert.assertEquals(expectedRouteResult, travelRoute.getRoute().toString());
        Assert.assertEquals(expectedRouteValueResult, travelRoute.getValue());
    }

    @Test
    public void shouldNotReturnBestRoute() {
        Route route1 = new Route("FGD", "KJH", 75L);
        Route route2 = new Route("HIJ", "KJH", 10L);
        Route route3 = new Route("FGD", "HIJ", 10L);
        String expectedRouteResult = "[FGD, KJH]";

        Mockito.when(this.routeRepository.findRouteFrom("HIJ")).thenReturn(new ArrayList<>(Arrays.asList(route2)));

        TravelRoute travelRoute = this.routeService.findBestRoute("FGD", "KJH", new ArrayList<>(Arrays.asList(route1, route3)));

        Assert.assertNotNull(expectedRouteResult, travelRoute.getRoute().toString());
    }

    @Test
    public void shouldNotReturnBestRouteValue() {
        Route route1 = new Route("FGD", "KJH", 75L);
        Route route2 = new Route("HIJ", "KJH", 10L);
        Route route3 = new Route("FGD", "HIJ", 10L);
        Long expectedRouteValueResult = 75L;

        Mockito.when(this.routeRepository.findRouteFrom("HIJ")).thenReturn(new ArrayList<>(Arrays.asList(route2)));

        TravelRoute travelRoute = this.routeService.findBestRoute("FGD", "KJH", new ArrayList<>(Arrays.asList(route1, route3)));

        Assert.assertNotEquals(expectedRouteValueResult, travelRoute.getValue());
    }
}
