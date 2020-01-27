package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.FileStorage;
import com.qnowapp.domain.UserContact;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.repository.FileStorageRepository;
import com.qnowapp.service.FileStorageService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.FileStorageQueryService;

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

@SpringBootTest(classes = Qnow1App.class)
public class FileStorageResourceIT {

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    @Autowired
    private FileStorageRepository fileStorageRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileStorageQueryService fileStorageQueryService;

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

    private MockMvc restFileStorageMockMvc;

    private FileStorage fileStorage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileStorageResource fileStorageResource = new FileStorageResource(fileStorageService, fileStorageQueryService);
        this.restFileStorageMockMvc = MockMvcBuilders.standaloneSetup(fileStorageResource)
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
    public static FileStorage createEntity(EntityManager em) {
        FileStorage fileStorage = new FileStorage()
            .filename(DEFAULT_FILENAME)
            .caption(DEFAULT_CAPTION);
        return fileStorage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileStorage createUpdatedEntity(EntityManager em) {
        FileStorage fileStorage = new FileStorage()
            .filename(UPDATED_FILENAME)
            .caption(UPDATED_CAPTION);
        return fileStorage;
    }

    @BeforeEach
    public void initTest() {
        fileStorage = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileStorage() throws Exception {
        int databaseSizeBeforeCreate = fileStorageRepository.findAll().size();

        // Create the FileStorage
        restFileStorageMockMvc.perform(post("/api/file-storages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileStorage)))
            .andExpect(status().isCreated());

        // Validate the FileStorage in the database
        List<FileStorage> fileStorageList = fileStorageRepository.findAll();
        assertThat(fileStorageList).hasSize(databaseSizeBeforeCreate + 1);
        FileStorage testFileStorage = fileStorageList.get(fileStorageList.size() - 1);
        assertThat(testFileStorage.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testFileStorage.getCaption()).isEqualTo(DEFAULT_CAPTION);
    }

    @Test
    @Transactional
    public void createFileStorageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileStorageRepository.findAll().size();

        // Create the FileStorage with an existing ID
        fileStorage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileStorageMockMvc.perform(post("/api/file-storages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileStorage)))
            .andExpect(status().isBadRequest());

        // Validate the FileStorage in the database
        List<FileStorage> fileStorageList = fileStorageRepository.findAll();
        assertThat(fileStorageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFileStorages() throws Exception {
        // Initialize the database
        fileStorageRepository.saveAndFlush(fileStorage);

        // Get all the fileStorageList
        restFileStorageMockMvc.perform(get("/api/file-storages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileStorage.getId().intValue())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME.toString())))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getFileStorage() throws Exception {
        // Initialize the database
        fileStorageRepository.saveAndFlush(fileStorage);

        // Get the fileStorage
        restFileStorageMockMvc.perform(get("/api/file-storages/{id}", fileStorage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileStorage.getId().intValue()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME.toString()))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllFileStoragesByFilenameIsEqualToSomething() throws Exception {
        // Initialize the database
        fileStorageRepository.saveAndFlush(fileStorage);

        // Get all the fileStorageList where filename equals to DEFAULT_FILENAME
        defaultFileStorageShouldBeFound("filename.equals=" + DEFAULT_FILENAME);

        // Get all the fileStorageList where filename equals to UPDATED_FILENAME
        defaultFileStorageShouldNotBeFound("filename.equals=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    public void getAllFileStoragesByFilenameIsInShouldWork() throws Exception {
        // Initialize the database
        fileStorageRepository.saveAndFlush(fileStorage);

        // Get all the fileStorageList where filename in DEFAULT_FILENAME or UPDATED_FILENAME
        defaultFileStorageShouldBeFound("filename.in=" + DEFAULT_FILENAME + "," + UPDATED_FILENAME);

        // Get all the fileStorageList where filename equals to UPDATED_FILENAME
        defaultFileStorageShouldNotBeFound("filename.in=" + UPDATED_FILENAME);
    }

    @Test
    @Transactional
    public void getAllFileStoragesByFilenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileStorageRepository.saveAndFlush(fileStorage);

        // Get all the fileStorageList where filename is not null
        defaultFileStorageShouldBeFound("filename.specified=true");

        // Get all the fileStorageList where filename is null
        defaultFileStorageShouldNotBeFound("filename.specified=false");
    }

    @Test
    @Transactional
    public void getAllFileStoragesByCaptionIsEqualToSomething() throws Exception {
        // Initialize the database
        fileStorageRepository.saveAndFlush(fileStorage);

        // Get all the fileStorageList where caption equals to DEFAULT_CAPTION
        defaultFileStorageShouldBeFound("caption.equals=" + DEFAULT_CAPTION);

        // Get all the fileStorageList where caption equals to UPDATED_CAPTION
        defaultFileStorageShouldNotBeFound("caption.equals=" + UPDATED_CAPTION);
    }

    @Test
    @Transactional
    public void getAllFileStoragesByCaptionIsInShouldWork() throws Exception {
        // Initialize the database
        fileStorageRepository.saveAndFlush(fileStorage);

        // Get all the fileStorageList where caption in DEFAULT_CAPTION or UPDATED_CAPTION
        defaultFileStorageShouldBeFound("caption.in=" + DEFAULT_CAPTION + "," + UPDATED_CAPTION);

        // Get all the fileStorageList where caption equals to UPDATED_CAPTION
        defaultFileStorageShouldNotBeFound("caption.in=" + UPDATED_CAPTION);
    }

    @Test
    @Transactional
    public void getAllFileStoragesByCaptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileStorageRepository.saveAndFlush(fileStorage);

        // Get all the fileStorageList where caption is not null
        defaultFileStorageShouldBeFound("caption.specified=true");

        // Get all the fileStorageList where caption is null
        defaultFileStorageShouldNotBeFound("caption.specified=false");
    }

    @Test
    @Transactional
    public void getAllFileStoragesByUserContactIsEqualToSomething() throws Exception {
        // Initialize the database
        UserContact userContact = UserContactResourceIT.createEntity(em);
        em.persist(userContact);
        em.flush();
        fileStorage.setUserContact(userContact);
        fileStorageRepository.saveAndFlush(fileStorage);
        Long userContactId = userContact.getId();

        // Get all the fileStorageList where userContact equals to userContactId
        defaultFileStorageShouldBeFound("userContactId.equals=" + userContactId);

        // Get all the fileStorageList where userContact equals to userContactId + 1
        defaultFileStorageShouldNotBeFound("userContactId.equals=" + (userContactId + 1));
    }


    @Test
    @Transactional
    public void getAllFileStoragesByImProjectsIsEqualToSomething() throws Exception {
        // Initialize the database
        ImProjects imProjects = ImProjectsResourceIT.createEntity(em);
        em.persist(imProjects);
        em.flush();
        fileStorage.setImProjects(imProjects);
        fileStorageRepository.saveAndFlush(fileStorage);
        Long imProjectsId = imProjects.getId();

        // Get all the fileStorageList where imProjects equals to imProjectsId
        defaultFileStorageShouldBeFound("imProjectsId.equals=" + imProjectsId);

        // Get all the fileStorageList where imProjects equals to imProjectsId + 1
        defaultFileStorageShouldNotBeFound("imProjectsId.equals=" + (imProjectsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFileStorageShouldBeFound(String filter) throws Exception {
        restFileStorageMockMvc.perform(get("/api/file-storages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileStorage.getId().intValue())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME)))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION)));

        // Check, that the count call also returns 1
        restFileStorageMockMvc.perform(get("/api/file-storages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFileStorageShouldNotBeFound(String filter) throws Exception {
        restFileStorageMockMvc.perform(get("/api/file-storages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFileStorageMockMvc.perform(get("/api/file-storages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFileStorage() throws Exception {
        // Get the fileStorage
        restFileStorageMockMvc.perform(get("/api/file-storages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileStorage() throws Exception {
        // Initialize the database
        fileStorageService.save(fileStorage);

        int databaseSizeBeforeUpdate = fileStorageRepository.findAll().size();

        // Update the fileStorage
        FileStorage updatedFileStorage = fileStorageRepository.findById(fileStorage.getId()).get();
        // Disconnect from session so that the updates on updatedFileStorage are not directly saved in db
        em.detach(updatedFileStorage);
        updatedFileStorage
            .filename(UPDATED_FILENAME)
            .caption(UPDATED_CAPTION);

        restFileStorageMockMvc.perform(put("/api/file-storages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFileStorage)))
            .andExpect(status().isOk());

        // Validate the FileStorage in the database
        List<FileStorage> fileStorageList = fileStorageRepository.findAll();
        assertThat(fileStorageList).hasSize(databaseSizeBeforeUpdate);
        FileStorage testFileStorage = fileStorageList.get(fileStorageList.size() - 1);
        assertThat(testFileStorage.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testFileStorage.getCaption()).isEqualTo(UPDATED_CAPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingFileStorage() throws Exception {
        int databaseSizeBeforeUpdate = fileStorageRepository.findAll().size();

        // Create the FileStorage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileStorageMockMvc.perform(put("/api/file-storages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileStorage)))
            .andExpect(status().isBadRequest());

        // Validate the FileStorage in the database
        List<FileStorage> fileStorageList = fileStorageRepository.findAll();
        assertThat(fileStorageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFileStorage() throws Exception {
        // Initialize the database
        fileStorageService.save(fileStorage);

        int databaseSizeBeforeDelete = fileStorageRepository.findAll().size();

        // Delete the fileStorage
        restFileStorageMockMvc.perform(delete("/api/file-storages/{id}", fileStorage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<FileStorage> fileStorageList = fileStorageRepository.findAll();
        assertThat(fileStorageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileStorage.class);
        FileStorage fileStorage1 = new FileStorage();
        fileStorage1.setId(1L);
        FileStorage fileStorage2 = new FileStorage();
        fileStorage2.setId(fileStorage1.getId());
        assertThat(fileStorage1).isEqualTo(fileStorage2);
        fileStorage2.setId(2L);
        assertThat(fileStorage1).isNotEqualTo(fileStorage2);
        fileStorage1.setId(null);
        assertThat(fileStorage1).isNotEqualTo(fileStorage2);
    }
}
