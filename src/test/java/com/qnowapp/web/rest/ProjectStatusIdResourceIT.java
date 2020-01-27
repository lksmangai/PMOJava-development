package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectStatusId;
import com.qnowapp.repository.ProjectStatusIdRepository;
import com.qnowapp.service.ProjectStatusIdService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectStatusIdCriteria;
import com.qnowapp.service.ProjectStatusIdQueryService;

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
 * Integration tests for the {@Link ProjectStatusIdResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectStatusIdResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectStatusIdRepository projectStatusIdRepository;

    @Autowired
    private ProjectStatusIdService projectStatusIdService;

    @Autowired
    private ProjectStatusIdQueryService projectStatusIdQueryService;

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

    private MockMvc restProjectStatusIdMockMvc;

    private ProjectStatusId projectStatusId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectStatusIdResource projectStatusIdResource = new ProjectStatusIdResource(projectStatusIdService, projectStatusIdQueryService);
        this.restProjectStatusIdMockMvc = MockMvcBuilders.standaloneSetup(projectStatusIdResource)
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
    public static ProjectStatusId createEntity(EntityManager em) {
        ProjectStatusId projectStatusId = new ProjectStatusId()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectStatusId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectStatusId createUpdatedEntity(EntityManager em) {
        ProjectStatusId projectStatusId = new ProjectStatusId()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectStatusId;
    }

    @BeforeEach
    public void initTest() {
        projectStatusId = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectStatusId() throws Exception {
        int databaseSizeBeforeCreate = projectStatusIdRepository.findAll().size();

        // Create the ProjectStatusId
        restProjectStatusIdMockMvc.perform(post("/api/project-status-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectStatusId)))
            .andExpect(status().isCreated());

        // Validate the ProjectStatusId in the database
        List<ProjectStatusId> projectStatusIdList = projectStatusIdRepository.findAll();
        assertThat(projectStatusIdList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectStatusId testProjectStatusId = projectStatusIdList.get(projectStatusIdList.size() - 1);
        assertThat(testProjectStatusId.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectStatusId.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectStatusId.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectStatusIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectStatusIdRepository.findAll().size();

        // Create the ProjectStatusId with an existing ID
        projectStatusId.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectStatusIdMockMvc.perform(post("/api/project-status-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectStatusId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectStatusId in the database
        List<ProjectStatusId> projectStatusIdList = projectStatusIdRepository.findAll();
        assertThat(projectStatusIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectStatusIds() throws Exception {
        // Initialize the database
        projectStatusIdRepository.saveAndFlush(projectStatusId);

        // Get all the projectStatusIdList
        restProjectStatusIdMockMvc.perform(get("/api/project-status-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectStatusId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectStatusId() throws Exception {
        // Initialize the database
        projectStatusIdRepository.saveAndFlush(projectStatusId);

        // Get the projectStatusId
        restProjectStatusIdMockMvc.perform(get("/api/project-status-ids/{id}", projectStatusId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectStatusId.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectStatusIdsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectStatusIdRepository.saveAndFlush(projectStatusId);

        // Get all the projectStatusIdList where code equals to DEFAULT_CODE
        defaultProjectStatusIdShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectStatusIdList where code equals to UPDATED_CODE
        defaultProjectStatusIdShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectStatusIdsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectStatusIdRepository.saveAndFlush(projectStatusId);

        // Get all the projectStatusIdList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectStatusIdShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectStatusIdList where code equals to UPDATED_CODE
        defaultProjectStatusIdShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectStatusIdsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectStatusIdRepository.saveAndFlush(projectStatusId);

        // Get all the projectStatusIdList where code is not null
        defaultProjectStatusIdShouldBeFound("code.specified=true");

        // Get all the projectStatusIdList where code is null
        defaultProjectStatusIdShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectStatusIdsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectStatusIdRepository.saveAndFlush(projectStatusId);

        // Get all the projectStatusIdList where name equals to DEFAULT_NAME
        defaultProjectStatusIdShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectStatusIdList where name equals to UPDATED_NAME
        defaultProjectStatusIdShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectStatusIdsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectStatusIdRepository.saveAndFlush(projectStatusId);

        // Get all the projectStatusIdList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectStatusIdShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectStatusIdList where name equals to UPDATED_NAME
        defaultProjectStatusIdShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectStatusIdsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectStatusIdRepository.saveAndFlush(projectStatusId);

        // Get all the projectStatusIdList where name is not null
        defaultProjectStatusIdShouldBeFound("name.specified=true");

        // Get all the projectStatusIdList where name is null
        defaultProjectStatusIdShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectStatusIdsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectStatusIdRepository.saveAndFlush(projectStatusId);

        // Get all the projectStatusIdList where description equals to DEFAULT_DESCRIPTION
        defaultProjectStatusIdShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectStatusIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectStatusIdShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectStatusIdsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectStatusIdRepository.saveAndFlush(projectStatusId);

        // Get all the projectStatusIdList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectStatusIdShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectStatusIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectStatusIdShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectStatusIdsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectStatusIdRepository.saveAndFlush(projectStatusId);

        // Get all the projectStatusIdList where description is not null
        defaultProjectStatusIdShouldBeFound("description.specified=true");

        // Get all the projectStatusIdList where description is null
        defaultProjectStatusIdShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectStatusIdShouldBeFound(String filter) throws Exception {
        restProjectStatusIdMockMvc.perform(get("/api/project-status-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectStatusId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectStatusIdMockMvc.perform(get("/api/project-status-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectStatusIdShouldNotBeFound(String filter) throws Exception {
        restProjectStatusIdMockMvc.perform(get("/api/project-status-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectStatusIdMockMvc.perform(get("/api/project-status-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectStatusId() throws Exception {
        // Get the projectStatusId
        restProjectStatusIdMockMvc.perform(get("/api/project-status-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectStatusId() throws Exception {
        // Initialize the database
        projectStatusIdService.save(projectStatusId);

        int databaseSizeBeforeUpdate = projectStatusIdRepository.findAll().size();

        // Update the projectStatusId
        ProjectStatusId updatedProjectStatusId = projectStatusIdRepository.findById(projectStatusId.getId()).get();
        // Disconnect from session so that the updates on updatedProjectStatusId are not directly saved in db
        em.detach(updatedProjectStatusId);
        updatedProjectStatusId
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectStatusIdMockMvc.perform(put("/api/project-status-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectStatusId)))
            .andExpect(status().isOk());

        // Validate the ProjectStatusId in the database
        List<ProjectStatusId> projectStatusIdList = projectStatusIdRepository.findAll();
        assertThat(projectStatusIdList).hasSize(databaseSizeBeforeUpdate);
        ProjectStatusId testProjectStatusId = projectStatusIdList.get(projectStatusIdList.size() - 1);
        assertThat(testProjectStatusId.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectStatusId.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectStatusId.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectStatusId() throws Exception {
        int databaseSizeBeforeUpdate = projectStatusIdRepository.findAll().size();

        // Create the ProjectStatusId

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectStatusIdMockMvc.perform(put("/api/project-status-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectStatusId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectStatusId in the database
        List<ProjectStatusId> projectStatusIdList = projectStatusIdRepository.findAll();
        assertThat(projectStatusIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectStatusId() throws Exception {
        // Initialize the database
        projectStatusIdService.save(projectStatusId);

        int databaseSizeBeforeDelete = projectStatusIdRepository.findAll().size();

        // Delete the projectStatusId
        restProjectStatusIdMockMvc.perform(delete("/api/project-status-ids/{id}", projectStatusId.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectStatusId> projectStatusIdList = projectStatusIdRepository.findAll();
        assertThat(projectStatusIdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectStatusId.class);
        ProjectStatusId projectStatusId1 = new ProjectStatusId();
        projectStatusId1.setId(1L);
        ProjectStatusId projectStatusId2 = new ProjectStatusId();
        projectStatusId2.setId(projectStatusId1.getId());
        assertThat(projectStatusId1).isEqualTo(projectStatusId2);
        projectStatusId2.setId(2L);
        assertThat(projectStatusId1).isNotEqualTo(projectStatusId2);
        projectStatusId1.setId(null);
        assertThat(projectStatusId1).isNotEqualTo(projectStatusId2);
    }
}
