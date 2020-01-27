package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectTypeId;
import com.qnowapp.repository.ProjectTypeIdRepository;
import com.qnowapp.service.ProjectTypeIdService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectTypeIdCriteria;
import com.qnowapp.service.ProjectTypeIdQueryService;

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
 * Integration tests for the {@Link ProjectTypeIdResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectTypeIdResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectTypeIdRepository projectTypeIdRepository;

    @Autowired
    private ProjectTypeIdService projectTypeIdService;

    @Autowired
    private ProjectTypeIdQueryService projectTypeIdQueryService;

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

    private MockMvc restProjectTypeIdMockMvc;

    private ProjectTypeId projectTypeId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectTypeIdResource projectTypeIdResource = new ProjectTypeIdResource( projectTypeIdService, projectTypeIdQueryService);
        this.restProjectTypeIdMockMvc = MockMvcBuilders.standaloneSetup(projectTypeIdResource)
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
    public static ProjectTypeId createEntity(EntityManager em) {
        ProjectTypeId projectTypeId = new ProjectTypeId()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectTypeId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTypeId createUpdatedEntity(EntityManager em) {
        ProjectTypeId projectTypeId = new ProjectTypeId()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectTypeId;
    }

    @BeforeEach
    public void initTest() {
        projectTypeId = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectTypeId() throws Exception {
        int databaseSizeBeforeCreate = projectTypeIdRepository.findAll().size();

        // Create the ProjectTypeId
        restProjectTypeIdMockMvc.perform(post("/api/project-type-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTypeId)))
            .andExpect(status().isCreated());

        // Validate the ProjectTypeId in the database
        List<ProjectTypeId> projectTypeIdList = projectTypeIdRepository.findAll();
        assertThat(projectTypeIdList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectTypeId testProjectTypeId = projectTypeIdList.get(projectTypeIdList.size() - 1);
        assertThat(testProjectTypeId.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectTypeId.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectTypeId.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectTypeIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectTypeIdRepository.findAll().size();

        // Create the ProjectTypeId with an existing ID
        projectTypeId.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectTypeIdMockMvc.perform(post("/api/project-type-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTypeId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTypeId in the database
        List<ProjectTypeId> projectTypeIdList = projectTypeIdRepository.findAll();
        assertThat(projectTypeIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectTypeIds() throws Exception {
        // Initialize the database
        projectTypeIdRepository.saveAndFlush(projectTypeId);

        // Get all the projectTypeIdList
        restProjectTypeIdMockMvc.perform(get("/api/project-type-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTypeId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectTypeId() throws Exception {
        // Initialize the database
        projectTypeIdRepository.saveAndFlush(projectTypeId);

        // Get the projectTypeId
        restProjectTypeIdMockMvc.perform(get("/api/project-type-ids/{id}", projectTypeId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectTypeId.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectTypeIdsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTypeIdRepository.saveAndFlush(projectTypeId);

        // Get all the projectTypeIdList where code equals to DEFAULT_CODE
        defaultProjectTypeIdShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectTypeIdList where code equals to UPDATED_CODE
        defaultProjectTypeIdShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectTypeIdsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectTypeIdRepository.saveAndFlush(projectTypeId);

        // Get all the projectTypeIdList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectTypeIdShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectTypeIdList where code equals to UPDATED_CODE
        defaultProjectTypeIdShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectTypeIdsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTypeIdRepository.saveAndFlush(projectTypeId);

        // Get all the projectTypeIdList where code is not null
        defaultProjectTypeIdShouldBeFound("code.specified=true");

        // Get all the projectTypeIdList where code is null
        defaultProjectTypeIdShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectTypeIdsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTypeIdRepository.saveAndFlush(projectTypeId);

        // Get all the projectTypeIdList where name equals to DEFAULT_NAME
        defaultProjectTypeIdShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectTypeIdList where name equals to UPDATED_NAME
        defaultProjectTypeIdShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectTypeIdsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectTypeIdRepository.saveAndFlush(projectTypeId);

        // Get all the projectTypeIdList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectTypeIdShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectTypeIdList where name equals to UPDATED_NAME
        defaultProjectTypeIdShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectTypeIdsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTypeIdRepository.saveAndFlush(projectTypeId);

        // Get all the projectTypeIdList where name is not null
        defaultProjectTypeIdShouldBeFound("name.specified=true");

        // Get all the projectTypeIdList where name is null
        defaultProjectTypeIdShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectTypeIdsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTypeIdRepository.saveAndFlush(projectTypeId);

        // Get all the projectTypeIdList where description equals to DEFAULT_DESCRIPTION
        defaultProjectTypeIdShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectTypeIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectTypeIdShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectTypeIdsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectTypeIdRepository.saveAndFlush(projectTypeId);

        // Get all the projectTypeIdList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectTypeIdShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectTypeIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectTypeIdShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectTypeIdsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTypeIdRepository.saveAndFlush(projectTypeId);

        // Get all the projectTypeIdList where description is not null
        defaultProjectTypeIdShouldBeFound("description.specified=true");

        // Get all the projectTypeIdList where description is null
        defaultProjectTypeIdShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectTypeIdShouldBeFound(String filter) throws Exception {
        restProjectTypeIdMockMvc.perform(get("/api/project-type-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTypeId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectTypeIdMockMvc.perform(get("/api/project-type-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectTypeIdShouldNotBeFound(String filter) throws Exception {
        restProjectTypeIdMockMvc.perform(get("/api/project-type-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectTypeIdMockMvc.perform(get("/api/project-type-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectTypeId() throws Exception {
        // Get the projectTypeId
        restProjectTypeIdMockMvc.perform(get("/api/project-type-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectTypeId() throws Exception {
        // Initialize the database
        projectTypeIdService.save(projectTypeId);

        int databaseSizeBeforeUpdate = projectTypeIdRepository.findAll().size();

        // Update the projectTypeId
        ProjectTypeId updatedProjectTypeId = projectTypeIdRepository.findById(projectTypeId.getId()).get();
        // Disconnect from session so that the updates on updatedProjectTypeId are not directly saved in db
        em.detach(updatedProjectTypeId);
        updatedProjectTypeId
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectTypeIdMockMvc.perform(put("/api/project-type-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectTypeId)))
            .andExpect(status().isOk());

        // Validate the ProjectTypeId in the database
        List<ProjectTypeId> projectTypeIdList = projectTypeIdRepository.findAll();
        assertThat(projectTypeIdList).hasSize(databaseSizeBeforeUpdate);
        ProjectTypeId testProjectTypeId = projectTypeIdList.get(projectTypeIdList.size() - 1);
        assertThat(testProjectTypeId.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectTypeId.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectTypeId.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectTypeId() throws Exception {
        int databaseSizeBeforeUpdate = projectTypeIdRepository.findAll().size();

        // Create the ProjectTypeId

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectTypeIdMockMvc.perform(put("/api/project-type-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTypeId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTypeId in the database
        List<ProjectTypeId> projectTypeIdList = projectTypeIdRepository.findAll();
        assertThat(projectTypeIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectTypeId() throws Exception {
        // Initialize the database
        projectTypeIdService.save(projectTypeId);

        int databaseSizeBeforeDelete = projectTypeIdRepository.findAll().size();

        // Delete the projectTypeId
        restProjectTypeIdMockMvc.perform(delete("/api/project-type-ids/{id}", projectTypeId.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectTypeId> projectTypeIdList = projectTypeIdRepository.findAll();
        assertThat(projectTypeIdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTypeId.class);
        ProjectTypeId projectTypeId1 = new ProjectTypeId();
        projectTypeId1.setId(1L);
        ProjectTypeId projectTypeId2 = new ProjectTypeId();
        projectTypeId2.setId(projectTypeId1.getId());
        assertThat(projectTypeId1).isEqualTo(projectTypeId2);
        projectTypeId2.setId(2L);
        assertThat(projectTypeId1).isNotEqualTo(projectTypeId2);
        projectTypeId1.setId(null);
        assertThat(projectTypeId1).isNotEqualTo(projectTypeId2);
    }
}
