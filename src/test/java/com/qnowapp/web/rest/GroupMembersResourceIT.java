package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.GroupMembers;
import com.qnowapp.domain.Roles;
import com.qnowapp.domain.UserContact;
import com.qnowapp.repository.GroupMembersRepository;
import com.qnowapp.service.GroupMembersService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.GroupMembersCriteria;
import com.qnowapp.service.GroupMembersQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.qnowapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link GroupMembersResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class GroupMembersResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private GroupMembersRepository groupMembersRepository;

    @Mock
    private GroupMembersRepository groupMembersRepositoryMock;

    @Mock
    private GroupMembersService groupMembersServiceMock;

    @Autowired
    private GroupMembersService groupMembersService;

    @Autowired
    private GroupMembersQueryService groupMembersQueryService;

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

    private MockMvc restGroupMembersMockMvc;

    private GroupMembers groupMembers;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupMembersResource groupMembersResource = new GroupMembersResource(groupMembersService, groupMembersQueryService);
        this.restGroupMembersMockMvc = MockMvcBuilders.standaloneSetup(groupMembersResource)
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
    public static GroupMembers createEntity(EntityManager em) {
        GroupMembers groupMembers = new GroupMembers()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return groupMembers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupMembers createUpdatedEntity(EntityManager em) {
        GroupMembers groupMembers = new GroupMembers()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return groupMembers;
    }

    @BeforeEach
    public void initTest() {
        groupMembers = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupMembers() throws Exception {
        int databaseSizeBeforeCreate = groupMembersRepository.findAll().size();

        // Create the GroupMembers
        restGroupMembersMockMvc.perform(post("/api/group-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupMembers)))
            .andExpect(status().isCreated());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeCreate + 1);
        GroupMembers testGroupMembers = groupMembersList.get(groupMembersList.size() - 1);
        assertThat(testGroupMembers.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGroupMembers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroupMembers.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGroupMembersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupMembersRepository.findAll().size();

        // Create the GroupMembers with an existing ID
        groupMembers.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupMembersMockMvc.perform(post("/api/group-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupMembers)))
            .andExpect(status().isBadRequest());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGroupMembers() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get all the groupMembersList
        restGroupMembersMockMvc.perform(get("/api/group-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupMembers.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGroupMembersWithEagerRelationshipsIsEnabled() throws Exception {
        GroupMembersResource groupMembersResource = new GroupMembersResource(groupMembersServiceMock, groupMembersQueryService);
        when(groupMembersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restGroupMembersMockMvc = MockMvcBuilders.standaloneSetup(groupMembersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGroupMembersMockMvc.perform(get("/api/group-members?eagerload=true"))
        .andExpect(status().isOk());

        verify(groupMembersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGroupMembersWithEagerRelationshipsIsNotEnabled() throws Exception {
        GroupMembersResource groupMembersResource = new GroupMembersResource(groupMembersServiceMock, groupMembersQueryService);
            when(groupMembersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restGroupMembersMockMvc = MockMvcBuilders.standaloneSetup(groupMembersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGroupMembersMockMvc.perform(get("/api/group-members?eagerload=true"))
        .andExpect(status().isOk());

            verify(groupMembersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGroupMembers() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get the groupMembers
        restGroupMembersMockMvc.perform(get("/api/group-members/{id}", groupMembers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupMembers.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllGroupMembersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get all the groupMembersList where code equals to DEFAULT_CODE
        defaultGroupMembersShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the groupMembersList where code equals to UPDATED_CODE
        defaultGroupMembersShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGroupMembersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get all the groupMembersList where code in DEFAULT_CODE or UPDATED_CODE
        defaultGroupMembersShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the groupMembersList where code equals to UPDATED_CODE
        defaultGroupMembersShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGroupMembersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get all the groupMembersList where code is not null
        defaultGroupMembersShouldBeFound("code.specified=true");

        // Get all the groupMembersList where code is null
        defaultGroupMembersShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupMembersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get all the groupMembersList where name equals to DEFAULT_NAME
        defaultGroupMembersShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the groupMembersList where name equals to UPDATED_NAME
        defaultGroupMembersShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGroupMembersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get all the groupMembersList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGroupMembersShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the groupMembersList where name equals to UPDATED_NAME
        defaultGroupMembersShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGroupMembersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get all the groupMembersList where name is not null
        defaultGroupMembersShouldBeFound("name.specified=true");

        // Get all the groupMembersList where name is null
        defaultGroupMembersShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupMembersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get all the groupMembersList where description equals to DEFAULT_DESCRIPTION
        defaultGroupMembersShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the groupMembersList where description equals to UPDATED_DESCRIPTION
        defaultGroupMembersShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGroupMembersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get all the groupMembersList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultGroupMembersShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the groupMembersList where description equals to UPDATED_DESCRIPTION
        defaultGroupMembersShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGroupMembersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupMembersRepository.saveAndFlush(groupMembers);

        // Get all the groupMembersList where description is not null
        defaultGroupMembersShouldBeFound("description.specified=true");

        // Get all the groupMembersList where description is null
        defaultGroupMembersShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupMembersByRolesIsEqualToSomething() throws Exception {
        // Initialize the database
        Roles roles = RolesResourceIT.createEntity(em);
        em.persist(roles);
        em.flush();
        groupMembers.addRoles(roles);
        groupMembersRepository.saveAndFlush(groupMembers);
        Long rolesId = roles.getId();

        // Get all the groupMembersList where roles equals to rolesId
        defaultGroupMembersShouldBeFound("rolesId.equals=" + rolesId);

        // Get all the groupMembersList where roles equals to rolesId + 1
        defaultGroupMembersShouldNotBeFound("rolesId.equals=" + (rolesId + 1));
    }


    @Test
    @Transactional
    public void getAllGroupMembersByUserContactIsEqualToSomething() throws Exception {
        // Initialize the database
        UserContact userContact = UserContactResourceIT.createEntity(em);
        em.persist(userContact);
        em.flush();
        groupMembers.addUserContact(userContact);
        groupMembersRepository.saveAndFlush(groupMembers);
        Long userContactId = userContact.getId();

        // Get all the groupMembersList where userContact equals to userContactId
        defaultGroupMembersShouldBeFound("userContactId.equals=" + userContactId);

        // Get all the groupMembersList where userContact equals to userContactId + 1
        defaultGroupMembersShouldNotBeFound("userContactId.equals=" + (userContactId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGroupMembersShouldBeFound(String filter) throws Exception {
        restGroupMembersMockMvc.perform(get("/api/group-members?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupMembers.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restGroupMembersMockMvc.perform(get("/api/group-members/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGroupMembersShouldNotBeFound(String filter) throws Exception {
        restGroupMembersMockMvc.perform(get("/api/group-members?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGroupMembersMockMvc.perform(get("/api/group-members/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingGroupMembers() throws Exception {
        // Get the groupMembers
        restGroupMembersMockMvc.perform(get("/api/group-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupMembers() throws Exception {
        // Initialize the database
        groupMembersService.save(groupMembers);

        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();

        // Update the groupMembers
        GroupMembers updatedGroupMembers = groupMembersRepository.findById(groupMembers.getId()).get();
        // Disconnect from session so that the updates on updatedGroupMembers are not directly saved in db
        em.detach(updatedGroupMembers);
        updatedGroupMembers
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restGroupMembersMockMvc.perform(put("/api/group-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupMembers)))
            .andExpect(status().isOk());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate);
        GroupMembers testGroupMembers = groupMembersList.get(groupMembersList.size() - 1);
        assertThat(testGroupMembers.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGroupMembers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroupMembers.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupMembers() throws Exception {
        int databaseSizeBeforeUpdate = groupMembersRepository.findAll().size();

        // Create the GroupMembers

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupMembersMockMvc.perform(put("/api/group-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupMembers)))
            .andExpect(status().isBadRequest());

        // Validate the GroupMembers in the database
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupMembers() throws Exception {
        // Initialize the database
        groupMembersService.save(groupMembers);

        int databaseSizeBeforeDelete = groupMembersRepository.findAll().size();

        // Delete the groupMembers
        restGroupMembersMockMvc.perform(delete("/api/group-members/{id}", groupMembers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<GroupMembers> groupMembersList = groupMembersRepository.findAll();
        assertThat(groupMembersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupMembers.class);
        GroupMembers groupMembers1 = new GroupMembers();
        groupMembers1.setId(1L);
        GroupMembers groupMembers2 = new GroupMembers();
        groupMembers2.setId(groupMembers1.getId());
        assertThat(groupMembers1).isEqualTo(groupMembers2);
        groupMembers2.setId(2L);
        assertThat(groupMembers1).isNotEqualTo(groupMembers2);
        groupMembers1.setId(null);
        assertThat(groupMembers1).isNotEqualTo(groupMembers2);
    }
}
