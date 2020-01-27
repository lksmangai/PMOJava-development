package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.UserContact;
import com.qnowapp.domain.QnowUser;
import com.qnowapp.domain.City;
import com.qnowapp.domain.StateMaster;
import com.qnowapp.domain.Country;
import com.qnowapp.domain.GroupMembers;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.repository.UserContactRepository;
import com.qnowapp.service.UserContactService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.UserContactCriteria;
import com.qnowapp.service.UserContactQueryService;

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
 * Integration tests for the {@Link UserContactResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class UserContactResourceIT {

    private static final String DEFAULT_HOME_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_HOME_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_WORK_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CELL_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CELL_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PERMENT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_PERMENT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_HA_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_HA_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_HA_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_HA_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_HA_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_HA_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_WA_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_WA_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_WA_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_WA_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_WA_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_WA_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_UC_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_UC_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_SECONDARY_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_INITIATIVE = "AAAAAAAAAA";
    private static final String UPDATED_INITIATIVE = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNOLOGY = "AAAAAAAAAA";
    private static final String UPDATED_TECHNOLOGY = "BBBBBBBBBB";

    private static final String DEFAULT_TEAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_NAME = "BBBBBBBBBB";

    @Autowired
    private UserContactRepository userContactRepository;

    @Mock
    private UserContactRepository userContactRepositoryMock;

    @Mock
    private UserContactService userContactServiceMock;

    @Autowired
    private UserContactService userContactService;

    @Autowired
    private UserContactQueryService userContactQueryService;

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

    private MockMvc restUserContactMockMvc;

    private UserContact userContact;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserContactResource userContactResource = new UserContactResource(userContactService, userContactQueryService);
        this.restUserContactMockMvc = MockMvcBuilders.standaloneSetup(userContactResource)
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
    public static UserContact createEntity(EntityManager em) {
        UserContact userContact = new UserContact()
            .homePhone(DEFAULT_HOME_PHONE)
            .workPhone(DEFAULT_WORK_PHONE)
            .cellPhone(DEFAULT_CELL_PHONE)
            .permentAddress(DEFAULT_PERMENT_ADDRESS)
            .haLine1(DEFAULT_HA_LINE_1)
            .haLine2(DEFAULT_HA_LINE_2)
            .haPostal(DEFAULT_HA_POSTAL)
            .waLine1(DEFAULT_WA_LINE_1)
            .waLine2(DEFAULT_WA_LINE_2)
            .waPostal(DEFAULT_WA_POSTAL)
            .ucNote(DEFAULT_UC_NOTE)
            .primaryRole(DEFAULT_PRIMARY_ROLE)
            .secondaryRole(DEFAULT_SECONDARY_ROLE)
            .initiative(DEFAULT_INITIATIVE)
            .technology(DEFAULT_TECHNOLOGY)
            .teamName(DEFAULT_TEAM_NAME);
        return userContact;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserContact createUpdatedEntity(EntityManager em) {
        UserContact userContact = new UserContact()
            .homePhone(UPDATED_HOME_PHONE)
            .workPhone(UPDATED_WORK_PHONE)
            .cellPhone(UPDATED_CELL_PHONE)
            .permentAddress(UPDATED_PERMENT_ADDRESS)
            .haLine1(UPDATED_HA_LINE_1)
            .haLine2(UPDATED_HA_LINE_2)
            .haPostal(UPDATED_HA_POSTAL)
            .waLine1(UPDATED_WA_LINE_1)
            .waLine2(UPDATED_WA_LINE_2)
            .waPostal(UPDATED_WA_POSTAL)
            .ucNote(UPDATED_UC_NOTE)
            .primaryRole(UPDATED_PRIMARY_ROLE)
            .secondaryRole(UPDATED_SECONDARY_ROLE)
            .initiative(UPDATED_INITIATIVE)
            .technology(UPDATED_TECHNOLOGY)
            .teamName(UPDATED_TEAM_NAME);
        return userContact;
    }

    @BeforeEach
    public void initTest() {
        userContact = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserContact() throws Exception {
        int databaseSizeBeforeCreate = userContactRepository.findAll().size();

        // Create the UserContact
        restUserContactMockMvc.perform(post("/api/user-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userContact)))
            .andExpect(status().isCreated());

        // Validate the UserContact in the database
        List<UserContact> userContactList = userContactRepository.findAll();
        assertThat(userContactList).hasSize(databaseSizeBeforeCreate + 1);
        UserContact testUserContact = userContactList.get(userContactList.size() - 1);
        assertThat(testUserContact.getHomePhone()).isEqualTo(DEFAULT_HOME_PHONE);
        assertThat(testUserContact.getWorkPhone()).isEqualTo(DEFAULT_WORK_PHONE);
        assertThat(testUserContact.getCellPhone()).isEqualTo(DEFAULT_CELL_PHONE);
        assertThat(testUserContact.getPermentAddress()).isEqualTo(DEFAULT_PERMENT_ADDRESS);
        assertThat(testUserContact.getHaLine1()).isEqualTo(DEFAULT_HA_LINE_1);
        assertThat(testUserContact.getHaLine2()).isEqualTo(DEFAULT_HA_LINE_2);
        assertThat(testUserContact.getHaPostal()).isEqualTo(DEFAULT_HA_POSTAL);
        assertThat(testUserContact.getWaLine1()).isEqualTo(DEFAULT_WA_LINE_1);
        assertThat(testUserContact.getWaLine2()).isEqualTo(DEFAULT_WA_LINE_2);
        assertThat(testUserContact.getWaPostal()).isEqualTo(DEFAULT_WA_POSTAL);
        assertThat(testUserContact.getUcNote()).isEqualTo(DEFAULT_UC_NOTE);
        assertThat(testUserContact.getPrimaryRole()).isEqualTo(DEFAULT_PRIMARY_ROLE);
        assertThat(testUserContact.getSecondaryRole()).isEqualTo(DEFAULT_SECONDARY_ROLE);
        assertThat(testUserContact.getInitiative()).isEqualTo(DEFAULT_INITIATIVE);
        assertThat(testUserContact.getTechnology()).isEqualTo(DEFAULT_TECHNOLOGY);
        assertThat(testUserContact.getTeamName()).isEqualTo(DEFAULT_TEAM_NAME);
    }

    @Test
    @Transactional
    public void createUserContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userContactRepository.findAll().size();

        // Create the UserContact with an existing ID
        userContact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserContactMockMvc.perform(post("/api/user-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userContact)))
            .andExpect(status().isBadRequest());

        // Validate the UserContact in the database
        List<UserContact> userContactList = userContactRepository.findAll();
        assertThat(userContactList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserContacts() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList
        restUserContactMockMvc.perform(get("/api/user-contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE.toString())))
            .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE.toString())))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE.toString())))
            .andExpect(jsonPath("$.[*].permentAddress").value(hasItem(DEFAULT_PERMENT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].haLine1").value(hasItem(DEFAULT_HA_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].haLine2").value(hasItem(DEFAULT_HA_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].haPostal").value(hasItem(DEFAULT_HA_POSTAL.toString())))
            .andExpect(jsonPath("$.[*].waLine1").value(hasItem(DEFAULT_WA_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].waLine2").value(hasItem(DEFAULT_WA_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].waPostal").value(hasItem(DEFAULT_WA_POSTAL.toString())))
            .andExpect(jsonPath("$.[*].ucNote").value(hasItem(DEFAULT_UC_NOTE.toString())))
            .andExpect(jsonPath("$.[*].primaryRole").value(hasItem(DEFAULT_PRIMARY_ROLE.toString())))
            .andExpect(jsonPath("$.[*].secondaryRole").value(hasItem(DEFAULT_SECONDARY_ROLE.toString())))
            .andExpect(jsonPath("$.[*].initiative").value(hasItem(DEFAULT_INITIATIVE.toString())))
            .andExpect(jsonPath("$.[*].technology").value(hasItem(DEFAULT_TECHNOLOGY.toString())))
            .andExpect(jsonPath("$.[*].teamName").value(hasItem(DEFAULT_TEAM_NAME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUserContactsWithEagerRelationshipsIsEnabled() throws Exception {
        UserContactResource userContactResource = new UserContactResource(userContactServiceMock, userContactQueryService);
        when(userContactServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUserContactMockMvc = MockMvcBuilders.standaloneSetup(userContactResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserContactMockMvc.perform(get("/api/user-contacts?eagerload=true"))
        .andExpect(status().isOk());

        verify(userContactServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUserContactsWithEagerRelationshipsIsNotEnabled() throws Exception {
        UserContactResource userContactResource = new UserContactResource(userContactServiceMock, userContactQueryService);
            when(userContactServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUserContactMockMvc = MockMvcBuilders.standaloneSetup(userContactResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUserContactMockMvc.perform(get("/api/user-contacts?eagerload=true"))
        .andExpect(status().isOk());

            verify(userContactServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUserContact() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get the userContact
        restUserContactMockMvc.perform(get("/api/user-contacts/{id}", userContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userContact.getId().intValue()))
            .andExpect(jsonPath("$.homePhone").value(DEFAULT_HOME_PHONE.toString()))
            .andExpect(jsonPath("$.workPhone").value(DEFAULT_WORK_PHONE.toString()))
            .andExpect(jsonPath("$.cellPhone").value(DEFAULT_CELL_PHONE.toString()))
            .andExpect(jsonPath("$.permentAddress").value(DEFAULT_PERMENT_ADDRESS.toString()))
            .andExpect(jsonPath("$.haLine1").value(DEFAULT_HA_LINE_1.toString()))
            .andExpect(jsonPath("$.haLine2").value(DEFAULT_HA_LINE_2.toString()))
            .andExpect(jsonPath("$.haPostal").value(DEFAULT_HA_POSTAL.toString()))
            .andExpect(jsonPath("$.waLine1").value(DEFAULT_WA_LINE_1.toString()))
            .andExpect(jsonPath("$.waLine2").value(DEFAULT_WA_LINE_2.toString()))
            .andExpect(jsonPath("$.waPostal").value(DEFAULT_WA_POSTAL.toString()))
            .andExpect(jsonPath("$.ucNote").value(DEFAULT_UC_NOTE.toString()))
            .andExpect(jsonPath("$.primaryRole").value(DEFAULT_PRIMARY_ROLE.toString()))
            .andExpect(jsonPath("$.secondaryRole").value(DEFAULT_SECONDARY_ROLE.toString()))
            .andExpect(jsonPath("$.initiative").value(DEFAULT_INITIATIVE.toString()))
            .andExpect(jsonPath("$.technology").value(DEFAULT_TECHNOLOGY.toString()))
            .andExpect(jsonPath("$.teamName").value(DEFAULT_TEAM_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllUserContactsByHomePhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where homePhone equals to DEFAULT_HOME_PHONE
        defaultUserContactShouldBeFound("homePhone.equals=" + DEFAULT_HOME_PHONE);

        // Get all the userContactList where homePhone equals to UPDATED_HOME_PHONE
        defaultUserContactShouldNotBeFound("homePhone.equals=" + UPDATED_HOME_PHONE);
    }

    @Test
    @Transactional
    public void getAllUserContactsByHomePhoneIsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where homePhone in DEFAULT_HOME_PHONE or UPDATED_HOME_PHONE
        defaultUserContactShouldBeFound("homePhone.in=" + DEFAULT_HOME_PHONE + "," + UPDATED_HOME_PHONE);

        // Get all the userContactList where homePhone equals to UPDATED_HOME_PHONE
        defaultUserContactShouldNotBeFound("homePhone.in=" + UPDATED_HOME_PHONE);
    }

    @Test
    @Transactional
    public void getAllUserContactsByHomePhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where homePhone is not null
        defaultUserContactShouldBeFound("homePhone.specified=true");

        // Get all the userContactList where homePhone is null
        defaultUserContactShouldNotBeFound("homePhone.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByWorkPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where workPhone equals to DEFAULT_WORK_PHONE
        defaultUserContactShouldBeFound("workPhone.equals=" + DEFAULT_WORK_PHONE);

        // Get all the userContactList where workPhone equals to UPDATED_WORK_PHONE
        defaultUserContactShouldNotBeFound("workPhone.equals=" + UPDATED_WORK_PHONE);
    }

    @Test
    @Transactional
    public void getAllUserContactsByWorkPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where workPhone in DEFAULT_WORK_PHONE or UPDATED_WORK_PHONE
        defaultUserContactShouldBeFound("workPhone.in=" + DEFAULT_WORK_PHONE + "," + UPDATED_WORK_PHONE);

        // Get all the userContactList where workPhone equals to UPDATED_WORK_PHONE
        defaultUserContactShouldNotBeFound("workPhone.in=" + UPDATED_WORK_PHONE);
    }

    @Test
    @Transactional
    public void getAllUserContactsByWorkPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where workPhone is not null
        defaultUserContactShouldBeFound("workPhone.specified=true");

        // Get all the userContactList where workPhone is null
        defaultUserContactShouldNotBeFound("workPhone.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByCellPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where cellPhone equals to DEFAULT_CELL_PHONE
        defaultUserContactShouldBeFound("cellPhone.equals=" + DEFAULT_CELL_PHONE);

        // Get all the userContactList where cellPhone equals to UPDATED_CELL_PHONE
        defaultUserContactShouldNotBeFound("cellPhone.equals=" + UPDATED_CELL_PHONE);
    }

    @Test
    @Transactional
    public void getAllUserContactsByCellPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where cellPhone in DEFAULT_CELL_PHONE or UPDATED_CELL_PHONE
        defaultUserContactShouldBeFound("cellPhone.in=" + DEFAULT_CELL_PHONE + "," + UPDATED_CELL_PHONE);

        // Get all the userContactList where cellPhone equals to UPDATED_CELL_PHONE
        defaultUserContactShouldNotBeFound("cellPhone.in=" + UPDATED_CELL_PHONE);
    }

    @Test
    @Transactional
    public void getAllUserContactsByCellPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where cellPhone is not null
        defaultUserContactShouldBeFound("cellPhone.specified=true");

        // Get all the userContactList where cellPhone is null
        defaultUserContactShouldNotBeFound("cellPhone.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByPermentAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where permentAddress equals to DEFAULT_PERMENT_ADDRESS
        defaultUserContactShouldBeFound("permentAddress.equals=" + DEFAULT_PERMENT_ADDRESS);

        // Get all the userContactList where permentAddress equals to UPDATED_PERMENT_ADDRESS
        defaultUserContactShouldNotBeFound("permentAddress.equals=" + UPDATED_PERMENT_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllUserContactsByPermentAddressIsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where permentAddress in DEFAULT_PERMENT_ADDRESS or UPDATED_PERMENT_ADDRESS
        defaultUserContactShouldBeFound("permentAddress.in=" + DEFAULT_PERMENT_ADDRESS + "," + UPDATED_PERMENT_ADDRESS);

        // Get all the userContactList where permentAddress equals to UPDATED_PERMENT_ADDRESS
        defaultUserContactShouldNotBeFound("permentAddress.in=" + UPDATED_PERMENT_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllUserContactsByPermentAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where permentAddress is not null
        defaultUserContactShouldBeFound("permentAddress.specified=true");

        // Get all the userContactList where permentAddress is null
        defaultUserContactShouldNotBeFound("permentAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByHaLine1IsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where haLine1 equals to DEFAULT_HA_LINE_1
        defaultUserContactShouldBeFound("haLine1.equals=" + DEFAULT_HA_LINE_1);

        // Get all the userContactList where haLine1 equals to UPDATED_HA_LINE_1
        defaultUserContactShouldNotBeFound("haLine1.equals=" + UPDATED_HA_LINE_1);
    }

    @Test
    @Transactional
    public void getAllUserContactsByHaLine1IsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where haLine1 in DEFAULT_HA_LINE_1 or UPDATED_HA_LINE_1
        defaultUserContactShouldBeFound("haLine1.in=" + DEFAULT_HA_LINE_1 + "," + UPDATED_HA_LINE_1);

        // Get all the userContactList where haLine1 equals to UPDATED_HA_LINE_1
        defaultUserContactShouldNotBeFound("haLine1.in=" + UPDATED_HA_LINE_1);
    }

    @Test
    @Transactional
    public void getAllUserContactsByHaLine1IsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where haLine1 is not null
        defaultUserContactShouldBeFound("haLine1.specified=true");

        // Get all the userContactList where haLine1 is null
        defaultUserContactShouldNotBeFound("haLine1.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByHaLine2IsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where haLine2 equals to DEFAULT_HA_LINE_2
        defaultUserContactShouldBeFound("haLine2.equals=" + DEFAULT_HA_LINE_2);

        // Get all the userContactList where haLine2 equals to UPDATED_HA_LINE_2
        defaultUserContactShouldNotBeFound("haLine2.equals=" + UPDATED_HA_LINE_2);
    }

    @Test
    @Transactional
    public void getAllUserContactsByHaLine2IsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where haLine2 in DEFAULT_HA_LINE_2 or UPDATED_HA_LINE_2
        defaultUserContactShouldBeFound("haLine2.in=" + DEFAULT_HA_LINE_2 + "," + UPDATED_HA_LINE_2);

        // Get all the userContactList where haLine2 equals to UPDATED_HA_LINE_2
        defaultUserContactShouldNotBeFound("haLine2.in=" + UPDATED_HA_LINE_2);
    }

    @Test
    @Transactional
    public void getAllUserContactsByHaLine2IsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where haLine2 is not null
        defaultUserContactShouldBeFound("haLine2.specified=true");

        // Get all the userContactList where haLine2 is null
        defaultUserContactShouldNotBeFound("haLine2.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByHaPostalIsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where haPostal equals to DEFAULT_HA_POSTAL
        defaultUserContactShouldBeFound("haPostal.equals=" + DEFAULT_HA_POSTAL);

        // Get all the userContactList where haPostal equals to UPDATED_HA_POSTAL
        defaultUserContactShouldNotBeFound("haPostal.equals=" + UPDATED_HA_POSTAL);
    }

    @Test
    @Transactional
    public void getAllUserContactsByHaPostalIsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where haPostal in DEFAULT_HA_POSTAL or UPDATED_HA_POSTAL
        defaultUserContactShouldBeFound("haPostal.in=" + DEFAULT_HA_POSTAL + "," + UPDATED_HA_POSTAL);

        // Get all the userContactList where haPostal equals to UPDATED_HA_POSTAL
        defaultUserContactShouldNotBeFound("haPostal.in=" + UPDATED_HA_POSTAL);
    }

    @Test
    @Transactional
    public void getAllUserContactsByHaPostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where haPostal is not null
        defaultUserContactShouldBeFound("haPostal.specified=true");

        // Get all the userContactList where haPostal is null
        defaultUserContactShouldNotBeFound("haPostal.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByWaLine1IsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where waLine1 equals to DEFAULT_WA_LINE_1
        defaultUserContactShouldBeFound("waLine1.equals=" + DEFAULT_WA_LINE_1);

        // Get all the userContactList where waLine1 equals to UPDATED_WA_LINE_1
        defaultUserContactShouldNotBeFound("waLine1.equals=" + UPDATED_WA_LINE_1);
    }

    @Test
    @Transactional
    public void getAllUserContactsByWaLine1IsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where waLine1 in DEFAULT_WA_LINE_1 or UPDATED_WA_LINE_1
        defaultUserContactShouldBeFound("waLine1.in=" + DEFAULT_WA_LINE_1 + "," + UPDATED_WA_LINE_1);

        // Get all the userContactList where waLine1 equals to UPDATED_WA_LINE_1
        defaultUserContactShouldNotBeFound("waLine1.in=" + UPDATED_WA_LINE_1);
    }

    @Test
    @Transactional
    public void getAllUserContactsByWaLine1IsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where waLine1 is not null
        defaultUserContactShouldBeFound("waLine1.specified=true");

        // Get all the userContactList where waLine1 is null
        defaultUserContactShouldNotBeFound("waLine1.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByWaLine2IsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where waLine2 equals to DEFAULT_WA_LINE_2
        defaultUserContactShouldBeFound("waLine2.equals=" + DEFAULT_WA_LINE_2);

        // Get all the userContactList where waLine2 equals to UPDATED_WA_LINE_2
        defaultUserContactShouldNotBeFound("waLine2.equals=" + UPDATED_WA_LINE_2);
    }

    @Test
    @Transactional
    public void getAllUserContactsByWaLine2IsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where waLine2 in DEFAULT_WA_LINE_2 or UPDATED_WA_LINE_2
        defaultUserContactShouldBeFound("waLine2.in=" + DEFAULT_WA_LINE_2 + "," + UPDATED_WA_LINE_2);

        // Get all the userContactList where waLine2 equals to UPDATED_WA_LINE_2
        defaultUserContactShouldNotBeFound("waLine2.in=" + UPDATED_WA_LINE_2);
    }

    @Test
    @Transactional
    public void getAllUserContactsByWaLine2IsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where waLine2 is not null
        defaultUserContactShouldBeFound("waLine2.specified=true");

        // Get all the userContactList where waLine2 is null
        defaultUserContactShouldNotBeFound("waLine2.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByWaPostalIsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where waPostal equals to DEFAULT_WA_POSTAL
        defaultUserContactShouldBeFound("waPostal.equals=" + DEFAULT_WA_POSTAL);

        // Get all the userContactList where waPostal equals to UPDATED_WA_POSTAL
        defaultUserContactShouldNotBeFound("waPostal.equals=" + UPDATED_WA_POSTAL);
    }

    @Test
    @Transactional
    public void getAllUserContactsByWaPostalIsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where waPostal in DEFAULT_WA_POSTAL or UPDATED_WA_POSTAL
        defaultUserContactShouldBeFound("waPostal.in=" + DEFAULT_WA_POSTAL + "," + UPDATED_WA_POSTAL);

        // Get all the userContactList where waPostal equals to UPDATED_WA_POSTAL
        defaultUserContactShouldNotBeFound("waPostal.in=" + UPDATED_WA_POSTAL);
    }

    @Test
    @Transactional
    public void getAllUserContactsByWaPostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where waPostal is not null
        defaultUserContactShouldBeFound("waPostal.specified=true");

        // Get all the userContactList where waPostal is null
        defaultUserContactShouldNotBeFound("waPostal.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByUcNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where ucNote equals to DEFAULT_UC_NOTE
        defaultUserContactShouldBeFound("ucNote.equals=" + DEFAULT_UC_NOTE);

        // Get all the userContactList where ucNote equals to UPDATED_UC_NOTE
        defaultUserContactShouldNotBeFound("ucNote.equals=" + UPDATED_UC_NOTE);
    }

    @Test
    @Transactional
    public void getAllUserContactsByUcNoteIsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where ucNote in DEFAULT_UC_NOTE or UPDATED_UC_NOTE
        defaultUserContactShouldBeFound("ucNote.in=" + DEFAULT_UC_NOTE + "," + UPDATED_UC_NOTE);

        // Get all the userContactList where ucNote equals to UPDATED_UC_NOTE
        defaultUserContactShouldNotBeFound("ucNote.in=" + UPDATED_UC_NOTE);
    }

    @Test
    @Transactional
    public void getAllUserContactsByUcNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where ucNote is not null
        defaultUserContactShouldBeFound("ucNote.specified=true");

        // Get all the userContactList where ucNote is null
        defaultUserContactShouldNotBeFound("ucNote.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByPrimaryRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where primaryRole equals to DEFAULT_PRIMARY_ROLE
        defaultUserContactShouldBeFound("primaryRole.equals=" + DEFAULT_PRIMARY_ROLE);

        // Get all the userContactList where primaryRole equals to UPDATED_PRIMARY_ROLE
        defaultUserContactShouldNotBeFound("primaryRole.equals=" + UPDATED_PRIMARY_ROLE);
    }

    @Test
    @Transactional
    public void getAllUserContactsByPrimaryRoleIsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where primaryRole in DEFAULT_PRIMARY_ROLE or UPDATED_PRIMARY_ROLE
        defaultUserContactShouldBeFound("primaryRole.in=" + DEFAULT_PRIMARY_ROLE + "," + UPDATED_PRIMARY_ROLE);

        // Get all the userContactList where primaryRole equals to UPDATED_PRIMARY_ROLE
        defaultUserContactShouldNotBeFound("primaryRole.in=" + UPDATED_PRIMARY_ROLE);
    }

    @Test
    @Transactional
    public void getAllUserContactsByPrimaryRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where primaryRole is not null
        defaultUserContactShouldBeFound("primaryRole.specified=true");

        // Get all the userContactList where primaryRole is null
        defaultUserContactShouldNotBeFound("primaryRole.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsBySecondaryRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where secondaryRole equals to DEFAULT_SECONDARY_ROLE
        defaultUserContactShouldBeFound("secondaryRole.equals=" + DEFAULT_SECONDARY_ROLE);

        // Get all the userContactList where secondaryRole equals to UPDATED_SECONDARY_ROLE
        defaultUserContactShouldNotBeFound("secondaryRole.equals=" + UPDATED_SECONDARY_ROLE);
    }

    @Test
    @Transactional
    public void getAllUserContactsBySecondaryRoleIsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where secondaryRole in DEFAULT_SECONDARY_ROLE or UPDATED_SECONDARY_ROLE
        defaultUserContactShouldBeFound("secondaryRole.in=" + DEFAULT_SECONDARY_ROLE + "," + UPDATED_SECONDARY_ROLE);

        // Get all the userContactList where secondaryRole equals to UPDATED_SECONDARY_ROLE
        defaultUserContactShouldNotBeFound("secondaryRole.in=" + UPDATED_SECONDARY_ROLE);
    }

    @Test
    @Transactional
    public void getAllUserContactsBySecondaryRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where secondaryRole is not null
        defaultUserContactShouldBeFound("secondaryRole.specified=true");

        // Get all the userContactList where secondaryRole is null
        defaultUserContactShouldNotBeFound("secondaryRole.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByInitiativeIsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where initiative equals to DEFAULT_INITIATIVE
        defaultUserContactShouldBeFound("initiative.equals=" + DEFAULT_INITIATIVE);

        // Get all the userContactList where initiative equals to UPDATED_INITIATIVE
        defaultUserContactShouldNotBeFound("initiative.equals=" + UPDATED_INITIATIVE);
    }

    @Test
    @Transactional
    public void getAllUserContactsByInitiativeIsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where initiative in DEFAULT_INITIATIVE or UPDATED_INITIATIVE
        defaultUserContactShouldBeFound("initiative.in=" + DEFAULT_INITIATIVE + "," + UPDATED_INITIATIVE);

        // Get all the userContactList where initiative equals to UPDATED_INITIATIVE
        defaultUserContactShouldNotBeFound("initiative.in=" + UPDATED_INITIATIVE);
    }

    @Test
    @Transactional
    public void getAllUserContactsByInitiativeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where initiative is not null
        defaultUserContactShouldBeFound("initiative.specified=true");

        // Get all the userContactList where initiative is null
        defaultUserContactShouldNotBeFound("initiative.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByTechnologyIsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where technology equals to DEFAULT_TECHNOLOGY
        defaultUserContactShouldBeFound("technology.equals=" + DEFAULT_TECHNOLOGY);

        // Get all the userContactList where technology equals to UPDATED_TECHNOLOGY
        defaultUserContactShouldNotBeFound("technology.equals=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    public void getAllUserContactsByTechnologyIsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where technology in DEFAULT_TECHNOLOGY or UPDATED_TECHNOLOGY
        defaultUserContactShouldBeFound("technology.in=" + DEFAULT_TECHNOLOGY + "," + UPDATED_TECHNOLOGY);

        // Get all the userContactList where technology equals to UPDATED_TECHNOLOGY
        defaultUserContactShouldNotBeFound("technology.in=" + UPDATED_TECHNOLOGY);
    }

    @Test
    @Transactional
    public void getAllUserContactsByTechnologyIsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where technology is not null
        defaultUserContactShouldBeFound("technology.specified=true");

        // Get all the userContactList where technology is null
        defaultUserContactShouldNotBeFound("technology.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByTeamNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where teamName equals to DEFAULT_TEAM_NAME
        defaultUserContactShouldBeFound("teamName.equals=" + DEFAULT_TEAM_NAME);

        // Get all the userContactList where teamName equals to UPDATED_TEAM_NAME
        defaultUserContactShouldNotBeFound("teamName.equals=" + UPDATED_TEAM_NAME);
    }

    @Test
    @Transactional
    public void getAllUserContactsByTeamNameIsInShouldWork() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where teamName in DEFAULT_TEAM_NAME or UPDATED_TEAM_NAME
        defaultUserContactShouldBeFound("teamName.in=" + DEFAULT_TEAM_NAME + "," + UPDATED_TEAM_NAME);

        // Get all the userContactList where teamName equals to UPDATED_TEAM_NAME
        defaultUserContactShouldNotBeFound("teamName.in=" + UPDATED_TEAM_NAME);
    }

    @Test
    @Transactional
    public void getAllUserContactsByTeamNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userContactRepository.saveAndFlush(userContact);

        // Get all the userContactList where teamName is not null
        defaultUserContactShouldBeFound("teamName.specified=true");

        // Get all the userContactList where teamName is null
        defaultUserContactShouldNotBeFound("teamName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserContactsByQnowUserIsEqualToSomething() throws Exception {
        // Initialize the database
        QnowUser qnowUser = QnowUserResourceIT.createEntity(em);
        em.persist(qnowUser);
        em.flush();
        userContact.setQnowUser(qnowUser);
        userContactRepository.saveAndFlush(userContact);
        Long qnowUserId = qnowUser.getId();

        // Get all the userContactList where qnowUser equals to qnowUserId
        defaultUserContactShouldBeFound("qnowUserId.equals=" + qnowUserId);

        // Get all the userContactList where qnowUser equals to qnowUserId + 1
        defaultUserContactShouldNotBeFound("qnowUserId.equals=" + (qnowUserId + 1));
    }


    @Test
    @Transactional
    public void getAllUserContactsByWaCityIsEqualToSomething() throws Exception {
        // Initialize the database
        City waCity = CityResourceIT.createEntity(em);
        em.persist(waCity);
        em.flush();
        userContact.setWaCity(waCity);
        userContactRepository.saveAndFlush(userContact);
        Long waCityId = waCity.getId();

        // Get all the userContactList where waCity equals to waCityId
        defaultUserContactShouldBeFound("waCityId.equals=" + waCityId);

        // Get all the userContactList where waCity equals to waCityId + 1
        defaultUserContactShouldNotBeFound("waCityId.equals=" + (waCityId + 1));
    }


    @Test
    @Transactional
    public void getAllUserContactsByHaCityIsEqualToSomething() throws Exception {
        // Initialize the database
        City haCity = CityResourceIT.createEntity(em);
        em.persist(haCity);
        em.flush();
        userContact.setHaCity(haCity);
        userContactRepository.saveAndFlush(userContact);
        Long haCityId = haCity.getId();

        // Get all the userContactList where haCity equals to haCityId
        defaultUserContactShouldBeFound("haCityId.equals=" + haCityId);

        // Get all the userContactList where haCity equals to haCityId + 1
        defaultUserContactShouldNotBeFound("haCityId.equals=" + (haCityId + 1));
    }


    @Test
    @Transactional
    public void getAllUserContactsByWaStateIsEqualToSomething() throws Exception {
        // Initialize the database
        StateMaster waState = StateMasterResourceIT.createEntity(em);
        em.persist(waState);
        em.flush();
        userContact.setWaState(waState);
        userContactRepository.saveAndFlush(userContact);
        Long waStateId = waState.getId();

        // Get all the userContactList where waState equals to waStateId
        defaultUserContactShouldBeFound("waStateId.equals=" + waStateId);

        // Get all the userContactList where waState equals to waStateId + 1
        defaultUserContactShouldNotBeFound("waStateId.equals=" + (waStateId + 1));
    }


    @Test
    @Transactional
    public void getAllUserContactsByHaStateIsEqualToSomething() throws Exception {
        // Initialize the database
        StateMaster haState = StateMasterResourceIT.createEntity(em);
        em.persist(haState);
        em.flush();
        userContact.setHaState(haState);
        userContactRepository.saveAndFlush(userContact);
        Long haStateId = haState.getId();

        // Get all the userContactList where haState equals to haStateId
        defaultUserContactShouldBeFound("haStateId.equals=" + haStateId);

        // Get all the userContactList where haState equals to haStateId + 1
        defaultUserContactShouldNotBeFound("haStateId.equals=" + (haStateId + 1));
    }


    @Test
    @Transactional
    public void getAllUserContactsByWaCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        Country waCountry = CountryResourceIT.createEntity(em);
        em.persist(waCountry);
        em.flush();
        userContact.setWaCountry(waCountry);
        userContactRepository.saveAndFlush(userContact);
        Long waCountryId = waCountry.getId();

        // Get all the userContactList where waCountry equals to waCountryId
        defaultUserContactShouldBeFound("waCountryId.equals=" + waCountryId);

        // Get all the userContactList where waCountry equals to waCountryId + 1
        defaultUserContactShouldNotBeFound("waCountryId.equals=" + (waCountryId + 1));
    }


    @Test
    @Transactional
    public void getAllUserContactsByHaCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        Country haCountry = CountryResourceIT.createEntity(em);
        em.persist(haCountry);
        em.flush();
        userContact.setHaCountry(haCountry);
        userContactRepository.saveAndFlush(userContact);
        Long haCountryId = haCountry.getId();

        // Get all the userContactList where haCountry equals to haCountryId
        defaultUserContactShouldBeFound("haCountryId.equals=" + haCountryId);

        // Get all the userContactList where haCountry equals to haCountryId + 1
        defaultUserContactShouldNotBeFound("haCountryId.equals=" + (haCountryId + 1));
    }


    @Test
    @Transactional
    public void getAllUserContactsByGroupMembersIsEqualToSomething() throws Exception {
        // Initialize the database
        GroupMembers groupMembers = GroupMembersResourceIT.createEntity(em);
        em.persist(groupMembers);
        em.flush();
        userContact.addGroupMembers(groupMembers);
        userContactRepository.saveAndFlush(userContact);
        Long groupMembersId = groupMembers.getId();

        // Get all the userContactList where groupMembers equals to groupMembersId
        defaultUserContactShouldBeFound("groupMembersId.equals=" + groupMembersId);

        // Get all the userContactList where groupMembers equals to groupMembersId + 1
        defaultUserContactShouldNotBeFound("groupMembersId.equals=" + (groupMembersId + 1));
    }


    @Test
    @Transactional
    public void getAllUserContactsByImEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        ImEmployee imEmployee = ImEmployeeResourceIT.createEntity(em);
        em.persist(imEmployee);
        em.flush();
        userContact.addImEmployee(imEmployee);
        userContactRepository.saveAndFlush(userContact);
        Long imEmployeeId = imEmployee.getId();

        // Get all the userContactList where imEmployee equals to imEmployeeId
        defaultUserContactShouldBeFound("imEmployeeId.equals=" + imEmployeeId);

        // Get all the userContactList where imEmployee equals to imEmployeeId + 1
        defaultUserContactShouldNotBeFound("imEmployeeId.equals=" + (imEmployeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserContactShouldBeFound(String filter) throws Exception {
        restUserContactMockMvc.perform(get("/api/user-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE)))
            .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE)))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE)))
            .andExpect(jsonPath("$.[*].permentAddress").value(hasItem(DEFAULT_PERMENT_ADDRESS)))
            .andExpect(jsonPath("$.[*].haLine1").value(hasItem(DEFAULT_HA_LINE_1)))
            .andExpect(jsonPath("$.[*].haLine2").value(hasItem(DEFAULT_HA_LINE_2)))
            .andExpect(jsonPath("$.[*].haPostal").value(hasItem(DEFAULT_HA_POSTAL)))
            .andExpect(jsonPath("$.[*].waLine1").value(hasItem(DEFAULT_WA_LINE_1)))
            .andExpect(jsonPath("$.[*].waLine2").value(hasItem(DEFAULT_WA_LINE_2)))
            .andExpect(jsonPath("$.[*].waPostal").value(hasItem(DEFAULT_WA_POSTAL)))
            .andExpect(jsonPath("$.[*].ucNote").value(hasItem(DEFAULT_UC_NOTE)))
            .andExpect(jsonPath("$.[*].primaryRole").value(hasItem(DEFAULT_PRIMARY_ROLE)))
            .andExpect(jsonPath("$.[*].secondaryRole").value(hasItem(DEFAULT_SECONDARY_ROLE)))
            .andExpect(jsonPath("$.[*].initiative").value(hasItem(DEFAULT_INITIATIVE)))
            .andExpect(jsonPath("$.[*].technology").value(hasItem(DEFAULT_TECHNOLOGY)))
            .andExpect(jsonPath("$.[*].teamName").value(hasItem(DEFAULT_TEAM_NAME)));

        // Check, that the count call also returns 1
        restUserContactMockMvc.perform(get("/api/user-contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserContactShouldNotBeFound(String filter) throws Exception {
        restUserContactMockMvc.perform(get("/api/user-contacts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserContactMockMvc.perform(get("/api/user-contacts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUserContact() throws Exception {
        // Get the userContact
        restUserContactMockMvc.perform(get("/api/user-contacts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserContact() throws Exception {
        // Initialize the database
        userContactService.save(userContact);

        int databaseSizeBeforeUpdate = userContactRepository.findAll().size();

        // Update the userContact
        UserContact updatedUserContact = userContactRepository.findById(userContact.getId()).get();
        // Disconnect from session so that the updates on updatedUserContact are not directly saved in db
        em.detach(updatedUserContact);
        updatedUserContact
            .homePhone(UPDATED_HOME_PHONE)
            .workPhone(UPDATED_WORK_PHONE)
            .cellPhone(UPDATED_CELL_PHONE)
            .permentAddress(UPDATED_PERMENT_ADDRESS)
            .haLine1(UPDATED_HA_LINE_1)
            .haLine2(UPDATED_HA_LINE_2)
            .haPostal(UPDATED_HA_POSTAL)
            .waLine1(UPDATED_WA_LINE_1)
            .waLine2(UPDATED_WA_LINE_2)
            .waPostal(UPDATED_WA_POSTAL)
            .ucNote(UPDATED_UC_NOTE)
            .primaryRole(UPDATED_PRIMARY_ROLE)
            .secondaryRole(UPDATED_SECONDARY_ROLE)
            .initiative(UPDATED_INITIATIVE)
            .technology(UPDATED_TECHNOLOGY)
            .teamName(UPDATED_TEAM_NAME);

        restUserContactMockMvc.perform(put("/api/user-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserContact)))
            .andExpect(status().isOk());

        // Validate the UserContact in the database
        List<UserContact> userContactList = userContactRepository.findAll();
        assertThat(userContactList).hasSize(databaseSizeBeforeUpdate);
        UserContact testUserContact = userContactList.get(userContactList.size() - 1);
        assertThat(testUserContact.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testUserContact.getWorkPhone()).isEqualTo(UPDATED_WORK_PHONE);
        assertThat(testUserContact.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testUserContact.getPermentAddress()).isEqualTo(UPDATED_PERMENT_ADDRESS);
        assertThat(testUserContact.getHaLine1()).isEqualTo(UPDATED_HA_LINE_1);
        assertThat(testUserContact.getHaLine2()).isEqualTo(UPDATED_HA_LINE_2);
        assertThat(testUserContact.getHaPostal()).isEqualTo(UPDATED_HA_POSTAL);
        assertThat(testUserContact.getWaLine1()).isEqualTo(UPDATED_WA_LINE_1);
        assertThat(testUserContact.getWaLine2()).isEqualTo(UPDATED_WA_LINE_2);
        assertThat(testUserContact.getWaPostal()).isEqualTo(UPDATED_WA_POSTAL);
        assertThat(testUserContact.getUcNote()).isEqualTo(UPDATED_UC_NOTE);
        assertThat(testUserContact.getPrimaryRole()).isEqualTo(UPDATED_PRIMARY_ROLE);
        assertThat(testUserContact.getSecondaryRole()).isEqualTo(UPDATED_SECONDARY_ROLE);
        assertThat(testUserContact.getInitiative()).isEqualTo(UPDATED_INITIATIVE);
        assertThat(testUserContact.getTechnology()).isEqualTo(UPDATED_TECHNOLOGY);
        assertThat(testUserContact.getTeamName()).isEqualTo(UPDATED_TEAM_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserContact() throws Exception {
        int databaseSizeBeforeUpdate = userContactRepository.findAll().size();

        // Create the UserContact

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserContactMockMvc.perform(put("/api/user-contacts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userContact)))
            .andExpect(status().isBadRequest());

        // Validate the UserContact in the database
        List<UserContact> userContactList = userContactRepository.findAll();
        assertThat(userContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserContact() throws Exception {
        // Initialize the database
        userContactService.save(userContact);

        int databaseSizeBeforeDelete = userContactRepository.findAll().size();

        // Delete the userContact
        restUserContactMockMvc.perform(delete("/api/user-contacts/{id}", userContact.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<UserContact> userContactList = userContactRepository.findAll();
        assertThat(userContactList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserContact.class);
        UserContact userContact1 = new UserContact();
        userContact1.setId(1L);
        UserContact userContact2 = new UserContact();
        userContact2.setId(userContact1.getId());
        assertThat(userContact1).isEqualTo(userContact2);
        userContact2.setId(2L);
        assertThat(userContact1).isNotEqualTo(userContact2);
        userContact1.setId(null);
        assertThat(userContact1).isNotEqualTo(userContact2);
    }
}
