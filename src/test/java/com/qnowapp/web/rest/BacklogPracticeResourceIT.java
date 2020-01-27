package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.BacklogPractice;
import com.qnowapp.repository.BacklogPracticeRepository;
import com.qnowapp.service.BacklogPracticeService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.BacklogPracticeCriteria;
import com.qnowapp.service.BacklogPracticeQueryService;

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
 * Integration tests for the {@Link BacklogPracticeResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class BacklogPracticeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private BacklogPracticeRepository backlogPracticeRepository;

    @Autowired
    private BacklogPracticeService backlogPracticeService;

    @Autowired
    private BacklogPracticeQueryService backlogPracticeQueryService;

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

    private MockMvc restBacklogPracticeMockMvc;

    private BacklogPractice backlogPractice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BacklogPracticeResource backlogPracticeResource = new BacklogPracticeResource( backlogPracticeService, backlogPracticeQueryService);
        this.restBacklogPracticeMockMvc = MockMvcBuilders.standaloneSetup(backlogPracticeResource)
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
    public static BacklogPractice createEntity(EntityManager em) {
        BacklogPractice backlogPractice = new BacklogPractice()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return backlogPractice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BacklogPractice createUpdatedEntity(EntityManager em) {
        BacklogPractice backlogPractice = new BacklogPractice()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return backlogPractice;
    }

    @BeforeEach
    public void initTest() {
        backlogPractice = createEntity(em);
    }

    @Test
    @Transactional
    public void createBacklogPractice() throws Exception {
        int databaseSizeBeforeCreate = backlogPracticeRepository.findAll().size();

        // Create the BacklogPractice
        restBacklogPracticeMockMvc.perform(post("/api/backlog-practices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backlogPractice)))
            .andExpect(status().isCreated());

        // Validate the BacklogPractice in the database
        List<BacklogPractice> backlogPracticeList = backlogPracticeRepository.findAll();
        assertThat(backlogPracticeList).hasSize(databaseSizeBeforeCreate + 1);
        BacklogPractice testBacklogPractice = backlogPracticeList.get(backlogPracticeList.size() - 1);
        assertThat(testBacklogPractice.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBacklogPractice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBacklogPractice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createBacklogPracticeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = backlogPracticeRepository.findAll().size();

        // Create the BacklogPractice with an existing ID
        backlogPractice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBacklogPracticeMockMvc.perform(post("/api/backlog-practices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backlogPractice)))
            .andExpect(status().isBadRequest());

        // Validate the BacklogPractice in the database
        List<BacklogPractice> backlogPracticeList = backlogPracticeRepository.findAll();
        assertThat(backlogPracticeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBacklogPractices() throws Exception {
        // Initialize the database
        backlogPracticeRepository.saveAndFlush(backlogPractice);

        // Get all the backlogPracticeList
        restBacklogPracticeMockMvc.perform(get("/api/backlog-practices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(backlogPractice.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getBacklogPractice() throws Exception {
        // Initialize the database
        backlogPracticeRepository.saveAndFlush(backlogPractice);

        // Get the backlogPractice
        restBacklogPracticeMockMvc.perform(get("/api/backlog-practices/{id}", backlogPractice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(backlogPractice.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllBacklogPracticesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        backlogPracticeRepository.saveAndFlush(backlogPractice);

        // Get all the backlogPracticeList where code equals to DEFAULT_CODE
        defaultBacklogPracticeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the backlogPracticeList where code equals to UPDATED_CODE
        defaultBacklogPracticeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBacklogPracticesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        backlogPracticeRepository.saveAndFlush(backlogPractice);

        // Get all the backlogPracticeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultBacklogPracticeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the backlogPracticeList where code equals to UPDATED_CODE
        defaultBacklogPracticeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBacklogPracticesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        backlogPracticeRepository.saveAndFlush(backlogPractice);

        // Get all the backlogPracticeList where code is not null
        defaultBacklogPracticeShouldBeFound("code.specified=true");

        // Get all the backlogPracticeList where code is null
        defaultBacklogPracticeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllBacklogPracticesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        backlogPracticeRepository.saveAndFlush(backlogPractice);

        // Get all the backlogPracticeList where name equals to DEFAULT_NAME
        defaultBacklogPracticeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the backlogPracticeList where name equals to UPDATED_NAME
        defaultBacklogPracticeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBacklogPracticesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        backlogPracticeRepository.saveAndFlush(backlogPractice);

        // Get all the backlogPracticeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBacklogPracticeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the backlogPracticeList where name equals to UPDATED_NAME
        defaultBacklogPracticeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBacklogPracticesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        backlogPracticeRepository.saveAndFlush(backlogPractice);

        // Get all the backlogPracticeList where name is not null
        defaultBacklogPracticeShouldBeFound("name.specified=true");

        // Get all the backlogPracticeList where name is null
        defaultBacklogPracticeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllBacklogPracticesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        backlogPracticeRepository.saveAndFlush(backlogPractice);

        // Get all the backlogPracticeList where description equals to DEFAULT_DESCRIPTION
        defaultBacklogPracticeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the backlogPracticeList where description equals to UPDATED_DESCRIPTION
        defaultBacklogPracticeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBacklogPracticesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        backlogPracticeRepository.saveAndFlush(backlogPractice);

        // Get all the backlogPracticeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultBacklogPracticeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the backlogPracticeList where description equals to UPDATED_DESCRIPTION
        defaultBacklogPracticeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBacklogPracticesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        backlogPracticeRepository.saveAndFlush(backlogPractice);

        // Get all the backlogPracticeList where description is not null
        defaultBacklogPracticeShouldBeFound("description.specified=true");

        // Get all the backlogPracticeList where description is null
        defaultBacklogPracticeShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBacklogPracticeShouldBeFound(String filter) throws Exception {
        restBacklogPracticeMockMvc.perform(get("/api/backlog-practices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(backlogPractice.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restBacklogPracticeMockMvc.perform(get("/api/backlog-practices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBacklogPracticeShouldNotBeFound(String filter) throws Exception {
        restBacklogPracticeMockMvc.perform(get("/api/backlog-practices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBacklogPracticeMockMvc.perform(get("/api/backlog-practices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBacklogPractice() throws Exception {
        // Get the backlogPractice
        restBacklogPracticeMockMvc.perform(get("/api/backlog-practices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBacklogPractice() throws Exception {
        // Initialize the database
        backlogPracticeService.save(backlogPractice);

        int databaseSizeBeforeUpdate = backlogPracticeRepository.findAll().size();

        // Update the backlogPractice
        BacklogPractice updatedBacklogPractice = backlogPracticeRepository.findById(backlogPractice.getId()).get();
        // Disconnect from session so that the updates on updatedBacklogPractice are not directly saved in db
        em.detach(updatedBacklogPractice);
        updatedBacklogPractice
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restBacklogPracticeMockMvc.perform(put("/api/backlog-practices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBacklogPractice)))
            .andExpect(status().isOk());

        // Validate the BacklogPractice in the database
        List<BacklogPractice> backlogPracticeList = backlogPracticeRepository.findAll();
        assertThat(backlogPracticeList).hasSize(databaseSizeBeforeUpdate);
        BacklogPractice testBacklogPractice = backlogPracticeList.get(backlogPracticeList.size() - 1);
        assertThat(testBacklogPractice.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBacklogPractice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBacklogPractice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingBacklogPractice() throws Exception {
        int databaseSizeBeforeUpdate = backlogPracticeRepository.findAll().size();

        // Create the BacklogPractice

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBacklogPracticeMockMvc.perform(put("/api/backlog-practices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(backlogPractice)))
            .andExpect(status().isBadRequest());

        // Validate the BacklogPractice in the database
        List<BacklogPractice> backlogPracticeList = backlogPracticeRepository.findAll();
        assertThat(backlogPracticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBacklogPractice() throws Exception {
        // Initialize the database
        backlogPracticeService.save(backlogPractice);

        int databaseSizeBeforeDelete = backlogPracticeRepository.findAll().size();

        // Delete the backlogPractice
        restBacklogPracticeMockMvc.perform(delete("/api/backlog-practices/{id}", backlogPractice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<BacklogPractice> backlogPracticeList = backlogPracticeRepository.findAll();
        assertThat(backlogPracticeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BacklogPractice.class);
        BacklogPractice backlogPractice1 = new BacklogPractice();
        backlogPractice1.setId(1L);
        BacklogPractice backlogPractice2 = new BacklogPractice();
        backlogPractice2.setId(backlogPractice1.getId());
        assertThat(backlogPractice1).isEqualTo(backlogPractice2);
        backlogPractice2.setId(2L);
        assertThat(backlogPractice1).isNotEqualTo(backlogPractice2);
        backlogPractice1.setId(null);
        assertThat(backlogPractice1).isNotEqualTo(backlogPractice2);
    }
}
