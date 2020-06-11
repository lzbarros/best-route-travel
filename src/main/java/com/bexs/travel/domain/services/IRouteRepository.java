package com.bexs.travel.domain.services;

import com.bexs.travel.domain.entities.Route;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface IRouteRepository {

    Route save(final @NotNull Route content);

    List<Route> findRouteFrom(@NotNull final String route);

    List<Route> findRouteTo(@NotNull final String route);

    List<Route> findRouteFromAndRouteTo(@NotNull final String routeFrom, @NotNull final String routeTo);
}
