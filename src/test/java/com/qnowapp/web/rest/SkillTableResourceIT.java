package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.SkillTable;
import com.qnowapp.domain.SkillExpertise;
import com.qnowapp.domain.UserContact;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.Skills;
import com.qnowapp.repository.SkillTableRepository;
import com.qnowapp.service.SkillTableService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.SkillTableCriteria;
import com.qnowapp.service.SkillTableQueryService;

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
 * Integration tests for the {@Link SkillTableResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class SkillTableResourceIT {

    @Autowired
    private SkillTableRepository skillTableRepository;

    @Autowired
    private SkillTableService skillTableService;

    @Autowired
    private SkillTableQueryService skillTableQueryService;

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

    private MockMvc restSkillTableMockMvc;

    private SkillTable skillTable;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillTableResource skillTableResource = new SkillTableResource(skillTableService, skillTableQueryService);
        this.restSkillTableMockMvc = MockMvcBuilders.standaloneSetup(skillTableResource)
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
    public static SkillTable createEntity(EntityManager em) {
        SkillTable skillTable = new SkillTable();
        return skillTable;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillTable createUpdatedEntity(EntityManager em) {
        SkillTable skillTable = new SkillTable();
        return skillTable;
    }

    @BeforeEach
    public void initTest() {
        skillTable = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillTable() throws Exception {
        int databaseSizeBeforeCreate = skillTableRepository.findAll().size();

        // Create the SkillTable
        restSkillTableMockMvc.perform(post("/api/skill-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillTable)))
            .andExpect(status().isCreated());

        // Validate the SkillTable in the database
        List<SkillTable> skillTableList = skillTableRepository.findAll();
        assertThat(skillTableList).hasSize(databaseSizeBeforeCreate + 1);
        SkillTable testSkillTable = skillTableList.get(skillTableList.size() - 1);
    }

    @Test
    @Transactional
    public void createSkillTableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillTableRepository.findAll().size();

        // Create the SkillTable with an existing ID
        skillTable.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillTableMockMvc.perform(post("/api/skill-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillTable)))
            .andExpect(status().isBadRequest());

        // Validate the SkillTable in the database
        List<SkillTable> skillTableList = skillTableRepository.findAll();
        assertThat(skillTableList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSkillTables() throws Exception {
        // Initialize the database
        skillTableRepository.saveAndFlush(skillTable);

        // Get all the skillTableList
        restSkillTableMockMvc.perform(get("/api/skill-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillTable.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getSkillTable() throws Exception {
        // Initialize the database
        skillTableRepository.saveAndFlush(skillTable);

        // Get the skillTable
        restSkillTableMockMvc.perform(get("/api/skill-tables/{id}", skillTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skillTable.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllSkillTablesBySkillExpertiseIsEqualToSomething() throws Exception {
        // Initialize the database
        SkillExpertise skillExpertise = SkillExpertiseResourceIT.createEntity(em);
        em.persist(skillExpertise);
        em.flush();
        skillTable.setSkillExpertise(skillExpertise);
        skillTableRepository.saveAndFlush(skillTable);
        Long skillExpertiseId = skillExpertise.getId();

        // Get all the skillTableList where skillExpertise equals to skillExpertiseId
        defaultSkillTableShouldBeFound("skillExpertiseId.equals=" + skillExpertiseId);

        // Get all the skillTableList where skillExpertise equals to skillExpertiseId + 1
        defaultSkillTableShouldNotBeFound("skillExpertiseId.equals=" + (skillExpertiseId + 1));
    }


    @Test
    @Transactional
    public void getAllSkillTablesByUserContactIsEqualToSomething() throws Exception {
        // Initialize the database
        UserContact userContact = UserContactResourceIT.createEntity(em);
        em.persist(userContact);
        em.flush();
        skillTable.setUserContact(userContact);
        skillTableRepository.saveAndFlush(skillTable);
        Long userContactId = userContact.getId();

        // Get all the skillTableList where userContact equals to userContactId
        defaultSkillTableShouldBeFound("userContactId.equals=" + userContactId);

        // Get all the skillTableList where userContact equals to userContactId + 1
        defaultSkillTableShouldNotBeFound("userContactId.equals=" + (userContactId + 1));
    }


    @Test
    @Transactional
    public void getAllSkillTablesByImProjectsIsEqualToSomething() throws Exception {
        // Initialize the database
        ImProjects imProjects = ImProjectsResourceIT.createEntity(em);
        em.persist(imProjects);
        em.flush();
        skillTable.setImProjects(imProjects);
        skillTableRepository.saveAndFlush(skillTable);
        Long imProjectsId = imProjects.getId();

        // Get all the skillTableList where imProjects equals to imProjectsId
        defaultSkillTableShouldBeFound("imProjectsId.equals=" + imProjectsId);

        // Get all the skillTableList where imProjects equals to imProjectsId + 1
        defaultSkillTableShouldNotBeFound("imProjectsId.equals=" + (imProjectsId + 1));
    }


    @Test
    @Transactional
    public void getAllSkillTablesBySkillsIsEqualToSomething() throws Exception {
        // Initialize the database
        Skills skills = SkillsResourceIT.createEntity(em);
        em.persist(skills);
        em.flush();
        skillTable.setSkills(skills);
        skillTableRepository.saveAndFlush(skillTable);
        Long skillsId = skills.getId();

        // Get all the skillTableList where skills equals to skillsId
        defaultSkillTableShouldBeFound("skillsId.equals=" + skillsId);

        // Get all the skillTableList where skills equals to skillsId + 1
        defaultSkillTableShouldNotBeFound("skillsId.equals=" + (skillsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSkillTableShouldBeFound(String filter) throws Exception {
        restSkillTableMockMvc.perform(get("/api/skill-tables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillTable.getId().intValue())));

        // Check, that the count call also returns 1
        restSkillTableMockMvc.perform(get("/api/skill-tables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSkillTableShouldNotBeFound(String filter) throws Exception {
        restSkillTableMockMvc.perform(get("/api/skill-tables?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSkillTableMockMvc.perform(get("/api/skill-tables/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSkillTable() throws Exception {
        // Get the skillTable
        restSkillTableMockMvc.perform(get("/api/skill-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillTable() throws Exception {
        // Initialize the database
        skillTableService.save(skillTable);

        int databaseSizeBeforeUpdate = skillTableRepository.findAll().size();

        // Update the skillTable
        SkillTable updatedSkillTable = skillTableRepository.findById(skillTable.getId()).get();
        // Disconnect from session so that the updates on updatedSkillTable are not directly saved in db
        em.detach(updatedSkillTable);

        restSkillTableMockMvc.perform(put("/api/skill-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSkillTable)))
            .andExpect(status().isOk());

        // Validate the SkillTable in the database
        List<SkillTable> skillTableList = skillTableRepository.findAll();
        assertThat(skillTableList).hasSize(databaseSizeBeforeUpdate);
        SkillTable testSkillTable = skillTableList.get(skillTableList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillTable() throws Exception {
        int databaseSizeBeforeUpdate = skillTableRepository.findAll().size();

        // Create the SkillTable

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillTableMockMvc.perform(put("/api/skill-tables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillTable)))
            .andExpect(status().isBadRequest());

        // Validate the SkillTable in the database
        List<SkillTable> skillTableList = skillTableRepository.findAll();
        assertThat(skillTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkillTable() throws Exception {
        // Initialize the database
        skillTableService.save(skillTable);

        int databaseSizeBeforeDelete = skillTableRepository.findAll().size();

        // Delete the skillTable
        restSkillTableMockMvc.perform(delete("/api/skill-tables/{id}", skillTable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<SkillTable> skillTableList = skillTableRepository.findAll();
        assertThat(skillTableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillTable.class);
        SkillTable skillTable1 = new SkillTable();
        skillTable1.setId(1L);
        SkillTable skillTable2 = new SkillTable();
        skillTable2.setId(skillTable1.getId());
        assertThat(skillTable1).isEqualTo(skillTable2);
        skillTable2.setId(2L);
        assertThat(skillTable1).isNotEqualTo(skillTable2);
        skillTable1.setId(null);
        assertThat(skillTable1).isNotEqualTo(skillTable2);
    }
}
