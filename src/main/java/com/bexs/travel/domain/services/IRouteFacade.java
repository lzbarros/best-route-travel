package com.bexs.travel.domain.services;

import com.bexs.travel.application.usecases.vo.TravelRoute;
import com.bexs.travel.domain.entities.Route;

public interface IRouteFacade {
    Route save(String routeFrom, String routeTo, Long value);

    TravelRoute findBestRoute(String routeFrom, String routeTo);
}
