package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectAllocation;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.ProjectRoles;
import com.qnowapp.repository.ProjectAllocationRepository;
import com.qnowapp.service.ProjectAllocationService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectAllocationCriteria;
import com.qnowapp.service.ProjectAllocationQueryService;

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
 * Integration tests for the {@Link ProjectAllocationResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectAllocationResourceIT {

    private static final Double DEFAULT_PERCENTAGE = 1D;
    private static final Double UPDATED_PERCENTAGE = 2D;

    @Autowired
    private ProjectAllocationRepository projectAllocationRepository;

    @Autowired
    private ProjectAllocationService projectAllocationService;

    @Autowired
    private ProjectAllocationQueryService projectAllocationQueryService;

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

    private MockMvc restProjectAllocationMockMvc;

    private ProjectAllocation projectAllocation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectAllocationResource projectAllocationResource = new ProjectAllocationResource( projectAllocationService, projectAllocationQueryService);
        this.restProjectAllocationMockMvc = MockMvcBuilders.standaloneSetup(projectAllocationResource)
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
    public static ProjectAllocation createEntity(EntityManager em) {
        ProjectAllocation projectAllocation = new ProjectAllocation()
            .percentage(DEFAULT_PERCENTAGE);
        return projectAllocation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectAllocation createUpdatedEntity(EntityManager em) {
        ProjectAllocation projectAllocation = new ProjectAllocation()
            .percentage(UPDATED_PERCENTAGE);
        return projectAllocation;
    }

    @BeforeEach
    public void initTest() {
        projectAllocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectAllocation() throws Exception {
        int databaseSizeBeforeCreate = projectAllocationRepository.findAll().size();

        // Create the ProjectAllocation
        restProjectAllocationMockMvc.perform(post("/api/project-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectAllocation)))
            .andExpect(status().isCreated());

        // Validate the ProjectAllocation in the database
        List<ProjectAllocation> projectAllocationList = projectAllocationRepository.findAll();
        assertThat(projectAllocationList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectAllocation testProjectAllocation = projectAllocationList.get(projectAllocationList.size() - 1);
        assertThat(testProjectAllocation.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createProjectAllocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectAllocationRepository.findAll().size();

        // Create the ProjectAllocation with an existing ID
        projectAllocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectAllocationMockMvc.perform(post("/api/project-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectAllocation)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectAllocation in the database
        List<ProjectAllocation> projectAllocationList = projectAllocationRepository.findAll();
        assertThat(projectAllocationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectAllocations() throws Exception {
        // Initialize the database
        projectAllocationRepository.saveAndFlush(projectAllocation);

        // Get all the projectAllocationList
        restProjectAllocationMockMvc.perform(get("/api/project-allocations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectAllocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getProjectAllocation() throws Exception {
        // Initialize the database
        projectAllocationRepository.saveAndFlush(projectAllocation);

        // Get the projectAllocation
        restProjectAllocationMockMvc.perform(get("/api/project-allocations/{id}", projectAllocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectAllocation.getId().intValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllProjectAllocationsByPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        projectAllocationRepository.saveAndFlush(projectAllocation);

        // Get all the projectAllocationList where percentage equals to DEFAULT_PERCENTAGE
        defaultProjectAllocationShouldBeFound("percentage.equals=" + DEFAULT_PERCENTAGE);

        // Get all the projectAllocationList where percentage equals to UPDATED_PERCENTAGE
        defaultProjectAllocationShouldNotBeFound("percentage.equals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProjectAllocationsByPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        projectAllocationRepository.saveAndFlush(projectAllocation);

        // Get all the projectAllocationList where percentage in DEFAULT_PERCENTAGE or UPDATED_PERCENTAGE
        defaultProjectAllocationShouldBeFound("percentage.in=" + DEFAULT_PERCENTAGE + "," + UPDATED_PERCENTAGE);

        // Get all the projectAllocationList where percentage equals to UPDATED_PERCENTAGE
        defaultProjectAllocationShouldNotBeFound("percentage.in=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProjectAllocationsByPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectAllocationRepository.saveAndFlush(projectAllocation);

        // Get all the projectAllocationList where percentage is not null
        defaultProjectAllocationShouldBeFound("percentage.specified=true");

        // Get all the projectAllocationList where percentage is null
        defaultProjectAllocationShouldNotBeFound("percentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectAllocationsByImEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        ImEmployee imEmployee = ImEmployeeResourceIT.createEntity(em);
        em.persist(imEmployee);
        em.flush();
        projectAllocation.setImEmployee(imEmployee);
        projectAllocationRepository.saveAndFlush(projectAllocation);
        Long imEmployeeId = imEmployee.getId();

        // Get all the projectAllocationList where imEmployee equals to imEmployeeId
        defaultProjectAllocationShouldBeFound("imEmployeeId.equals=" + imEmployeeId);

        // Get all the projectAllocationList where imEmployee equals to imEmployeeId + 1
        defaultProjectAllocationShouldNotBeFound("imEmployeeId.equals=" + (imEmployeeId + 1));
    }


    @Test
    @Transactional
    public void getAllProjectAllocationsByImProjectsIsEqualToSomething() throws Exception {
        // Initialize the database
        ImProjects imProjects = ImProjectsResourceIT.createEntity(em);
        em.persist(imProjects);
        em.flush();
        projectAllocation.setImProjects(imProjects);
        projectAllocationRepository.saveAndFlush(projectAllocation);
        Long imProjectsId = imProjects.getId();

        // Get all the projectAllocationList where imProjects equals to imProjectsId
        defaultProjectAllocationShouldBeFound("imProjectsId.equals=" + imProjectsId);

        // Get all the projectAllocationList where imProjects equals to imProjectsId + 1
        defaultProjectAllocationShouldNotBeFound("imProjectsId.equals=" + (imProjectsId + 1));
    }


    @Test
    @Transactional
    public void getAllProjectAllocationsByProjectRolesIsEqualToSomething() throws Exception {
        // Initialize the database
        ProjectRoles projectRoles = ProjectRolesResourceIT.createEntity(em);
        em.persist(projectRoles);
        em.flush();
        projectAllocation.setProjectRoles(projectRoles);
        projectAllocationRepository.saveAndFlush(projectAllocation);
        Long projectRolesId = projectRoles.getId();

        // Get all the projectAllocationList where projectRoles equals to projectRolesId
        defaultProjectAllocationShouldBeFound("projectRolesId.equals=" + projectRolesId);

        // Get all the projectAllocationList where projectRoles equals to projectRolesId + 1
        defaultProjectAllocationShouldNotBeFound("projectRolesId.equals=" + (projectRolesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectAllocationShouldBeFound(String filter) throws Exception {
        restProjectAllocationMockMvc.perform(get("/api/project-allocations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectAllocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())));

        // Check, that the count call also returns 1
        restProjectAllocationMockMvc.perform(get("/api/project-allocations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectAllocationShouldNotBeFound(String filter) throws Exception {
        restProjectAllocationMockMvc.perform(get("/api/project-allocations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectAllocationMockMvc.perform(get("/api/project-allocations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectAllocation() throws Exception {
        // Get the projectAllocation
        restProjectAllocationMockMvc.perform(get("/api/project-allocations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectAllocation() throws Exception {
        // Initialize the database
        projectAllocationService.save(projectAllocation);

        int databaseSizeBeforeUpdate = projectAllocationRepository.findAll().size();

        // Update the projectAllocation
        ProjectAllocation updatedProjectAllocation = projectAllocationRepository.findById(projectAllocation.getId()).get();
        // Disconnect from session so that the updates on updatedProjectAllocation are not directly saved in db
        em.detach(updatedProjectAllocation);
        updatedProjectAllocation
            .percentage(UPDATED_PERCENTAGE);

        restProjectAllocationMockMvc.perform(put("/api/project-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectAllocation)))
            .andExpect(status().isOk());

        // Validate the ProjectAllocation in the database
        List<ProjectAllocation> projectAllocationList = projectAllocationRepository.findAll();
        assertThat(projectAllocationList).hasSize(databaseSizeBeforeUpdate);
        ProjectAllocation testProjectAllocation = projectAllocationList.get(projectAllocationList.size() - 1);
        assertThat(testProjectAllocation.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectAllocation() throws Exception {
        int databaseSizeBeforeUpdate = projectAllocationRepository.findAll().size();

        // Create the ProjectAllocation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectAllocationMockMvc.perform(put("/api/project-allocations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectAllocation)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectAllocation in the database
        List<ProjectAllocation> projectAllocationList = projectAllocationRepository.findAll();
        assertThat(projectAllocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectAllocation() throws Exception {
        // Initialize the database
        projectAllocationService.save(projectAllocation);

        int databaseSizeBeforeDelete = projectAllocationRepository.findAll().size();

        // Delete the projectAllocation
        restProjectAllocationMockMvc.perform(delete("/api/project-allocations/{id}", projectAllocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectAllocation> projectAllocationList = projectAllocationRepository.findAll();
        assertThat(projectAllocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectAllocation.class);
        ProjectAllocation projectAllocation1 = new ProjectAllocation();
        projectAllocation1.setId(1L);
        ProjectAllocation projectAllocation2 = new ProjectAllocation();
        projectAllocation2.setId(projectAllocation1.getId());
        assertThat(projectAllocation1).isEqualTo(projectAllocation2);
        projectAllocation2.setId(2L);
        assertThat(projectAllocation1).isNotEqualTo(projectAllocation2);
        projectAllocation1.setId(null);
        assertThat(projectAllocation1).isNotEqualTo(projectAllocation2);
    }
}
