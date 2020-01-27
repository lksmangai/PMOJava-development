package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectSubgoalId;
import com.qnowapp.repository.ProjectSubgoalIdRepository;
import com.qnowapp.service.ProjectSubgoalIdService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectSubgoalIdCriteria;
import com.qnowapp.service.ProjectSubgoalIdQueryService;

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
 * Integration tests for the {@Link ProjectSubgoalIdResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectSubgoalIdResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectSubgoalIdRepository projectSubgoalIdRepository;

    @Autowired
    private ProjectSubgoalIdService projectSubgoalIdService;

    @Autowired
    private ProjectSubgoalIdQueryService projectSubgoalIdQueryService;

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

    private MockMvc restProjectSubgoalIdMockMvc;

    private ProjectSubgoalId projectSubgoalId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectSubgoalIdResource projectSubgoalIdResource = new ProjectSubgoalIdResource( projectSubgoalIdService, projectSubgoalIdQueryService);
        this.restProjectSubgoalIdMockMvc = MockMvcBuilders.standaloneSetup(projectSubgoalIdResource)
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
    public static ProjectSubgoalId createEntity(EntityManager em) {
        ProjectSubgoalId projectSubgoalId = new ProjectSubgoalId()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectSubgoalId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectSubgoalId createUpdatedEntity(EntityManager em) {
        ProjectSubgoalId projectSubgoalId = new ProjectSubgoalId()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectSubgoalId;
    }

    @BeforeEach
    public void initTest() {
        projectSubgoalId = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectSubgoalId() throws Exception {
        int databaseSizeBeforeCreate = projectSubgoalIdRepository.findAll().size();

        // Create the ProjectSubgoalId
        restProjectSubgoalIdMockMvc.perform(post("/api/project-subgoal-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSubgoalId)))
            .andExpect(status().isCreated());

        // Validate the ProjectSubgoalId in the database
        List<ProjectSubgoalId> projectSubgoalIdList = projectSubgoalIdRepository.findAll();
        assertThat(projectSubgoalIdList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectSubgoalId testProjectSubgoalId = projectSubgoalIdList.get(projectSubgoalIdList.size() - 1);
        assertThat(testProjectSubgoalId.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectSubgoalId.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectSubgoalId.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectSubgoalIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectSubgoalIdRepository.findAll().size();

        // Create the ProjectSubgoalId with an existing ID
        projectSubgoalId.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectSubgoalIdMockMvc.perform(post("/api/project-subgoal-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSubgoalId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectSubgoalId in the database
        List<ProjectSubgoalId> projectSubgoalIdList = projectSubgoalIdRepository.findAll();
        assertThat(projectSubgoalIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectSubgoalIds() throws Exception {
        // Initialize the database
        projectSubgoalIdRepository.saveAndFlush(projectSubgoalId);

        // Get all the projectSubgoalIdList
        restProjectSubgoalIdMockMvc.perform(get("/api/project-subgoal-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectSubgoalId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectSubgoalId() throws Exception {
        // Initialize the database
        projectSubgoalIdRepository.saveAndFlush(projectSubgoalId);

        // Get the projectSubgoalId
        restProjectSubgoalIdMockMvc.perform(get("/api/project-subgoal-ids/{id}", projectSubgoalId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectSubgoalId.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectSubgoalIdsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectSubgoalIdRepository.saveAndFlush(projectSubgoalId);

        // Get all the projectSubgoalIdList where code equals to DEFAULT_CODE
        defaultProjectSubgoalIdShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectSubgoalIdList where code equals to UPDATED_CODE
        defaultProjectSubgoalIdShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectSubgoalIdsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectSubgoalIdRepository.saveAndFlush(projectSubgoalId);

        // Get all the projectSubgoalIdList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectSubgoalIdShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectSubgoalIdList where code equals to UPDATED_CODE
        defaultProjectSubgoalIdShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectSubgoalIdsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectSubgoalIdRepository.saveAndFlush(projectSubgoalId);

        // Get all the projectSubgoalIdList where code is not null
        defaultProjectSubgoalIdShouldBeFound("code.specified=true");

        // Get all the projectSubgoalIdList where code is null
        defaultProjectSubgoalIdShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectSubgoalIdsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectSubgoalIdRepository.saveAndFlush(projectSubgoalId);

        // Get all the projectSubgoalIdList where name equals to DEFAULT_NAME
        defaultProjectSubgoalIdShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectSubgoalIdList where name equals to UPDATED_NAME
        defaultProjectSubgoalIdShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectSubgoalIdsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectSubgoalIdRepository.saveAndFlush(projectSubgoalId);

        // Get all the projectSubgoalIdList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectSubgoalIdShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectSubgoalIdList where name equals to UPDATED_NAME
        defaultProjectSubgoalIdShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectSubgoalIdsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectSubgoalIdRepository.saveAndFlush(projectSubgoalId);

        // Get all the projectSubgoalIdList where name is not null
        defaultProjectSubgoalIdShouldBeFound("name.specified=true");

        // Get all the projectSubgoalIdList where name is null
        defaultProjectSubgoalIdShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectSubgoalIdsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectSubgoalIdRepository.saveAndFlush(projectSubgoalId);

        // Get all the projectSubgoalIdList where description equals to DEFAULT_DESCRIPTION
        defaultProjectSubgoalIdShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectSubgoalIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectSubgoalIdShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectSubgoalIdsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectSubgoalIdRepository.saveAndFlush(projectSubgoalId);

        // Get all the projectSubgoalIdList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectSubgoalIdShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectSubgoalIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectSubgoalIdShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectSubgoalIdsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectSubgoalIdRepository.saveAndFlush(projectSubgoalId);

        // Get all the projectSubgoalIdList where description is not null
        defaultProjectSubgoalIdShouldBeFound("description.specified=true");

        // Get all the projectSubgoalIdList where description is null
        defaultProjectSubgoalIdShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectSubgoalIdShouldBeFound(String filter) throws Exception {
        restProjectSubgoalIdMockMvc.perform(get("/api/project-subgoal-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectSubgoalId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectSubgoalIdMockMvc.perform(get("/api/project-subgoal-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectSubgoalIdShouldNotBeFound(String filter) throws Exception {
        restProjectSubgoalIdMockMvc.perform(get("/api/project-subgoal-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectSubgoalIdMockMvc.perform(get("/api/project-subgoal-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectSubgoalId() throws Exception {
        // Get the projectSubgoalId
        restProjectSubgoalIdMockMvc.perform(get("/api/project-subgoal-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectSubgoalId() throws Exception {
        // Initialize the database
        projectSubgoalIdService.save(projectSubgoalId);

        int databaseSizeBeforeUpdate = projectSubgoalIdRepository.findAll().size();

        // Update the projectSubgoalId
        ProjectSubgoalId updatedProjectSubgoalId = projectSubgoalIdRepository.findById(projectSubgoalId.getId()).get();
        // Disconnect from session so that the updates on updatedProjectSubgoalId are not directly saved in db
        em.detach(updatedProjectSubgoalId);
        updatedProjectSubgoalId
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectSubgoalIdMockMvc.perform(put("/api/project-subgoal-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectSubgoalId)))
            .andExpect(status().isOk());

        // Validate the ProjectSubgoalId in the database
        List<ProjectSubgoalId> projectSubgoalIdList = projectSubgoalIdRepository.findAll();
        assertThat(projectSubgoalIdList).hasSize(databaseSizeBeforeUpdate);
        ProjectSubgoalId testProjectSubgoalId = projectSubgoalIdList.get(projectSubgoalIdList.size() - 1);
        assertThat(testProjectSubgoalId.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectSubgoalId.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectSubgoalId.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectSubgoalId() throws Exception {
        int databaseSizeBeforeUpdate = projectSubgoalIdRepository.findAll().size();

        // Create the ProjectSubgoalId

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectSubgoalIdMockMvc.perform(put("/api/project-subgoal-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectSubgoalId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectSubgoalId in the database
        List<ProjectSubgoalId> projectSubgoalIdList = projectSubgoalIdRepository.findAll();
        assertThat(projectSubgoalIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectSubgoalId() throws Exception {
        // Initialize the database
        projectSubgoalIdService.save(projectSubgoalId);

        int databaseSizeBeforeDelete = projectSubgoalIdRepository.findAll().size();

        // Delete the projectSubgoalId
        restProjectSubgoalIdMockMvc.perform(delete("/api/project-subgoal-ids/{id}", projectSubgoalId.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectSubgoalId> projectSubgoalIdList = projectSubgoalIdRepository.findAll();
        assertThat(projectSubgoalIdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectSubgoalId.class);
        ProjectSubgoalId projectSubgoalId1 = new ProjectSubgoalId();
        projectSubgoalId1.setId(1L);
        ProjectSubgoalId projectSubgoalId2 = new ProjectSubgoalId();
        projectSubgoalId2.setId(projectSubgoalId1.getId());
        assertThat(projectSubgoalId1).isEqualTo(projectSubgoalId2);
        projectSubgoalId2.setId(2L);
        assertThat(projectSubgoalId1).isNotEqualTo(projectSubgoalId2);
        projectSubgoalId1.setId(null);
        assertThat(projectSubgoalId1).isNotEqualTo(projectSubgoalId2);
    }
}
