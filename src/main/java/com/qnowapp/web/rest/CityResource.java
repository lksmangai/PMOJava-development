package com.qnowapp.web.rest;

import com.qnowapp.domain.City;

import com.qnowapp.service.CityService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.CityCriteria;


import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qnowapp.domain.City}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class CityResource {

    private final Logger log = LoggerFactory.getLogger(CityResource.class);

    private static final String ENTITY_NAME = "city";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CityService cityService;

    public CityResource(CityService cityService) {
        this.cityService = cityService;
      
    }

    /**
     * {@code POST  /cities} : Create a new city.
     *
     * @param city the city to create.
     * @return 
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new city, or with status {@code 400 (Bad Request)} if the city has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @GetMapping("/hello")
    public String test () {
    	return "hello";
    }
    
    
    
    
    @CrossOrigin
    @PostMapping("/cities")
    public ResponseEntity<City> createCity(@RequestBody City city) throws URISyntaxException {
        log.debug("REST request to save City : {}", city);
        if (city.getId() != null) {
            throw new BadRequestAlertException("A new city cannot already have an ID", ENTITY_NAME, "idexists");
        }
        City result = cityService.save(city);
        return ResponseEntity.created(new URI("/api/cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cities} : Updates an existing city.
     *
     * @param city the city to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated city,
     * or with status {@code 400 (Bad Request)} if the city is not valid,
     * or with status {@code 500 (Internal Server Error)} if the city couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/cities")
    public ResponseEntity<City> updateCity(@RequestBody City city) throws URISyntaxException {
        log.debug("REST request to update City : {}", city);
        if (city.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        City result = cityService.save(city);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, city.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cities} : get all the cities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cities in body.
     */
    @CrossOrigin
    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities() {
        log.debug("REST request to get Cities by criteria: {}");
        List<City> entityList = cityService.findAll();
        return ResponseEntity.ok().body(entityList);
    }


    /**
     * {@code GET  /cities/:id} : get the "id" city.
     *
     * @param id the id of the city to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the city, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/cities/{id}")
    public ResponseEntity<City> getCity(@PathVariable Long id) {
        log.debug("REST request to get City : {}", id);
        Optional<City> city = cityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(city);
    }

    /**
     * {@code DELETE  /cities/:id} : delete the "id" city.
     *
     * @param id the id of the city to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        log.debug("REST request to delete City : {}", id);
        cityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}