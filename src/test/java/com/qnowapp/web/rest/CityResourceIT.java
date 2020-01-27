package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.City;
import com.qnowapp.repository.CityRepository;
import com.qnowapp.service.CityService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.CityCriteria;
import com.qnowapp.service.CityQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.qnowapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CityResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class CityResourceIT {

    private static final String DEFAULT_CITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CITY_NAME = "BBBBBBBBBB";

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private CityQueryService cityQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCityMockMvc;

    private City city;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CityResource cityResource = new CityResource(cityService, cityQueryService);
        this.restCityMockMvc = MockMvcBuilders.standaloneSetup(cityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createEntity(EntityManager em) {
        City city = new City()
            .cityCode(DEFAULT_CITY_CODE)
            .cityName(DEFAULT_CITY_NAME);
        return city;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static City createUpdatedEntity(EntityManager em) {
        City city = new City()
            .cityCode(UPDATED_CITY_CODE)
            .cityName(UPDATED_CITY_NAME);
        return city;
    }

    @BeforeEach
    public void initTest() {
        city = createEntity(em);
    }

    @Test
    @Transactional
    public void createCity() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // Create the City
        restCityMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(city)))
            .andExpect(status().isCreated());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate + 1);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getCityCode()).isEqualTo(DEFAULT_CITY_CODE);
        assertThat(testCity.getCityName()).isEqualTo(DEFAULT_CITY_NAME);
    }

    @Test
    @Transactional
    public void createCityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cityRepository.findAll().size();

        // Create the City with an existing ID
        city.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityMockMvc.perform(post("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(city)))
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCities() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList
        restCityMockMvc.perform(get("/api/cities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(city.getId().intValue())))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE.toString())))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getCity() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get the city
        restCityMockMvc.perform(get("/api/cities/{id}", city.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(city.getId().intValue()))
            .andExpect(jsonPath("$.cityCode").value(DEFAULT_CITY_CODE.toString()))
            .andExpect(jsonPath("$.cityName").value(DEFAULT_CITY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllCitiesByCityCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityCode equals to DEFAULT_CITY_CODE
        defaultCityShouldBeFound("cityCode.equals=" + DEFAULT_CITY_CODE);

        // Get all the cityList where cityCode equals to UPDATED_CITY_CODE
        defaultCityShouldNotBeFound("cityCode.equals=" + UPDATED_CITY_CODE);
    }

    @Test
    @Transactional
    public void getAllCitiesByCityCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityCode in DEFAULT_CITY_CODE or UPDATED_CITY_CODE
        defaultCityShouldBeFound("cityCode.in=" + DEFAULT_CITY_CODE + "," + UPDATED_CITY_CODE);

        // Get all the cityList where cityCode equals to UPDATED_CITY_CODE
        defaultCityShouldNotBeFound("cityCode.in=" + UPDATED_CITY_CODE);
    }

    @Test
    @Transactional
    public void getAllCitiesByCityCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityCode is not null
        defaultCityShouldBeFound("cityCode.specified=true");

        // Get all the cityList where cityCode is null
        defaultCityShouldNotBeFound("cityCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCitiesByCityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityName equals to DEFAULT_CITY_NAME
        defaultCityShouldBeFound("cityName.equals=" + DEFAULT_CITY_NAME);

        // Get all the cityList where cityName equals to UPDATED_CITY_NAME
        defaultCityShouldNotBeFound("cityName.equals=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesByCityNameIsInShouldWork() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityName in DEFAULT_CITY_NAME or UPDATED_CITY_NAME
        defaultCityShouldBeFound("cityName.in=" + DEFAULT_CITY_NAME + "," + UPDATED_CITY_NAME);

        // Get all the cityList where cityName equals to UPDATED_CITY_NAME
        defaultCityShouldNotBeFound("cityName.in=" + UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    public void getAllCitiesByCityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cityRepository.saveAndFlush(city);

        // Get all the cityList where cityName is not null
        defaultCityShouldBeFound("cityName.specified=true");

        // Get all the cityList where cityName is null
        defaultCityShouldNotBeFound("cityName.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCityShouldBeFound(String filter) throws Exception {
        restCityMockMvc.perform(get("/api/cities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(city.getId().intValue())))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE)))
            .andExpect(jsonPath("$.[*].cityName").value(hasItem(DEFAULT_CITY_NAME)));

        // Check, that the count call also returns 1
        restCityMockMvc.perform(get("/api/cities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCityShouldNotBeFound(String filter) throws Exception {
        restCityMockMvc.perform(get("/api/cities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCityMockMvc.perform(get("/api/cities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCity() throws Exception {
        // Get the city
        restCityMockMvc.perform(get("/api/cities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCity() throws Exception {
        // Initialize the database
        cityService.save(city);

        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Update the city
        City updatedCity = cityRepository.findById(city.getId()).get();
        // Disconnect from session so that the updates on updatedCity are not directly saved in db
        em.detach(updatedCity);
        updatedCity
            .cityCode(UPDATED_CITY_CODE)
            .cityName(UPDATED_CITY_NAME);

        restCityMockMvc.perform(put("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCity)))
            .andExpect(status().isOk());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
        City testCity = cityList.get(cityList.size() - 1);
        assertThat(testCity.getCityCode()).isEqualTo(UPDATED_CITY_CODE);
        assertThat(testCity.getCityName()).isEqualTo(UPDATED_CITY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCity() throws Exception {
        int databaseSizeBeforeUpdate = cityRepository.findAll().size();

        // Create the City

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityMockMvc.perform(put("/api/cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(city)))
            .andExpect(status().isBadRequest());

        // Validate the City in the database
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCity() throws Exception {
        // Initialize the database
        cityService.save(city);

        int databaseSizeBeforeDelete = cityRepository.findAll().size();

        // Delete the city
        restCityMockMvc.perform(delete("/api/cities/{id}", city.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<City> cityList = cityRepository.findAll();
        assertThat(cityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(City.class);
        City city1 = new City();
        city1.setId(1L);
        City city2 = new City();
        city2.setId(city1.getId());
        assertThat(city1).isEqualTo(city2);
        city2.setId(2L);
        assertThat(city1).isNotEqualTo(city2);
        city1.setId(null);
        assertThat(city1).isNotEqualTo(city2);
    }
}
