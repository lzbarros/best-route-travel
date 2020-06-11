package com.bexs.travel.framework;

import com.bexs.travel.application.usecases.vo.TravelRoute;
import com.bexs.travel.framework.presenters.TravelRoutePresenter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Deque;
import java.util.LinkedList;

@RunWith(MockitoJUnitRunner.class)
public class TravelRoutePresenterTest {

    @InjectMocks
    TravelRoutePresenter travelRoutePresenter;

    @Test
    public void shouldReturnABestRoute() {
        String expectedBestRoute = "GFD > HJK > OIU > $77";
        Deque<String> linkedList = new LinkedList<>();
        linkedList.addFirst("OIU");
        linkedList.addFirst("HJK");
        linkedList.addFirst("GFD");

        TravelRoute travelRoute = new TravelRoute(linkedList, 77L);

        String bestRoute = travelRoutePresenter.getTravelRoutePresenter(travelRoute);

        Assert.assertEquals(expectedBestRoute, bestRoute);
    }

    @Test
    public void shouldReturnAWrongBestRoute() {
        String expectedBestRoute = "GFD > HJK > OIU > $77";
        Deque<String> linkedList = new LinkedList<>();
        linkedList.addFirst("OIU");
        linkedList.addFirst("GFD");
        linkedList.addFirst("HJK");

        TravelRoute travelRoute = new TravelRoute(linkedList, 77L);

        String bestRoute = travelRoutePresenter.getTravelRoutePresenter(travelRoute);

        Assert.assertNotEquals(expectedBestRoute, bestRoute);
    }
}
