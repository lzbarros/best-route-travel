package com.bexs.travel.framework.presenters;

import com.bexs.travel.application.usecases.vo.TravelRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.Deque;

@Named("travelRoutePresenter")
public class TravelRoutePresenter implements ITravelRoutePresenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TravelRoutePresenter.class);

    public String getTravelRoutePresenter(@NotNull TravelRoute travelRoute) {
        LOGGER.info("Creating the route presenter");

        Deque<String> linkedList = travelRoute.getRoute();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(linkedList.removeFirst());

        while (!linkedList.isEmpty()) {
            stringBuilder.append(" > ").append(linkedList.removeFirst());
        }
        stringBuilder.append(" > ").append("$").append(travelRoute.getValue());

        return stringBuilder.toString();
    }
}
