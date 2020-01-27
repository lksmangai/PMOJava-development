package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.SkillExpertise;
import com.qnowapp.repository.SkillExpertiseRepository;
import com.qnowapp.service.SkillExpertiseService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.SkillExpertiseCriteria;
import com.qnowapp.service.SkillExpertiseQueryService;

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
 * Integration tests for the {@Link SkillExpertiseResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class SkillExpertiseResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SkillExpertiseRepository skillExpertiseRepository;

    @Autowired
    private SkillExpertiseService skillExpertiseService;

    @Autowired
    private SkillExpertiseQueryService skillExpertiseQueryService;

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

    private MockMvc restSkillExpertiseMockMvc;

    private SkillExpertise skillExpertise;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillExpertiseResource skillExpertiseResource = new SkillExpertiseResource(skillExpertiseService, skillExpertiseQueryService);
        this.restSkillExpertiseMockMvc = MockMvcBuilders.standaloneSetup(skillExpertiseResource)
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
    public static SkillExpertise createEntity(EntityManager em) {
        SkillExpertise skillExpertise = new SkillExpertise()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return skillExpertise;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillExpertise createUpdatedEntity(EntityManager em) {
        SkillExpertise skillExpertise = new SkillExpertise()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return skillExpertise;
    }

    @BeforeEach
    public void initTest() {
        skillExpertise = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillExpertise() throws Exception {
        int databaseSizeBeforeCreate = skillExpertiseRepository.findAll().size();

        // Create the SkillExpertise
        restSkillExpertiseMockMvc.perform(post("/api/skill-expertises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillExpertise)))
            .andExpect(status().isCreated());

        // Validate the SkillExpertise in the database
        List<SkillExpertise> skillExpertiseList = skillExpertiseRepository.findAll();
        assertThat(skillExpertiseList).hasSize(databaseSizeBeforeCreate + 1);
        SkillExpertise testSkillExpertise = skillExpertiseList.get(skillExpertiseList.size() - 1);
        assertThat(testSkillExpertise.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSkillExpertise.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSkillExpertise.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSkillExpertiseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillExpertiseRepository.findAll().size();

        // Create the SkillExpertise with an existing ID
        skillExpertise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillExpertiseMockMvc.perform(post("/api/skill-expertises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillExpertise)))
            .andExpect(status().isBadRequest());

        // Validate the SkillExpertise in the database
        List<SkillExpertise> skillExpertiseList = skillExpertiseRepository.findAll();
        assertThat(skillExpertiseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSkillExpertises() throws Exception {
        // Initialize the database
        skillExpertiseRepository.saveAndFlush(skillExpertise);

        // Get all the skillExpertiseList
        restSkillExpertiseMockMvc.perform(get("/api/skill-expertises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillExpertise.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getSkillExpertise() throws Exception {
        // Initialize the database
        skillExpertiseRepository.saveAndFlush(skillExpertise);

        // Get the skillExpertise
        restSkillExpertiseMockMvc.perform(get("/api/skill-expertises/{id}", skillExpertise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skillExpertise.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllSkillExpertisesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        skillExpertiseRepository.saveAndFlush(skillExpertise);

        // Get all the skillExpertiseList where code equals to DEFAULT_CODE
        defaultSkillExpertiseShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the skillExpertiseList where code equals to UPDATED_CODE
        defaultSkillExpertiseShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSkillExpertisesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        skillExpertiseRepository.saveAndFlush(skillExpertise);

        // Get all the skillExpertiseList where code in DEFAULT_CODE or UPDATED_CODE
        defaultSkillExpertiseShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the skillExpertiseList where code equals to UPDATED_CODE
        defaultSkillExpertiseShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllSkillExpertisesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillExpertiseRepository.saveAndFlush(skillExpertise);

        // Get all the skillExpertiseList where code is not null
        defaultSkillExpertiseShouldBeFound("code.specified=true");

        // Get all the skillExpertiseList where code is null
        defaultSkillExpertiseShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillExpertisesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        skillExpertiseRepository.saveAndFlush(skillExpertise);

        // Get all the skillExpertiseList where name equals to DEFAULT_NAME
        defaultSkillExpertiseShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the skillExpertiseList where name equals to UPDATED_NAME
        defaultSkillExpertiseShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSkillExpertisesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        skillExpertiseRepository.saveAndFlush(skillExpertise);

        // Get all the skillExpertiseList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSkillExpertiseShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the skillExpertiseList where name equals to UPDATED_NAME
        defaultSkillExpertiseShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSkillExpertisesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillExpertiseRepository.saveAndFlush(skillExpertise);

        // Get all the skillExpertiseList where name is not null
        defaultSkillExpertiseShouldBeFound("name.specified=true");

        // Get all the skillExpertiseList where name is null
        defaultSkillExpertiseShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillExpertisesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        skillExpertiseRepository.saveAndFlush(skillExpertise);

        // Get all the skillExpertiseList where description equals to DEFAULT_DESCRIPTION
        defaultSkillExpertiseShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the skillExpertiseList where description equals to UPDATED_DESCRIPTION
        defaultSkillExpertiseShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSkillExpertisesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        skillExpertiseRepository.saveAndFlush(skillExpertise);

        // Get all the skillExpertiseList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSkillExpertiseShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the skillExpertiseList where description equals to UPDATED_DESCRIPTION
        defaultSkillExpertiseShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSkillExpertisesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillExpertiseRepository.saveAndFlush(skillExpertise);

        // Get all the skillExpertiseList where description is not null
        defaultSkillExpertiseShouldBeFound("description.specified=true");

        // Get all the skillExpertiseList where description is null
        defaultSkillExpertiseShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSkillExpertiseShouldBeFound(String filter) throws Exception {
        restSkillExpertiseMockMvc.perform(get("/api/skill-expertises?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillExpertise.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restSkillExpertiseMockMvc.perform(get("/api/skill-expertises/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSkillExpertiseShouldNotBeFound(String filter) throws Exception {
        restSkillExpertiseMockMvc.perform(get("/api/skill-expertises?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSkillExpertiseMockMvc.perform(get("/api/skill-expertises/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSkillExpertise() throws Exception {
        // Get the skillExpertise
        restSkillExpertiseMockMvc.perform(get("/api/skill-expertises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillExpertise() throws Exception {
        // Initialize the database
        skillExpertiseService.save(skillExpertise);

        int databaseSizeBeforeUpdate = skillExpertiseRepository.findAll().size();

        // Update the skillExpertise
        SkillExpertise updatedSkillExpertise = skillExpertiseRepository.findById(skillExpertise.getId()).get();
        // Disconnect from session so that the updates on updatedSkillExpertise are not directly saved in db
        em.detach(updatedSkillExpertise);
        updatedSkillExpertise
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restSkillExpertiseMockMvc.perform(put("/api/skill-expertises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSkillExpertise)))
            .andExpect(status().isOk());

        // Validate the SkillExpertise in the database
        List<SkillExpertise> skillExpertiseList = skillExpertiseRepository.findAll();
        assertThat(skillExpertiseList).hasSize(databaseSizeBeforeUpdate);
        SkillExpertise testSkillExpertise = skillExpertiseList.get(skillExpertiseList.size() - 1);
        assertThat(testSkillExpertise.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSkillExpertise.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkillExpertise.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillExpertise() throws Exception {
        int databaseSizeBeforeUpdate = skillExpertiseRepository.findAll().size();

        // Create the SkillExpertise

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillExpertiseMockMvc.perform(put("/api/skill-expertises")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillExpertise)))
            .andExpect(status().isBadRequest());

        // Validate the SkillExpertise in the database
        List<SkillExpertise> skillExpertiseList = skillExpertiseRepository.findAll();
        assertThat(skillExpertiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkillExpertise() throws Exception {
        // Initialize the database
        skillExpertiseService.save(skillExpertise);

        int databaseSizeBeforeDelete = skillExpertiseRepository.findAll().size();

        // Delete the skillExpertise
        restSkillExpertiseMockMvc.perform(delete("/api/skill-expertises/{id}", skillExpertise.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<SkillExpertise> skillExpertiseList = skillExpertiseRepository.findAll();
        assertThat(skillExpertiseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillExpertise.class);
        SkillExpertise skillExpertise1 = new SkillExpertise();
        skillExpertise1.setId(1L);
        SkillExpertise skillExpertise2 = new SkillExpertise();
        skillExpertise2.setId(skillExpertise1.getId());
        assertThat(skillExpertise1).isEqualTo(skillExpertise2);
        skillExpertise2.setId(2L);
        assertThat(skillExpertise1).isNotEqualTo(skillExpertise2);
        skillExpertise1.setId(null);
        assertThat(skillExpertise1).isNotEqualTo(skillExpertise2);
    }
}
