package com.bexs.travel.application;

import com.bexs.travel.domain.entities.Route;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

public class RouteTest {

    @Mock
    Route route;

    @Test
    public void shouldReturnEqualObjectWithTheSameContent() {
        Route route = new Route("GTH", "OIT", 10L);

        Route route1 = new Route("GTH", "OIT", 10L);

        Assert.assertTrue(route.equals(route1));
    }

    @Test
    public void shouldReturnEqualObjectWithTheSameReference() {
        Route route = new Route("GTH", "OIT", 10L);

        Route route1 = route;

        Assert.assertTrue(route.equals(route1));
    }

    @Test
    public void shouldNotReturnEqualObject() {
        Route route = new Route("GTH", "OIT", 10L);

        Route route1 = new Route("GTH", "OIT", 77L);

        Assert.assertFalse(route.equals(route1));
    }

    @Test
    public void shouldNotReturnEqualObjectWhenNull() {
        Route route = new Route("GTH", "OIT", 10L);

        Assert.assertFalse(route.equals(null));
    }
}
