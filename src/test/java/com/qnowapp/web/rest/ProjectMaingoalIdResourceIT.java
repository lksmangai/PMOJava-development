package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectMaingoalId;
import com.qnowapp.repository.ProjectMaingoalIdRepository;
import com.qnowapp.service.ProjectMaingoalIdService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectMaingoalIdCriteria;
import com.qnowapp.service.ProjectMaingoalIdQueryService;

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
 * Integration tests for the {@Link ProjectMaingoalIdResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectMaingoalIdResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectMaingoalIdRepository projectMaingoalIdRepository;

    @Autowired
    private ProjectMaingoalIdService projectMaingoalIdService;

    @Autowired
    private ProjectMaingoalIdQueryService projectMaingoalIdQueryService;

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

    private MockMvc restProjectMaingoalIdMockMvc;

    private ProjectMaingoalId projectMaingoalId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectMaingoalIdResource projectMaingoalIdResource = new ProjectMaingoalIdResource( projectMaingoalIdService, projectMaingoalIdQueryService);
        this.restProjectMaingoalIdMockMvc = MockMvcBuilders.standaloneSetup(projectMaingoalIdResource)
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
    public static ProjectMaingoalId createEntity(EntityManager em) {
        ProjectMaingoalId projectMaingoalId = new ProjectMaingoalId()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectMaingoalId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectMaingoalId createUpdatedEntity(EntityManager em) {
        ProjectMaingoalId projectMaingoalId = new ProjectMaingoalId()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectMaingoalId;
    }

    @BeforeEach
    public void initTest() {
        projectMaingoalId = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectMaingoalId() throws Exception {
        int databaseSizeBeforeCreate = projectMaingoalIdRepository.findAll().size();

        // Create the ProjectMaingoalId
        restProjectMaingoalIdMockMvc.perform(post("/api/project-maingoal-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectMaingoalId)))
            .andExpect(status().isCreated());

        // Validate the ProjectMaingoalId in the database
        List<ProjectMaingoalId> projectMaingoalIdList = projectMaingoalIdRepository.findAll();
        assertThat(projectMaingoalIdList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectMaingoalId testProjectMaingoalId = projectMaingoalIdList.get(projectMaingoalIdList.size() - 1);
        assertThat(testProjectMaingoalId.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectMaingoalId.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectMaingoalId.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectMaingoalIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectMaingoalIdRepository.findAll().size();

        // Create the ProjectMaingoalId with an existing ID
        projectMaingoalId.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectMaingoalIdMockMvc.perform(post("/api/project-maingoal-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectMaingoalId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectMaingoalId in the database
        List<ProjectMaingoalId> projectMaingoalIdList = projectMaingoalIdRepository.findAll();
        assertThat(projectMaingoalIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectMaingoalIds() throws Exception {
        // Initialize the database
        projectMaingoalIdRepository.saveAndFlush(projectMaingoalId);

        // Get all the projectMaingoalIdList
        restProjectMaingoalIdMockMvc.perform(get("/api/project-maingoal-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectMaingoalId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectMaingoalId() throws Exception {
        // Initialize the database
        projectMaingoalIdRepository.saveAndFlush(projectMaingoalId);

        // Get the projectMaingoalId
        restProjectMaingoalIdMockMvc.perform(get("/api/project-maingoal-ids/{id}", projectMaingoalId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectMaingoalId.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectMaingoalIdsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectMaingoalIdRepository.saveAndFlush(projectMaingoalId);

        // Get all the projectMaingoalIdList where code equals to DEFAULT_CODE
        defaultProjectMaingoalIdShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectMaingoalIdList where code equals to UPDATED_CODE
        defaultProjectMaingoalIdShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectMaingoalIdsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectMaingoalIdRepository.saveAndFlush(projectMaingoalId);

        // Get all the projectMaingoalIdList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectMaingoalIdShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectMaingoalIdList where code equals to UPDATED_CODE
        defaultProjectMaingoalIdShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectMaingoalIdsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectMaingoalIdRepository.saveAndFlush(projectMaingoalId);

        // Get all the projectMaingoalIdList where code is not null
        defaultProjectMaingoalIdShouldBeFound("code.specified=true");

        // Get all the projectMaingoalIdList where code is null
        defaultProjectMaingoalIdShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectMaingoalIdsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectMaingoalIdRepository.saveAndFlush(projectMaingoalId);

        // Get all the projectMaingoalIdList where name equals to DEFAULT_NAME
        defaultProjectMaingoalIdShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectMaingoalIdList where name equals to UPDATED_NAME
        defaultProjectMaingoalIdShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectMaingoalIdsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectMaingoalIdRepository.saveAndFlush(projectMaingoalId);

        // Get all the projectMaingoalIdList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectMaingoalIdShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectMaingoalIdList where name equals to UPDATED_NAME
        defaultProjectMaingoalIdShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectMaingoalIdsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectMaingoalIdRepository.saveAndFlush(projectMaingoalId);

        // Get all the projectMaingoalIdList where name is not null
        defaultProjectMaingoalIdShouldBeFound("name.specified=true");

        // Get all the projectMaingoalIdList where name is null
        defaultProjectMaingoalIdShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectMaingoalIdsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectMaingoalIdRepository.saveAndFlush(projectMaingoalId);

        // Get all the projectMaingoalIdList where description equals to DEFAULT_DESCRIPTION
        defaultProjectMaingoalIdShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectMaingoalIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectMaingoalIdShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectMaingoalIdsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectMaingoalIdRepository.saveAndFlush(projectMaingoalId);

        // Get all the projectMaingoalIdList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectMaingoalIdShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectMaingoalIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectMaingoalIdShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectMaingoalIdsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectMaingoalIdRepository.saveAndFlush(projectMaingoalId);

        // Get all the projectMaingoalIdList where description is not null
        defaultProjectMaingoalIdShouldBeFound("description.specified=true");

        // Get all the projectMaingoalIdList where description is null
        defaultProjectMaingoalIdShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectMaingoalIdShouldBeFound(String filter) throws Exception {
        restProjectMaingoalIdMockMvc.perform(get("/api/project-maingoal-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectMaingoalId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectMaingoalIdMockMvc.perform(get("/api/project-maingoal-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectMaingoalIdShouldNotBeFound(String filter) throws Exception {
        restProjectMaingoalIdMockMvc.perform(get("/api/project-maingoal-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectMaingoalIdMockMvc.perform(get("/api/project-maingoal-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectMaingoalId() throws Exception {
        // Get the projectMaingoalId
        restProjectMaingoalIdMockMvc.perform(get("/api/project-maingoal-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectMaingoalId() throws Exception {
        // Initialize the database
        projectMaingoalIdService.save(projectMaingoalId);

        int databaseSizeBeforeUpdate = projectMaingoalIdRepository.findAll().size();

        // Update the projectMaingoalId
        ProjectMaingoalId updatedProjectMaingoalId = projectMaingoalIdRepository.findById(projectMaingoalId.getId()).get();
        // Disconnect from session so that the updates on updatedProjectMaingoalId are not directly saved in db
        em.detach(updatedProjectMaingoalId);
        updatedProjectMaingoalId
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectMaingoalIdMockMvc.perform(put("/api/project-maingoal-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectMaingoalId)))
            .andExpect(status().isOk());

        // Validate the ProjectMaingoalId in the database
        List<ProjectMaingoalId> projectMaingoalIdList = projectMaingoalIdRepository.findAll();
        assertThat(projectMaingoalIdList).hasSize(databaseSizeBeforeUpdate);
        ProjectMaingoalId testProjectMaingoalId = projectMaingoalIdList.get(projectMaingoalIdList.size() - 1);
        assertThat(testProjectMaingoalId.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectMaingoalId.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectMaingoalId.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectMaingoalId() throws Exception {
        int databaseSizeBeforeUpdate = projectMaingoalIdRepository.findAll().size();

        // Create the ProjectMaingoalId

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectMaingoalIdMockMvc.perform(put("/api/project-maingoal-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectMaingoalId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectMaingoalId in the database
        List<ProjectMaingoalId> projectMaingoalIdList = projectMaingoalIdRepository.findAll();
        assertThat(projectMaingoalIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectMaingoalId() throws Exception {
        // Initialize the database
        projectMaingoalIdService.save(projectMaingoalId);

        int databaseSizeBeforeDelete = projectMaingoalIdRepository.findAll().size();

        // Delete the projectMaingoalId
        restProjectMaingoalIdMockMvc.perform(delete("/api/project-maingoal-ids/{id}", projectMaingoalId.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectMaingoalId> projectMaingoalIdList = projectMaingoalIdRepository.findAll();
        assertThat(projectMaingoalIdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectMaingoalId.class);
        ProjectMaingoalId projectMaingoalId1 = new ProjectMaingoalId();
        projectMaingoalId1.setId(1L);
        ProjectMaingoalId projectMaingoalId2 = new ProjectMaingoalId();
        projectMaingoalId2.setId(projectMaingoalId1.getId());
        assertThat(projectMaingoalId1).isEqualTo(projectMaingoalId2);
        projectMaingoalId2.setId(2L);
        assertThat(projectMaingoalId1).isNotEqualTo(projectMaingoalId2);
        projectMaingoalId1.setId(null);
        assertThat(projectMaingoalId1).isNotEqualTo(projectMaingoalId2);
    }
}
