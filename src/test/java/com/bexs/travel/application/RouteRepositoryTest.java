package com.bexs.travel.application;

import com.bexs.travel.application.infrastructure.databaseconnector.IDatabaseConnector;
import com.bexs.travel.domain.entities.Route;
import com.bexs.travel.domain.services.RouteRepository;
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
public class RouteRepositoryTest {

    @InjectMocks
    RouteRepository routeRepository;

    @Mock
    IDatabaseConnector databaseConnector;

    @Test
    public void shouldReturnAListRouteFrom() {
        List<Route> routeList = new ArrayList<>(Arrays.asList(new Route("GHT", "GTR", 10L)));
        Mockito.when(this.databaseConnector.find()).thenReturn(routeList);

        List<Route> routeListFrom = this.routeRepository.findRouteFrom("GHT");

        Assert.assertEquals(routeList.size(), routeListFrom.size());
        Assert.assertEquals(routeList.get(0).getRouteFrom(), routeListFrom.get(0).getRouteFrom());
    }

    @Test
    public void shouldNotReturnAListRouteFrom() {
        List<Route> routeList = new ArrayList<>(Arrays.asList(new Route("GHT", "GTR", 10L)));
        Mockito.when(this.databaseConnector.find()).thenReturn(routeList);

        List<Route> routeListFrom = this.routeRepository.findRouteFrom("GHI");

        Assert.assertEquals(0, routeListFrom.size());
    }

    @Test
    public void shouldReturnAListRouteTo() {
        List<Route> routeList = new ArrayList<>(Arrays.asList(new Route("GHT", "GTR", 10L)));
        Mockito.when(this.databaseConnector.find()).thenReturn(routeList);

        List<Route> routeListTo = this.routeRepository.findRouteTo("GTR");

        Assert.assertEquals(routeList.size(), routeListTo.size());
        Assert.assertEquals(routeList.get(0).getRouteFrom(), routeListTo.get(0).getRouteFrom());
    }

    @Test
    public void shouldNotReturnAListRouteTo() {
        List<Route> routeList = new ArrayList<>(Arrays.asList(new Route("GHT", "GTR", 10L)));
        Mockito.when(this.databaseConnector.find()).thenReturn(routeList);

        List<Route> routeListTo = this.routeRepository.findRouteTo("GHI");

        Assert.assertEquals(0, routeListTo.size());
    }

    @Test
    public void shouldReturnAListRouteFromAndTo() {
        List<Route> routeList = new ArrayList<>(Arrays.asList(new Route("GHT", "GTR", 10L)));
        Mockito.when(this.databaseConnector.find()).thenReturn(routeList);

        List<Route> routeListFromAndTo = this.routeRepository.findRouteFromAndRouteTo("GHT", "GTR");

        Assert.assertEquals(routeList.size(), routeListFromAndTo.size());
        Assert.assertEquals(routeList.get(0).getRouteFrom(), routeListFromAndTo.get(0).getRouteFrom());
    }

    @Test
    public void shouldNotReturnAListRouteFromAndToWrongTo() {
        List<Route> routeList = new ArrayList<>(Arrays.asList(new Route("GHT", "GTR", 10L)));
        Mockito.when(this.databaseConnector.find()).thenReturn(routeList);

        List<Route> routeListFromAndTo = this.routeRepository.findRouteFromAndRouteTo("GHT", "GTI");

        Assert.assertEquals(0, routeListFromAndTo.size());
    }

    @Test
    public void shouldNotReturnAListRouteFromAndToWrongFrom() {
        List<Route> routeList = new ArrayList<>(Arrays.asList(new Route("GTT", "GTI", 10L)));
        Mockito.when(this.databaseConnector.find()).thenReturn(routeList);

        List<Route> routeListFromAndTo = this.routeRepository.findRouteFromAndRouteTo("GHT", "GTI");

        Assert.assertEquals(0, routeListFromAndTo.size());
    }

    @Test
    public void shouldSaveARoute() {
        Route expectedRoute = new Route("FGD", "KJH", 10L);

        Mockito.when(this.databaseConnector.save(ArgumentMatchers.any())).thenReturn(expectedRoute);

        Route route = this.routeRepository.save(expectedRoute);

        Assert.assertEquals(expectedRoute, route);
    }
}
