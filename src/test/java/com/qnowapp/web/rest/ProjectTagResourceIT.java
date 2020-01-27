package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.ProjectTag;
import com.qnowapp.domain.TagType;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.repository.ProjectTagRepository;
import com.qnowapp.service.ProjectTagService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.ProjectTagCriteria;
import com.qnowapp.service.ProjectTagQueryService;

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
 * Integration tests for the {@Link ProjectTagResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class ProjectTagResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    @Autowired
    private ProjectTagRepository projectTagRepository;

    @Autowired
    private ProjectTagService projectTagService;

    @Autowired
    private ProjectTagQueryService projectTagQueryService;

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

    private MockMvc restProjectTagMockMvc;

    private ProjectTag projectTag;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectTagResource projectTagResource = new ProjectTagResource( projectTagService, projectTagQueryService);
        this.restProjectTagMockMvc = MockMvcBuilders.standaloneSetup(projectTagResource)
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
    public static ProjectTag createEntity(EntityManager em) {
        ProjectTag projectTag = new ProjectTag()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .flag(DEFAULT_FLAG)
            .color(DEFAULT_COLOR);
        return projectTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectTag createUpdatedEntity(EntityManager em) {
        ProjectTag projectTag = new ProjectTag()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .flag(UPDATED_FLAG)
            .color(UPDATED_COLOR);
        return projectTag;
    }

    @BeforeEach
    public void initTest() {
        projectTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectTag() throws Exception {
        int databaseSizeBeforeCreate = projectTagRepository.findAll().size();

        // Create the ProjectTag
        restProjectTagMockMvc.perform(post("/api/project-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTag)))
            .andExpect(status().isCreated());

        // Validate the ProjectTag in the database
        List<ProjectTag> projectTagList = projectTagRepository.findAll();
        assertThat(projectTagList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectTag testProjectTag = projectTagList.get(projectTagList.size() - 1);
        assertThat(testProjectTag.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectTag.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProjectTag.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectTag.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testProjectTag.getColor()).isEqualTo(DEFAULT_COLOR);
    }

    @Test
    @Transactional
    public void createProjectTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectTagRepository.findAll().size();

        // Create the ProjectTag with an existing ID
        projectTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectTagMockMvc.perform(post("/api/project-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTag)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTag in the database
        List<ProjectTag> projectTagList = projectTagRepository.findAll();
        assertThat(projectTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProjectTags() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList
        restProjectTagMockMvc.perform(get("/api/project-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())));
    }
    
    @Test
    @Transactional
    public void getProjectTag() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get the projectTag
        restProjectTagMockMvc.perform(get("/api/project-tags/{id}", projectTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectTag.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()));
    }

    @Test
    @Transactional
    public void getAllProjectTagsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where code equals to DEFAULT_CODE
        defaultProjectTagShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the projectTagList where code equals to UPDATED_CODE
        defaultProjectTagShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectTagsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProjectTagShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the projectTagList where code equals to UPDATED_CODE
        defaultProjectTagShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProjectTagsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where code is not null
        defaultProjectTagShouldBeFound("code.specified=true");

        // Get all the projectTagList where code is null
        defaultProjectTagShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectTagsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where name equals to DEFAULT_NAME
        defaultProjectTagShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the projectTagList where name equals to UPDATED_NAME
        defaultProjectTagShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectTagsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProjectTagShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the projectTagList where name equals to UPDATED_NAME
        defaultProjectTagShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProjectTagsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where name is not null
        defaultProjectTagShouldBeFound("name.specified=true");

        // Get all the projectTagList where name is null
        defaultProjectTagShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectTagsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where description equals to DEFAULT_DESCRIPTION
        defaultProjectTagShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the projectTagList where description equals to UPDATED_DESCRIPTION
        defaultProjectTagShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectTagsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProjectTagShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the projectTagList where description equals to UPDATED_DESCRIPTION
        defaultProjectTagShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProjectTagsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where description is not null
        defaultProjectTagShouldBeFound("description.specified=true");

        // Get all the projectTagList where description is null
        defaultProjectTagShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectTagsByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where flag equals to DEFAULT_FLAG
        defaultProjectTagShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the projectTagList where flag equals to UPDATED_FLAG
        defaultProjectTagShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    public void getAllProjectTagsByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultProjectTagShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the projectTagList where flag equals to UPDATED_FLAG
        defaultProjectTagShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    public void getAllProjectTagsByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where flag is not null
        defaultProjectTagShouldBeFound("flag.specified=true");

        // Get all the projectTagList where flag is null
        defaultProjectTagShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectTagsByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where color equals to DEFAULT_COLOR
        defaultProjectTagShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the projectTagList where color equals to UPDATED_COLOR
        defaultProjectTagShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllProjectTagsByColorIsInShouldWork() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultProjectTagShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the projectTagList where color equals to UPDATED_COLOR
        defaultProjectTagShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllProjectTagsByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        projectTagRepository.saveAndFlush(projectTag);

        // Get all the projectTagList where color is not null
        defaultProjectTagShouldBeFound("color.specified=true");

        // Get all the projectTagList where color is null
        defaultProjectTagShouldNotBeFound("color.specified=false");
    }

    @Test
    @Transactional
    public void getAllProjectTagsByTagTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        TagType tagType = TagTypeResourceIT.createEntity(em);
        em.persist(tagType);
        em.flush();
        projectTag.setTagType(tagType);
        projectTagRepository.saveAndFlush(projectTag);
        Long tagTypeId = tagType.getId();

        // Get all the projectTagList where tagType equals to tagTypeId
        defaultProjectTagShouldBeFound("tagTypeId.equals=" + tagTypeId);

        // Get all the projectTagList where tagType equals to tagTypeId + 1
        defaultProjectTagShouldNotBeFound("tagTypeId.equals=" + (tagTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllProjectTagsByImEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        ImEmployee imEmployee = ImEmployeeResourceIT.createEntity(em);
        em.persist(imEmployee);
        em.flush();
        projectTag.setImEmployee(imEmployee);
        projectTagRepository.saveAndFlush(projectTag);
        Long imEmployeeId = imEmployee.getId();

        // Get all the projectTagList where imEmployee equals to imEmployeeId
        defaultProjectTagShouldBeFound("imEmployeeId.equals=" + imEmployeeId);

        // Get all the projectTagList where imEmployee equals to imEmployeeId + 1
        defaultProjectTagShouldNotBeFound("imEmployeeId.equals=" + (imEmployeeId + 1));
    }


    @Test
    @Transactional
    public void getAllProjectTagsByImProjectsIsEqualToSomething() throws Exception {
        // Initialize the database
        ImProjects imProjects = ImProjectsResourceIT.createEntity(em);
        em.persist(imProjects);
        em.flush();
        projectTag.setImProjects(imProjects);
        projectTagRepository.saveAndFlush(projectTag);
        Long imProjectsId = imProjects.getId();

        // Get all the projectTagList where imProjects equals to imProjectsId
        defaultProjectTagShouldBeFound("imProjectsId.equals=" + imProjectsId);

        // Get all the projectTagList where imProjects equals to imProjectsId + 1
        defaultProjectTagShouldNotBeFound("imProjectsId.equals=" + (imProjectsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProjectTagShouldBeFound(String filter) throws Exception {
        restProjectTagMockMvc.perform(get("/api/project-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)));

        // Check, that the count call also returns 1
        restProjectTagMockMvc.perform(get("/api/project-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProjectTagShouldNotBeFound(String filter) throws Exception {
        restProjectTagMockMvc.perform(get("/api/project-tags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProjectTagMockMvc.perform(get("/api/project-tags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProjectTag() throws Exception {
        // Get the projectTag
        restProjectTagMockMvc.perform(get("/api/project-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectTag() throws Exception {
        // Initialize the database
        projectTagService.save(projectTag);

        int databaseSizeBeforeUpdate = projectTagRepository.findAll().size();

        // Update the projectTag
        ProjectTag updatedProjectTag = projectTagRepository.findById(projectTag.getId()).get();
        // Disconnect from session so that the updates on updatedProjectTag are not directly saved in db
        em.detach(updatedProjectTag);
        updatedProjectTag
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .flag(UPDATED_FLAG)
            .color(UPDATED_COLOR);

        restProjectTagMockMvc.perform(put("/api/project-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectTag)))
            .andExpect(status().isOk());

        // Validate the ProjectTag in the database
        List<ProjectTag> projectTagList = projectTagRepository.findAll();
        assertThat(projectTagList).hasSize(databaseSizeBeforeUpdate);
        ProjectTag testProjectTag = projectTagList.get(projectTagList.size() - 1);
        assertThat(testProjectTag.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectTag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProjectTag.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectTag.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testProjectTag.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectTag() throws Exception {
        int databaseSizeBeforeUpdate = projectTagRepository.findAll().size();

        // Create the ProjectTag

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectTagMockMvc.perform(put("/api/project-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectTag)))
            .andExpect(status().isBadRequest());

        // Validate the ProjectTag in the database
        List<ProjectTag> projectTagList = projectTagRepository.findAll();
        assertThat(projectTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectTag() throws Exception {
        // Initialize the database
        projectTagService.save(projectTag);

        int databaseSizeBeforeDelete = projectTagRepository.findAll().size();

        // Delete the projectTag
        restProjectTagMockMvc.perform(delete("/api/project-tags/{id}", projectTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProjectTag> projectTagList = projectTagRepository.findAll();
        assertThat(projectTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectTag.class);
        ProjectTag projectTag1 = new ProjectTag();
        projectTag1.setId(1L);
        ProjectTag projectTag2 = new ProjectTag();
        projectTag2.setId(projectTag1.getId());
        assertThat(projectTag1).isEqualTo(projectTag2);
        projectTag2.setId(2L);
        assertThat(projectTag1).isNotEqualTo(projectTag2);
        projectTag1.setId(null);
        assertThat(projectTag1).isNotEqualTo(projectTag2);
    }
}
