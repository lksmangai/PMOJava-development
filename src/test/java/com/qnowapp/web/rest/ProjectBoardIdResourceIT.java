package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectBoardId;
import com.qnowapp.repository.ProjectBoardIdRepository;
import com.qnowapp.service.ProjectBoardIdService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectBoardIdCriteria;
import com.qnowapp.service.ProjectBoardIdQueryService;

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
 * Integration tests for the {@Link ProjectBoardIdResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectBoardIdResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectBoardIdRepository projectBoardIdRepository;

    @Autowired
    private ProjectBoardIdService projectBoardIdService;

    @Autowired
    private ProjectBoardIdQueryService projectBoardIdQueryService;

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

    private MockMvc restProjectBoardIdMockMvc;

    private ProjectBoardId projectBoardId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectBoardIdResource projectBoardIdResource = new ProjectBoardIdResource(projectBoardIdService, projectBoardIdQueryService);
        this.restProjectBoardIdMockMvc = MockMvcBuilders.standaloneSetup(projectBoardIdResource)
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
    public static ProjectBoardId createEntity(EntityManager em) {
        ProjectBoardId projectBoardId = new ProjectBoardId()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectBoardId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectBoardId createUpdatedEntity(EntityManager em) {
        ProjectBoardId projectBoardId = new ProjectBoardId()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectBoardId;
    }

    @BeforeEach
    public void initTest() {
        projectBoardId = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectBoardId() throws Exception {
        int databaseSizeBeforeCreate = projectBoardIdRepository.findAll().size();

        // Create the ProjectBoardId
        restProjectBoardIdMockMvc.perform(post("/api/project-board-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectBoardId)))
            .andExpect(status().isCreated());

        // Validate the ProjectBoardId in the database
        List<ProjectBoardId> projectBoardIdList = projectBoardIdRepository.findAll();
        assertThat(projectBoardIdList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectBoardId testProjectBoardId = projectBoardIdList.get(projectBoardIdList.size() - 1);
        assertThat(testProjectBoardId.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectBoardId.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectBoardId.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectBoardIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectBoardIdRepository.findAll().size();

        // Create the ProjectBoardId with an existing ID
        projectBoardId.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectBoardIdMockMvc.perform(post("/api/project-board-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectBoardId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectBoardId in the database
        List<ProjectBoardId> projectBoardIdList = projectBoardIdRepository.findAll();
        assertThat(projectBoardIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectBoardIds() throws Exception {
        // Initialize the database
        projectBoardIdRepository.saveAndFlush(projectBoardId);

        // Get all the projectBoardIdList
        restProjectBoardIdMockMvc.perform(get("/api/project-board-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectBoardId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectBoardId() throws Exception {
        // Initialize the database
        projectBoardIdRepository.saveAndFlush(projectBoardId);

        // Get the projectBoardId
        restProjectBoardIdMockMvc.perform(get("/api/project-board-ids/{id}", projectBoardId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectBoardId.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectBoardIdsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectBoardIdRepository.saveAndFlush(projectBoardId);

        // Get all the projectBoardIdList where code equals to DEFAULT_CODE
        defaultProjectBoardIdShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectBoardIdList where code equals to UPDATED_CODE
        defaultProjectBoardIdShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectBoardIdsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectBoardIdRepository.saveAndFlush(projectBoardId);

        // Get all the projectBoardIdList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectBoardIdShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectBoardIdList where code equals to UPDATED_CODE
        defaultProjectBoardIdShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectBoardIdsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectBoardIdRepository.saveAndFlush(projectBoardId);

        // Get all the projectBoardIdList where code is not null
        defaultProjectBoardIdShouldBeFound("code.specified=true");

        // Get all the projectBoardIdList where code is null
        defaultProjectBoardIdShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectBoardIdsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectBoardIdRepository.saveAndFlush(projectBoardId);

        // Get all the projectBoardIdList where name equals to DEFAULT_NAME
        defaultProjectBoardIdShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectBoardIdList where name equals to UPDATED_NAME
        defaultProjectBoardIdShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectBoardIdsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectBoardIdRepository.saveAndFlush(projectBoardId);

        // Get all the projectBoardIdList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectBoardIdShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectBoardIdList where name equals to UPDATED_NAME
        defaultProjectBoardIdShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectBoardIdsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectBoardIdRepository.saveAndFlush(projectBoardId);

        // Get all the projectBoardIdList where name is not null
        defaultProjectBoardIdShouldBeFound("name.specified=true");

        // Get all the projectBoardIdList where name is null
        defaultProjectBoardIdShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectBoardIdsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectBoardIdRepository.saveAndFlush(projectBoardId);

        // Get all the projectBoardIdList where description equals to DEFAULT_DESCRIPTION
        defaultProjectBoardIdShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectBoardIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectBoardIdShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectBoardIdsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectBoardIdRepository.saveAndFlush(projectBoardId);

        // Get all the projectBoardIdList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectBoardIdShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectBoardIdList where description equals to UPDATED_DESCRIPTION
        defaultProjectBoardIdShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectBoardIdsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectBoardIdRepository.saveAndFlush(projectBoardId);

        // Get all the projectBoardIdList where description is not null
        defaultProjectBoardIdShouldBeFound("description.specified=true");

        // Get all the projectBoardIdList where description is null
        defaultProjectBoardIdShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectBoardIdShouldBeFound(String filter) throws Exception {
        restProjectBoardIdMockMvc.perform(get("/api/project-board-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectBoardId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectBoardIdMockMvc.perform(get("/api/project-board-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectBoardIdShouldNotBeFound(String filter) throws Exception {
        restProjectBoardIdMockMvc.perform(get("/api/project-board-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectBoardIdMockMvc.perform(get("/api/project-board-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectBoardId() throws Exception {
        // Get the projectBoardId
        restProjectBoardIdMockMvc.perform(get("/api/project-board-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectBoardId() throws Exception {
        // Initialize the database
        projectBoardIdService.save(projectBoardId);

        int databaseSizeBeforeUpdate = projectBoardIdRepository.findAll().size();

        // Update the projectBoardId
        ProjectBoardId updatedProjectBoardId = projectBoardIdRepository.findById(projectBoardId.getId()).get();
        // Disconnect from session so that the updates on updatedProjectBoardId are not directly saved in db
        em.detach(updatedProjectBoardId);
        updatedProjectBoardId
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectBoardIdMockMvc.perform(put("/api/project-board-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectBoardId)))
            .andExpect(status().isOk());

        // Validate the ProjectBoardId in the database
        List<ProjectBoardId> projectBoardIdList = projectBoardIdRepository.findAll();
        assertThat(projectBoardIdList).hasSize(databaseSizeBeforeUpdate);
        ProjectBoardId testProjectBoardId = projectBoardIdList.get(projectBoardIdList.size() - 1);
        assertThat(testProjectBoardId.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectBoardId.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectBoardId.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectBoardId() throws Exception {
        int databaseSizeBeforeUpdate = projectBoardIdRepository.findAll().size();

        // Create the ProjectBoardId

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectBoardIdMockMvc.perform(put("/api/project-board-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectBoardId)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectBoardId in the database
        List<ProjectBoardId> projectBoardIdList = projectBoardIdRepository.findAll();
        assertThat(projectBoardIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectBoardId() throws Exception {
        // Initialize the database
        projectBoardIdService.save(projectBoardId);

        int databaseSizeBeforeDelete = projectBoardIdRepository.findAll().size();

        // Delete the projectBoardId
        restProjectBoardIdMockMvc.perform(delete("/api/project-board-ids/{id}", projectBoardId.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectBoardId> projectBoardIdList = projectBoardIdRepository.findAll();
        assertThat(projectBoardIdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectBoardId.class);
        ProjectBoardId projectBoardId1 = new ProjectBoardId();
        projectBoardId1.setId(1L);
        ProjectBoardId projectBoardId2 = new ProjectBoardId();
        projectBoardId2.setId(projectBoardId1.getId());
        assertThat(projectBoardId1).isEqualTo(projectBoardId2);
        projectBoardId2.setId(2L);
        assertThat(projectBoardId1).isNotEqualTo(projectBoardId2);
        projectBoardId1.setId(null);
        assertThat(projectBoardId1).isNotEqualTo(projectBoardId2);
    }
}
