package com.eurovision;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.eurovision.controller.CityController;
import com.eurovision.entities.City;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RequestControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private CityController cityController;
	
	/*Application can start*/
	@Test
	public void contextLoads() {
		assertThat(cityController).isNotNull();
	}

	@Test
	public void emptyShouldReturnWelcomeMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
				String.class)).contains("Welcome to Eurovision Services Sandbox!");
	}
	
	
	@Test
	public void citiesShouldReturnList() throws Exception {
		List<City> returnedList=this.restTemplate.getForObject("http://localhost:" + port + "/cities",
				List.class);	
		List<City> expectedList=cityController.getAllCities();

		assertThat(returnedList).usingElementComparatorOnFields("name")
        .containsAnyElementsOf(expectedList);
	}
	
}