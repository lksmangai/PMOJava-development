package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ImTimesheet;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.repository.ImTimesheetRepository;
import com.qnowapp.service.ImTimesheetService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ImTimesheetCriteria;
import com.qnowapp.service.ImTimesheetQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.qnowapp.web.rest.TestUtil.sameInstant;
import static com.qnowapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ImTimesheetResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ImTimesheetResourceIT {

    private static final ZonedDateTime DEFAULT_LOGDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LOGDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_LOGHOURS = 1D;
    private static final Double UPDATED_LOGHOURS = 2D;

    private static final Double DEFAULT_BILLHOURS = 1D;
    private static final Double UPDATED_BILLHOURS = 2D;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    @Autowired
    private ImTimesheetRepository imTimesheetRepository;

    @Autowired
    private ImTimesheetService imTimesheetService;

    @Autowired
    private ImTimesheetQueryService imTimesheetQueryService;

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

    private MockMvc restImTimesheetMockMvc;

    private ImTimesheet imTimesheet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImTimesheetResource imTimesheetResource = new ImTimesheetResource(imTimesheetService, imTimesheetQueryService);
        this.restImTimesheetMockMvc = MockMvcBuilders.standaloneSetup(imTimesheetResource)
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
    public static ImTimesheet createEntity(EntityManager em) {
        ImTimesheet imTimesheet = new ImTimesheet()
            .logdate(DEFAULT_LOGDATE)
            .loghours(DEFAULT_LOGHOURS)
            .billhours(DEFAULT_BILLHOURS)
            .notes(DEFAULT_NOTES);
        return imTimesheet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImTimesheet createUpdatedEntity(EntityManager em) {
        ImTimesheet imTimesheet = new ImTimesheet()
            .logdate(UPDATED_LOGDATE)
            .loghours(UPDATED_LOGHOURS)
            .billhours(UPDATED_BILLHOURS)
            .notes(UPDATED_NOTES);
        return imTimesheet;
    }

    @BeforeEach
    public void initTest() {
        imTimesheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createImTimesheet() throws Exception {
        int databaseSizeBeforeCreate = imTimesheetRepository.findAll().size();

        // Create the ImTimesheet
        restImTimesheetMockMvc.perform(post("/api/im-timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imTimesheet)))
            .andExpect(status().isCreated());

        // Validate the ImTimesheet in the database
        List<ImTimesheet> imTimesheetList = imTimesheetRepository.findAll();
        assertThat(imTimesheetList).hasSize(databaseSizeBeforeCreate + 1);
        ImTimesheet testImTimesheet = imTimesheetList.get(imTimesheetList.size() - 1);
        assertThat(testImTimesheet.getLogdate()).isEqualTo(DEFAULT_LOGDATE);
        assertThat(testImTimesheet.getLoghours()).isEqualTo(DEFAULT_LOGHOURS);
        assertThat(testImTimesheet.getBillhours()).isEqualTo(DEFAULT_BILLHOURS);
        assertThat(testImTimesheet.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    public void createImTimesheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imTimesheetRepository.findAll().size();

        // Create the ImTimesheet with an existing ID
        imTimesheet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImTimesheetMockMvc.perform(post("/api/im-timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imTimesheet)))
            .andExpect(status().isBadRequest());

        // Validate the ImTimesheet in the database
        List<ImTimesheet> imTimesheetList = imTimesheetRepository.findAll();
        assertThat(imTimesheetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLogdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = imTimesheetRepository.findAll().size();
        // set the field null
        imTimesheet.setLogdate(null);

        // Create the ImTimesheet, which fails.

        restImTimesheetMockMvc.perform(post("/api/im-timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imTimesheet)))
            .andExpect(status().isBadRequest());

        List<ImTimesheet> imTimesheetList = imTimesheetRepository.findAll();
        assertThat(imTimesheetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImTimesheets() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList
        restImTimesheetMockMvc.perform(get("/api/im-timesheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imTimesheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].logdate").value(hasItem(sameInstant(DEFAULT_LOGDATE))))
            .andExpect(jsonPath("$.[*].loghours").value(hasItem(DEFAULT_LOGHOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].billhours").value(hasItem(DEFAULT_BILLHOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())));
    }
    
    @Test
    @Transactional
    public void getImTimesheet() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get the imTimesheet
        restImTimesheetMockMvc.perform(get("/api/im-timesheets/{id}", imTimesheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imTimesheet.getId().intValue()))
            .andExpect(jsonPath("$.logdate").value(sameInstant(DEFAULT_LOGDATE)))
            .andExpect(jsonPath("$.loghours").value(DEFAULT_LOGHOURS.doubleValue()))
            .andExpect(jsonPath("$.billhours").value(DEFAULT_BILLHOURS.doubleValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()));
  
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByLogdateIsEqualToSomething() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where logdate equals to DEFAULT_LOGDATE
        defaultImTimesheetShouldBeFound("logdate.equals=" + DEFAULT_LOGDATE);

        // Get all the imTimesheetList where logdate equals to UPDATED_LOGDATE
        defaultImTimesheetShouldNotBeFound("logdate.equals=" + UPDATED_LOGDATE);
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByLogdateIsInShouldWork() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where logdate in DEFAULT_LOGDATE or UPDATED_LOGDATE
        defaultImTimesheetShouldBeFound("logdate.in=" + DEFAULT_LOGDATE + "," + UPDATED_LOGDATE);

        // Get all the imTimesheetList where logdate equals to UPDATED_LOGDATE
        defaultImTimesheetShouldNotBeFound("logdate.in=" + UPDATED_LOGDATE);
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByLogdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where logdate is not null
        defaultImTimesheetShouldBeFound("logdate.specified=true");

        // Get all the imTimesheetList where logdate is null
        defaultImTimesheetShouldNotBeFound("logdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByLogdateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where logdate greater than or equals to DEFAULT_LOGDATE
        defaultImTimesheetShouldBeFound("logdate.greaterOrEqualThan=" + DEFAULT_LOGDATE);

        // Get all the imTimesheetList where logdate greater than or equals to UPDATED_LOGDATE
        defaultImTimesheetShouldNotBeFound("logdate.greaterOrEqualThan=" + UPDATED_LOGDATE);
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByLogdateIsLessThanSomething() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where logdate less than or equals to DEFAULT_LOGDATE
        defaultImTimesheetShouldNotBeFound("logdate.lessThan=" + DEFAULT_LOGDATE);

        // Get all the imTimesheetList where logdate less than or equals to UPDATED_LOGDATE
        defaultImTimesheetShouldBeFound("logdate.lessThan=" + UPDATED_LOGDATE);
    }


    @Test
    @Transactional
    public void getAllImTimesheetsByLoghoursIsEqualToSomething() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where loghours equals to DEFAULT_LOGHOURS
        defaultImTimesheetShouldBeFound("loghours.equals=" + DEFAULT_LOGHOURS);

        // Get all the imTimesheetList where loghours equals to UPDATED_LOGHOURS
        defaultImTimesheetShouldNotBeFound("loghours.equals=" + UPDATED_LOGHOURS);
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByLoghoursIsInShouldWork() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where loghours in DEFAULT_LOGHOURS or UPDATED_LOGHOURS
        defaultImTimesheetShouldBeFound("loghours.in=" + DEFAULT_LOGHOURS + "," + UPDATED_LOGHOURS);

        // Get all the imTimesheetList where loghours equals to UPDATED_LOGHOURS
        defaultImTimesheetShouldNotBeFound("loghours.in=" + UPDATED_LOGHOURS);
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByLoghoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where loghours is not null
        defaultImTimesheetShouldBeFound("loghours.specified=true");

        // Get all the imTimesheetList where loghours is null
        defaultImTimesheetShouldNotBeFound("loghours.specified=false");

    }

    @Test
    @Transactional
    public void getAllImTimesheetsByBillhoursIsEqualToSomething() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where billhours equals to DEFAULT_BILLHOURS
        defaultImTimesheetShouldBeFound("billhours.equals=" + DEFAULT_BILLHOURS);

        // Get all the imTimesheetList where billhours equals to UPDATED_BILLHOURS
        defaultImTimesheetShouldNotBeFound("billhours.equals=" + UPDATED_BILLHOURS);
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByBillhoursIsInShouldWork() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where billhours in DEFAULT_BILLHOURS or UPDATED_BILLHOURS
        defaultImTimesheetShouldBeFound("billhours.in=" + DEFAULT_BILLHOURS + "," + UPDATED_BILLHOURS);

        // Get all the imTimesheetList where billhours equals to UPDATED_BILLHOURS
        defaultImTimesheetShouldNotBeFound("billhours.in=" + UPDATED_BILLHOURS);
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByBillhoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where billhours is not null
        defaultImTimesheetShouldBeFound("billhours.specified=true");

        // Get all the imTimesheetList where billhours is null
        defaultImTimesheetShouldNotBeFound("billhours.specified=false");
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where notes equals to DEFAULT_NOTES
        defaultImTimesheetShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the imTimesheetList where notes equals to UPDATED_NOTES
        defaultImTimesheetShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultImTimesheetShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the imTimesheetList where notes equals to UPDATED_NOTES
        defaultImTimesheetShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        imTimesheetRepository.saveAndFlush(imTimesheet);

        // Get all the imTimesheetList where notes is not null
        defaultImTimesheetShouldBeFound("notes.specified=true");

        // Get all the imTimesheetList where notes is null
        defaultImTimesheetShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    public void getAllImTimesheetsByImEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        ImEmployee imEmployee = ImEmployeeResourceIT.createEntity(em);
        em.persist(imEmployee);
        em.flush();
        imTimesheet.setImEmployee(imEmployee);
        imTimesheetRepository.saveAndFlush(imTimesheet);
        Long imEmployeeId = imEmployee.getId();

        // Get all the imTimesheetList where imEmployee equals to imEmployeeId
        defaultImTimesheetShouldBeFound("imEmployeeId.equals=" + imEmployeeId);

        // Get all the imTimesheetList where imEmployee equals to imEmployeeId + 1
        defaultImTimesheetShouldNotBeFound("imEmployeeId.equals=" + (imEmployeeId + 1));
    }


    @Test
    @Transactional
    public void getAllImTimesheetsByImProjectsIsEqualToSomething() throws Exception {
        // Initialize the database
        ImProjects imProjects = ImProjectsResourceIT.createEntity(em);
        em.persist(imProjects);
        em.flush();
        imTimesheet.setImProjects(imProjects);
        imTimesheetRepository.saveAndFlush(imTimesheet);
        Long imProjectsId = imProjects.getId();

        // Get all the imTimesheetList where imProjects equals to imProjectsId
        defaultImTimesheetShouldBeFound("imProjectsId.equals=" + imProjectsId);

        // Get all the imTimesheetList where imProjects equals to imProjectsId + 1
        defaultImTimesheetShouldNotBeFound("imProjectsId.equals=" + (imProjectsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultImTimesheetShouldBeFound(String filter) throws Exception {
        restImTimesheetMockMvc.perform(get("/api/im-timesheets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imTimesheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].logdate").value(hasItem(sameInstant(DEFAULT_LOGDATE))))
            .andExpect(jsonPath("$.[*].loghours").value(hasItem(DEFAULT_LOGHOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].billhours").value(hasItem(DEFAULT_BILLHOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));

        // Check, that the count call also returns 1
        restImTimesheetMockMvc.perform(get("/api/im-timesheets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultImTimesheetShouldNotBeFound(String filter) throws Exception {
        restImTimesheetMockMvc.perform(get("/api/im-timesheets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restImTimesheetMockMvc.perform(get("/api/im-timesheets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingImTimesheet() throws Exception {
        // Get the imTimesheet
        restImTimesheetMockMvc.perform(get("/api/im-timesheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImTimesheet() throws Exception {
        // Initialize the database
        imTimesheetService.save(imTimesheet);

        int databaseSizeBeforeUpdate = imTimesheetRepository.findAll().size();

        // Update the imTimesheet
        ImTimesheet updatedImTimesheet = imTimesheetRepository.findById(imTimesheet.getId()).get();
        // Disconnect from session so that the updates on updatedImTimesheet are not directly saved in db
        em.detach(updatedImTimesheet);
        updatedImTimesheet
            .logdate(UPDATED_LOGDATE)
            .loghours(UPDATED_LOGHOURS)
            .billhours(UPDATED_BILLHOURS)
            .notes(UPDATED_NOTES);

        restImTimesheetMockMvc.perform(put("/api/im-timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImTimesheet)))
            .andExpect(status().isOk());

        // Validate the ImTimesheet in the database
        List<ImTimesheet> imTimesheetList = imTimesheetRepository.findAll();
        assertThat(imTimesheetList).hasSize(databaseSizeBeforeUpdate);
        ImTimesheet testImTimesheet = imTimesheetList.get(imTimesheetList.size() - 1);
        assertThat(testImTimesheet.getLogdate()).isEqualTo(UPDATED_LOGDATE);
        assertThat(testImTimesheet.getLoghours()).isEqualTo(UPDATED_LOGHOURS);
        assertThat(testImTimesheet.getBillhours()).isEqualTo(UPDATED_BILLHOURS);
        assertThat(testImTimesheet.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingImTimesheet() throws Exception {
        int databaseSizeBeforeUpdate = imTimesheetRepository.findAll().size();

        // Create the ImTimesheet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImTimesheetMockMvc.perform(put("/api/im-timesheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imTimesheet)))
            .andExpect(status().isBadRequest());

        // Validate the ImTimesheet in the database
        List<ImTimesheet> imTimesheetList = imTimesheetRepository.findAll();
        assertThat(imTimesheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImTimesheet() throws Exception {
        // Initialize the database
        imTimesheetService.save(imTimesheet);

        int databaseSizeBeforeDelete = imTimesheetRepository.findAll().size();

        // Delete the imTimesheet
        restImTimesheetMockMvc.perform(delete("/api/im-timesheets/{id}", imTimesheet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ImTimesheet> imTimesheetList = imTimesheetRepository.findAll();
        assertThat(imTimesheetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImTimesheet.class);
        ImTimesheet imTimesheet1 = new ImTimesheet();
        imTimesheet1.setId(1L);
        ImTimesheet imTimesheet2 = new ImTimesheet();
        imTimesheet2.setId(imTimesheet1.getId());
        assertThat(imTimesheet1).isEqualTo(imTimesheet2);
        imTimesheet2.setId(2L);
        assertThat(imTimesheet1).isNotEqualTo(imTimesheet2);
        imTimesheet1.setId(null);
        assertThat(imTimesheet1).isNotEqualTo(imTimesheet2);
    }
}
