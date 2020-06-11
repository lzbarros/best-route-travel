package com.bexs.travel.framework;

import com.bexs.travel.application.usecases.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;
import com.bexs.travel.domain.services.IRouteFacade;
import com.bexs.travel.framework.interfaces.commandline.RouteCommandLine;
import com.bexs.travel.framework.presenters.ITravelRoutePresenter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Deque;
import java.util.LinkedList;

@RunWith(MockitoJUnitRunner.class)
public class RouteCommandLineTest {

    @InjectMocks
    RouteCommandLine routeCommandLine;

    @Mock
    IRouteFacade routeFacade;

    @Mock
    ITravelRoutePresenter travelRoutePresenter;

    @Test
    public void shouldReturnABestRoute() {
        Route route = new Route("HGT", "JET", 10L);
        Deque<String> linkedList = new LinkedList<>();
        linkedList.addFirst(route.getRouteFrom());
        linkedList.addFirst(route.getRouteTo());
        String expectedBestRoute = "HGT > JET > $10";

        TravelRoute expectedTravelRoute = new TravelRoute(linkedList, 10L);

        Mockito.when(routeFacade.findBestRoute(route.getRouteFrom(), route.getRouteTo())).thenReturn(expectedTravelRoute);
        Mockito.when(travelRoutePresenter.getTravelRoutePresenter(expectedTravelRoute)).thenReturn(expectedBestRoute);

        String bestRoute = routeCommandLine.findBestRoute("HGT-JET");

        Assert.assertEquals(expectedBestRoute, bestRoute);
    }

    @Test
    public void shouldNotReturnABestRoute() {
        String expectedBestRoute = "HGT > JET > $10";

        String bestRoute = routeCommandLine.findBestRoute("HGT-JET");

        Assert.assertNotEquals(expectedBestRoute, bestRoute);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldReturnUnsupportedOperationExceptionForRouteWithSizeLessThan7() {
        routeCommandLine.findBestRoute("HGT-JE");

        Assert.fail();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldReturnUnsupportedOperationExceptionForRouteWithoutSplit() {
        routeCommandLine.findBestRoute("HGT+JET");

        Assert.fail();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldReturnUnsupportedOperationExceptionForRouteFromWithSizeLessThan3() {
        routeCommandLine.findBestRoute("HG -JET");

        Assert.fail();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldReturnUnsupportedOperationExceptionForRouteToWithSizeLessThan3() {
        routeCommandLine.findBestRoute("HGT- JE");

        Assert.fail();
    }
}
