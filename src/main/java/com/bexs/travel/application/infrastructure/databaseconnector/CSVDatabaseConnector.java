package com.bexs.travel.application.infrastructure.databaseconnector;

import com.bexs.travel.application.infrastructure.datasource.IDataSource;
import com.bexs.travel.domain.entities.Route;
import com.bexs.travel.domain.exceptions.DatabaseFailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Named("csvDatabaseFile")
public class CSVDatabaseConnector implements IDatabaseConnector {
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVDatabaseConnector.class.getName());

    @Inject
    IDataSource<File> dataSource;

    public CSVDatabaseConnector(@NotNull final IDataSource<File> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Route> find() {
        List<Route> routeList = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(this.dataSource.getDataSource()))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] fields = line.split(",");

                routeList.add(createRoute(fields));

                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            LOGGER.error("Error reading CSV file");
            throw new DatabaseFailException("Error reading CSV file");
        }

        return routeList;
    }

    @Override
    public Route save(@NotNull Route route) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.dataSource.getDataSource(), true))) {
            bufferedWriter.write(routeToLine(route));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            LOGGER.error("Error saving CSV file");
            throw new DatabaseFailException("Error saving CSV file");
        }

        return route;
    }

    public String routeToLine(@NotNull final Route route) {
        return new StringBuilder().append(route.getRouteFrom())
                .append(",").append(route.getRouteTo()).append(",").append(route.getValue()).toString();
    }

    public Route createRoute(@NotNull final String[] routeMetadata) {
        String routeFrom = routeMetadata[0];
        String routeTo = routeMetadata[1];
        Long value = Long.valueOf(routeMetadata[2]);

        return new Route(routeFrom, routeTo, value);
    }
}
