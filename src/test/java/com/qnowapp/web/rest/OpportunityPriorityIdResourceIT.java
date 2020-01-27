package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.OpportunityPriorityId;
import com.qnowapp.repository.OpportunityPriorityIdRepository;
import com.qnowapp.service.OpportunityPriorityIdService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.OpportunityPriorityIdCriteria;
import com.qnowapp.service.OpportunityPriorityIdQueryService;

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
 * Integration tests for the {@Link OpportunityPriorityIdResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class OpportunityPriorityIdResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OpportunityPriorityIdRepository opportunityPriorityIdRepository;

    @Autowired
    private OpportunityPriorityIdService opportunityPriorityIdService;

    @Autowired
    private OpportunityPriorityIdQueryService opportunityPriorityIdQueryService;

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

    private MockMvc restOpportunityPriorityIdMockMvc;

    private OpportunityPriorityId opportunityPriorityId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OpportunityPriorityIdResource opportunityPriorityIdResource = new OpportunityPriorityIdResource( opportunityPriorityIdService, opportunityPriorityIdQueryService);
        this.restOpportunityPriorityIdMockMvc = MockMvcBuilders.standaloneSetup(opportunityPriorityIdResource)
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
    public static OpportunityPriorityId createEntity(EntityManager em) {
        OpportunityPriorityId opportunityPriorityId = new OpportunityPriorityId()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return opportunityPriorityId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OpportunityPriorityId createUpdatedEntity(EntityManager em) {
        OpportunityPriorityId opportunityPriorityId = new OpportunityPriorityId()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return opportunityPriorityId;
    }

    @BeforeEach
    public void initTest() {
        opportunityPriorityId = createEntity(em);
    }

    @Test
    @Transactional
    public void createOpportunityPriorityId() throws Exception {
        int databaseSizeBeforeCreate = opportunityPriorityIdRepository.findAll().size();

        // Create the OpportunityPriorityId
        restOpportunityPriorityIdMockMvc.perform(post("/api/opportunity-priority-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityPriorityId)))
            .andExpect(status().isCreated());

        // Validate the OpportunityPriorityId in the database
        List<OpportunityPriorityId> opportunityPriorityIdList = opportunityPriorityIdRepository.findAll();
        assertThat(opportunityPriorityIdList).hasSize(databaseSizeBeforeCreate + 1);
        OpportunityPriorityId testOpportunityPriorityId = opportunityPriorityIdList.get(opportunityPriorityIdList.size() - 1);
        assertThat(testOpportunityPriorityId.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOpportunityPriorityId.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOpportunityPriorityId.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOpportunityPriorityIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = opportunityPriorityIdRepository.findAll().size();

        // Create the OpportunityPriorityId with an existing ID
        opportunityPriorityId.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpportunityPriorityIdMockMvc.perform(post("/api/opportunity-priority-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityPriorityId)))
            .andExpect(status().isBadRequest());

        // Validate the OpportunityPriorityId in the database
        List<OpportunityPriorityId> opportunityPriorityIdList = opportunityPriorityIdRepository.findAll();
        assertThat(opportunityPriorityIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOpportunityPriorityIds() throws Exception {
        // Initialize the database
        opportunityPriorityIdRepository.saveAndFlush(opportunityPriorityId);

        // Get all the opportunityPriorityIdList
        restOpportunityPriorityIdMockMvc.perform(get("/api/opportunity-priority-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opportunityPriorityId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getOpportunityPriorityId() throws Exception {
        // Initialize the database
        opportunityPriorityIdRepository.saveAndFlush(opportunityPriorityId);

        // Get the opportunityPriorityId
        restOpportunityPriorityIdMockMvc.perform(get("/api/opportunity-priority-ids/{id}", opportunityPriorityId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(opportunityPriorityId.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllOpportunityPriorityIdsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        opportunityPriorityIdRepository.saveAndFlush(opportunityPriorityId);

        // Get all the opportunityPriorityIdList where code equals to DEFAULT_CODE
        defaultOpportunityPriorityIdShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the opportunityPriorityIdList where code equals to UPDATED_CODE
        defaultOpportunityPriorityIdShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllOpportunityPriorityIdsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        opportunityPriorityIdRepository.saveAndFlush(opportunityPriorityId);

        // Get all the opportunityPriorityIdList where code in DEFAULT_CODE or UPDATED_CODE
        defaultOpportunityPriorityIdShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the opportunityPriorityIdList where code equals to UPDATED_CODE
        defaultOpportunityPriorityIdShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllOpportunityPriorityIdsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        opportunityPriorityIdRepository.saveAndFlush(opportunityPriorityId);

        // Get all the opportunityPriorityIdList where code is not null
        defaultOpportunityPriorityIdShouldBeFound("code.specified=true");

        // Get all the opportunityPriorityIdList where code is null
        defaultOpportunityPriorityIdShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllOpportunityPriorityIdsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        opportunityPriorityIdRepository.saveAndFlush(opportunityPriorityId);

        // Get all the opportunityPriorityIdList where name equals to DEFAULT_NAME
        defaultOpportunityPriorityIdShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the opportunityPriorityIdList where name equals to UPDATED_NAME
        defaultOpportunityPriorityIdShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOpportunityPriorityIdsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        opportunityPriorityIdRepository.saveAndFlush(opportunityPriorityId);

        // Get all the opportunityPriorityIdList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOpportunityPriorityIdShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the opportunityPriorityIdList where name equals to UPDATED_NAME
        defaultOpportunityPriorityIdShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOpportunityPriorityIdsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        opportunityPriorityIdRepository.saveAndFlush(opportunityPriorityId);

        // Get all the opportunityPriorityIdList where name is not null
        defaultOpportunityPriorityIdShouldBeFound("name.specified=true");

        // Get all the opportunityPriorityIdList where name is null
        defaultOpportunityPriorityIdShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllOpportunityPriorityIdsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        opportunityPriorityIdRepository.saveAndFlush(opportunityPriorityId);

        // Get all the opportunityPriorityIdList where description equals to DEFAULT_DESCRIPTION
        defaultOpportunityPriorityIdShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the opportunityPriorityIdList where description equals to UPDATED_DESCRIPTION
        defaultOpportunityPriorityIdShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOpportunityPriorityIdsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        opportunityPriorityIdRepository.saveAndFlush(opportunityPriorityId);

        // Get all the opportunityPriorityIdList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOpportunityPriorityIdShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the opportunityPriorityIdList where description equals to UPDATED_DESCRIPTION
        defaultOpportunityPriorityIdShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllOpportunityPriorityIdsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        opportunityPriorityIdRepository.saveAndFlush(opportunityPriorityId);

        // Get all the opportunityPriorityIdList where description is not null
        defaultOpportunityPriorityIdShouldBeFound("description.specified=true");

        // Get all the opportunityPriorityIdList where description is null
        defaultOpportunityPriorityIdShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOpportunityPriorityIdShouldBeFound(String filter) throws Exception {
        restOpportunityPriorityIdMockMvc.perform(get("/api/opportunity-priority-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opportunityPriorityId.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restOpportunityPriorityIdMockMvc.perform(get("/api/opportunity-priority-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOpportunityPriorityIdShouldNotBeFound(String filter) throws Exception {
        restOpportunityPriorityIdMockMvc.perform(get("/api/opportunity-priority-ids?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOpportunityPriorityIdMockMvc.perform(get("/api/opportunity-priority-ids/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOpportunityPriorityId() throws Exception {
        // Get the opportunityPriorityId
        restOpportunityPriorityIdMockMvc.perform(get("/api/opportunity-priority-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOpportunityPriorityId() throws Exception {
        // Initialize the database
        opportunityPriorityIdService.save(opportunityPriorityId);

        int databaseSizeBeforeUpdate = opportunityPriorityIdRepository.findAll().size();

        // Update the opportunityPriorityId
        OpportunityPriorityId updatedOpportunityPriorityId = opportunityPriorityIdRepository.findById(opportunityPriorityId.getId()).get();
        // Disconnect from session so that the updates on updatedOpportunityPriorityId are not directly saved in db
        em.detach(updatedOpportunityPriorityId);
        updatedOpportunityPriorityId
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restOpportunityPriorityIdMockMvc.perform(put("/api/opportunity-priority-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOpportunityPriorityId)))
            .andExpect(status().isOk());

        // Validate the OpportunityPriorityId in the database
        List<OpportunityPriorityId> opportunityPriorityIdList = opportunityPriorityIdRepository.findAll();
        assertThat(opportunityPriorityIdList).hasSize(databaseSizeBeforeUpdate);
        OpportunityPriorityId testOpportunityPriorityId = opportunityPriorityIdList.get(opportunityPriorityIdList.size() - 1);
        assertThat(testOpportunityPriorityId.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOpportunityPriorityId.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOpportunityPriorityId.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOpportunityPriorityId() throws Exception {
        int databaseSizeBeforeUpdate = opportunityPriorityIdRepository.findAll().size();

        // Create the OpportunityPriorityId

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpportunityPriorityIdMockMvc.perform(put("/api/opportunity-priority-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(opportunityPriorityId)))
            .andExpect(status().isBadRequest());

        // Validate the OpportunityPriorityId in the database
        List<OpportunityPriorityId> opportunityPriorityIdList = opportunityPriorityIdRepository.findAll();
        assertThat(opportunityPriorityIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOpportunityPriorityId() throws Exception {
        // Initialize the database
        opportunityPriorityIdService.save(opportunityPriorityId);

        int databaseSizeBeforeDelete = opportunityPriorityIdRepository.findAll().size();

        // Delete the opportunityPriorityId
        restOpportunityPriorityIdMockMvc.perform(delete("/api/opportunity-priority-ids/{id}", opportunityPriorityId.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<OpportunityPriorityId> opportunityPriorityIdList = opportunityPriorityIdRepository.findAll();
        assertThat(opportunityPriorityIdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpportunityPriorityId.class);
        OpportunityPriorityId opportunityPriorityId1 = new OpportunityPriorityId();
        opportunityPriorityId1.setId(1L);
        OpportunityPriorityId opportunityPriorityId2 = new OpportunityPriorityId();
        opportunityPriorityId2.setId(opportunityPriorityId1.getId());
        assertThat(opportunityPriorityId1).isEqualTo(opportunityPriorityId2);
        opportunityPriorityId2.setId(2L);
        assertThat(opportunityPriorityId1).isNotEqualTo(opportunityPriorityId2);
        opportunityPriorityId1.setId(null);
        assertThat(opportunityPriorityId1).isNotEqualTo(opportunityPriorityId2);
    }
}
