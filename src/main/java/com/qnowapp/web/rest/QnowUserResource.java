package com.qnowapp.web.rest;

import com.qnowapp.domain.QnowUser;
import com.qnowapp.service.QnowUserService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.QnowUserCriteria;
import com.qnowapp.service.QnowUserQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qnowapp.domain.QnowUser}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class QnowUserResource {

    private final Logger log = LoggerFactory.getLogger(QnowUserResource.class);

    private static final String ENTITY_NAME = "qnowUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QnowUserService qnowUserService;

    private final QnowUserQueryService qnowUserQueryService;

    public QnowUserResource(QnowUserService qnowUserService, QnowUserQueryService qnowUserQueryService) {
        this.qnowUserService = qnowUserService;
        this.qnowUserQueryService = qnowUserQueryService;
    }

    /**
     * {@code POST  /qnow-users} : Create a new qnowUser.
     *
     * @param qnowUser the qnowUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qnowUser, or with status {@code 400 (Bad Request)} if the qnowUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/qnow-users")
    public ResponseEntity<QnowUser> createQnowUser(@RequestBody QnowUser qnowUser) throws URISyntaxException {
        log.debug("REST request to save QnowUser : {}", qnowUser);
        if (qnowUser.getId() != null) {
            throw new BadRequestAlertException("A new qnowUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QnowUser result = qnowUserService.save(qnowUser);
        return ResponseEntity.created(new URI("/api/qnow-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /qnow-users} : Updates an existing qnowUser.
     *
     * @param qnowUser the qnowUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qnowUser,
     * or with status {@code 400 (Bad Request)} if the qnowUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qnowUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/qnow-users")
    public ResponseEntity<QnowUser> updateQnowUser(@RequestBody QnowUser qnowUser) throws URISyntaxException {
        log.debug("REST request to update QnowUser : {}", qnowUser);
        if (qnowUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QnowUser result = qnowUserService.save(qnowUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, qnowUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /qnow-users} : get all the qnowUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qnowUsers in body.
     */
    @CrossOrigin
    @GetMapping("/qnow-users")
    public ResponseEntity<List<QnowUser>> getAllQnowUsers(QnowUserCriteria criteria) {
        log.debug("REST request to get QnowUsers by criteria: {}", criteria);
        List<QnowUser> entityList = qnowUserQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /qnow-users/count} : count all the qnowUsers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/qnow-users/count")
    public ResponseEntity<Long> countQnowUsers(QnowUserCriteria criteria) {
        log.debug("REST request to count QnowUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(qnowUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /qnow-users/:id} : get the "id" qnowUser.
     *
     * @param id the id of the qnowUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qnowUser, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/qnow-users/{id}")
    public ResponseEntity<QnowUser> getQnowUser(@PathVariable Long id) {
        log.debug("REST request to get QnowUser : {}", id);
        Optional<QnowUser> qnowUser = qnowUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qnowUser);
    }

    /**
     * {@code DELETE  /qnow-users/:id} : delete the "id" qnowUser.
     *
     * @param id the id of the qnowUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/qnow-users/{id}")
    public ResponseEntity<Void> deleteQnowUser(@PathVariable Long id) {
        log.debug("REST request to delete QnowUser : {}", id);
        qnowUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
