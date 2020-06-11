package com.bexs.travel;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BexsTravelApiApplicationTests {

//	@Test
//	void shouldReturnBestRoute() {
//		String routeFrom = "GRU";
//		String routeTo = "CDG";
//		String expectedResult = "GRU > BRC > SCL > ORL > CDG > $40";
//
//		this.routeFacade = new RouteFacade();
//
//		String test = this.routeFacade.findBestRoute(routeFrom, routeTo);
//
//		//this.routeService = new RouteService(new RouteRepository(new CSVDatabaseFile()));
//
//		//List<Route> bestRoute = routeService.findRouteFrom(routeFrom);
//
//		//AssertionErrors.assertEquals("Best route", expectedResult, "");
//	}
//
//	@Test
//	void shouldSaveARoute() {
//		Route route = new Route("TET", "TOW", 10L);
//
//		this.routeFacade = new RouteFacade();
//
//		Route test = this.routeFacade.save("TET", "TOW", 10L);
//
//		test.toString();
//	}

//	@DisplayName("Test loading CSV")
//	@Test
//	void loadCSVTest() {
//
//		ClassLoader classLoader = getClass().getClassLoader();
//
//		try (FileReader reader = new FileReader(classLoader.getResource("input-routes.csv").getFile());
//			 BufferedReader br = new BufferedReader(reader)) {
//
//			String line;
//			while ((line = br.readLine()) != null) {
//				System.out.println(line);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}


	public static void main(String[] args) {
		String route = "GRUCDG";

		String[] routeFrom = route.split("-");

		System.out.println(routeFrom[0]);

		System.out.println(routeFrom[1]);
	}
}
