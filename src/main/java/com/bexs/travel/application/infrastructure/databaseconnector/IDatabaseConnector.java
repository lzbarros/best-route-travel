package com.bexs.travel.application.infrastructure.databaseconnector;

import com.bexs.travel.domain.entities.Route;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IDatabaseConnector {
    List<Route> find();

    Route save(@NotNull final Route route);
}
