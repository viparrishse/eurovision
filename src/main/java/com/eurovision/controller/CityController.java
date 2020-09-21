package com.eurovision.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eurovision.entities.City;
import com.eurovision.exceptions.CityNotFoundException;
import com.eurovision.repository.CityRepository;

@RestController
public class CityController {

	@Autowired
	private CityRepository cityRepository;
	
	@GetMapping("/")
	public String welcome() {
		return "Welcome to Eurovision Services Sandbox!";
	}

	@RequestMapping(value = "/cities/queryByPage", method = RequestMethod.GET)
	public Page<City> getPagedCities(Pageable pageable) {
		return cityRepository.findAll(pageable);
	}
	
	/*
	 * Not necessary,but useful for this exercise.
	 * @return list of all cities
	 */
	@GetMapping("/cities")
	public List<City> getAllCities() {
		return cityRepository.findAll();
	}
	
	/*
	 * Not necessary, but useful for this exercise.
	 * @param id long of the city to consult
	 * @return city with this id
	 */
	@GetMapping("/cities/{id}")
	public City getCity(@PathVariable long id) {
		Optional<City> city = cityRepository.findById(id);
		if (!city.isPresent())
			throw new CityNotFoundException("id-" + id);
		return city.get();
	}
	
	/*
	 * Returns the cities with N highest IDs, ordered alphabetically by their names
	 * @param numberElements long number of elements to show
	 * @return list of all cities in sequence
	 */
	@GetMapping("/sequence/{numberElements}")
	public List<City> sequenceCity(@PathVariable long numberElements) {
		return cityRepository.findAll().stream().sorted(Comparator.comparingLong(City::getId).reversed()).
		limit(numberElements).sorted(Comparator.comparing(City::getName)).collect(Collectors.toList());
	}
}