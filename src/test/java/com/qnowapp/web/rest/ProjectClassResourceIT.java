package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectClass;
import com.qnowapp.repository.ProjectClassRepository;
import com.qnowapp.service.ProjectClassService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectClassCriteria;
import com.qnowapp.service.ProjectClassQueryService;

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
 * Integration tests for the {@Link ProjectClassResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectClassResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectClassRepository projectClassRepository;

    @Autowired
    private ProjectClassService projectClassService;

    @Autowired
    private ProjectClassQueryService projectClassQueryService;

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

    private MockMvc restProjectClassMockMvc;

    private ProjectClass projectClass;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectClassResource projectClassResource = new ProjectClassResource( projectClassService, projectClassQueryService);
        this.restProjectClassMockMvc = MockMvcBuilders.standaloneSetup(projectClassResource)
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
    public static ProjectClass createEntity(EntityManager em) {
        ProjectClass projectClass = new ProjectClass()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectClass;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectClass createUpdatedEntity(EntityManager em) {
        ProjectClass projectClass = new ProjectClass()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectClass;
    }

    @BeforeEach
    public void initTest() {
        projectClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectClass() throws Exception {
        int databaseSizeBeforeCreate = projectClassRepository.findAll().size();

        // Create the ProjectClass
        restProjectClassMockMvc.perform(post("/api/project-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectClass)))
            .andExpect(status().isCreated());

        // Validate the ProjectClass in the database
        List<ProjectClass> projectClassList = projectClassRepository.findAll();
        assertThat(projectClassList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectClass testProjectClass = projectClassList.get(projectClassList.size() - 1);
        assertThat(testProjectClass.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectClass.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectClassRepository.findAll().size();

        // Create the ProjectClass with an existing ID
        projectClass.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectClassMockMvc.perform(post("/api/project-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectClass)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectClass in the database
        List<ProjectClass> projectClassList = projectClassRepository.findAll();
        assertThat(projectClassList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectClasses() throws Exception {
        // Initialize the database
        projectClassRepository.saveAndFlush(projectClass);

        // Get all the projectClassList
        restProjectClassMockMvc.perform(get("/api/project-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectClass() throws Exception {
        // Initialize the database
        projectClassRepository.saveAndFlush(projectClass);

        // Get the projectClass
        restProjectClassMockMvc.perform(get("/api/project-classes/{id}", projectClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectClass.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectClassesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectClassRepository.saveAndFlush(projectClass);

        // Get all the projectClassList where code equals to DEFAULT_CODE
        defaultProjectClassShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectClassList where code equals to UPDATED_CODE
        defaultProjectClassShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectClassesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectClassRepository.saveAndFlush(projectClass);

        // Get all the projectClassList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectClassShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectClassList where code equals to UPDATED_CODE
        defaultProjectClassShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectClassesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectClassRepository.saveAndFlush(projectClass);

        // Get all the projectClassList where code is not null
        defaultProjectClassShouldBeFound("code.specified=true");

        // Get all the projectClassList where code is null
        defaultProjectClassShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectClassesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectClassRepository.saveAndFlush(projectClass);

        // Get all the projectClassList where name equals to DEFAULT_NAME
        defaultProjectClassShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectClassList where name equals to UPDATED_NAME
        defaultProjectClassShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectClassesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectClassRepository.saveAndFlush(projectClass);

        // Get all the projectClassList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectClassShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectClassList where name equals to UPDATED_NAME
        defaultProjectClassShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectClassesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectClassRepository.saveAndFlush(projectClass);

        // Get all the projectClassList where name is not null
        defaultProjectClassShouldBeFound("name.specified=true");

        // Get all the projectClassList where name is null
        defaultProjectClassShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectClassesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectClassRepository.saveAndFlush(projectClass);

        // Get all the projectClassList where description equals to DEFAULT_DESCRIPTION
        defaultProjectClassShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectClassList where description equals to UPDATED_DESCRIPTION
        defaultProjectClassShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectClassesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectClassRepository.saveAndFlush(projectClass);

        // Get all the projectClassList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectClassShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectClassList where description equals to UPDATED_DESCRIPTION
        defaultProjectClassShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectClassesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectClassRepository.saveAndFlush(projectClass);

        // Get all the projectClassList where description is not null
        defaultProjectClassShouldBeFound("description.specified=true");

        // Get all the projectClassList where description is null
        defaultProjectClassShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectClassShouldBeFound(String filter) throws Exception {
        restProjectClassMockMvc.perform(get("/api/project-classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectClassMockMvc.perform(get("/api/project-classes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectClassShouldNotBeFound(String filter) throws Exception {
        restProjectClassMockMvc.perform(get("/api/project-classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectClassMockMvc.perform(get("/api/project-classes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectClass() throws Exception {
        // Get the projectClass
        restProjectClassMockMvc.perform(get("/api/project-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectClass() throws Exception {
        // Initialize the database
        projectClassService.save(projectClass);

        int databaseSizeBeforeUpdate = projectClassRepository.findAll().size();

        // Update the projectClass
        ProjectClass updatedProjectClass = projectClassRepository.findById(projectClass.getId()).get();
        // Disconnect from session so that the updates on updatedProjectClass are not directly saved in db
        em.detach(updatedProjectClass);
        updatedProjectClass
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectClassMockMvc.perform(put("/api/project-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectClass)))
            .andExpect(status().isOk());

        // Validate the ProjectClass in the database
        List<ProjectClass> projectClassList = projectClassRepository.findAll();
        assertThat(projectClassList).hasSize(databaseSizeBeforeUpdate);
        ProjectClass testProjectClass = projectClassList.get(projectClassList.size() - 1);
        assertThat(testProjectClass.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectClass.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectClass() throws Exception {
        int databaseSizeBeforeUpdate = projectClassRepository.findAll().size();

        // Create the ProjectClass

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectClassMockMvc.perform(put("/api/project-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectClass)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectClass in the database
        List<ProjectClass> projectClassList = projectClassRepository.findAll();
        assertThat(projectClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectClass() throws Exception {
        // Initialize the database
        projectClassService.save(projectClass);

        int databaseSizeBeforeDelete = projectClassRepository.findAll().size();

        // Delete the projectClass
        restProjectClassMockMvc.perform(delete("/api/project-classes/{id}", projectClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectClass> projectClassList = projectClassRepository.findAll();
        assertThat(projectClassList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectClass.class);
        ProjectClass projectClass1 = new ProjectClass();
        projectClass1.setId(1L);
        ProjectClass projectClass2 = new ProjectClass();
        projectClass2.setId(projectClass1.getId());
        assertThat(projectClass1).isEqualTo(projectClass2);
        projectClass2.setId(2L);
        assertThat(projectClass1).isNotEqualTo(projectClass2);
        projectClass1.setId(null);
        assertThat(projectClass1).isNotEqualTo(projectClass2);
    }
}
