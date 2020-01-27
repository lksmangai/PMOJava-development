package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.SkillCategory;
import com.qnowapp.repository.SkillCategoryRepository;
import com.qnowapp.service.SkillCategoryService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.SkillCategoryCriteria;
import com.qnowapp.service.SkillCategoryQueryService;

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
 * Integration tests for the {@Link SkillCategoryResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class SkillCategoryResourceIT {

    private static final String DEFAULT_SKILL_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SKILL_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SKILL_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SKILL_CATEGORY_NAME = "BBBBBBBBBB";

    @Autowired
    private SkillCategoryRepository skillCategoryRepository;

    @Autowired
    private SkillCategoryService skillCategoryService;

    @Autowired
    private SkillCategoryQueryService skillCategoryQueryService;

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

    private MockMvc restSkillCategoryMockMvc;

    private SkillCategory skillCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillCategoryResource skillCategoryResource = new SkillCategoryResource(skillCategoryService, skillCategoryQueryService);
        this.restSkillCategoryMockMvc = MockMvcBuilders.standaloneSetup(skillCategoryResource)
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
    public static SkillCategory createEntity(EntityManager em) {
        SkillCategory skillCategory = new SkillCategory()
            .skillCategoryCode(DEFAULT_SKILL_CATEGORY_CODE)
            .skillCategoryName(DEFAULT_SKILL_CATEGORY_NAME);
        return skillCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillCategory createUpdatedEntity(EntityManager em) {
        SkillCategory skillCategory = new SkillCategory()
            .skillCategoryCode(UPDATED_SKILL_CATEGORY_CODE)
            .skillCategoryName(UPDATED_SKILL_CATEGORY_NAME);
        return skillCategory;
    }

    @BeforeEach
    public void initTest() {
        skillCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillCategory() throws Exception {
        int databaseSizeBeforeCreate = skillCategoryRepository.findAll().size();

        // Create the SkillCategory
        restSkillCategoryMockMvc.perform(post("/api/skill-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillCategory)))
            .andExpect(status().isCreated());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        SkillCategory testSkillCategory = skillCategoryList.get(skillCategoryList.size() - 1);
        assertThat(testSkillCategory.getSkillCategoryCode()).isEqualTo(DEFAULT_SKILL_CATEGORY_CODE);
        assertThat(testSkillCategory.getSkillCategoryName()).isEqualTo(DEFAULT_SKILL_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void createSkillCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillCategoryRepository.findAll().size();

        // Create the SkillCategory with an existing ID
        skillCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillCategoryMockMvc.perform(post("/api/skill-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillCategory)))
            .andExpect(status().isBadRequest());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSkillCategories() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        // Get all the skillCategoryList
        restSkillCategoryMockMvc.perform(get("/api/skill-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].skillCategoryCode").value(hasItem(DEFAULT_SKILL_CATEGORY_CODE.toString())))
            .andExpect(jsonPath("$.[*].skillCategoryName").value(hasItem(DEFAULT_SKILL_CATEGORY_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getSkillCategory() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        // Get the skillCategory
        restSkillCategoryMockMvc.perform(get("/api/skill-categories/{id}", skillCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skillCategory.getId().intValue()))
            .andExpect(jsonPath("$.skillCategoryCode").value(DEFAULT_SKILL_CATEGORY_CODE.toString()))
            .andExpect(jsonPath("$.skillCategoryName").value(DEFAULT_SKILL_CATEGORY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllSkillCategoriesBySkillCategoryCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        // Get all the skillCategoryList where skillCategoryCode equals to DEFAULT_SKILL_CATEGORY_CODE
        defaultSkillCategoryShouldBeFound("skillCategoryCode.equals=" + DEFAULT_SKILL_CATEGORY_CODE);

        // Get all the skillCategoryList where skillCategoryCode equals to UPDATED_SKILL_CATEGORY_CODE
        defaultSkillCategoryShouldNotBeFound("skillCategoryCode.equals=" + UPDATED_SKILL_CATEGORY_CODE);
    }

    @Test
    @Transactional
    public void getAllSkillCategoriesBySkillCategoryCodeIsInShouldWork() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        // Get all the skillCategoryList where skillCategoryCode in DEFAULT_SKILL_CATEGORY_CODE or UPDATED_SKILL_CATEGORY_CODE
        defaultSkillCategoryShouldBeFound("skillCategoryCode.in=" + DEFAULT_SKILL_CATEGORY_CODE + "," + UPDATED_SKILL_CATEGORY_CODE);

        // Get all the skillCategoryList where skillCategoryCode equals to UPDATED_SKILL_CATEGORY_CODE
        defaultSkillCategoryShouldNotBeFound("skillCategoryCode.in=" + UPDATED_SKILL_CATEGORY_CODE);
    }

    @Test
    @Transactional
    public void getAllSkillCategoriesBySkillCategoryCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        // Get all the skillCategoryList where skillCategoryCode is not null
        defaultSkillCategoryShouldBeFound("skillCategoryCode.specified=true");

        // Get all the skillCategoryList where skillCategoryCode is null
        defaultSkillCategoryShouldNotBeFound("skillCategoryCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillCategoriesBySkillCategoryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        // Get all the skillCategoryList where skillCategoryName equals to DEFAULT_SKILL_CATEGORY_NAME
        defaultSkillCategoryShouldBeFound("skillCategoryName.equals=" + DEFAULT_SKILL_CATEGORY_NAME);

        // Get all the skillCategoryList where skillCategoryName equals to UPDATED_SKILL_CATEGORY_NAME
        defaultSkillCategoryShouldNotBeFound("skillCategoryName.equals=" + UPDATED_SKILL_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllSkillCategoriesBySkillCategoryNameIsInShouldWork() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        // Get all the skillCategoryList where skillCategoryName in DEFAULT_SKILL_CATEGORY_NAME or UPDATED_SKILL_CATEGORY_NAME
        defaultSkillCategoryShouldBeFound("skillCategoryName.in=" + DEFAULT_SKILL_CATEGORY_NAME + "," + UPDATED_SKILL_CATEGORY_NAME);

        // Get all the skillCategoryList where skillCategoryName equals to UPDATED_SKILL_CATEGORY_NAME
        defaultSkillCategoryShouldNotBeFound("skillCategoryName.in=" + UPDATED_SKILL_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllSkillCategoriesBySkillCategoryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillCategoryRepository.saveAndFlush(skillCategory);

        // Get all the skillCategoryList where skillCategoryName is not null
        defaultSkillCategoryShouldBeFound("skillCategoryName.specified=true");

        // Get all the skillCategoryList where skillCategoryName is null
        defaultSkillCategoryShouldNotBeFound("skillCategoryName.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSkillCategoryShouldBeFound(String filter) throws Exception {
        restSkillCategoryMockMvc.perform(get("/api/skill-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].skillCategoryCode").value(hasItem(DEFAULT_SKILL_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].skillCategoryName").value(hasItem(DEFAULT_SKILL_CATEGORY_NAME)));

        // Check, that the count call also returns 1
        restSkillCategoryMockMvc.perform(get("/api/skill-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSkillCategoryShouldNotBeFound(String filter) throws Exception {
        restSkillCategoryMockMvc.perform(get("/api/skill-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSkillCategoryMockMvc.perform(get("/api/skill-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSkillCategory() throws Exception {
        // Get the skillCategory
        restSkillCategoryMockMvc.perform(get("/api/skill-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillCategory() throws Exception {
        // Initialize the database
        skillCategoryService.save(skillCategory);

        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();

        // Update the skillCategory
        SkillCategory updatedSkillCategory = skillCategoryRepository.findById(skillCategory.getId()).get();
        // Disconnect from session so that the updates on updatedSkillCategory are not directly saved in db
        em.detach(updatedSkillCategory);
        updatedSkillCategory
            .skillCategoryCode(UPDATED_SKILL_CATEGORY_CODE)
            .skillCategoryName(UPDATED_SKILL_CATEGORY_NAME);

        restSkillCategoryMockMvc.perform(put("/api/skill-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSkillCategory)))
            .andExpect(status().isOk());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
        SkillCategory testSkillCategory = skillCategoryList.get(skillCategoryList.size() - 1);
        assertThat(testSkillCategory.getSkillCategoryCode()).isEqualTo(UPDATED_SKILL_CATEGORY_CODE);
        assertThat(testSkillCategory.getSkillCategoryName()).isEqualTo(UPDATED_SKILL_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillCategory() throws Exception {
        int databaseSizeBeforeUpdate = skillCategoryRepository.findAll().size();

        // Create the SkillCategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillCategoryMockMvc.perform(put("/api/skill-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillCategory)))
            .andExpect(status().isBadRequest());

        // Validate the SkillCategory in the database
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkillCategory() throws Exception {
        // Initialize the database
        skillCategoryService.save(skillCategory);

        int databaseSizeBeforeDelete = skillCategoryRepository.findAll().size();

        // Delete the skillCategory
        restSkillCategoryMockMvc.perform(delete("/api/skill-categories/{id}", skillCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<SkillCategory> skillCategoryList = skillCategoryRepository.findAll();
        assertThat(skillCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillCategory.class);
        SkillCategory skillCategory1 = new SkillCategory();
        skillCategory1.setId(1L);
        SkillCategory skillCategory2 = new SkillCategory();
        skillCategory2.setId(skillCategory1.getId());
        assertThat(skillCategory1).isEqualTo(skillCategory2);
        skillCategory2.setId(2L);
        assertThat(skillCategory1).isNotEqualTo(skillCategory2);
        skillCategory1.setId(null);
        assertThat(skillCategory1).isNotEqualTo(skillCategory2);
    }
}
