package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.EmployeeStatus;
import com.qnowapp.repository.EmployeeStatusRepository;
import com.qnowapp.service.EmployeeStatusService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.EmployeeStatusCriteria;
import com.qnowapp.service.EmployeeStatusQueryService;

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
 * Integration tests for the {@Link EmployeeStatusResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class EmployeeStatusResourceIT {

    private static final String DEFAULT_STATUS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_NAME = "BBBBBBBBBB";

    @Autowired
    private EmployeeStatusRepository employeeStatusRepository;

    @Autowired
    private EmployeeStatusService employeeStatusService;

    @Autowired
    private EmployeeStatusQueryService employeeStatusQueryService;

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

    private MockMvc restEmployeeStatusMockMvc;

    private EmployeeStatus employeeStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeStatusResource employeeStatusResource = new EmployeeStatusResource(employeeStatusService, employeeStatusQueryService);
        this.restEmployeeStatusMockMvc = MockMvcBuilders.standaloneSetup(employeeStatusResource)
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
    public static EmployeeStatus createEntity(EntityManager em) {
        EmployeeStatus employeeStatus = new EmployeeStatus()
            .statusCode(DEFAULT_STATUS_CODE)
            .statusName(DEFAULT_STATUS_NAME);
        return employeeStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeStatus createUpdatedEntity(EntityManager em) {
        EmployeeStatus employeeStatus = new EmployeeStatus()
            .statusCode(UPDATED_STATUS_CODE)
            .statusName(UPDATED_STATUS_NAME);
        return employeeStatus;
    }

    @BeforeEach
    public void initTest() {
        employeeStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeStatus() throws Exception {
        int databaseSizeBeforeCreate = employeeStatusRepository.findAll().size();

        // Create the EmployeeStatus
        restEmployeeStatusMockMvc.perform(post("/api/employee-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeStatus)))
            .andExpect(status().isCreated());

        // Validate the EmployeeStatus in the database
        List<EmployeeStatus> employeeStatusList = employeeStatusRepository.findAll();
        assertThat(employeeStatusList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeStatus testEmployeeStatus = employeeStatusList.get(employeeStatusList.size() - 1);
        assertThat(testEmployeeStatus.getStatusCode()).isEqualTo(DEFAULT_STATUS_CODE);
        assertThat(testEmployeeStatus.getStatusName()).isEqualTo(DEFAULT_STATUS_NAME);
    }

    @Test
    @Transactional
    public void createEmployeeStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeStatusRepository.findAll().size();

        // Create the EmployeeStatus with an existing ID
        employeeStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeStatusMockMvc.perform(post("/api/employee-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeStatus)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeStatus in the database
        List<EmployeeStatus> employeeStatusList = employeeStatusRepository.findAll();
        assertThat(employeeStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeStatuses() throws Exception {
        // Initialize the database
        employeeStatusRepository.saveAndFlush(employeeStatus);

        // Get all the employeeStatusList
        restEmployeeStatusMockMvc.perform(get("/api/employee-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusCode").value(hasItem(DEFAULT_STATUS_CODE.toString())))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployeeStatus() throws Exception {
        // Initialize the database
        employeeStatusRepository.saveAndFlush(employeeStatus);

        // Get the employeeStatus
        restEmployeeStatusMockMvc.perform(get("/api/employee-statuses/{id}", employeeStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeStatus.getId().intValue()))
            .andExpect(jsonPath("$.statusCode").value(DEFAULT_STATUS_CODE.toString()))
            .andExpect(jsonPath("$.statusName").value(DEFAULT_STATUS_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllEmployeeStatusesByStatusCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeStatusRepository.saveAndFlush(employeeStatus);

        // Get all the employeeStatusList where statusCode equals to DEFAULT_STATUS_CODE
        defaultEmployeeStatusShouldBeFound("statusCode.equals=" + DEFAULT_STATUS_CODE);

        // Get all the employeeStatusList where statusCode equals to UPDATED_STATUS_CODE
        defaultEmployeeStatusShouldNotBeFound("statusCode.equals=" + UPDATED_STATUS_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeeStatusesByStatusCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeStatusRepository.saveAndFlush(employeeStatus);

        // Get all the employeeStatusList where statusCode in DEFAULT_STATUS_CODE or UPDATED_STATUS_CODE
        defaultEmployeeStatusShouldBeFound("statusCode.in=" + DEFAULT_STATUS_CODE + "," + UPDATED_STATUS_CODE);

        // Get all the employeeStatusList where statusCode equals to UPDATED_STATUS_CODE
        defaultEmployeeStatusShouldNotBeFound("statusCode.in=" + UPDATED_STATUS_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeeStatusesByStatusCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeStatusRepository.saveAndFlush(employeeStatus);

        // Get all the employeeStatusList where statusCode is not null
        defaultEmployeeStatusShouldBeFound("statusCode.specified=true");

        // Get all the employeeStatusList where statusCode is null
        defaultEmployeeStatusShouldNotBeFound("statusCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeeStatusesByStatusNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeStatusRepository.saveAndFlush(employeeStatus);

        // Get all the employeeStatusList where statusName equals to DEFAULT_STATUS_NAME
        defaultEmployeeStatusShouldBeFound("statusName.equals=" + DEFAULT_STATUS_NAME);

        // Get all the employeeStatusList where statusName equals to UPDATED_STATUS_NAME
        defaultEmployeeStatusShouldNotBeFound("statusName.equals=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeStatusesByStatusNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeStatusRepository.saveAndFlush(employeeStatus);

        // Get all the employeeStatusList where statusName in DEFAULT_STATUS_NAME or UPDATED_STATUS_NAME
        defaultEmployeeStatusShouldBeFound("statusName.in=" + DEFAULT_STATUS_NAME + "," + UPDATED_STATUS_NAME);

        // Get all the employeeStatusList where statusName equals to UPDATED_STATUS_NAME
        defaultEmployeeStatusShouldNotBeFound("statusName.in=" + UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeeStatusesByStatusNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeStatusRepository.saveAndFlush(employeeStatus);

        // Get all the employeeStatusList where statusName is not null
        defaultEmployeeStatusShouldBeFound("statusName.specified=true");

        // Get all the employeeStatusList where statusName is null
        defaultEmployeeStatusShouldNotBeFound("statusName.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeStatusShouldBeFound(String filter) throws Exception {
        restEmployeeStatusMockMvc.perform(get("/api/employee-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusCode").value(hasItem(DEFAULT_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].statusName").value(hasItem(DEFAULT_STATUS_NAME)));

        // Check, that the count call also returns 1
        restEmployeeStatusMockMvc.perform(get("/api/employee-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeStatusShouldNotBeFound(String filter) throws Exception {
        restEmployeeStatusMockMvc.perform(get("/api/employee-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeStatusMockMvc.perform(get("/api/employee-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmployeeStatus() throws Exception {
        // Get the employeeStatus
        restEmployeeStatusMockMvc.perform(get("/api/employee-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeStatus() throws Exception {
        // Initialize the database
        employeeStatusService.save(employeeStatus);

        int databaseSizeBeforeUpdate = employeeStatusRepository.findAll().size();

        // Update the employeeStatus
        EmployeeStatus updatedEmployeeStatus = employeeStatusRepository.findById(employeeStatus.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeStatus are not directly saved in db
        em.detach(updatedEmployeeStatus);
        updatedEmployeeStatus
            .statusCode(UPDATED_STATUS_CODE)
            .statusName(UPDATED_STATUS_NAME);

        restEmployeeStatusMockMvc.perform(put("/api/employee-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeStatus)))
            .andExpect(status().isOk());

        // Validate the EmployeeStatus in the database
        List<EmployeeStatus> employeeStatusList = employeeStatusRepository.findAll();
        assertThat(employeeStatusList).hasSize(databaseSizeBeforeUpdate);
        EmployeeStatus testEmployeeStatus = employeeStatusList.get(employeeStatusList.size() - 1);
        assertThat(testEmployeeStatus.getStatusCode()).isEqualTo(UPDATED_STATUS_CODE);
        assertThat(testEmployeeStatus.getStatusName()).isEqualTo(UPDATED_STATUS_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeStatus() throws Exception {
        int databaseSizeBeforeUpdate = employeeStatusRepository.findAll().size();

        // Create the EmployeeStatus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeStatusMockMvc.perform(put("/api/employee-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeStatus)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeStatus in the database
        List<EmployeeStatus> employeeStatusList = employeeStatusRepository.findAll();
        assertThat(employeeStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeStatus() throws Exception {
        // Initialize the database
        employeeStatusService.save(employeeStatus);

        int databaseSizeBeforeDelete = employeeStatusRepository.findAll().size();

        // Delete the employeeStatus
        restEmployeeStatusMockMvc.perform(delete("/api/employee-statuses/{id}", employeeStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EmployeeStatus> employeeStatusList = employeeStatusRepository.findAll();
        assertThat(employeeStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeStatus.class);
        EmployeeStatus employeeStatus1 = new EmployeeStatus();
        employeeStatus1.setId(1L);
        EmployeeStatus employeeStatus2 = new EmployeeStatus();
        employeeStatus2.setId(employeeStatus1.getId());
        assertThat(employeeStatus1).isEqualTo(employeeStatus2);
        employeeStatus2.setId(2L);
        assertThat(employeeStatus1).isNotEqualTo(employeeStatus2);
        employeeStatus1.setId(null);
        assertThat(employeeStatus1).isNotEqualTo(employeeStatus2);
    }
}
