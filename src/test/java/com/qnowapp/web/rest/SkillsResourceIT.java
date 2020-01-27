package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.Skills;
import com.qnowapp.domain.Skills;
import com.qnowapp.domain.SkillCategory;
import com.qnowapp.repository.SkillsRepository;
import com.qnowapp.service.SkillsService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.SkillsCriteria;
import com.qnowapp.service.SkillsQueryService;

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
 * Integration tests for the {@Link SkillsResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class SkillsResourceIT {

    private static final String DEFAULT_SKILL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SKILL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SKILL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SKILL_NAME = "BBBBBBBBBB";

    @Autowired
    private SkillsRepository skillsRepository;

    @Autowired
    private SkillsService skillsService;

    @Autowired
    private SkillsQueryService skillsQueryService;

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

    private MockMvc restSkillsMockMvc;

    private Skills skills;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillsResource skillsResource = new SkillsResource(skillsService, skillsQueryService);
        this.restSkillsMockMvc = MockMvcBuilders.standaloneSetup(skillsResource)
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
    public static Skills createEntity(EntityManager em) {
        Skills skills = new Skills()
            .skillCode(DEFAULT_SKILL_CODE)
            .skillName(DEFAULT_SKILL_NAME);
        return skills;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skills createUpdatedEntity(EntityManager em) {
        Skills skills = new Skills()
            .skillCode(UPDATED_SKILL_CODE)
            .skillName(UPDATED_SKILL_NAME);
        return skills;
    }

    @BeforeEach
    public void initTest() {
        skills = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkills() throws Exception {
        int databaseSizeBeforeCreate = skillsRepository.findAll().size();

        // Create the Skills
        restSkillsMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skills)))
            .andExpect(status().isCreated());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeCreate + 1);
        Skills testSkills = skillsList.get(skillsList.size() - 1);
        assertThat(testSkills.getSkillCode()).isEqualTo(DEFAULT_SKILL_CODE);
        assertThat(testSkills.getSkillName()).isEqualTo(DEFAULT_SKILL_NAME);
    }

    @Test
    @Transactional
    public void createSkillsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillsRepository.findAll().size();

        // Create the Skills with an existing ID
        skills.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillsMockMvc.perform(post("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skills)))
            .andExpect(status().isBadRequest());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSkills() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList
        restSkillsMockMvc.perform(get("/api/skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skills.getId().intValue())))
            .andExpect(jsonPath("$.[*].skillCode").value(hasItem(DEFAULT_SKILL_CODE.toString())))
            .andExpect(jsonPath("$.[*].skillName").value(hasItem(DEFAULT_SKILL_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getSkills() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get the skills
        restSkillsMockMvc.perform(get("/api/skills/{id}", skills.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skills.getId().intValue()))
            .andExpect(jsonPath("$.skillCode").value(DEFAULT_SKILL_CODE.toString()))
            .andExpect(jsonPath("$.skillName").value(DEFAULT_SKILL_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllSkillsBySkillCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where skillCode equals to DEFAULT_SKILL_CODE
        defaultSkillsShouldBeFound("skillCode.equals=" + DEFAULT_SKILL_CODE);

        // Get all the skillsList where skillCode equals to UPDATED_SKILL_CODE
        defaultSkillsShouldNotBeFound("skillCode.equals=" + UPDATED_SKILL_CODE);
    }

    @Test
    @Transactional
    public void getAllSkillsBySkillCodeIsInShouldWork() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where skillCode in DEFAULT_SKILL_CODE or UPDATED_SKILL_CODE
        defaultSkillsShouldBeFound("skillCode.in=" + DEFAULT_SKILL_CODE + "," + UPDATED_SKILL_CODE);

        // Get all the skillsList where skillCode equals to UPDATED_SKILL_CODE
        defaultSkillsShouldNotBeFound("skillCode.in=" + UPDATED_SKILL_CODE);
    }

    @Test
    @Transactional
    public void getAllSkillsBySkillCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where skillCode is not null
        defaultSkillsShouldBeFound("skillCode.specified=true");

        // Get all the skillsList where skillCode is null
        defaultSkillsShouldNotBeFound("skillCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillsBySkillNameIsEqualToSomething() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where skillName equals to DEFAULT_SKILL_NAME
        defaultSkillsShouldBeFound("skillName.equals=" + DEFAULT_SKILL_NAME);

        // Get all the skillsList where skillName equals to UPDATED_SKILL_NAME
        defaultSkillsShouldNotBeFound("skillName.equals=" + UPDATED_SKILL_NAME);
    }

    @Test
    @Transactional
    public void getAllSkillsBySkillNameIsInShouldWork() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where skillName in DEFAULT_SKILL_NAME or UPDATED_SKILL_NAME
        defaultSkillsShouldBeFound("skillName.in=" + DEFAULT_SKILL_NAME + "," + UPDATED_SKILL_NAME);

        // Get all the skillsList where skillName equals to UPDATED_SKILL_NAME
        defaultSkillsShouldNotBeFound("skillName.in=" + UPDATED_SKILL_NAME);
    }

    @Test
    @Transactional
    public void getAllSkillsBySkillNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillsRepository.saveAndFlush(skills);

        // Get all the skillsList where skillName is not null
        defaultSkillsShouldBeFound("skillName.specified=true");

        // Get all the skillsList where skillName is null
        defaultSkillsShouldNotBeFound("skillName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkillsByParentSkillsIdIsEqualToSomething() throws Exception {
        // Initialize the database
        Skills parentSkillsId = SkillsResourceIT.createEntity(em);
        em.persist(parentSkillsId);
        em.flush();
        skills.setParentSkillsId(parentSkillsId);
        skillsRepository.saveAndFlush(skills);
        Long parentSkillsIdId = parentSkillsId.getId();

        // Get all the skillsList where parentSkillsId equals to parentSkillsIdId
        defaultSkillsShouldBeFound("parentSkillsIdId.equals=" + parentSkillsIdId);

        // Get all the skillsList where parentSkillsId equals to parentSkillsIdId + 1
        defaultSkillsShouldNotBeFound("parentSkillsIdId.equals=" + (parentSkillsIdId + 1));
    }


    @Test
    @Transactional
    public void getAllSkillsBySkillCategoryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        SkillCategory skillCategoryId = SkillCategoryResourceIT.createEntity(em);
        em.persist(skillCategoryId);
        em.flush();
        skills.setSkillCategoryId(skillCategoryId);
        skillsRepository.saveAndFlush(skills);
        Long skillCategoryIdId = skillCategoryId.getId();

        // Get all the skillsList where skillCategoryId equals to skillCategoryIdId
        defaultSkillsShouldBeFound("skillCategoryIdId.equals=" + skillCategoryIdId);

        // Get all the skillsList where skillCategoryId equals to skillCategoryIdId + 1
        defaultSkillsShouldNotBeFound("skillCategoryIdId.equals=" + (skillCategoryIdId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSkillsShouldBeFound(String filter) throws Exception {
        restSkillsMockMvc.perform(get("/api/skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skills.getId().intValue())))
            .andExpect(jsonPath("$.[*].skillCode").value(hasItem(DEFAULT_SKILL_CODE)))
            .andExpect(jsonPath("$.[*].skillName").value(hasItem(DEFAULT_SKILL_NAME)));

        // Check, that the count call also returns 1
        restSkillsMockMvc.perform(get("/api/skills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSkillsShouldNotBeFound(String filter) throws Exception {
        restSkillsMockMvc.perform(get("/api/skills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSkillsMockMvc.perform(get("/api/skills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSkills() throws Exception {
        // Get the skills
        restSkillsMockMvc.perform(get("/api/skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkills() throws Exception {
        // Initialize the database
        skillsService.save(skills);

        int databaseSizeBeforeUpdate = skillsRepository.findAll().size();

        // Update the skills
        Skills updatedSkills = skillsRepository.findById(skills.getId()).get();
        // Disconnect from session so that the updates on updatedSkills are not directly saved in db
        em.detach(updatedSkills);
        updatedSkills
            .skillCode(UPDATED_SKILL_CODE)
            .skillName(UPDATED_SKILL_NAME);

        restSkillsMockMvc.perform(put("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSkills)))
            .andExpect(status().isOk());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeUpdate);
        Skills testSkills = skillsList.get(skillsList.size() - 1);
        assertThat(testSkills.getSkillCode()).isEqualTo(UPDATED_SKILL_CODE);
        assertThat(testSkills.getSkillName()).isEqualTo(UPDATED_SKILL_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSkills() throws Exception {
        int databaseSizeBeforeUpdate = skillsRepository.findAll().size();

        // Create the Skills

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillsMockMvc.perform(put("/api/skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skills)))
            .andExpect(status().isBadRequest());

        // Validate the Skills in the database
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkills() throws Exception {
        // Initialize the database
        skillsService.save(skills);

        int databaseSizeBeforeDelete = skillsRepository.findAll().size();

        // Delete the skills
        restSkillsMockMvc.perform(delete("/api/skills/{id}", skills.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Skills> skillsList = skillsRepository.findAll();
        assertThat(skillsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Skills.class);
        Skills skills1 = new Skills();
        skills1.setId(1L);
        Skills skills2 = new Skills();
        skills2.setId(skills1.getId());
        assertThat(skills1).isEqualTo(skills2);
        skills2.setId(2L);
        assertThat(skills1).isNotEqualTo(skills2);
        skills1.setId(null);
        assertThat(skills1).isNotEqualTo(skills2);
    }
}
