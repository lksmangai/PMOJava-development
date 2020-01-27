package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectCostCenterId;
import com.qnowapp.domain.Company;
import com.qnowapp.repository.ProjectCostCenterIdRepository;
import com.qnowapp.service.ProjectCostCenterIdService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectCostCenterIdCriteria;
import com.qnowapp.service.ProjectCostCenterIdQueryService;

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
 * Integration tests for the {@Link ProjectCostCenterIdResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectCostCenterIdResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectCostCenterIdRepository projectCostCenterIdRepository;

    @Autowired
    private ProjectCostCenterIdService projectCostCenterIdService;

    @Autowired
    private ProjectCostCenterIdQueryService projectCostCenterIdQueryService;

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

    private MockMvc restProjectCostCenterIdMockMvc;

    private ProjectCostCenterId projectCostCenterId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectCostCenterIdResource projectCostCenterIdResource = new ProjectCostCenterIdResource(projectCostCenterIdService, projectCostCenterIdQueryService);
        this.restProjectCostCenterIdMockMvc = MockMvcBuilders.standaloneSetup(projectCostCenterIdResource)
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
    public static ProjectCostCenterId createEntity(EntityManager em) {
        ProjectCostCenterId projectCostCenterId = new ProjectCostCenterId()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectCostCenterId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectCostCenterId createUpdatedEntity(EntityManager em) {
        ProjectCostCenterId projectCostCenterId = new ProjectCostCenterId()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectCostCenterId;
    }

    @BeforeEach
    public void initTest() {
        projectCostCenterId = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectCostCenterId() throws Exception {
        int databaseSizeBeforeCreate = projectCostCenterIdRepository.findAll().size();

        // Create the ProjectCostCenterId
        restProjectCostCenterIdMockMvc.perform(post("/api/project-cost-center-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectCostCenterId)))
            .andExpect(status().isCreated());

        // Validate the ProjectCostCenterId in the database
        List<ProjectCostCenterId> projectCostCenterIdList = projectCostCenterIdRepository.findAll();
        assertThat(projectCostCenterIdList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectCostCenterId testProjectCostCenterId = projectCostCenterIdList.get(projectCostCenterIdList.size() - 1);
        assertThat(testProjectCostCenterId.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectCostCenterId.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectCostCenterId.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectCostCenterIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectCostCenterIdRepository.findAll().size();

        // Create the ProjectCostCenterId with an existing ID
        projectCostCenterId.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectCostCenterIdMockMvc.perform(post("/api/project-cost-center-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectCostCenterId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectCostCenterId in the database
        List<ProjectCostCenterId> projectCostCenterIdList = projectCostCenterIdRepository.findAll();
        assertThat(projectCostCenterIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectCostCenterIds() throws Exception {
        // Initialize the database
        projectCostCenterIdRepository.saveAndFlush(projectCostCenterId);

        // Get all the projectCostCenterIdList
        restProjectCostCenterIdMockMvc.perform(get("/api/project-cost-center-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectCostCenterId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectCostCenterId() throws Exception {
        // Initialize the database
        projectCostCenterIdRepository.saveAndFlush(projectCostCenterId);

        // Get the projectCostCenterId
        restProjectCostCenterIdMockMvc.perform(get("/api/project-cost-center-ids/{id}", projectCostCenterId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectCostCenterId.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectCostCenterIdsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCostCenterIdRepository.saveAndFlush(projectCostCenterId);

        // Get all the projectCostCenterIdList where code equals to DEFAULT_CODE
        defaultProjectCostCenterIdShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectCostCenterIdList where code equals to UPDATED_CODE
        defaultProjectCostCenterIdShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectCostCenterIdsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectCostCenterIdRepository.saveAndFlush(projectCostCenterId);

        // Get all the projectCostCenterIdList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectCostCenterIdShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectCostCenterIdList where code equals to UPDATED_CODE
        defaultProjectCostCenterIdShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectCostCenterIdsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCostCenterIdRepository.saveAndFlush(projectCostCenterId);

        // Get all the projectCostCenterIdList where code is not null
        defaultProjectCostCenterIdShouldBeFound("code.specified=true");

        // Get all the projectCostCenterIdList where code is null
        defaultProjectCostCenterIdShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectCostCenterIdsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCostCenterIdRepository.saveAndFlush(projectCostCenterId);

        // Get all the projectCostCenterIdList where name equals to DEFAULT_NAME
        defaultProjectCostCenterIdShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectCostCenterIdList where name equals to UPDATED_NAME
        defaultProjectCostCenterIdShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectCostCenterIdsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectCostCenterIdRepository.saveAndFlush(projectCostCenterId);

        // Get all the projectCostCenterIdList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectCostCenterIdShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectCostCenterIdList where name equals to UPDATED_NAME
        defaultProjectCostCenterIdShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectCostCenterIdsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCostCenterIdRepository.saveAndFlush(projectCostCenterId);

        // Get all the projectCostCenterIdList where name is not null
        defaultProjectCostCenterIdShouldBeFound("name.specified=true");

        // Get all the projectCostCenterIdList where name is null
        defaultProjectCostCenterIdShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectCostCenterIdsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectCostCenterIdRepository.saveAndFlush(projectCostCenterId);

        // Get all the projectCostCenterIdList where description equals to DEFAULT_DESCRIPTION
        defaultProjectCostCenterIdShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectCostCenterIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectCostCenterIdShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectCostCenterIdsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectCostCenterIdRepository.saveAndFlush(projectCostCenterId);

        // Get all the projectCostCenterIdList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectCostCenterIdShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectCostCenterIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectCostCenterIdShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectCostCenterIdsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectCostCenterIdRepository.saveAndFlush(projectCostCenterId);

        // Get all the projectCostCenterIdList where description is not null
        defaultProjectCostCenterIdShouldBeFound("description.specified=true");

        // Get all the projectCostCenterIdList where description is null
        defaultProjectCostCenterIdShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectCostCenterIdsByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        Company company = CompanyResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        projectCostCenterId.addCompany(company);
        projectCostCenterIdRepository.saveAndFlush(projectCostCenterId);
        Long companyId = company.getId();

        // Get all the projectCostCenterIdList where company equals to companyId
        defaultProjectCostCenterIdShouldBeFound("companyId.equals=" + companyId);

        // Get all the projectCostCenterIdList where company equals to companyId + 1
        defaultProjectCostCenterIdShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectCostCenterIdShouldBeFound(String filter) throws Exception {
        restProjectCostCenterIdMockMvc.perform(get("/api/project-cost-center-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectCostCenterId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectCostCenterIdMockMvc.perform(get("/api/project-cost-center-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectCostCenterIdShouldNotBeFound(String filter) throws Exception {
        restProjectCostCenterIdMockMvc.perform(get("/api/project-cost-center-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectCostCenterIdMockMvc.perform(get("/api/project-cost-center-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectCostCenterId() throws Exception {
        // Get the projectCostCenterId
        restProjectCostCenterIdMockMvc.perform(get("/api/project-cost-center-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectCostCenterId() throws Exception {
        // Initialize the database
        projectCostCenterIdService.save(projectCostCenterId);

        int databaseSizeBeforeUpdate = projectCostCenterIdRepository.findAll().size();

        // Update the projectCostCenterId
        ProjectCostCenterId updatedProjectCostCenterId = projectCostCenterIdRepository.findById(projectCostCenterId.getId()).get();
        // Disconnect from session so that the updates on updatedProjectCostCenterId are not directly saved in db
        em.detach(updatedProjectCostCenterId);
        updatedProjectCostCenterId
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectCostCenterIdMockMvc.perform(put("/api/project-cost-center-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectCostCenterId)))
            .andExpect(status().isOk());

        // Validate the ProjectCostCenterId in the database
        List<ProjectCostCenterId> projectCostCenterIdList = projectCostCenterIdRepository.findAll();
        assertThat(projectCostCenterIdList).hasSize(databaseSizeBeforeUpdate);
        ProjectCostCenterId testProjectCostCenterId = projectCostCenterIdList.get(projectCostCenterIdList.size() - 1);
        assertThat(testProjectCostCenterId.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectCostCenterId.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectCostCenterId.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectCostCenterId() throws Exception {
        int databaseSizeBeforeUpdate = projectCostCenterIdRepository.findAll().size();

        // Create the ProjectCostCenterId

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectCostCenterIdMockMvc.perform(put("/api/project-cost-center-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectCostCenterId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectCostCenterId in the database
        List<ProjectCostCenterId> projectCostCenterIdList = projectCostCenterIdRepository.findAll();
        assertThat(projectCostCenterIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectCostCenterId() throws Exception {
        // Initialize the database
        projectCostCenterIdService.save(projectCostCenterId);

        int databaseSizeBeforeDelete = projectCostCenterIdRepository.findAll().size();

        // Delete the projectCostCenterId
        restProjectCostCenterIdMockMvc.perform(delete("/api/project-cost-center-ids/{id}", projectCostCenterId.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectCostCenterId> projectCostCenterIdList = projectCostCenterIdRepository.findAll();
        assertThat(projectCostCenterIdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectCostCenterId.class);
        ProjectCostCenterId projectCostCenterId1 = new ProjectCostCenterId();
        projectCostCenterId1.setId(1L);
        ProjectCostCenterId projectCostCenterId2 = new ProjectCostCenterId();
        projectCostCenterId2.setId(projectCostCenterId1.getId());
        assertThat(projectCostCenterId1).isEqualTo(projectCostCenterId2);
        projectCostCenterId2.setId(2L);
        assertThat(projectCostCenterId1).isNotEqualTo(projectCostCenterId2);
        projectCostCenterId1.setId(null);
        assertThat(projectCostCenterId1).isNotEqualTo(projectCostCenterId2);
    }
}
