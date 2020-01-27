package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectRoles;
import com.qnowapp.repository.ProjectRolesRepository;
import com.qnowapp.service.ProjectRolesService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectRolesCriteria;
import com.qnowapp.service.ProjectRolesQueryService;

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
 * Integration tests for the {@Link ProjectRolesResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectRolesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProjectRolesRepository projectRolesRepository;

    @Autowired
    private ProjectRolesService projectRolesService;

    @Autowired
    private ProjectRolesQueryService projectRolesQueryService;

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

    private MockMvc restProjectRolesMockMvc;

    private ProjectRoles projectRoles;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectRolesResource projectRolesResource = new ProjectRolesResource( projectRolesService, projectRolesQueryService);
        this.restProjectRolesMockMvc = MockMvcBuilders.standaloneSetup(projectRolesResource)
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
    public static ProjectRoles createEntity(EntityManager em) {
        ProjectRoles projectRoles = new ProjectRoles()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return projectRoles;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectRoles createUpdatedEntity(EntityManager em) {
        ProjectRoles projectRoles = new ProjectRoles()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return projectRoles;
    }

    @BeforeEach
    public void initTest() {
        projectRoles = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectRoles() throws Exception {
        int databaseSizeBeforeCreate = projectRolesRepository.findAll().size();

        // Create the ProjectRoles
        restProjectRolesMockMvc.perform(post("/api/project-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectRoles)))
            .andExpect(status().isCreated());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectRoles testProjectRoles = projectRolesList.get(projectRolesList.size() - 1);
        assertThat(testProjectRoles.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectRoles.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectRoles.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProjectRolesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectRolesRepository.findAll().size();

        // Create the ProjectRoles with an existing ID
        projectRoles.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectRolesMockMvc.perform(post("/api/project-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectRoles)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectRoles() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList
        restProjectRolesMockMvc.perform(get("/api/project-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectRoles.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectRoles() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get the projectRoles
        restProjectRolesMockMvc.perform(get("/api/project-roles/{id}", projectRoles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectRoles.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectRolesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where code equals to DEFAULT_CODE
        defaultProjectRolesShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectRolesList where code equals to UPDATED_CODE
        defaultProjectRolesShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectRolesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectRolesShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectRolesList where code equals to UPDATED_CODE
        defaultProjectRolesShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectRolesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where code is not null
        defaultProjectRolesShouldBeFound("code.specified=true");

        // Get all the projectRolesList where code is null
        defaultProjectRolesShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectRolesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where name equals to DEFAULT_NAME
        defaultProjectRolesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectRolesList where name equals to UPDATED_NAME
        defaultProjectRolesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectRolesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectRolesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectRolesList where name equals to UPDATED_NAME
        defaultProjectRolesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectRolesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where name is not null
        defaultProjectRolesShouldBeFound("name.specified=true");

        // Get all the projectRolesList where name is null
        defaultProjectRolesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectRolesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where description equals to DEFAULT_DESCRIPTION
        defaultProjectRolesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectRolesList where description equals to UPDATED_DESCRIPTION
        defaultProjectRolesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectRolesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectRolesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectRolesList where description equals to UPDATED_DESCRIPTION
        defaultProjectRolesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectRolesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectRolesRepository.saveAndFlush(projectRoles);

        // Get all the projectRolesList where description is not null
        defaultProjectRolesShouldBeFound("description.specified=true");

        // Get all the projectRolesList where description is null
        defaultProjectRolesShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectRolesShouldBeFound(String filter) throws Exception {
        restProjectRolesMockMvc.perform(get("/api/project-roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectRoles.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restProjectRolesMockMvc.perform(get("/api/project-roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectRolesShouldNotBeFound(String filter) throws Exception {
        restProjectRolesMockMvc.perform(get("/api/project-roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectRolesMockMvc.perform(get("/api/project-roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectRoles() throws Exception {
        // Get the projectRoles
        restProjectRolesMockMvc.perform(get("/api/project-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectRoles() throws Exception {
        // Initialize the database
        projectRolesService.save(projectRoles);

        int databaseSizeBeforeUpdate = projectRolesRepository.findAll().size();

        // Update the projectRoles
        ProjectRoles updatedProjectRoles = projectRolesRepository.findById(projectRoles.getId()).get();
        // Disconnect from session so that the updates on updatedProjectRoles are not directly saved in db
        em.detach(updatedProjectRoles);
        updatedProjectRoles
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProjectRolesMockMvc.perform(put("/api/project-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectRoles)))
            .andExpect(status().isOk());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeUpdate);
        ProjectRoles testProjectRoles = projectRolesList.get(projectRolesList.size() - 1);
        assertThat(testProjectRoles.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectRoles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectRoles.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectRoles() throws Exception {
        int databaseSizeBeforeUpdate = projectRolesRepository.findAll().size();

        // Create the ProjectRoles

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectRolesMockMvc.perform(put("/api/project-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectRoles)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectRoles in the database
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectRoles() throws Exception {
        // Initialize the database
        projectRolesService.save(projectRoles);

        int databaseSizeBeforeDelete = projectRolesRepository.findAll().size();

        // Delete the projectRoles
        restProjectRolesMockMvc.perform(delete("/api/project-roles/{id}", projectRoles.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectRoles> projectRolesList = projectRolesRepository.findAll();
        assertThat(projectRolesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectRoles.class);
        ProjectRoles projectRoles1 = new ProjectRoles();
        projectRoles1.setId(1L);
        ProjectRoles projectRoles2 = new ProjectRoles();
        projectRoles2.setId(projectRoles1.getId());
        assertThat(projectRoles1).isEqualTo(projectRoles2);
        projectRoles2.setId(2L);
        assertThat(projectRoles1).isNotEqualTo(projectRoles2);
        projectRoles1.setId(null);
        assertThat(projectRoles1).isNotEqualTo(projectRoles2);
    }
}
