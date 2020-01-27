package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.QnowUser;
import com.qnowapp.domain.EmployeeStatus;
import com.qnowapp.domain.Department;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.UserContact;
import com.qnowapp.repository.ImEmployeeRepository;
import com.qnowapp.service.ImEmployeeService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ImEmployeeCriteria;
import com.qnowapp.service.ImEmployeeQueryService;

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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.qnowapp.web.rest.TestUtil.sameInstant;
import static com.qnowapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ImEmployeeResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ImEmployeeResourceIT {

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_JOB_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_AVAILABILITY = 1;
    private static final Integer UPDATED_AVAILABILITY = 2;

    private static final String DEFAULT_SS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SS_NUMBER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SALARY = new BigDecimal(1);
    private static final BigDecimal UPDATED_SALARY = new BigDecimal(2);

    private static final Integer DEFAULT_SOCIAL_SECURITY = 1;
    private static final Integer UPDATED_SOCIAL_SECURITY = 2;

    private static final Integer DEFAULT_INSURANCE = 1;
    private static final Integer UPDATED_INSURANCE = 2;

    private static final Integer DEFAULT_OTHER_COSTS = 1;
    private static final Integer UPDATED_OTHER_COSTS = 2;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_DEPENDANT_P = "AAAAAAAAAA";
    private static final String UPDATED_DEPENDANT_P = "BBBBBBBBBB";

    private static final String DEFAULT_ONLY_JOB_P = "AAAAAAAAAA";
    private static final String UPDATED_ONLY_JOB_P = "BBBBBBBBBB";

    private static final String DEFAULT_MARRIED_P = "AAAAAAAAAA";
    private static final String UPDATED_MARRIED_P = "BBBBBBBBBB";

    private static final String DEFAULT_HEAD_OF_HOUSEHOLD_P = "AAAAAAAAAA";
    private static final String UPDATED_HEAD_OF_HOUSEHOLD_P = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_BIRTHDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_BIRTHDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_HOURLY_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_HOURLY_COST = new BigDecimal(2);

    @Autowired
    private ImEmployeeRepository imEmployeeRepository;

    @Mock
    private ImEmployeeRepository imEmployeeRepositoryMock;

    @Mock
    private ImEmployeeService imEmployeeServiceMock;

    @Autowired
    private ImEmployeeService imEmployeeService;

    @Autowired
    private ImEmployeeQueryService imEmployeeQueryService;

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

    private MockMvc restImEmployeeMockMvc;

    private ImEmployee imEmployee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImEmployeeResource imEmployeeResource = new ImEmployeeResource(imEmployeeService, imEmployeeQueryService);
        this.restImEmployeeMockMvc = MockMvcBuilders.standaloneSetup(imEmployeeResource)
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
    public static ImEmployee createEntity(EntityManager em) {
        ImEmployee imEmployee = new ImEmployee()
            .jobTitle(DEFAULT_JOB_TITLE)
            .jobDescription(DEFAULT_JOB_DESCRIPTION)
            .availability(DEFAULT_AVAILABILITY)
            .ssNumber(DEFAULT_SS_NUMBER)
            .salary(DEFAULT_SALARY)
            .socialSecurity(DEFAULT_SOCIAL_SECURITY)
            .insurance(DEFAULT_INSURANCE)
            .otherCosts(DEFAULT_OTHER_COSTS)
            .currency(DEFAULT_CURRENCY)
            .dependantP(DEFAULT_DEPENDANT_P)
            .onlyJobP(DEFAULT_ONLY_JOB_P)
            .marriedP(DEFAULT_MARRIED_P)
            .headOfHouseholdP(DEFAULT_HEAD_OF_HOUSEHOLD_P)
            .birthdate(DEFAULT_BIRTHDATE)
            .hourlyCost(DEFAULT_HOURLY_COST);
        return imEmployee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImEmployee createUpdatedEntity(EntityManager em) {
        ImEmployee imEmployee = new ImEmployee()
            .jobTitle(UPDATED_JOB_TITLE)
            .jobDescription(UPDATED_JOB_DESCRIPTION)
            .availability(UPDATED_AVAILABILITY)
            .ssNumber(UPDATED_SS_NUMBER)
            .salary(UPDATED_SALARY)
            .socialSecurity(UPDATED_SOCIAL_SECURITY)
            .insurance(UPDATED_INSURANCE)
            .otherCosts(UPDATED_OTHER_COSTS)
            .currency(UPDATED_CURRENCY)
            .dependantP(UPDATED_DEPENDANT_P)
            .onlyJobP(UPDATED_ONLY_JOB_P)
            .marriedP(UPDATED_MARRIED_P)
            .headOfHouseholdP(UPDATED_HEAD_OF_HOUSEHOLD_P)
            .birthdate(UPDATED_BIRTHDATE)
            .hourlyCost(UPDATED_HOURLY_COST);
        return imEmployee;
    }

    @BeforeEach
    public void initTest() {
        imEmployee = createEntity(em);
    }

    @Test
    @Transactional
    public void createImEmployee() throws Exception {
        int databaseSizeBeforeCreate = imEmployeeRepository.findAll().size();

        // Create the ImEmployee
        restImEmployeeMockMvc.perform(post("/api/im-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imEmployee)))
            .andExpect(status().isCreated());

        // Validate the ImEmployee in the database
        List<ImEmployee> imEmployeeList = imEmployeeRepository.findAll();
        assertThat(imEmployeeList).hasSize(databaseSizeBeforeCreate + 1);
        ImEmployee testImEmployee = imEmployeeList.get(imEmployeeList.size() - 1);
        assertThat(testImEmployee.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testImEmployee.getJobDescription()).isEqualTo(DEFAULT_JOB_DESCRIPTION);
        assertThat(testImEmployee.getAvailability()).isEqualTo(DEFAULT_AVAILABILITY);
        assertThat(testImEmployee.getSsNumber()).isEqualTo(DEFAULT_SS_NUMBER);
        assertThat(testImEmployee.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testImEmployee.getSocialSecurity()).isEqualTo(DEFAULT_SOCIAL_SECURITY);
        assertThat(testImEmployee.getInsurance()).isEqualTo(DEFAULT_INSURANCE);
        assertThat(testImEmployee.getOtherCosts()).isEqualTo(DEFAULT_OTHER_COSTS);
        assertThat(testImEmployee.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testImEmployee.getDependantP()).isEqualTo(DEFAULT_DEPENDANT_P);
        assertThat(testImEmployee.getOnlyJobP()).isEqualTo(DEFAULT_ONLY_JOB_P);
        assertThat(testImEmployee.getMarriedP()).isEqualTo(DEFAULT_MARRIED_P);
        assertThat(testImEmployee.getHeadOfHouseholdP()).isEqualTo(DEFAULT_HEAD_OF_HOUSEHOLD_P);
        assertThat(testImEmployee.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testImEmployee.getHourlyCost()).isEqualTo(DEFAULT_HOURLY_COST);
    }

    @Test
    @Transactional
    public void createImEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imEmployeeRepository.findAll().size();

        // Create the ImEmployee with an existing ID
        imEmployee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImEmployeeMockMvc.perform(post("/api/im-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imEmployee)))
            .andExpect(status().isBadRequest());

        // Validate the ImEmployee in the database
        List<ImEmployee> imEmployeeList = imEmployeeRepository.findAll();
        assertThat(imEmployeeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImEmployees() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList
        restImEmployeeMockMvc.perform(get("/api/im-employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imEmployee.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].jobDescription").value(hasItem(DEFAULT_JOB_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY)))
            .andExpect(jsonPath("$.[*].ssNumber").value(hasItem(DEFAULT_SS_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].socialSecurity").value(hasItem(DEFAULT_SOCIAL_SECURITY)))
            .andExpect(jsonPath("$.[*].insurance").value(hasItem(DEFAULT_INSURANCE)))
            .andExpect(jsonPath("$.[*].otherCosts").value(hasItem(DEFAULT_OTHER_COSTS)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].dependantP").value(hasItem(DEFAULT_DEPENDANT_P.toString())))
            .andExpect(jsonPath("$.[*].onlyJobP").value(hasItem(DEFAULT_ONLY_JOB_P.toString())))
            .andExpect(jsonPath("$.[*].marriedP").value(hasItem(DEFAULT_MARRIED_P.toString())))
            .andExpect(jsonPath("$.[*].headOfHouseholdP").value(hasItem(DEFAULT_HEAD_OF_HOUSEHOLD_P.toString())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(sameInstant(DEFAULT_BIRTHDATE))))
            .andExpect(jsonPath("$.[*].hourlyCost").value(hasItem(DEFAULT_HOURLY_COST.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllImEmployeesWithEagerRelationshipsIsEnabled() throws Exception {
        ImEmployeeResource imEmployeeResource = new ImEmployeeResource(imEmployeeServiceMock, imEmployeeQueryService);
        when(imEmployeeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restImEmployeeMockMvc = MockMvcBuilders.standaloneSetup(imEmployeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restImEmployeeMockMvc.perform(get("/api/im-employees?eagerload=true"))
        .andExpect(status().isOk());

        verify(imEmployeeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllImEmployeesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ImEmployeeResource imEmployeeResource = new ImEmployeeResource(imEmployeeServiceMock, imEmployeeQueryService);
            when(imEmployeeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restImEmployeeMockMvc = MockMvcBuilders.standaloneSetup(imEmployeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restImEmployeeMockMvc.perform(get("/api/im-employees?eagerload=true"))
        .andExpect(status().isOk());

            verify(imEmployeeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getImEmployee() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get the imEmployee
        restImEmployeeMockMvc.perform(get("/api/im-employees/{id}", imEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imEmployee.getId().intValue()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE.toString()))
            .andExpect(jsonPath("$.jobDescription").value(DEFAULT_JOB_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.availability").value(DEFAULT_AVAILABILITY))
            .andExpect(jsonPath("$.ssNumber").value(DEFAULT_SS_NUMBER.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.intValue()))
            .andExpect(jsonPath("$.socialSecurity").value(DEFAULT_SOCIAL_SECURITY))
            .andExpect(jsonPath("$.insurance").value(DEFAULT_INSURANCE))
            .andExpect(jsonPath("$.otherCosts").value(DEFAULT_OTHER_COSTS))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.dependantP").value(DEFAULT_DEPENDANT_P.toString()))
            .andExpect(jsonPath("$.onlyJobP").value(DEFAULT_ONLY_JOB_P.toString()))
            .andExpect(jsonPath("$.marriedP").value(DEFAULT_MARRIED_P.toString()))
            .andExpect(jsonPath("$.headOfHouseholdP").value(DEFAULT_HEAD_OF_HOUSEHOLD_P.toString()))
            .andExpect(jsonPath("$.birthdate").value(sameInstant(DEFAULT_BIRTHDATE)))
            .andExpect(jsonPath("$.hourlyCost").value(DEFAULT_HOURLY_COST.intValue()));
    }

    @Test
    @Transactional
    public void getAllImEmployeesByJobTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where jobTitle equals to DEFAULT_JOB_TITLE
        defaultImEmployeeShouldBeFound("jobTitle.equals=" + DEFAULT_JOB_TITLE);

        // Get all the imEmployeeList where jobTitle equals to UPDATED_JOB_TITLE
        defaultImEmployeeShouldNotBeFound("jobTitle.equals=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByJobTitleIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where jobTitle in DEFAULT_JOB_TITLE or UPDATED_JOB_TITLE
        defaultImEmployeeShouldBeFound("jobTitle.in=" + DEFAULT_JOB_TITLE + "," + UPDATED_JOB_TITLE);

        // Get all the imEmployeeList where jobTitle equals to UPDATED_JOB_TITLE
        defaultImEmployeeShouldNotBeFound("jobTitle.in=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByJobTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where jobTitle is not null
        defaultImEmployeeShouldBeFound("jobTitle.specified=true");

        // Get all the imEmployeeList where jobTitle is null
        defaultImEmployeeShouldNotBeFound("jobTitle.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesByJobDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where jobDescription equals to DEFAULT_JOB_DESCRIPTION
        defaultImEmployeeShouldBeFound("jobDescription.equals=" + DEFAULT_JOB_DESCRIPTION);

        // Get all the imEmployeeList where jobDescription equals to UPDATED_JOB_DESCRIPTION
        defaultImEmployeeShouldNotBeFound("jobDescription.equals=" + UPDATED_JOB_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByJobDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where jobDescription in DEFAULT_JOB_DESCRIPTION or UPDATED_JOB_DESCRIPTION
        defaultImEmployeeShouldBeFound("jobDescription.in=" + DEFAULT_JOB_DESCRIPTION + "," + UPDATED_JOB_DESCRIPTION);

        // Get all the imEmployeeList where jobDescription equals to UPDATED_JOB_DESCRIPTION
        defaultImEmployeeShouldNotBeFound("jobDescription.in=" + UPDATED_JOB_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByJobDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where jobDescription is not null
        defaultImEmployeeShouldBeFound("jobDescription.specified=true");

        // Get all the imEmployeeList where jobDescription is null
        defaultImEmployeeShouldNotBeFound("jobDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesByAvailabilityIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where availability equals to DEFAULT_AVAILABILITY
        defaultImEmployeeShouldBeFound("availability.equals=" + DEFAULT_AVAILABILITY);

        // Get all the imEmployeeList where availability equals to UPDATED_AVAILABILITY
        defaultImEmployeeShouldNotBeFound("availability.equals=" + UPDATED_AVAILABILITY);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByAvailabilityIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where availability in DEFAULT_AVAILABILITY or UPDATED_AVAILABILITY
        defaultImEmployeeShouldBeFound("availability.in=" + DEFAULT_AVAILABILITY + "," + UPDATED_AVAILABILITY);

        // Get all the imEmployeeList where availability equals to UPDATED_AVAILABILITY
        defaultImEmployeeShouldNotBeFound("availability.in=" + UPDATED_AVAILABILITY);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByAvailabilityIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where availability is not null
        defaultImEmployeeShouldBeFound("availability.specified=true");

        // Get all the imEmployeeList where availability is null
        defaultImEmployeeShouldNotBeFound("availability.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesByAvailabilityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where availability greater than or equals to DEFAULT_AVAILABILITY
        defaultImEmployeeShouldBeFound("availability.greaterOrEqualThan=" + DEFAULT_AVAILABILITY);

        // Get all the imEmployeeList where availability greater than or equals to UPDATED_AVAILABILITY
        defaultImEmployeeShouldNotBeFound("availability.greaterOrEqualThan=" + UPDATED_AVAILABILITY);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByAvailabilityIsLessThanSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where availability less than or equals to DEFAULT_AVAILABILITY
        defaultImEmployeeShouldNotBeFound("availability.lessThan=" + DEFAULT_AVAILABILITY);

        // Get all the imEmployeeList where availability less than or equals to UPDATED_AVAILABILITY
        defaultImEmployeeShouldBeFound("availability.lessThan=" + UPDATED_AVAILABILITY);
    }


    @Test
    @Transactional
    public void getAllImEmployeesBySsNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where ssNumber equals to DEFAULT_SS_NUMBER
        defaultImEmployeeShouldBeFound("ssNumber.equals=" + DEFAULT_SS_NUMBER);

        // Get all the imEmployeeList where ssNumber equals to UPDATED_SS_NUMBER
        defaultImEmployeeShouldNotBeFound("ssNumber.equals=" + UPDATED_SS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllImEmployeesBySsNumberIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where ssNumber in DEFAULT_SS_NUMBER or UPDATED_SS_NUMBER
        defaultImEmployeeShouldBeFound("ssNumber.in=" + DEFAULT_SS_NUMBER + "," + UPDATED_SS_NUMBER);

        // Get all the imEmployeeList where ssNumber equals to UPDATED_SS_NUMBER
        defaultImEmployeeShouldNotBeFound("ssNumber.in=" + UPDATED_SS_NUMBER);
    }

    @Test
    @Transactional
    public void getAllImEmployeesBySsNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where ssNumber is not null
        defaultImEmployeeShouldBeFound("ssNumber.specified=true");

        // Get all the imEmployeeList where ssNumber is null
        defaultImEmployeeShouldNotBeFound("ssNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesBySalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where salary equals to DEFAULT_SALARY
        defaultImEmployeeShouldBeFound("salary.equals=" + DEFAULT_SALARY);

        // Get all the imEmployeeList where salary equals to UPDATED_SALARY
        defaultImEmployeeShouldNotBeFound("salary.equals=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllImEmployeesBySalaryIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where salary in DEFAULT_SALARY or UPDATED_SALARY
        defaultImEmployeeShouldBeFound("salary.in=" + DEFAULT_SALARY + "," + UPDATED_SALARY);

        // Get all the imEmployeeList where salary equals to UPDATED_SALARY
        defaultImEmployeeShouldNotBeFound("salary.in=" + UPDATED_SALARY);
    }

    @Test
    @Transactional
    public void getAllImEmployeesBySalaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where salary is not null
        defaultImEmployeeShouldBeFound("salary.specified=true");

        // Get all the imEmployeeList where salary is null
        defaultImEmployeeShouldNotBeFound("salary.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesBySocialSecurityIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where socialSecurity equals to DEFAULT_SOCIAL_SECURITY
        defaultImEmployeeShouldBeFound("socialSecurity.equals=" + DEFAULT_SOCIAL_SECURITY);

        // Get all the imEmployeeList where socialSecurity equals to UPDATED_SOCIAL_SECURITY
        defaultImEmployeeShouldNotBeFound("socialSecurity.equals=" + UPDATED_SOCIAL_SECURITY);
    }

    @Test
    @Transactional
    public void getAllImEmployeesBySocialSecurityIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where socialSecurity in DEFAULT_SOCIAL_SECURITY or UPDATED_SOCIAL_SECURITY
        defaultImEmployeeShouldBeFound("socialSecurity.in=" + DEFAULT_SOCIAL_SECURITY + "," + UPDATED_SOCIAL_SECURITY);

        // Get all the imEmployeeList where socialSecurity equals to UPDATED_SOCIAL_SECURITY
        defaultImEmployeeShouldNotBeFound("socialSecurity.in=" + UPDATED_SOCIAL_SECURITY);
    }

    @Test
    @Transactional
    public void getAllImEmployeesBySocialSecurityIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where socialSecurity is not null
        defaultImEmployeeShouldBeFound("socialSecurity.specified=true");

        // Get all the imEmployeeList where socialSecurity is null
        defaultImEmployeeShouldNotBeFound("socialSecurity.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesBySocialSecurityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where socialSecurity greater than or equals to DEFAULT_SOCIAL_SECURITY
        defaultImEmployeeShouldBeFound("socialSecurity.greaterOrEqualThan=" + DEFAULT_SOCIAL_SECURITY);

        // Get all the imEmployeeList where socialSecurity greater than or equals to UPDATED_SOCIAL_SECURITY
        defaultImEmployeeShouldNotBeFound("socialSecurity.greaterOrEqualThan=" + UPDATED_SOCIAL_SECURITY);
    }

    @Test
    @Transactional
    public void getAllImEmployeesBySocialSecurityIsLessThanSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where socialSecurity less than or equals to DEFAULT_SOCIAL_SECURITY
        defaultImEmployeeShouldNotBeFound("socialSecurity.lessThan=" + DEFAULT_SOCIAL_SECURITY);

        // Get all the imEmployeeList where socialSecurity less than or equals to UPDATED_SOCIAL_SECURITY
        defaultImEmployeeShouldBeFound("socialSecurity.lessThan=" + UPDATED_SOCIAL_SECURITY);
    }


    @Test
    @Transactional
    public void getAllImEmployeesByInsuranceIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where insurance equals to DEFAULT_INSURANCE
        defaultImEmployeeShouldBeFound("insurance.equals=" + DEFAULT_INSURANCE);

        // Get all the imEmployeeList where insurance equals to UPDATED_INSURANCE
        defaultImEmployeeShouldNotBeFound("insurance.equals=" + UPDATED_INSURANCE);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByInsuranceIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where insurance in DEFAULT_INSURANCE or UPDATED_INSURANCE
        defaultImEmployeeShouldBeFound("insurance.in=" + DEFAULT_INSURANCE + "," + UPDATED_INSURANCE);

        // Get all the imEmployeeList where insurance equals to UPDATED_INSURANCE
        defaultImEmployeeShouldNotBeFound("insurance.in=" + UPDATED_INSURANCE);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByInsuranceIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where insurance is not null
        defaultImEmployeeShouldBeFound("insurance.specified=true");

        // Get all the imEmployeeList where insurance is null
        defaultImEmployeeShouldNotBeFound("insurance.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesByInsuranceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where insurance greater than or equals to DEFAULT_INSURANCE
        defaultImEmployeeShouldBeFound("insurance.greaterOrEqualThan=" + DEFAULT_INSURANCE);

        // Get all the imEmployeeList where insurance greater than or equals to UPDATED_INSURANCE
        defaultImEmployeeShouldNotBeFound("insurance.greaterOrEqualThan=" + UPDATED_INSURANCE);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByInsuranceIsLessThanSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where insurance less than or equals to DEFAULT_INSURANCE
        defaultImEmployeeShouldNotBeFound("insurance.lessThan=" + DEFAULT_INSURANCE);

        // Get all the imEmployeeList where insurance less than or equals to UPDATED_INSURANCE
        defaultImEmployeeShouldBeFound("insurance.lessThan=" + UPDATED_INSURANCE);
    }


    @Test
    @Transactional
    public void getAllImEmployeesByOtherCostsIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where otherCosts equals to DEFAULT_OTHER_COSTS
        defaultImEmployeeShouldBeFound("otherCosts.equals=" + DEFAULT_OTHER_COSTS);

        // Get all the imEmployeeList where otherCosts equals to UPDATED_OTHER_COSTS
        defaultImEmployeeShouldNotBeFound("otherCosts.equals=" + UPDATED_OTHER_COSTS);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByOtherCostsIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where otherCosts in DEFAULT_OTHER_COSTS or UPDATED_OTHER_COSTS
        defaultImEmployeeShouldBeFound("otherCosts.in=" + DEFAULT_OTHER_COSTS + "," + UPDATED_OTHER_COSTS);

        // Get all the imEmployeeList where otherCosts equals to UPDATED_OTHER_COSTS
        defaultImEmployeeShouldNotBeFound("otherCosts.in=" + UPDATED_OTHER_COSTS);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByOtherCostsIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where otherCosts is not null
        defaultImEmployeeShouldBeFound("otherCosts.specified=true");

        // Get all the imEmployeeList where otherCosts is null
        defaultImEmployeeShouldNotBeFound("otherCosts.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesByOtherCostsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where otherCosts greater than or equals to DEFAULT_OTHER_COSTS
        defaultImEmployeeShouldBeFound("otherCosts.greaterOrEqualThan=" + DEFAULT_OTHER_COSTS);

        // Get all the imEmployeeList where otherCosts greater than or equals to UPDATED_OTHER_COSTS
        defaultImEmployeeShouldNotBeFound("otherCosts.greaterOrEqualThan=" + UPDATED_OTHER_COSTS);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByOtherCostsIsLessThanSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where otherCosts less than or equals to DEFAULT_OTHER_COSTS
        defaultImEmployeeShouldNotBeFound("otherCosts.lessThan=" + DEFAULT_OTHER_COSTS);

        // Get all the imEmployeeList where otherCosts less than or equals to UPDATED_OTHER_COSTS
        defaultImEmployeeShouldBeFound("otherCosts.lessThan=" + UPDATED_OTHER_COSTS);
    }


    @Test
    @Transactional
    public void getAllImEmployeesByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where currency equals to DEFAULT_CURRENCY
        defaultImEmployeeShouldBeFound("currency.equals=" + DEFAULT_CURRENCY);

        // Get all the imEmployeeList where currency equals to UPDATED_CURRENCY
        defaultImEmployeeShouldNotBeFound("currency.equals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where currency in DEFAULT_CURRENCY or UPDATED_CURRENCY
        defaultImEmployeeShouldBeFound("currency.in=" + DEFAULT_CURRENCY + "," + UPDATED_CURRENCY);

        // Get all the imEmployeeList where currency equals to UPDATED_CURRENCY
        defaultImEmployeeShouldNotBeFound("currency.in=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where currency is not null
        defaultImEmployeeShouldBeFound("currency.specified=true");

        // Get all the imEmployeeList where currency is null
        defaultImEmployeeShouldNotBeFound("currency.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesByDependantPIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where dependantP equals to DEFAULT_DEPENDANT_P
        defaultImEmployeeShouldBeFound("dependantP.equals=" + DEFAULT_DEPENDANT_P);

        // Get all the imEmployeeList where dependantP equals to UPDATED_DEPENDANT_P
        defaultImEmployeeShouldNotBeFound("dependantP.equals=" + UPDATED_DEPENDANT_P);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByDependantPIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where dependantP in DEFAULT_DEPENDANT_P or UPDATED_DEPENDANT_P
        defaultImEmployeeShouldBeFound("dependantP.in=" + DEFAULT_DEPENDANT_P + "," + UPDATED_DEPENDANT_P);

        // Get all the imEmployeeList where dependantP equals to UPDATED_DEPENDANT_P
        defaultImEmployeeShouldNotBeFound("dependantP.in=" + UPDATED_DEPENDANT_P);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByDependantPIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where dependantP is not null
        defaultImEmployeeShouldBeFound("dependantP.specified=true");

        // Get all the imEmployeeList where dependantP is null
        defaultImEmployeeShouldNotBeFound("dependantP.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesByOnlyJobPIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where onlyJobP equals to DEFAULT_ONLY_JOB_P
        defaultImEmployeeShouldBeFound("onlyJobP.equals=" + DEFAULT_ONLY_JOB_P);

        // Get all the imEmployeeList where onlyJobP equals to UPDATED_ONLY_JOB_P
        defaultImEmployeeShouldNotBeFound("onlyJobP.equals=" + UPDATED_ONLY_JOB_P);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByOnlyJobPIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where onlyJobP in DEFAULT_ONLY_JOB_P or UPDATED_ONLY_JOB_P
        defaultImEmployeeShouldBeFound("onlyJobP.in=" + DEFAULT_ONLY_JOB_P + "," + UPDATED_ONLY_JOB_P);

        // Get all the imEmployeeList where onlyJobP equals to UPDATED_ONLY_JOB_P
        defaultImEmployeeShouldNotBeFound("onlyJobP.in=" + UPDATED_ONLY_JOB_P);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByOnlyJobPIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where onlyJobP is not null
        defaultImEmployeeShouldBeFound("onlyJobP.specified=true");

        // Get all the imEmployeeList where onlyJobP is null
        defaultImEmployeeShouldNotBeFound("onlyJobP.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesByMarriedPIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where marriedP equals to DEFAULT_MARRIED_P
        defaultImEmployeeShouldBeFound("marriedP.equals=" + DEFAULT_MARRIED_P);

        // Get all the imEmployeeList where marriedP equals to UPDATED_MARRIED_P
        defaultImEmployeeShouldNotBeFound("marriedP.equals=" + UPDATED_MARRIED_P);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByMarriedPIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where marriedP in DEFAULT_MARRIED_P or UPDATED_MARRIED_P
        defaultImEmployeeShouldBeFound("marriedP.in=" + DEFAULT_MARRIED_P + "," + UPDATED_MARRIED_P);

        // Get all the imEmployeeList where marriedP equals to UPDATED_MARRIED_P
        defaultImEmployeeShouldNotBeFound("marriedP.in=" + UPDATED_MARRIED_P);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByMarriedPIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where marriedP is not null
        defaultImEmployeeShouldBeFound("marriedP.specified=true");

        // Get all the imEmployeeList where marriedP is null
        defaultImEmployeeShouldNotBeFound("marriedP.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesByHeadOfHouseholdPIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where headOfHouseholdP equals to DEFAULT_HEAD_OF_HOUSEHOLD_P
        defaultImEmployeeShouldBeFound("headOfHouseholdP.equals=" + DEFAULT_HEAD_OF_HOUSEHOLD_P);

        // Get all the imEmployeeList where headOfHouseholdP equals to UPDATED_HEAD_OF_HOUSEHOLD_P
        defaultImEmployeeShouldNotBeFound("headOfHouseholdP.equals=" + UPDATED_HEAD_OF_HOUSEHOLD_P);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByHeadOfHouseholdPIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where headOfHouseholdP in DEFAULT_HEAD_OF_HOUSEHOLD_P or UPDATED_HEAD_OF_HOUSEHOLD_P
        defaultImEmployeeShouldBeFound("headOfHouseholdP.in=" + DEFAULT_HEAD_OF_HOUSEHOLD_P + "," + UPDATED_HEAD_OF_HOUSEHOLD_P);

        // Get all the imEmployeeList where headOfHouseholdP equals to UPDATED_HEAD_OF_HOUSEHOLD_P
        defaultImEmployeeShouldNotBeFound("headOfHouseholdP.in=" + UPDATED_HEAD_OF_HOUSEHOLD_P);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByHeadOfHouseholdPIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where headOfHouseholdP is not null
        defaultImEmployeeShouldBeFound("headOfHouseholdP.specified=true");

        // Get all the imEmployeeList where headOfHouseholdP is null
        defaultImEmployeeShouldNotBeFound("headOfHouseholdP.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesByBirthdateIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where birthdate equals to DEFAULT_BIRTHDATE
        defaultImEmployeeShouldBeFound("birthdate.equals=" + DEFAULT_BIRTHDATE);

        // Get all the imEmployeeList where birthdate equals to UPDATED_BIRTHDATE
        defaultImEmployeeShouldNotBeFound("birthdate.equals=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByBirthdateIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where birthdate in DEFAULT_BIRTHDATE or UPDATED_BIRTHDATE
        defaultImEmployeeShouldBeFound("birthdate.in=" + DEFAULT_BIRTHDATE + "," + UPDATED_BIRTHDATE);

        // Get all the imEmployeeList where birthdate equals to UPDATED_BIRTHDATE
        defaultImEmployeeShouldNotBeFound("birthdate.in=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByBirthdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where birthdate is not null
        defaultImEmployeeShouldBeFound("birthdate.specified=true");

        // Get all the imEmployeeList where birthdate is null
        defaultImEmployeeShouldNotBeFound("birthdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesByBirthdateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where birthdate greater than or equals to DEFAULT_BIRTHDATE
        defaultImEmployeeShouldBeFound("birthdate.greaterOrEqualThan=" + DEFAULT_BIRTHDATE);

        // Get all the imEmployeeList where birthdate greater than or equals to UPDATED_BIRTHDATE
        defaultImEmployeeShouldNotBeFound("birthdate.greaterOrEqualThan=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByBirthdateIsLessThanSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where birthdate less than or equals to DEFAULT_BIRTHDATE
        defaultImEmployeeShouldNotBeFound("birthdate.lessThan=" + DEFAULT_BIRTHDATE);

        // Get all the imEmployeeList where birthdate less than or equals to UPDATED_BIRTHDATE
        defaultImEmployeeShouldBeFound("birthdate.lessThan=" + UPDATED_BIRTHDATE);
    }


    @Test
    @Transactional
    public void getAllImEmployeesByHourlyCostIsEqualToSomething() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where hourlyCost equals to DEFAULT_HOURLY_COST
        defaultImEmployeeShouldBeFound("hourlyCost.equals=" + DEFAULT_HOURLY_COST);

        // Get all the imEmployeeList where hourlyCost equals to UPDATED_HOURLY_COST
        defaultImEmployeeShouldNotBeFound("hourlyCost.equals=" + UPDATED_HOURLY_COST);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByHourlyCostIsInShouldWork() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where hourlyCost in DEFAULT_HOURLY_COST or UPDATED_HOURLY_COST
        defaultImEmployeeShouldBeFound("hourlyCost.in=" + DEFAULT_HOURLY_COST + "," + UPDATED_HOURLY_COST);

        // Get all the imEmployeeList where hourlyCost equals to UPDATED_HOURLY_COST
        defaultImEmployeeShouldNotBeFound("hourlyCost.in=" + UPDATED_HOURLY_COST);
    }

    @Test
    @Transactional
    public void getAllImEmployeesByHourlyCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        imEmployeeRepository.saveAndFlush(imEmployee);

        // Get all the imEmployeeList where hourlyCost is not null
        defaultImEmployeeShouldBeFound("hourlyCost.specified=true");

        // Get all the imEmployeeList where hourlyCost is null
        defaultImEmployeeShouldNotBeFound("hourlyCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllImEmployeesByQnowUserIsEqualToSomething() throws Exception {
        // Initialize the database
        QnowUser qnowUser = QnowUserResourceIT.createEntity(em);
        em.persist(qnowUser);
        em.flush();
        imEmployee.setQnowUser(qnowUser);
        imEmployeeRepository.saveAndFlush(imEmployee);
        Long qnowUserId = qnowUser.getId();

        // Get all the imEmployeeList where qnowUser equals to qnowUserId
        defaultImEmployeeShouldBeFound("qnowUserId.equals=" + qnowUserId);

        // Get all the imEmployeeList where qnowUser equals to qnowUserId + 1
        defaultImEmployeeShouldNotBeFound("qnowUserId.equals=" + (qnowUserId + 1));
    }


    @Test
    @Transactional
    public void getAllImEmployeesByEmployeeStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        EmployeeStatus employeeStatus = EmployeeStatusResourceIT.createEntity(em);
        em.persist(employeeStatus);
        em.flush();
        imEmployee.setEmployeeStatus(employeeStatus);
        imEmployeeRepository.saveAndFlush(imEmployee);
        Long employeeStatusId = employeeStatus.getId();

        // Get all the imEmployeeList where employeeStatus equals to employeeStatusId
        defaultImEmployeeShouldBeFound("employeeStatusId.equals=" + employeeStatusId);

        // Get all the imEmployeeList where employeeStatus equals to employeeStatusId + 1
        defaultImEmployeeShouldNotBeFound("employeeStatusId.equals=" + (employeeStatusId + 1));
    }


    @Test
    @Transactional
    public void getAllImEmployeesByDepartmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        Department departmentId = DepartmentResourceIT.createEntity(em);
        em.persist(departmentId);
        em.flush();
        imEmployee.setDepartmentId(departmentId);
        imEmployeeRepository.saveAndFlush(imEmployee);
        Long departmentIdId = departmentId.getId();

        // Get all the imEmployeeList where departmentId equals to departmentIdId
        defaultImEmployeeShouldBeFound("departmentIdId.equals=" + departmentIdId);

        // Get all the imEmployeeList where departmentId equals to departmentIdId + 1
        defaultImEmployeeShouldNotBeFound("departmentIdId.equals=" + (departmentIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImEmployeesBySupervisorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ImEmployee supervisorId = ImEmployeeResourceIT.createEntity(em);
        em.persist(supervisorId);
        em.flush();
        imEmployee.setSupervisorId(supervisorId);
        imEmployeeRepository.saveAndFlush(imEmployee);
        Long supervisorIdId = supervisorId.getId();

        // Get all the imEmployeeList where supervisorId equals to supervisorIdId
        defaultImEmployeeShouldBeFound("supervisorIdId.equals=" + supervisorIdId);

        // Get all the imEmployeeList where supervisorId equals to supervisorIdId + 1
        defaultImEmployeeShouldNotBeFound("supervisorIdId.equals=" + (supervisorIdId + 1));
    }


    @Test
    @Transactional
    public void getAllImEmployeesByUserContactIsEqualToSomething() throws Exception {
        // Initialize the database
        UserContact userContact = UserContactResourceIT.createEntity(em);
        em.persist(userContact);
        em.flush();
        imEmployee.addUserContact(userContact);
        imEmployeeRepository.saveAndFlush(imEmployee);
        Long userContactId = userContact.getId();

        // Get all the imEmployeeList where userContact equals to userContactId
        defaultImEmployeeShouldBeFound("userContactId.equals=" + userContactId);

        // Get all the imEmployeeList where userContact equals to userContactId + 1
        defaultImEmployeeShouldNotBeFound("userContactId.equals=" + (userContactId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImEmployeeShouldBeFound(String filter) throws Exception {
        restImEmployeeMockMvc.perform(get("/api/im-employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imEmployee.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].jobDescription").value(hasItem(DEFAULT_JOB_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY)))
            .andExpect(jsonPath("$.[*].ssNumber").value(hasItem(DEFAULT_SS_NUMBER)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].socialSecurity").value(hasItem(DEFAULT_SOCIAL_SECURITY)))
            .andExpect(jsonPath("$.[*].insurance").value(hasItem(DEFAULT_INSURANCE)))
            .andExpect(jsonPath("$.[*].otherCosts").value(hasItem(DEFAULT_OTHER_COSTS)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].dependantP").value(hasItem(DEFAULT_DEPENDANT_P)))
            .andExpect(jsonPath("$.[*].onlyJobP").value(hasItem(DEFAULT_ONLY_JOB_P)))
            .andExpect(jsonPath("$.[*].marriedP").value(hasItem(DEFAULT_MARRIED_P)))
            .andExpect(jsonPath("$.[*].headOfHouseholdP").value(hasItem(DEFAULT_HEAD_OF_HOUSEHOLD_P)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(sameInstant(DEFAULT_BIRTHDATE))))
            .andExpect(jsonPath("$.[*].hourlyCost").value(hasItem(DEFAULT_HOURLY_COST.intValue())));

        // Check, that the count call also returns 1
        restImEmployeeMockMvc.perform(get("/api/im-employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImEmployeeShouldNotBeFound(String filter) throws Exception {
        restImEmployeeMockMvc.perform(get("/api/im-employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImEmployeeMockMvc.perform(get("/api/im-employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingImEmployee() throws Exception {
        // Get the imEmployee
        restImEmployeeMockMvc.perform(get("/api/im-employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImEmployee() throws Exception {
        // Initialize the database
        imEmployeeService.save(imEmployee);

        int databaseSizeBeforeUpdate = imEmployeeRepository.findAll().size();

        // Update the imEmployee
        ImEmployee updatedImEmployee = imEmployeeRepository.findById(imEmployee.getId()).get();
        // Disconnect from session so that the updates on updatedImEmployee are not directly saved in db
        em.detach(updatedImEmployee);
        updatedImEmployee
            .jobTitle(UPDATED_JOB_TITLE)
            .jobDescription(UPDATED_JOB_DESCRIPTION)
            .availability(UPDATED_AVAILABILITY)
            .ssNumber(UPDATED_SS_NUMBER)
            .salary(UPDATED_SALARY)
            .socialSecurity(UPDATED_SOCIAL_SECURITY)
            .insurance(UPDATED_INSURANCE)
            .otherCosts(UPDATED_OTHER_COSTS)
            .currency(UPDATED_CURRENCY)
            .dependantP(UPDATED_DEPENDANT_P)
            .onlyJobP(UPDATED_ONLY_JOB_P)
            .marriedP(UPDATED_MARRIED_P)
            .headOfHouseholdP(UPDATED_HEAD_OF_HOUSEHOLD_P)
            .birthdate(UPDATED_BIRTHDATE)
            .hourlyCost(UPDATED_HOURLY_COST);

        restImEmployeeMockMvc.perform(put("/api/im-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImEmployee)))
            .andExpect(status().isOk());

        // Validate the ImEmployee in the database
        List<ImEmployee> imEmployeeList = imEmployeeRepository.findAll();
        assertThat(imEmployeeList).hasSize(databaseSizeBeforeUpdate);
        ImEmployee testImEmployee = imEmployeeList.get(imEmployeeList.size() - 1);
        assertThat(testImEmployee.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testImEmployee.getJobDescription()).isEqualTo(UPDATED_JOB_DESCRIPTION);
        assertThat(testImEmployee.getAvailability()).isEqualTo(UPDATED_AVAILABILITY);
        assertThat(testImEmployee.getSsNumber()).isEqualTo(UPDATED_SS_NUMBER);
        assertThat(testImEmployee.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testImEmployee.getSocialSecurity()).isEqualTo(UPDATED_SOCIAL_SECURITY);
        assertThat(testImEmployee.getInsurance()).isEqualTo(UPDATED_INSURANCE);
        assertThat(testImEmployee.getOtherCosts()).isEqualTo(UPDATED_OTHER_COSTS);
        assertThat(testImEmployee.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testImEmployee.getDependantP()).isEqualTo(UPDATED_DEPENDANT_P);
        assertThat(testImEmployee.getOnlyJobP()).isEqualTo(UPDATED_ONLY_JOB_P);
        assertThat(testImEmployee.getMarriedP()).isEqualTo(UPDATED_MARRIED_P);
        assertThat(testImEmployee.getHeadOfHouseholdP()).isEqualTo(UPDATED_HEAD_OF_HOUSEHOLD_P);
        assertThat(testImEmployee.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testImEmployee.getHourlyCost()).isEqualTo(UPDATED_HOURLY_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingImEmployee() throws Exception {
        int databaseSizeBeforeUpdate = imEmployeeRepository.findAll().size();

        // Create the ImEmployee

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImEmployeeMockMvc.perform(put("/api/im-employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imEmployee)))
            .andExpect(status().isBadRequest());

        // Validate the ImEmployee in the database
        List<ImEmployee> imEmployeeList = imEmployeeRepository.findAll();
        assertThat(imEmployeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImEmployee() throws Exception {
        // Initialize the database
        imEmployeeService.save(imEmployee);

        int databaseSizeBeforeDelete = imEmployeeRepository.findAll().size();

        // Delete the imEmployee
        restImEmployeeMockMvc.perform(delete("/api/im-employees/{id}", imEmployee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ImEmployee> imEmployeeList = imEmployeeRepository.findAll();
        assertThat(imEmployeeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImEmployee.class);
        ImEmployee imEmployee1 = new ImEmployee();
        imEmployee1.setId(1L);
        ImEmployee imEmployee2 = new ImEmployee();
        imEmployee2.setId(imEmployee1.getId());
        assertThat(imEmployee1).isEqualTo(imEmployee2);
        imEmployee2.setId(2L);
        assertThat(imEmployee1).isNotEqualTo(imEmployee2);
        imEmployee1.setId(null);
        assertThat(imEmployee1).isNotEqualTo(imEmployee2);
    }
}
