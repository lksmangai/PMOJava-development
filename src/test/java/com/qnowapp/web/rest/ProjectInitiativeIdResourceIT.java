package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.repository.ProjectInitiativeIdRepository;
import com.qnowapp.service.ProjectInitiativeIdService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectInitiativeIdCriteria;
import com.qnowapp.service.ProjectInitiativeIdQueryService;

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
 * Integration tests for the {@Link ProjectInitiativeIdResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectInitiativeIdResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectInitiativeIdRepository projectInitiativeIdRepository;

    @Autowired
    private ProjectInitiativeIdService projectInitiativeIdService;

    @Autowired
    private ProjectInitiativeIdQueryService projectInitiativeIdQueryService;

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

    private MockMvc restProjectInitiativeIdMockMvc;

    private ProjectInitiativeId projectInitiativeId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectInitiativeIdResource projectInitiativeIdResource = new ProjectInitiativeIdResource( projectInitiativeIdService, projectInitiativeIdQueryService);
        this.restProjectInitiativeIdMockMvc = MockMvcBuilders.standaloneSetup(projectInitiativeIdResource)
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
    public static ProjectInitiativeId createEntity(EntityManager em) {
        ProjectInitiativeId projectInitiativeId = new ProjectInitiativeId()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectInitiativeId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectInitiativeId createUpdatedEntity(EntityManager em) {
        ProjectInitiativeId projectInitiativeId = new ProjectInitiativeId()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectInitiativeId;
    }

    @BeforeEach
    public void initTest() {
        projectInitiativeId = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectInitiativeId() throws Exception {
        int databaseSizeBeforeCreate = projectInitiativeIdRepository.findAll().size();

        // Create the ProjectInitiativeId
        restProjectInitiativeIdMockMvc.perform(post("/api/project-initiative-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectInitiativeId)))
            .andExpect(status().isCreated());

        // Validate the ProjectInitiativeId in the database
        List<ProjectInitiativeId> projectInitiativeIdList = projectInitiativeIdRepository.findAll();
        assertThat(projectInitiativeIdList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectInitiativeId testProjectInitiativeId = projectInitiativeIdList.get(projectInitiativeIdList.size() - 1);
        assertThat(testProjectInitiativeId.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectInitiativeId.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectInitiativeId.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectInitiativeIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectInitiativeIdRepository.findAll().size();

        // Create the ProjectInitiativeId with an existing ID
        projectInitiativeId.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectInitiativeIdMockMvc.perform(post("/api/project-initiative-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectInitiativeId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectInitiativeId in the database
        List<ProjectInitiativeId> projectInitiativeIdList = projectInitiativeIdRepository.findAll();
        assertThat(projectInitiativeIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectInitiativeIds() throws Exception {
        // Initialize the database
        projectInitiativeIdRepository.saveAndFlush(projectInitiativeId);

        // Get all the projectInitiativeIdList
        restProjectInitiativeIdMockMvc.perform(get("/api/project-initiative-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectInitiativeId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectInitiativeId() throws Exception {
        // Initialize the database
        projectInitiativeIdRepository.saveAndFlush(projectInitiativeId);

        // Get the projectInitiativeId
        restProjectInitiativeIdMockMvc.perform(get("/api/project-initiative-ids/{id}", projectInitiativeId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectInitiativeId.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectInitiativeIdsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectInitiativeIdRepository.saveAndFlush(projectInitiativeId);

        // Get all the projectInitiativeIdList where code equals to DEFAULT_CODE
        defaultProjectInitiativeIdShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectInitiativeIdList where code equals to UPDATED_CODE
        defaultProjectInitiativeIdShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectInitiativeIdsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectInitiativeIdRepository.saveAndFlush(projectInitiativeId);

        // Get all the projectInitiativeIdList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectInitiativeIdShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectInitiativeIdList where code equals to UPDATED_CODE
        defaultProjectInitiativeIdShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectInitiativeIdsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectInitiativeIdRepository.saveAndFlush(projectInitiativeId);

        // Get all the projectInitiativeIdList where code is not null
        defaultProjectInitiativeIdShouldBeFound("code.specified=true");

        // Get all the projectInitiativeIdList where code is null
        defaultProjectInitiativeIdShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectInitiativeIdsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectInitiativeIdRepository.saveAndFlush(projectInitiativeId);

        // Get all the projectInitiativeIdList where name equals to DEFAULT_NAME
        defaultProjectInitiativeIdShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectInitiativeIdList where name equals to UPDATED_NAME
        defaultProjectInitiativeIdShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectInitiativeIdsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectInitiativeIdRepository.saveAndFlush(projectInitiativeId);

        // Get all the projectInitiativeIdList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectInitiativeIdShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectInitiativeIdList where name equals to UPDATED_NAME
        defaultProjectInitiativeIdShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectInitiativeIdsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectInitiativeIdRepository.saveAndFlush(projectInitiativeId);

        // Get all the projectInitiativeIdList where name is not null
        defaultProjectInitiativeIdShouldBeFound("name.specified=true");

        // Get all the projectInitiativeIdList where name is null
        defaultProjectInitiativeIdShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectInitiativeIdsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectInitiativeIdRepository.saveAndFlush(projectInitiativeId);

        // Get all the projectInitiativeIdList where description equals to DEFAULT_DESCRIPTION
        defaultProjectInitiativeIdShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectInitiativeIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectInitiativeIdShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectInitiativeIdsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectInitiativeIdRepository.saveAndFlush(projectInitiativeId);

        // Get all the projectInitiativeIdList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectInitiativeIdShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectInitiativeIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectInitiativeIdShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectInitiativeIdsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectInitiativeIdRepository.saveAndFlush(projectInitiativeId);

        // Get all the projectInitiativeIdList where description is not null
        defaultProjectInitiativeIdShouldBeFound("description.specified=true");

        // Get all the projectInitiativeIdList where description is null
        defaultProjectInitiativeIdShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectInitiativeIdShouldBeFound(String filter) throws Exception {
        restProjectInitiativeIdMockMvc.perform(get("/api/project-initiative-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectInitiativeId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectInitiativeIdMockMvc.perform(get("/api/project-initiative-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectInitiativeIdShouldNotBeFound(String filter) throws Exception {
        restProjectInitiativeIdMockMvc.perform(get("/api/project-initiative-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectInitiativeIdMockMvc.perform(get("/api/project-initiative-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectInitiativeId() throws Exception {
        // Get the projectInitiativeId
        restProjectInitiativeIdMockMvc.perform(get("/api/project-initiative-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectInitiativeId() throws Exception {
        // Initialize the database
        projectInitiativeIdService.save(projectInitiativeId);

        int databaseSizeBeforeUpdate = projectInitiativeIdRepository.findAll().size();

        // Update the projectInitiativeId
        ProjectInitiativeId updatedProjectInitiativeId = projectInitiativeIdRepository.findById(projectInitiativeId.getId()).get();
        // Disconnect from session so that the updates on updatedProjectInitiativeId are not directly saved in db
        em.detach(updatedProjectInitiativeId);
        updatedProjectInitiativeId
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectInitiativeIdMockMvc.perform(put("/api/project-initiative-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectInitiativeId)))
            .andExpect(status().isOk());

        // Validate the ProjectInitiativeId in the database
        List<ProjectInitiativeId> projectInitiativeIdList = projectInitiativeIdRepository.findAll();
        assertThat(projectInitiativeIdList).hasSize(databaseSizeBeforeUpdate);
        ProjectInitiativeId testProjectInitiativeId = projectInitiativeIdList.get(projectInitiativeIdList.size() - 1);
        assertThat(testProjectInitiativeId.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectInitiativeId.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectInitiativeId.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectInitiativeId() throws Exception {
        int databaseSizeBeforeUpdate = projectInitiativeIdRepository.findAll().size();

        // Create the ProjectInitiativeId

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectInitiativeIdMockMvc.perform(put("/api/project-initiative-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectInitiativeId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectInitiativeId in the database
        List<ProjectInitiativeId> projectInitiativeIdList = projectInitiativeIdRepository.findAll();
        assertThat(projectInitiativeIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectInitiativeId() throws Exception {
        // Initialize the database
        projectInitiativeIdService.save(projectInitiativeId);

        int databaseSizeBeforeDelete = projectInitiativeIdRepository.findAll().size();

        // Delete the projectInitiativeId
        restProjectInitiativeIdMockMvc.perform(delete("/api/project-initiative-ids/{id}", projectInitiativeId.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectInitiativeId> projectInitiativeIdList = projectInitiativeIdRepository.findAll();
        assertThat(projectInitiativeIdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectInitiativeId.class);
        ProjectInitiativeId projectInitiativeId1 = new ProjectInitiativeId();
        projectInitiativeId1.setId(1L);
        ProjectInitiativeId projectInitiativeId2 = new ProjectInitiativeId();
        projectInitiativeId2.setId(projectInitiativeId1.getId());
        assertThat(projectInitiativeId1).isEqualTo(projectInitiativeId2);
        projectInitiativeId2.setId(2L);
        assertThat(projectInitiativeId1).isNotEqualTo(projectInitiativeId2);
        projectInitiativeId1.setId(null);
        assertThat(projectInitiativeId1).isNotEqualTo(projectInitiativeId2);
    }
}
