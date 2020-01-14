package com.qnowapp.web.rest;

import com.qnowapp.domain.UserContact;
import com.qnowapp.service.UserContactService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.UserContactCriteria;
import com.qnowapp.service.UserContactQueryService;

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
 * REST controller for managing {@link com.qnowapp.domain.UserContact}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class UserContactResource {

    private final Logger log = LoggerFactory.getLogger(UserContactResource.class);

    private static final String ENTITY_NAME = "userContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserContactService userContactService;

    private final UserContactQueryService userContactQueryService;

    public UserContactResource(UserContactService userContactService, UserContactQueryService userContactQueryService) {
        this.userContactService = userContactService;
        this.userContactQueryService = userContactQueryService;
    }

    /**
     * {@code POST  /user-contacts} : Create a new userContact.
     *
     * @param userContact the userContact to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userContact, or with status {@code 400 (Bad Request)} if the userContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/user-contacts")
    public ResponseEntity<UserContact> createUserContact(@RequestBody UserContact userContact) throws URISyntaxException {
        log.debug("REST request to save UserContact : {}", userContact);
        if (userContact.getId() != null) {
            throw new BadRequestAlertException("A new userContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserContact result = userContactService.save(userContact);
        return ResponseEntity.created(new URI("/api/user-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-contacts} : Updates an existing userContact.
     *
     * @param userContact the userContact to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userContact,
     * or with status {@code 400 (Bad Request)} if the userContact is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userContact couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/user-contacts")
    public ResponseEntity<UserContact> updateUserContact(@RequestBody UserContact userContact) throws URISyntaxException {
        log.debug("REST request to update UserContact : {}", userContact);
        if (userContact.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserContact result = userContactService.save(userContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userContact.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-contacts} : get all the userContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userContacts in body.
     */
    @CrossOrigin
    @GetMapping("/user-contacts")
    public ResponseEntity<List<UserContact>> getAllUserContacts(UserContactCriteria criteria) {
        log.debug("REST request to get UserContacts by criteria: {}", criteria);
        List<UserContact> entityList = userContactQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /user-contacts/count} : count all the userContacts.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/user-contacts/count")
    public ResponseEntity<Long> countUserContacts(UserContactCriteria criteria) {
        log.debug("REST request to count UserContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(userContactQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-contacts/:id} : get the "id" userContact.
     *
     * @param id the id of the userContact to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userContact, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/user-contacts/{id}")
    public ResponseEntity<UserContact> getUserContact(@PathVariable Long id) {
        log.debug("REST request to get UserContact : {}", id);
        Optional<UserContact> userContact = userContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userContact);
    }

    /**
     * {@code DELETE  /user-contacts/:id} : delete the "id" userContact.
     *
     * @param id the id of the userContact to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/user-contacts/{id}")
    public ResponseEntity<Void> deleteUserContact(@PathVariable Long id) {
        log.debug("REST request to delete UserContact : {}", id);
        userContactService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
