package com.bexs.travel.application;

import com.bexs.travel.application.usecases.vo.TravelRoute;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Deque;
import java.util.LinkedList;

public class TravelRouteTest {

    @Mock
    TravelRoute travelRoute;

    @Test
    public void shouldReturnEqualObjectWithTheSameContent() {
        Deque<String> linkedList = new LinkedList<>();
        linkedList.addFirst("OIU");
        linkedList.addFirst("GFD");
        linkedList.addFirst("HJK");

        TravelRoute travelRoute = new TravelRoute(linkedList, 10L);

        TravelRoute travelRoute1 = new TravelRoute(linkedList, 10L);

        Assert.assertTrue(travelRoute.equals(travelRoute1));
    }

    @Test
    public void shouldReturnEqualObjectWithTheSameReference() {
        Deque<String> linkedList = new LinkedList<>();
        linkedList.addFirst("OIU");
        linkedList.addFirst("GFD");
        linkedList.addFirst("HJK");

        TravelRoute travelRoute = new TravelRoute(linkedList, 10L);

        TravelRoute travelRoute1 = travelRoute;

        Assert.assertTrue(travelRoute.equals(travelRoute1));
    }

    @Test
    public void shouldNotReturnEqualObject() {
        Deque<String> linkedList = new LinkedList<>();
        linkedList.addFirst("OIU");
        linkedList.addFirst("GFD");
        linkedList.addFirst("HJK");

        TravelRoute travelRoute = new TravelRoute(linkedList, 10L);

        TravelRoute travelRoute1 = new TravelRoute(linkedList, 70L);

        Assert.assertFalse(travelRoute.equals(travelRoute1));
    }

    @Test
    public void shouldNotReturnEqualObjectWhenNull() {
        Deque<String> linkedList = new LinkedList<>();
        linkedList.addFirst("OIU");
        linkedList.addFirst("GFD");
        linkedList.addFirst("HJK");

        TravelRoute travelRoute = new TravelRoute(linkedList, 10L);

        Assert.assertFalse(travelRoute.equals(null));
    }
}
