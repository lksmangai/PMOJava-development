package com.qnowapp.web.rest;

import com.qnowapp.Qnow1App;
import com.qnowapp.domain.QnowUser;
import com.qnowapp.domain.User;
import com.qnowapp.repository.QnowUserRepository;
import com.qnowapp.service.QnowUserService;
import com.qnowapp.web.rest.errors.ExceptionTranslator;
import com.qnowapp.service.dto.QnowUserCriteria;
import com.qnowapp.service.QnowUserQueryService;

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
 * Integration tests for the {@Link QnowUserResource} REST controller.
 */
@SpringBootTest(classes = Qnow1App.class)
public class QnowUserResourceIT {

    @Autowired
    private QnowUserRepository qnowUserRepository;

    @Autowired
    private QnowUserService qnowUserService;

    @Autowired
    private QnowUserQueryService qnowUserQueryService;

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

    private MockMvc restQnowUserMockMvc;

    private QnowUser qnowUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QnowUserResource qnowUserResource = new QnowUserResource(qnowUserService, qnowUserQueryService);
        this.restQnowUserMockMvc = MockMvcBuilders.standaloneSetup(qnowUserResource)
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
    public static QnowUser createEntity(EntityManager em) {
        QnowUser qnowUser = new QnowUser();
        return qnowUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QnowUser createUpdatedEntity(EntityManager em) {
        QnowUser qnowUser = new QnowUser();
        return qnowUser;
    }

    @BeforeEach
    public void initTest() {
        qnowUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createQnowUser() throws Exception {
        int databaseSizeBeforeCreate = qnowUserRepository.findAll().size();

        // Create the QnowUser
        restQnowUserMockMvc.perform(post("/api/qnow-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qnowUser)))
            .andExpect(status().isCreated());

        // Validate the QnowUser in the database
        List<QnowUser> qnowUserList = qnowUserRepository.findAll();
        assertThat(qnowUserList).hasSize(databaseSizeBeforeCreate + 1);
        QnowUser testQnowUser = qnowUserList.get(qnowUserList.size() - 1);
    }

    @Test
    @Transactional
    public void createQnowUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qnowUserRepository.findAll().size();

        // Create the QnowUser with an existing ID
        qnowUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQnowUserMockMvc.perform(post("/api/qnow-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qnowUser)))
            .andExpect(status().isBadRequest());

        // Validate the QnowUser in the database
        List<QnowUser> qnowUserList = qnowUserRepository.findAll();
        assertThat(qnowUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQnowUsers() throws Exception {
        // Initialize the database
        qnowUserRepository.saveAndFlush(qnowUser);

        // Get all the qnowUserList
        restQnowUserMockMvc.perform(get("/api/qnow-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qnowUser.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getQnowUser() throws Exception {
        // Initialize the database
        qnowUserRepository.saveAndFlush(qnowUser);

        // Get the qnowUser
        restQnowUserMockMvc.perform(get("/api/qnow-users/{id}", qnowUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(qnowUser.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllQnowUsersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        qnowUser.setUser(user);
        qnowUserRepository.saveAndFlush(qnowUser);
        Long userId = user.getId();

        // Get all the qnowUserList where user equals to userId
        defaultQnowUserShouldBeFound("userId.equals=" + userId);

        // Get all the qnowUserList where user equals to userId + 1
        defaultQnowUserShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQnowUserShouldBeFound(String filter) throws Exception {
        restQnowUserMockMvc.perform(get("/api/qnow-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qnowUser.getId().intValue())));

        // Check, that the count call also returns 1
        restQnowUserMockMvc.perform(get("/api/qnow-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQnowUserShouldNotBeFound(String filter) throws Exception {
        restQnowUserMockMvc.perform(get("/api/qnow-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQnowUserMockMvc.perform(get("/api/qnow-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingQnowUser() throws Exception {
        // Get the qnowUser
        restQnowUserMockMvc.perform(get("/api/qnow-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQnowUser() throws Exception {
        // Initialize the database
        qnowUserService.save(qnowUser);

        int databaseSizeBeforeUpdate = qnowUserRepository.findAll().size();

        // Update the qnowUser
        QnowUser updatedQnowUser = qnowUserRepository.findById(qnowUser.getId()).get();
        // Disconnect from session so that the updates on updatedQnowUser are not directly saved in db
        em.detach(updatedQnowUser);

        restQnowUserMockMvc.perform(put("/api/qnow-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQnowUser)))
            .andExpect(status().isOk());

        // Validate the QnowUser in the database
        List<QnowUser> qnowUserList = qnowUserRepository.findAll();
        assertThat(qnowUserList).hasSize(databaseSizeBeforeUpdate);
        QnowUser testQnowUser = qnowUserList.get(qnowUserList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingQnowUser() throws Exception {
        int databaseSizeBeforeUpdate = qnowUserRepository.findAll().size();

        // Create the QnowUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQnowUserMockMvc.perform(put("/api/qnow-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(qnowUser)))
            .andExpect(status().isBadRequest());

        // Validate the QnowUser in the database
        List<QnowUser> qnowUserList = qnowUserRepository.findAll();
        assertThat(qnowUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQnowUser() throws Exception {
        // Initialize the database
        qnowUserService.save(qnowUser);

        int databaseSizeBeforeDelete = qnowUserRepository.findAll().size();

        // Delete the qnowUser
        restQnowUserMockMvc.perform(delete("/api/qnow-users/{id}", qnowUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<QnowUser> qnowUserList = qnowUserRepository.findAll();
        assertThat(qnowUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QnowUser.class);
        QnowUser qnowUser1 = new QnowUser();
        qnowUser1.setId(1L);
        QnowUser qnowUser2 = new QnowUser();
        qnowUser2.setId(qnowUser1.getId());
        assertThat(qnowUser1).isEqualTo(qnowUser2);
        qnowUser2.setId(2L);
        assertThat(qnowUser1).isNotEqualTo(qnowUser2);
        qnowUser1.setId(null);
        assertThat(qnowUser1).isNotEqualTo(qnowUser2);
    }
}
