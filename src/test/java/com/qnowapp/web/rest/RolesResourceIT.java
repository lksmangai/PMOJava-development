package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.Roles;
import com.qnowapp.domain.GroupMembers;
import com.qnowapp.repository.RolesRepository;
import com.qnowapp.service.RolesService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.RolesCriteria;
import com.qnowapp.service.RolesQueryService;

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
 * Integration tests for the {@Link RolesResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class RolesResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private RolesQueryService rolesQueryService;

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

    private MockMvc restRolesMockMvc;

    private Roles roles;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RolesResource rolesResource = new RolesResource(rolesService, rolesQueryService);
        this.restRolesMockMvc = MockMvcBuilders.standaloneSetup(rolesResource)
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
    public static Roles createEntity(EntityManager em) {
        Roles roles = new Roles()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return roles;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Roles createUpdatedEntity(EntityManager em) {
        Roles roles = new Roles()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return roles;
    }

    @BeforeEach
    public void initTest() {
        roles = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoles() throws Exception {
        int databaseSizeBeforeCreate = rolesRepository.findAll().size();

        // Create the Roles
        restRolesMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roles)))
            .andExpect(status().isCreated());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeCreate + 1);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRoles.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRoles.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRolesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rolesRepository.findAll().size();

        // Create the Roles with an existing ID
        roles.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolesMockMvc.perform(post("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roles)))
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList
        restRolesMockMvc.perform(get("/api/roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roles.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getRoles() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get the roles
        restRolesMockMvc.perform(get("/api/roles/{id}", roles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roles.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllRolesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where code equals to DEFAULT_CODE
        defaultRolesShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the rolesList where code equals to UPDATED_CODE
        defaultRolesShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllRolesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where code in DEFAULT_CODE or UPDATED_CODE
        defaultRolesShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the rolesList where code equals to UPDATED_CODE
        defaultRolesShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllRolesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where code is not null
        defaultRolesShouldBeFound("code.specified=true");

        // Get all the rolesList where code is null
        defaultRolesShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where name equals to DEFAULT_NAME
        defaultRolesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the rolesList where name equals to UPDATED_NAME
        defaultRolesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRolesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the rolesList where name equals to UPDATED_NAME
        defaultRolesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where name is not null
        defaultRolesShouldBeFound("name.specified=true");

        // Get all the rolesList where name is null
        defaultRolesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where description equals to DEFAULT_DESCRIPTION
        defaultRolesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the rolesList where description equals to UPDATED_DESCRIPTION
        defaultRolesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRolesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultRolesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the rolesList where description equals to UPDATED_DESCRIPTION
        defaultRolesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRolesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        rolesRepository.saveAndFlush(roles);

        // Get all the rolesList where description is not null
        defaultRolesShouldBeFound("description.specified=true");

        // Get all the rolesList where description is null
        defaultRolesShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllRolesByGroupMembersIsEqualToSomething() throws Exception {
        // Initialize the database
        GroupMembers groupMembers = GroupMembersResourceIT.createEntity(em);
        em.persist(groupMembers);
        em.flush();
        roles.addGroupMembers(groupMembers);
        rolesRepository.saveAndFlush(roles);
        Long groupMembersId = groupMembers.getId();

        // Get all the rolesList where groupMembers equals to groupMembersId
        defaultRolesShouldBeFound("groupMembersId.equals=" + groupMembersId);

        // Get all the rolesList where groupMembers equals to groupMembersId + 1
        defaultRolesShouldNotBeFound("groupMembersId.equals=" + (groupMembersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRolesShouldBeFound(String filter) throws Exception {
        restRolesMockMvc.perform(get("/api/roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roles.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restRolesMockMvc.perform(get("/api/roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRolesShouldNotBeFound(String filter) throws Exception {
        restRolesMockMvc.perform(get("/api/roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRolesMockMvc.perform(get("/api/roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRoles() throws Exception {
        // Get the roles
        restRolesMockMvc.perform(get("/api/roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoles() throws Exception {
        // Initialize the database
        rolesService.save(roles);

        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();

        // Update the roles
        Roles updatedRoles = rolesRepository.findById(roles.getId()).get();
        // Disconnect from session so that the updates on updatedRoles are not directly saved in db
        em.detach(updatedRoles);
        updatedRoles
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restRolesMockMvc.perform(put("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoles)))
            .andExpect(status().isOk());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
        Roles testRoles = rolesList.get(rolesList.size() - 1);
        assertThat(testRoles.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRoles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRoles.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRoles() throws Exception {
        int databaseSizeBeforeUpdate = rolesRepository.findAll().size();

        // Create the Roles

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolesMockMvc.perform(put("/api/roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roles)))
            .andExpect(status().isBadRequest());

        // Validate the Roles in the database
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoles() throws Exception {
        // Initialize the database
        rolesService.save(roles);

        int databaseSizeBeforeDelete = rolesRepository.findAll().size();

        // Delete the roles
        restRolesMockMvc.perform(delete("/api/roles/{id}", roles.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Roles> rolesList = rolesRepository.findAll();
        assertThat(rolesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Roles.class);
        Roles roles1 = new Roles();
        roles1.setId(1L);
        Roles roles2 = new Roles();
        roles2.setId(roles1.getId());
        assertThat(roles1).isEqualTo(roles2);
        roles2.setId(2L);
        assertThat(roles1).isNotEqualTo(roles2);
        roles1.setId(null);
        assertThat(roles1).isNotEqualTo(roles2);
    }
}
