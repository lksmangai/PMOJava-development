package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectVertical;
import com.qnowapp.repository.ProjectVerticalRepository;
import com.qnowapp.service.ProjectVerticalService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectVerticalCriteria;
import com.qnowapp.service.ProjectVerticalQueryService;

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
 * Integration tests for the {@Link ProjectVerticalResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectVerticalResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectVerticalRepository projectVerticalRepository;

    @Autowired
    private ProjectVerticalService projectVerticalService;

    @Autowired
    private ProjectVerticalQueryService projectVerticalQueryService;

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

    private MockMvc restProjectVerticalMockMvc;

    private ProjectVertical projectVertical;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectVerticalResource projectVerticalResource = new ProjectVerticalResource(projectVerticalService, projectVerticalQueryService);
        this.restProjectVerticalMockMvc = MockMvcBuilders.standaloneSetup(projectVerticalResource)
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
    public static ProjectVertical createEntity(EntityManager em) {
        ProjectVertical projectVertical = new ProjectVertical()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectVertical;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectVertical createUpdatedEntity(EntityManager em) {
        ProjectVertical projectVertical = new ProjectVertical()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectVertical;
    }

    @BeforeEach
    public void initTest() {
        projectVertical = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectVertical() throws Exception {
        int databaseSizeBeforeCreate = projectVerticalRepository.findAll().size();

        // Create the ProjectVertical
        restProjectVerticalMockMvc.perform(post("/api/project-verticals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectVertical)))
            .andExpect(status().isCreated());

        // Validate the ProjectVertical in the database
        List<ProjectVertical> projectVerticalList = projectVerticalRepository.findAll();
        assertThat(projectVerticalList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectVertical testProjectVertical = projectVerticalList.get(projectVerticalList.size() - 1);
        assertThat(testProjectVertical.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectVertical.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectVertical.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectVerticalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectVerticalRepository.findAll().size();

        // Create the ProjectVertical with an existing ID
        projectVertical.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectVerticalMockMvc.perform(post("/api/project-verticals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectVertical)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectVertical in the database
        List<ProjectVertical> projectVerticalList = projectVerticalRepository.findAll();
        assertThat(projectVerticalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectVerticals() throws Exception {
        // Initialize the database
        projectVerticalRepository.saveAndFlush(projectVertical);

        // Get all the projectVerticalList
        restProjectVerticalMockMvc.perform(get("/api/project-verticals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectVertical.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectVertical() throws Exception {
        // Initialize the database
        projectVerticalRepository.saveAndFlush(projectVertical);

        // Get the projectVertical
        restProjectVerticalMockMvc.perform(get("/api/project-verticals/{id}", projectVertical.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectVertical.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectVerticalsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectVerticalRepository.saveAndFlush(projectVertical);

        // Get all the projectVerticalList where code equals to DEFAULT_CODE
        defaultProjectVerticalShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectVerticalList where code equals to UPDATED_CODE
        defaultProjectVerticalShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectVerticalsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectVerticalRepository.saveAndFlush(projectVertical);

        // Get all the projectVerticalList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectVerticalShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectVerticalList where code equals to UPDATED_CODE
        defaultProjectVerticalShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectVerticalsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectVerticalRepository.saveAndFlush(projectVertical);

        // Get all the projectVerticalList where code is not null
        defaultProjectVerticalShouldBeFound("code.specified=true");

        // Get all the projectVerticalList where code is null
        defaultProjectVerticalShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectVerticalsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectVerticalRepository.saveAndFlush(projectVertical);

        // Get all the projectVerticalList where name equals to DEFAULT_NAME
        defaultProjectVerticalShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectVerticalList where name equals to UPDATED_NAME
        defaultProjectVerticalShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectVerticalsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectVerticalRepository.saveAndFlush(projectVertical);

        // Get all the projectVerticalList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectVerticalShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectVerticalList where name equals to UPDATED_NAME
        defaultProjectVerticalShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectVerticalsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectVerticalRepository.saveAndFlush(projectVertical);

        // Get all the projectVerticalList where name is not null
        defaultProjectVerticalShouldBeFound("name.specified=true");

        // Get all the projectVerticalList where name is null
        defaultProjectVerticalShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectVerticalsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectVerticalRepository.saveAndFlush(projectVertical);

        // Get all the projectVerticalList where description equals to DEFAULT_DESCRIPTION
        defaultProjectVerticalShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectVerticalList where description equals to UPDATED_DESCRIPTION
        defaultProjectVerticalShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectVerticalsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectVerticalRepository.saveAndFlush(projectVertical);

        // Get all the projectVerticalList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectVerticalShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectVerticalList where description equals to UPDATED_DESCRIPTION
        defaultProjectVerticalShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectVerticalsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectVerticalRepository.saveAndFlush(projectVertical);

        // Get all the projectVerticalList where description is not null
        defaultProjectVerticalShouldBeFound("description.specified=true");

        // Get all the projectVerticalList where description is null
        defaultProjectVerticalShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectVerticalShouldBeFound(String filter) throws Exception {
        restProjectVerticalMockMvc.perform(get("/api/project-verticals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectVertical.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectVerticalMockMvc.perform(get("/api/project-verticals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectVerticalShouldNotBeFound(String filter) throws Exception {
        restProjectVerticalMockMvc.perform(get("/api/project-verticals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectVerticalMockMvc.perform(get("/api/project-verticals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectVertical() throws Exception {
        // Get the projectVertical
        restProjectVerticalMockMvc.perform(get("/api/project-verticals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectVertical() throws Exception {
        // Initialize the database
        projectVerticalService.save(projectVertical);

        int databaseSizeBeforeUpdate = projectVerticalRepository.findAll().size();

        // Update the projectVertical
        ProjectVertical updatedProjectVertical = projectVerticalRepository.findById(projectVertical.getId()).get();
        // Disconnect from session so that the updates on updatedProjectVertical are not directly saved in db
        em.detach(updatedProjectVertical);
        updatedProjectVertical
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectVerticalMockMvc.perform(put("/api/project-verticals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectVertical)))
            .andExpect(status().isOk());

        // Validate the ProjectVertical in the database
        List<ProjectVertical> projectVerticalList = projectVerticalRepository.findAll();
        assertThat(projectVerticalList).hasSize(databaseSizeBeforeUpdate);
        ProjectVertical testProjectVertical = projectVerticalList.get(projectVerticalList.size() - 1);
        assertThat(testProjectVertical.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectVertical.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectVertical.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectVertical() throws Exception {
        int databaseSizeBeforeUpdate = projectVerticalRepository.findAll().size();

        // Create the ProjectVertical

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectVerticalMockMvc.perform(put("/api/project-verticals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectVertical)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectVertical in the database
        List<ProjectVertical> projectVerticalList = projectVerticalRepository.findAll();
        assertThat(projectVerticalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectVertical() throws Exception {
        // Initialize the database
        projectVerticalService.save(projectVertical);

        int databaseSizeBeforeDelete = projectVerticalRepository.findAll().size();

        // Delete the projectVertical
        restProjectVerticalMockMvc.perform(delete("/api/project-verticals/{id}", projectVertical.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectVertical> projectVerticalList = projectVerticalRepository.findAll();
        assertThat(projectVerticalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectVertical.class);
        ProjectVertical projectVertical1 = new ProjectVertical();
        projectVertical1.setId(1L);
        ProjectVertical projectVertical2 = new ProjectVertical();
        projectVertical2.setId(projectVertical1.getId());
        assertThat(projectVertical1).isEqualTo(projectVertical2);
        projectVertical2.setId(2L);
        assertThat(projectVertical1).isNotEqualTo(projectVertical2);
        projectVertical1.setId(null);
        assertThat(projectVertical1).isNotEqualTo(projectVertical2);
    }
}
