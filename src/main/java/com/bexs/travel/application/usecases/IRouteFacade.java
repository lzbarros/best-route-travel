package com.bexs.travel.application.usecases;

import com.bexs.travel.domain.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;

public interface IRouteFacade {
    Route save(String routeFrom, String routeTo, Long value);

    TravelRoute findBestRoute(String routeFrom, String routeTo);
}
