package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectTheme;
import com.qnowapp.repository.ProjectThemeRepository;
import com.qnowapp.service.ProjectThemeService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectThemeCriteria;
import com.qnowapp.service.ProjectThemeQueryService;

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
 * Integration tests for the {@Link ProjectThemeResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectThemeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectThemeRepository projectThemeRepository;

    @Autowired
    private ProjectThemeService projectThemeService;

    @Autowired
    private ProjectThemeQueryService projectThemeQueryService;

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

    private MockMvc restProjectThemeMockMvc;

    private ProjectTheme projectTheme;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectThemeResource projectThemeResource = new ProjectThemeResource( projectThemeService, projectThemeQueryService);
        this.restProjectThemeMockMvc = MockMvcBuilders.standaloneSetup(projectThemeResource)
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
    public static ProjectTheme createEntity(EntityManager em) {
        ProjectTheme projectTheme = new ProjectTheme()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectTheme;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTheme createUpdatedEntity(EntityManager em) {
        ProjectTheme projectTheme = new ProjectTheme()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectTheme;
    }

    @BeforeEach
    public void initTest() {
        projectTheme = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectTheme() throws Exception {
        int databaseSizeBeforeCreate = projectThemeRepository.findAll().size();

        // Create the ProjectTheme
        restProjectThemeMockMvc.perform(post("/api/project-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTheme)))
            .andExpect(status().isCreated());

        // Validate the ProjectTheme in the database
        List<ProjectTheme> projectThemeList = projectThemeRepository.findAll();
        assertThat(projectThemeList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectTheme testProjectTheme = projectThemeList.get(projectThemeList.size() - 1);
        assertThat(testProjectTheme.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectTheme.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectTheme.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectThemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectThemeRepository.findAll().size();

        // Create the ProjectTheme with an existing ID
        projectTheme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectThemeMockMvc.perform(post("/api/project-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTheme)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTheme in the database
        List<ProjectTheme> projectThemeList = projectThemeRepository.findAll();
        assertThat(projectThemeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectThemes() throws Exception {
        // Initialize the database
        projectThemeRepository.saveAndFlush(projectTheme);

        // Get all the projectThemeList
        restProjectThemeMockMvc.perform(get("/api/project-themes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectTheme() throws Exception {
        // Initialize the database
        projectThemeRepository.saveAndFlush(projectTheme);

        // Get the projectTheme
        restProjectThemeMockMvc.perform(get("/api/project-themes/{id}", projectTheme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectTheme.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectThemesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectThemeRepository.saveAndFlush(projectTheme);

        // Get all the projectThemeList where code equals to DEFAULT_CODE
        defaultProjectThemeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectThemeList where code equals to UPDATED_CODE
        defaultProjectThemeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectThemesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectThemeRepository.saveAndFlush(projectTheme);

        // Get all the projectThemeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectThemeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectThemeList where code equals to UPDATED_CODE
        defaultProjectThemeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectThemesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectThemeRepository.saveAndFlush(projectTheme);

        // Get all the projectThemeList where code is not null
        defaultProjectThemeShouldBeFound("code.specified=true");

        // Get all the projectThemeList where code is null
        defaultProjectThemeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectThemesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectThemeRepository.saveAndFlush(projectTheme);

        // Get all the projectThemeList where name equals to DEFAULT_NAME
        defaultProjectThemeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectThemeList where name equals to UPDATED_NAME
        defaultProjectThemeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectThemesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectThemeRepository.saveAndFlush(projectTheme);

        // Get all the projectThemeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectThemeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectThemeList where name equals to UPDATED_NAME
        defaultProjectThemeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectThemesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectThemeRepository.saveAndFlush(projectTheme);

        // Get all the projectThemeList where name is not null
        defaultProjectThemeShouldBeFound("name.specified=true");

        // Get all the projectThemeList where name is null
        defaultProjectThemeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectThemesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectThemeRepository.saveAndFlush(projectTheme);

        // Get all the projectThemeList where description equals to DEFAULT_DESCRIPTION
        defaultProjectThemeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectThemeList where description equals to UPDATED_DESCRIPTION
        defaultProjectThemeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectThemesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectThemeRepository.saveAndFlush(projectTheme);

        // Get all the projectThemeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectThemeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectThemeList where description equals to UPDATED_DESCRIPTION
        defaultProjectThemeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectThemesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectThemeRepository.saveAndFlush(projectTheme);

        // Get all the projectThemeList where description is not null
        defaultProjectThemeShouldBeFound("description.specified=true");

        // Get all the projectThemeList where description is null
        defaultProjectThemeShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectThemeShouldBeFound(String filter) throws Exception {
        restProjectThemeMockMvc.perform(get("/api/project-themes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTheme.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectThemeMockMvc.perform(get("/api/project-themes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectThemeShouldNotBeFound(String filter) throws Exception {
        restProjectThemeMockMvc.perform(get("/api/project-themes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectThemeMockMvc.perform(get("/api/project-themes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectTheme() throws Exception {
        // Get the projectTheme
        restProjectThemeMockMvc.perform(get("/api/project-themes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectTheme() throws Exception {
        // Initialize the database
        projectThemeService.save(projectTheme);

        int databaseSizeBeforeUpdate = projectThemeRepository.findAll().size();

        // Update the projectTheme
        ProjectTheme updatedProjectTheme = projectThemeRepository.findById(projectTheme.getId()).get();
        // Disconnect from session so that the updates on updatedProjectTheme are not directly saved in db
        em.detach(updatedProjectTheme);
        updatedProjectTheme
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectThemeMockMvc.perform(put("/api/project-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectTheme)))
            .andExpect(status().isOk());

        // Validate the ProjectTheme in the database
        List<ProjectTheme> projectThemeList = projectThemeRepository.findAll();
        assertThat(projectThemeList).hasSize(databaseSizeBeforeUpdate);
        ProjectTheme testProjectTheme = projectThemeList.get(projectThemeList.size() - 1);
        assertThat(testProjectTheme.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectTheme.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectTheme.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectTheme() throws Exception {
        int databaseSizeBeforeUpdate = projectThemeRepository.findAll().size();

        // Create the ProjectTheme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectThemeMockMvc.perform(put("/api/project-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTheme)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTheme in the database
        List<ProjectTheme> projectThemeList = projectThemeRepository.findAll();
        assertThat(projectThemeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectTheme() throws Exception {
        // Initialize the database
        projectThemeService.save(projectTheme);

        int databaseSizeBeforeDelete = projectThemeRepository.findAll().size();

        // Delete the projectTheme
        restProjectThemeMockMvc.perform(delete("/api/project-themes/{id}", projectTheme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectTheme> projectThemeList = projectThemeRepository.findAll();
        assertThat(projectThemeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTheme.class);
        ProjectTheme projectTheme1 = new ProjectTheme();
        projectTheme1.setId(1L);
        ProjectTheme projectTheme2 = new ProjectTheme();
        projectTheme2.setId(projectTheme1.getId());
        assertThat(projectTheme1).isEqualTo(projectTheme2);
        projectTheme2.setId(2L);
        assertThat(projectTheme1).isNotEqualTo(projectTheme2);
        projectTheme1.setId(null);
        assertThat(projectTheme1).isNotEqualTo(projectTheme2);
    }
}
