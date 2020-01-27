package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectBusinessgoalId;
import com.qnowapp.repository.ProjectBusinessgoalIdRepository;
import com.qnowapp.service.ProjectBusinessgoalIdService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectBusinessgoalIdCriteria;
import com.qnowapp.service.ProjectBusinessgoalIdQueryService;

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
 * Integration tests for the {@Link ProjectBusinessgoalIdResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectBusinessgoalIdResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectBusinessgoalIdRepository projectBusinessgoalIdRepository;

    @Autowired
    private ProjectBusinessgoalIdService projectBusinessgoalIdService;

    @Autowired
    private ProjectBusinessgoalIdQueryService projectBusinessgoalIdQueryService;

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

    private MockMvc restProjectBusinessgoalIdMockMvc;

    private ProjectBusinessgoalId projectBusinessgoalId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectBusinessgoalIdResource projectBusinessgoalIdResource = new ProjectBusinessgoalIdResource( projectBusinessgoalIdService, projectBusinessgoalIdQueryService);
        this.restProjectBusinessgoalIdMockMvc = MockMvcBuilders.standaloneSetup(projectBusinessgoalIdResource)
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
    public static ProjectBusinessgoalId createEntity(EntityManager em) {
        ProjectBusinessgoalId projectBusinessgoalId = new ProjectBusinessgoalId()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectBusinessgoalId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectBusinessgoalId createUpdatedEntity(EntityManager em) {
        ProjectBusinessgoalId projectBusinessgoalId = new ProjectBusinessgoalId()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectBusinessgoalId;
    }

    @BeforeEach
    public void initTest() {
        projectBusinessgoalId = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectBusinessgoalId() throws Exception {
        int databaseSizeBeforeCreate = projectBusinessgoalIdRepository.findAll().size();

        // Create the ProjectBusinessgoalId
        restProjectBusinessgoalIdMockMvc.perform(post("/api/project-businessgoal-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectBusinessgoalId)))
            .andExpect(status().isCreated());

        // Validate the ProjectBusinessgoalId in the database
        List<ProjectBusinessgoalId> projectBusinessgoalIdList = projectBusinessgoalIdRepository.findAll();
        assertThat(projectBusinessgoalIdList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectBusinessgoalId testProjectBusinessgoalId = projectBusinessgoalIdList.get(projectBusinessgoalIdList.size() - 1);
        assertThat(testProjectBusinessgoalId.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectBusinessgoalId.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectBusinessgoalId.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectBusinessgoalIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectBusinessgoalIdRepository.findAll().size();

        // Create the ProjectBusinessgoalId with an existing ID
        projectBusinessgoalId.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectBusinessgoalIdMockMvc.perform(post("/api/project-businessgoal-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectBusinessgoalId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectBusinessgoalId in the database
        List<ProjectBusinessgoalId> projectBusinessgoalIdList = projectBusinessgoalIdRepository.findAll();
        assertThat(projectBusinessgoalIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectBusinessgoalIds() throws Exception {
        // Initialize the database
        projectBusinessgoalIdRepository.saveAndFlush(projectBusinessgoalId);

        // Get all the projectBusinessgoalIdList
        restProjectBusinessgoalIdMockMvc.perform(get("/api/project-businessgoal-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectBusinessgoalId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectBusinessgoalId() throws Exception {
        // Initialize the database
        projectBusinessgoalIdRepository.saveAndFlush(projectBusinessgoalId);

        // Get the projectBusinessgoalId
        restProjectBusinessgoalIdMockMvc.perform(get("/api/project-businessgoal-ids/{id}", projectBusinessgoalId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectBusinessgoalId.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectBusinessgoalIdsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectBusinessgoalIdRepository.saveAndFlush(projectBusinessgoalId);

        // Get all the projectBusinessgoalIdList where code equals to DEFAULT_CODE
        defaultProjectBusinessgoalIdShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectBusinessgoalIdList where code equals to UPDATED_CODE
        defaultProjectBusinessgoalIdShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectBusinessgoalIdsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectBusinessgoalIdRepository.saveAndFlush(projectBusinessgoalId);

        // Get all the projectBusinessgoalIdList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectBusinessgoalIdShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectBusinessgoalIdList where code equals to UPDATED_CODE
        defaultProjectBusinessgoalIdShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectBusinessgoalIdsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectBusinessgoalIdRepository.saveAndFlush(projectBusinessgoalId);

        // Get all the projectBusinessgoalIdList where code is not null
        defaultProjectBusinessgoalIdShouldBeFound("code.specified=true");

        // Get all the projectBusinessgoalIdList where code is null
        defaultProjectBusinessgoalIdShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectBusinessgoalIdsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectBusinessgoalIdRepository.saveAndFlush(projectBusinessgoalId);

        // Get all the projectBusinessgoalIdList where name equals to DEFAULT_NAME
        defaultProjectBusinessgoalIdShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectBusinessgoalIdList where name equals to UPDATED_NAME
        defaultProjectBusinessgoalIdShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectBusinessgoalIdsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectBusinessgoalIdRepository.saveAndFlush(projectBusinessgoalId);

        // Get all the projectBusinessgoalIdList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectBusinessgoalIdShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectBusinessgoalIdList where name equals to UPDATED_NAME
        defaultProjectBusinessgoalIdShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectBusinessgoalIdsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectBusinessgoalIdRepository.saveAndFlush(projectBusinessgoalId);

        // Get all the projectBusinessgoalIdList where name is not null
        defaultProjectBusinessgoalIdShouldBeFound("name.specified=true");

        // Get all the projectBusinessgoalIdList where name is null
        defaultProjectBusinessgoalIdShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectBusinessgoalIdsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectBusinessgoalIdRepository.saveAndFlush(projectBusinessgoalId);

        // Get all the projectBusinessgoalIdList where description equals to DEFAULT_DESCRIPTION
        defaultProjectBusinessgoalIdShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectBusinessgoalIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectBusinessgoalIdShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectBusinessgoalIdsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectBusinessgoalIdRepository.saveAndFlush(projectBusinessgoalId);

        // Get all the projectBusinessgoalIdList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectBusinessgoalIdShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectBusinessgoalIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectBusinessgoalIdShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectBusinessgoalIdsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectBusinessgoalIdRepository.saveAndFlush(projectBusinessgoalId);

        // Get all the projectBusinessgoalIdList where description is not null
        defaultProjectBusinessgoalIdShouldBeFound("description.specified=true");

        // Get all the projectBusinessgoalIdList where description is null
        defaultProjectBusinessgoalIdShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectBusinessgoalIdShouldBeFound(String filter) throws Exception {
        restProjectBusinessgoalIdMockMvc.perform(get("/api/project-businessgoal-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectBusinessgoalId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectBusinessgoalIdMockMvc.perform(get("/api/project-businessgoal-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectBusinessgoalIdShouldNotBeFound(String filter) throws Exception {
        restProjectBusinessgoalIdMockMvc.perform(get("/api/project-businessgoal-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectBusinessgoalIdMockMvc.perform(get("/api/project-businessgoal-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectBusinessgoalId() throws Exception {
        // Get the projectBusinessgoalId
        restProjectBusinessgoalIdMockMvc.perform(get("/api/project-businessgoal-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectBusinessgoalId() throws Exception {
        // Initialize the database
        projectBusinessgoalIdService.save(projectBusinessgoalId);

        int databaseSizeBeforeUpdate = projectBusinessgoalIdRepository.findAll().size();

        // Update the projectBusinessgoalId
        ProjectBusinessgoalId updatedProjectBusinessgoalId = projectBusinessgoalIdRepository.findById(projectBusinessgoalId.getId()).get();
        // Disconnect from session so that the updates on updatedProjectBusinessgoalId are not directly saved in db
        em.detach(updatedProjectBusinessgoalId);
        updatedProjectBusinessgoalId
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectBusinessgoalIdMockMvc.perform(put("/api/project-businessgoal-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectBusinessgoalId)))
            .andExpect(status().isOk());

        // Validate the ProjectBusinessgoalId in the database
        List<ProjectBusinessgoalId> projectBusinessgoalIdList = projectBusinessgoalIdRepository.findAll();
        assertThat(projectBusinessgoalIdList).hasSize(databaseSizeBeforeUpdate);
        ProjectBusinessgoalId testProjectBusinessgoalId = projectBusinessgoalIdList.get(projectBusinessgoalIdList.size() - 1);
        assertThat(testProjectBusinessgoalId.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectBusinessgoalId.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectBusinessgoalId.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectBusinessgoalId() throws Exception {
        int databaseSizeBeforeUpdate = projectBusinessgoalIdRepository.findAll().size();

        // Create the ProjectBusinessgoalId

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectBusinessgoalIdMockMvc.perform(put("/api/project-businessgoal-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectBusinessgoalId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectBusinessgoalId in the database
        List<ProjectBusinessgoalId> projectBusinessgoalIdList = projectBusinessgoalIdRepository.findAll();
        assertThat(projectBusinessgoalIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectBusinessgoalId() throws Exception {
        // Initialize the database
        projectBusinessgoalIdService.save(projectBusinessgoalId);

        int databaseSizeBeforeDelete = projectBusinessgoalIdRepository.findAll().size();

        // Delete the projectBusinessgoalId
        restProjectBusinessgoalIdMockMvc.perform(delete("/api/project-businessgoal-ids/{id}", projectBusinessgoalId.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectBusinessgoalId> projectBusinessgoalIdList = projectBusinessgoalIdRepository.findAll();
        assertThat(projectBusinessgoalIdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectBusinessgoalId.class);
        ProjectBusinessgoalId projectBusinessgoalId1 = new ProjectBusinessgoalId();
        projectBusinessgoalId1.setId(1L);
        ProjectBusinessgoalId projectBusinessgoalId2 = new ProjectBusinessgoalId();
        projectBusinessgoalId2.setId(projectBusinessgoalId1.getId());
        assertThat(projectBusinessgoalId1).isEqualTo(projectBusinessgoalId2);
        projectBusinessgoalId2.setId(2L);
        assertThat(projectBusinessgoalId1).isNotEqualTo(projectBusinessgoalId2);
        projectBusinessgoalId1.setId(null);
        assertThat(projectBusinessgoalId1).isNotEqualTo(projectBusinessgoalId2);
    }
}
