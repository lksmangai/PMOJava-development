package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.StateMaster;
import com.qnowapp.repository.StateMasterRepository;
import com.qnowapp.service.StateMasterService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.StateMasterCriteria;
import com.qnowapp.service.StateMasterQueryService;

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
 * Integration tests for the {@Link StateMasterResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class StateMasterResourceIT {

    private static final String DEFAULT_STATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATE_NAME = "BBBBBBBBBB";

    @Autowired
    private StateMasterRepository stateMasterRepository;

    @Autowired
    private StateMasterService stateMasterService;

    @Autowired
    private StateMasterQueryService stateMasterQueryService;

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

    private MockMvc restStateMasterMockMvc;

    private StateMaster stateMaster;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StateMasterResource stateMasterResource = new StateMasterResource(stateMasterService, stateMasterQueryService);
        this.restStateMasterMockMvc = MockMvcBuilders.standaloneSetup(stateMasterResource)
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
    public static StateMaster createEntity(EntityManager em) {
        StateMaster stateMaster = new StateMaster()
            .stateCode(DEFAULT_STATE_CODE)
            .stateName(DEFAULT_STATE_NAME);
        return stateMaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StateMaster createUpdatedEntity(EntityManager em) {
        StateMaster stateMaster = new StateMaster()
            .stateCode(UPDATED_STATE_CODE)
            .stateName(UPDATED_STATE_NAME);
        return stateMaster;
    }

    @BeforeEach
    public void initTest() {
        stateMaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createStateMaster() throws Exception {
        int databaseSizeBeforeCreate = stateMasterRepository.findAll().size();

        // Create the StateMaster
        restStateMasterMockMvc.perform(post("/api/state-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateMaster)))
            .andExpect(status().isCreated());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeCreate + 1);
        StateMaster testStateMaster = stateMasterList.get(stateMasterList.size() - 1);
        assertThat(testStateMaster.getStateCode()).isEqualTo(DEFAULT_STATE_CODE);
        assertThat(testStateMaster.getStateName()).isEqualTo(DEFAULT_STATE_NAME);
    }

    @Test
    @Transactional
    public void createStateMasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateMasterRepository.findAll().size();

        // Create the StateMaster with an existing ID
        stateMaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateMasterMockMvc.perform(post("/api/state-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateMaster)))
            .andExpect(status().isBadRequest());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStateMasters() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        // Get all the stateMasterList
        restStateMasterMockMvc.perform(get("/api/state-masters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE.toString())))
            .andExpect(jsonPath("$.[*].stateName").value(hasItem(DEFAULT_STATE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getStateMaster() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        // Get the stateMaster
        restStateMasterMockMvc.perform(get("/api/state-masters/{id}", stateMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stateMaster.getId().intValue()))
            .andExpect(jsonPath("$.stateCode").value(DEFAULT_STATE_CODE.toString()))
            .andExpect(jsonPath("$.stateName").value(DEFAULT_STATE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllStateMastersByStateCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        // Get all the stateMasterList where stateCode equals to DEFAULT_STATE_CODE
        defaultStateMasterShouldBeFound("stateCode.equals=" + DEFAULT_STATE_CODE);

        // Get all the stateMasterList where stateCode equals to UPDATED_STATE_CODE
        defaultStateMasterShouldNotBeFound("stateCode.equals=" + UPDATED_STATE_CODE);
    }

    @Test
    @Transactional
    public void getAllStateMastersByStateCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        // Get all the stateMasterList where stateCode in DEFAULT_STATE_CODE or UPDATED_STATE_CODE
        defaultStateMasterShouldBeFound("stateCode.in=" + DEFAULT_STATE_CODE + "," + UPDATED_STATE_CODE);

        // Get all the stateMasterList where stateCode equals to UPDATED_STATE_CODE
        defaultStateMasterShouldNotBeFound("stateCode.in=" + UPDATED_STATE_CODE);
    }

    @Test
    @Transactional
    public void getAllStateMastersByStateCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        // Get all the stateMasterList where stateCode is not null
        defaultStateMasterShouldBeFound("stateCode.specified=true");

        // Get all the stateMasterList where stateCode is null
        defaultStateMasterShouldNotBeFound("stateCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStateMastersByStateNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        // Get all the stateMasterList where stateName equals to DEFAULT_STATE_NAME
        defaultStateMasterShouldBeFound("stateName.equals=" + DEFAULT_STATE_NAME);

        // Get all the stateMasterList where stateName equals to UPDATED_STATE_NAME
        defaultStateMasterShouldNotBeFound("stateName.equals=" + UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    public void getAllStateMastersByStateNameIsInShouldWork() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        // Get all the stateMasterList where stateName in DEFAULT_STATE_NAME or UPDATED_STATE_NAME
        defaultStateMasterShouldBeFound("stateName.in=" + DEFAULT_STATE_NAME + "," + UPDATED_STATE_NAME);

        // Get all the stateMasterList where stateName equals to UPDATED_STATE_NAME
        defaultStateMasterShouldNotBeFound("stateName.in=" + UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    public void getAllStateMastersByStateNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateMasterRepository.saveAndFlush(stateMaster);

        // Get all the stateMasterList where stateName is not null
        defaultStateMasterShouldBeFound("stateName.specified=true");

        // Get all the stateMasterList where stateName is null
        defaultStateMasterShouldNotBeFound("stateName.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStateMasterShouldBeFound(String filter) throws Exception {
        restStateMasterMockMvc.perform(get("/api/state-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stateMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].stateCode").value(hasItem(DEFAULT_STATE_CODE)))
            .andExpect(jsonPath("$.[*].stateName").value(hasItem(DEFAULT_STATE_NAME)));

        // Check, that the count call also returns 1
        restStateMasterMockMvc.perform(get("/api/state-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStateMasterShouldNotBeFound(String filter) throws Exception {
        restStateMasterMockMvc.perform(get("/api/state-masters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStateMasterMockMvc.perform(get("/api/state-masters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStateMaster() throws Exception {
        // Get the stateMaster
        restStateMasterMockMvc.perform(get("/api/state-masters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStateMaster() throws Exception {
        // Initialize the database
        stateMasterService.save(stateMaster);

        int databaseSizeBeforeUpdate = stateMasterRepository.findAll().size();

        // Update the stateMaster
        StateMaster updatedStateMaster = stateMasterRepository.findById(stateMaster.getId()).get();
        // Disconnect from session so that the updates on updatedStateMaster are not directly saved in db
        em.detach(updatedStateMaster);
        updatedStateMaster
            .stateCode(UPDATED_STATE_CODE)
            .stateName(UPDATED_STATE_NAME);

        restStateMasterMockMvc.perform(put("/api/state-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStateMaster)))
            .andExpect(status().isOk());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeUpdate);
        StateMaster testStateMaster = stateMasterList.get(stateMasterList.size() - 1);
        assertThat(testStateMaster.getStateCode()).isEqualTo(UPDATED_STATE_CODE);
        assertThat(testStateMaster.getStateName()).isEqualTo(UPDATED_STATE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStateMaster() throws Exception {
        int databaseSizeBeforeUpdate = stateMasterRepository.findAll().size();

        // Create the StateMaster

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateMasterMockMvc.perform(put("/api/state-masters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stateMaster)))
            .andExpect(status().isBadRequest());

        // Validate the StateMaster in the database
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStateMaster() throws Exception {
        // Initialize the database
        stateMasterService.save(stateMaster);

        int databaseSizeBeforeDelete = stateMasterRepository.findAll().size();

        // Delete the stateMaster
        restStateMasterMockMvc.perform(delete("/api/state-masters/{id}", stateMaster.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<StateMaster> stateMasterList = stateMasterRepository.findAll();
        assertThat(stateMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateMaster.class);
        StateMaster stateMaster1 = new StateMaster();
        stateMaster1.setId(1L);
        StateMaster stateMaster2 = new StateMaster();
        stateMaster2.setId(stateMaster1.getId());
        assertThat(stateMaster1).isEqualTo(stateMaster2);
        stateMaster2.setId(2L);
        assertThat(stateMaster1).isNotEqualTo(stateMaster2);
        stateMaster1.setId(null);
        assertThat(stateMaster1).isNotEqualTo(stateMaster2);
    }
}
