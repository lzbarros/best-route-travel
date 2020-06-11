package com.bexs.travel.application;

import com.bexs.travel.application.infrastructure.databaseconnector.CSVDatabaseConnector;
import com.bexs.travel.application.infrastructure.datasource.IDataSource;
import com.bexs.travel.domain.entities.Route;
import com.bexs.travel.domain.exceptions.DatabaseFailException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CSVDatabaseConnectorTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @InjectMocks
    CSVDatabaseConnector csvDatabaseConnector;

    @Mock
    IDataSource dataSource;

    @Test
    public void shouldReturnAListOfRoutes() throws IOException {
        File file = folder.newFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write("GRU,CDG,10");
        bufferedWriter.close();

        Mockito.when(dataSource.getDataSource()).thenReturn(file);

        List<Route> routeList = csvDatabaseConnector.find();

        Assert.assertEquals(false, routeList.isEmpty());
    }

    @Test
    public void shouldNotReturnAListOfRoutes() throws IOException {
        File file = folder.newFile();

        Mockito.when(dataSource.getDataSource()).thenReturn(file);

        List<Route> routeList = csvDatabaseConnector.find();

        Assert.assertEquals(true, routeList.isEmpty());
    }

    @Test(expected = DatabaseFailException.class)
    public void shouldReturnInterruptedIOExceptionWhenFind() throws IOException {
        File file = folder.newFolder();

        Mockito.when(dataSource.getDataSource()).thenReturn(file);

        csvDatabaseConnector.find();

        Assert.fail();
    }

    @Test
    public void shouldSaveARoute() throws IOException {
        Route expectedRoute = new Route("GRT", "HJG", 10L);

        File file = folder.newFile();

        Mockito.when(dataSource.getDataSource()).thenReturn(file);

        csvDatabaseConnector.save(expectedRoute);

        List<Route> routeList = csvDatabaseConnector.find();

        Optional<Route> optionalRoute = routeList.stream().filter(
                r -> r.getRouteFrom().equalsIgnoreCase(expectedRoute.getRouteFrom()) && r.getRouteTo().equalsIgnoreCase(expectedRoute.getRouteTo()))
                .findFirst();

        Assert.assertTrue(optionalRoute.isPresent());
    }

    @Test(expected = DatabaseFailException.class)
    public void shouldReturnInterruptedIOExceptionWhenSave() throws IOException {
        File file = folder.newFolder();

        Mockito.when(dataSource.getDataSource()).thenReturn(file);

        csvDatabaseConnector.save(new Route("FGD", "KJH", 10L));

        Assert.fail();
    }

    @Test
    public void shouldGenerateARouteLine() {
        Route route = new Route("GRT", "HJG", 10L);
        String expectedRouteLine = "GRT,HJG,10";

        String routeLine = csvDatabaseConnector.routeToLine(route);

        Assert.assertEquals(expectedRouteLine, routeLine);
    }

    @Test
    public void shouldReturnARoute() {
        Route expectedRoute = new Route("GRT", "HJG", 10L);
        String[] metadata = new String[]{"GRT", "HJG", "10"};

        Route route = csvDatabaseConnector.createRoute(metadata);

        Assert.assertEquals(expectedRoute, route);
    }
}
