package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectBucketId;
import com.qnowapp.repository.ProjectBucketIdRepository;
import com.qnowapp.service.ProjectBucketIdService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectBucketIdCriteria;
import com.qnowapp.service.ProjectBucketIdQueryService;

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
 * Integration tests for the {@Link ProjectBucketIdResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectBucketIdResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectBucketIdRepository projectBucketIdRepository;

    @Autowired
    private ProjectBucketIdService projectBucketIdService;

    @Autowired
    private ProjectBucketIdQueryService projectBucketIdQueryService;

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

    private MockMvc restProjectBucketIdMockMvc;

    private ProjectBucketId projectBucketId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectBucketIdResource projectBucketIdResource = new ProjectBucketIdResource(projectBucketIdService, projectBucketIdQueryService);
        this.restProjectBucketIdMockMvc = MockMvcBuilders.standaloneSetup(projectBucketIdResource)
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
    public static ProjectBucketId createEntity(EntityManager em) {
        ProjectBucketId projectBucketId = new ProjectBucketId()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectBucketId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectBucketId createUpdatedEntity(EntityManager em) {
        ProjectBucketId projectBucketId = new ProjectBucketId()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectBucketId;
    }

    @BeforeEach
    public void initTest() {
        projectBucketId = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectBucketId() throws Exception {
        int databaseSizeBeforeCreate = projectBucketIdRepository.findAll().size();

        // Create the ProjectBucketId
        restProjectBucketIdMockMvc.perform(post("/api/project-bucket-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectBucketId)))
            .andExpect(status().isCreated());

        // Validate the ProjectBucketId in the database
        List<ProjectBucketId> projectBucketIdList = projectBucketIdRepository.findAll();
        assertThat(projectBucketIdList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectBucketId testProjectBucketId = projectBucketIdList.get(projectBucketIdList.size() - 1);
        assertThat(testProjectBucketId.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectBucketId.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectBucketId.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectBucketIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectBucketIdRepository.findAll().size();

        // Create the ProjectBucketId with an existing ID
        projectBucketId.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectBucketIdMockMvc.perform(post("/api/project-bucket-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectBucketId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectBucketId in the database
        List<ProjectBucketId> projectBucketIdList = projectBucketIdRepository.findAll();
        assertThat(projectBucketIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectBucketIds() throws Exception {
        // Initialize the database
        projectBucketIdRepository.saveAndFlush(projectBucketId);

        // Get all the projectBucketIdList
        restProjectBucketIdMockMvc.perform(get("/api/project-bucket-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectBucketId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectBucketId() throws Exception {
        // Initialize the database
        projectBucketIdRepository.saveAndFlush(projectBucketId);

        // Get the projectBucketId
        restProjectBucketIdMockMvc.perform(get("/api/project-bucket-ids/{id}", projectBucketId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectBucketId.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectBucketIdsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectBucketIdRepository.saveAndFlush(projectBucketId);

        // Get all the projectBucketIdList where code equals to DEFAULT_CODE
        defaultProjectBucketIdShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectBucketIdList where code equals to UPDATED_CODE
        defaultProjectBucketIdShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectBucketIdsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectBucketIdRepository.saveAndFlush(projectBucketId);

        // Get all the projectBucketIdList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectBucketIdShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectBucketIdList where code equals to UPDATED_CODE
        defaultProjectBucketIdShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectBucketIdsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectBucketIdRepository.saveAndFlush(projectBucketId);

        // Get all the projectBucketIdList where code is not null
        defaultProjectBucketIdShouldBeFound("code.specified=true");

        // Get all the projectBucketIdList where code is null
        defaultProjectBucketIdShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectBucketIdsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectBucketIdRepository.saveAndFlush(projectBucketId);

        // Get all the projectBucketIdList where name equals to DEFAULT_NAME
        defaultProjectBucketIdShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectBucketIdList where name equals to UPDATED_NAME
        defaultProjectBucketIdShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectBucketIdsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectBucketIdRepository.saveAndFlush(projectBucketId);

        // Get all the projectBucketIdList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectBucketIdShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectBucketIdList where name equals to UPDATED_NAME
        defaultProjectBucketIdShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectBucketIdsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectBucketIdRepository.saveAndFlush(projectBucketId);

        // Get all the projectBucketIdList where name is not null
        defaultProjectBucketIdShouldBeFound("name.specified=true");

        // Get all the projectBucketIdList where name is null
        defaultProjectBucketIdShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectBucketIdsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectBucketIdRepository.saveAndFlush(projectBucketId);

        // Get all the projectBucketIdList where description equals to DEFAULT_DESCRIPTION
        defaultProjectBucketIdShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectBucketIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectBucketIdShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectBucketIdsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectBucketIdRepository.saveAndFlush(projectBucketId);

        // Get all the projectBucketIdList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectBucketIdShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectBucketIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectBucketIdShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectBucketIdsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectBucketIdRepository.saveAndFlush(projectBucketId);

        // Get all the projectBucketIdList where description is not null
        defaultProjectBucketIdShouldBeFound("description.specified=true");

        // Get all the projectBucketIdList where description is null
        defaultProjectBucketIdShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectBucketIdShouldBeFound(String filter) throws Exception {
        restProjectBucketIdMockMvc.perform(get("/api/project-bucket-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectBucketId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectBucketIdMockMvc.perform(get("/api/project-bucket-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectBucketIdShouldNotBeFound(String filter) throws Exception {
        restProjectBucketIdMockMvc.perform(get("/api/project-bucket-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectBucketIdMockMvc.perform(get("/api/project-bucket-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectBucketId() throws Exception {
        // Get the projectBucketId
        restProjectBucketIdMockMvc.perform(get("/api/project-bucket-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectBucketId() throws Exception {
        // Initialize the database
        projectBucketIdService.save(projectBucketId);

        int databaseSizeBeforeUpdate = projectBucketIdRepository.findAll().size();

        // Update the projectBucketId
        ProjectBucketId updatedProjectBucketId = projectBucketIdRepository.findById(projectBucketId.getId()).get();
        // Disconnect from session so that the updates on updatedProjectBucketId are not directly saved in db
        em.detach(updatedProjectBucketId);
        updatedProjectBucketId
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectBucketIdMockMvc.perform(put("/api/project-bucket-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectBucketId)))
            .andExpect(status().isOk());

        // Validate the ProjectBucketId in the database
        List<ProjectBucketId> projectBucketIdList = projectBucketIdRepository.findAll();
        assertThat(projectBucketIdList).hasSize(databaseSizeBeforeUpdate);
        ProjectBucketId testProjectBucketId = projectBucketIdList.get(projectBucketIdList.size() - 1);
        assertThat(testProjectBucketId.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectBucketId.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectBucketId.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectBucketId() throws Exception {
        int databaseSizeBeforeUpdate = projectBucketIdRepository.findAll().size();

        // Create the ProjectBucketId

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectBucketIdMockMvc.perform(put("/api/project-bucket-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectBucketId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectBucketId in the database
        List<ProjectBucketId> projectBucketIdList = projectBucketIdRepository.findAll();
        assertThat(projectBucketIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectBucketId() throws Exception {
        // Initialize the database
        projectBucketIdService.save(projectBucketId);

        int databaseSizeBeforeDelete = projectBucketIdRepository.findAll().size();

        // Delete the projectBucketId
        restProjectBucketIdMockMvc.perform(delete("/api/project-bucket-ids/{id}", projectBucketId.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectBucketId> projectBucketIdList = projectBucketIdRepository.findAll();
        assertThat(projectBucketIdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectBucketId.class);
        ProjectBucketId projectBucketId1 = new ProjectBucketId();
        projectBucketId1.setId(1L);
        ProjectBucketId projectBucketId2 = new ProjectBucketId();
        projectBucketId2.setId(projectBucketId1.getId());
        assertThat(projectBucketId1).isEqualTo(projectBucketId2);
        projectBucketId2.setId(2L);
        assertThat(projectBucketId1).isNotEqualTo(projectBucketId2);
        projectBucketId1.setId(null);
        assertThat(projectBucketId1).isNotEqualTo(projectBucketId2);
    }
}
