package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.TagType;
import com.qnowapp.repository.TagTypeRepository;
import com.qnowapp.service.TagTypeService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.TagTypeCriteria;
import com.qnowapp.service.TagTypeQueryService;

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
 * Integration tests for the {@Link TagTypeResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class TagTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TagTypeRepository tagTypeRepository;

    @Autowired
    private TagTypeService tagTypeService;

    @Autowired
    private TagTypeQueryService tagTypeQueryService;

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

    private MockMvc restTagTypeMockMvc;

    private TagType tagType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TagTypeResource tagTypeResource = new TagTypeResource(tagTypeService, tagTypeQueryService);
        this.restTagTypeMockMvc = MockMvcBuilders.standaloneSetup(tagTypeResource)
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
    public static TagType createEntity(EntityManager em) {
        TagType tagType = new TagType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return tagType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TagType createUpdatedEntity(EntityManager em) {
        TagType tagType = new TagType()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return tagType;
    }

    @BeforeEach
    public void initTest() {
        tagType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTagType() throws Exception {
        int databaseSizeBeforeCreate = tagTypeRepository.findAll().size();

        // Create the TagType
        restTagTypeMockMvc.perform(post("/api/tag-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagType)))
            .andExpect(status().isCreated());

        // Validate the TagType in the database
        List<TagType> tagTypeList = tagTypeRepository.findAll();
        assertThat(tagTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TagType testTagType = tagTypeList.get(tagTypeList.size() - 1);
        assertThat(testTagType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTagType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTagType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTagTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tagTypeRepository.findAll().size();

        // Create the TagType with an existing ID
        tagType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTagTypeMockMvc.perform(post("/api/tag-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagType)))
            .andExpect(status().isBadRequest());

        // Validate the TagType in the database
        List<TagType> tagTypeList = tagTypeRepository.findAll();
        assertThat(tagTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTagTypes() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList
        restTagTypeMockMvc.perform(get("/api/tag-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getTagType() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get the tagType
        restTagTypeMockMvc.perform(get("/api/tag-types/{id}", tagType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tagType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllTagTypesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where code equals to DEFAULT_CODE
        defaultTagTypeShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the tagTypeList where code equals to UPDATED_CODE
        defaultTagTypeShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTagTypesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTagTypeShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the tagTypeList where code equals to UPDATED_CODE
        defaultTagTypeShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllTagTypesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where code is not null
        defaultTagTypeShouldBeFound("code.specified=true");

        // Get all the tagTypeList where code is null
        defaultTagTypeShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllTagTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where name equals to DEFAULT_NAME
        defaultTagTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the tagTypeList where name equals to UPDATED_NAME
        defaultTagTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTagTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTagTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the tagTypeList where name equals to UPDATED_NAME
        defaultTagTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTagTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where name is not null
        defaultTagTypeShouldBeFound("name.specified=true");

        // Get all the tagTypeList where name is null
        defaultTagTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllTagTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where description equals to DEFAULT_DESCRIPTION
        defaultTagTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the tagTypeList where description equals to UPDATED_DESCRIPTION
        defaultTagTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTagTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTagTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the tagTypeList where description equals to UPDATED_DESCRIPTION
        defaultTagTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTagTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tagTypeRepository.saveAndFlush(tagType);

        // Get all the tagTypeList where description is not null
        defaultTagTypeShouldBeFound("description.specified=true");

        // Get all the tagTypeList where description is null
        defaultTagTypeShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTagTypeShouldBeFound(String filter) throws Exception {
        restTagTypeMockMvc.perform(get("/api/tag-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tagType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restTagTypeMockMvc.perform(get("/api/tag-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTagTypeShouldNotBeFound(String filter) throws Exception {
        restTagTypeMockMvc.perform(get("/api/tag-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTagTypeMockMvc.perform(get("/api/tag-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTagType() throws Exception {
        // Get the tagType
        restTagTypeMockMvc.perform(get("/api/tag-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTagType() throws Exception {
        // Initialize the database
        tagTypeService.save(tagType);

        int databaseSizeBeforeUpdate = tagTypeRepository.findAll().size();

        // Update the tagType
        TagType updatedTagType = tagTypeRepository.findById(tagType.getId()).get();
        // Disconnect from session so that the updates on updatedTagType are not directly saved in db
        em.detach(updatedTagType);
        updatedTagType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restTagTypeMockMvc.perform(put("/api/tag-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTagType)))
            .andExpect(status().isOk());

        // Validate the TagType in the database
        List<TagType> tagTypeList = tagTypeRepository.findAll();
        assertThat(tagTypeList).hasSize(databaseSizeBeforeUpdate);
        TagType testTagType = tagTypeList.get(tagTypeList.size() - 1);
        assertThat(testTagType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTagType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTagType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTagType() throws Exception {
        int databaseSizeBeforeUpdate = tagTypeRepository.findAll().size();

        // Create the TagType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTagTypeMockMvc.perform(put("/api/tag-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tagType)))
            .andExpect(status().isBadRequest());

        // Validate the TagType in the database
        List<TagType> tagTypeList = tagTypeRepository.findAll();
        assertThat(tagTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTagType() throws Exception {
        // Initialize the database
        tagTypeService.save(tagType);

        int databaseSizeBeforeDelete = tagTypeRepository.findAll().size();

        // Delete the tagType
        restTagTypeMockMvc.perform(delete("/api/tag-types/{id}", tagType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TagType> tagTypeList = tagTypeRepository.findAll();
        assertThat(tagTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TagType.class);
        TagType tagType1 = new TagType();
        tagType1.setId(1L);
        TagType tagType2 = new TagType();
        tagType2.setId(tagType1.getId());
        assertThat(tagType1).isEqualTo(tagType2);
        tagType2.setId(2L);
        assertThat(tagType1).isNotEqualTo(tagType2);
        tagType1.setId(null);
        assertThat(tagType1).isNotEqualTo(tagType2);
    }
}
