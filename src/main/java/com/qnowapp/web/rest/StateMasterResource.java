package com.qnowapp.web.rest;

import com.qnowapp.domain.StateMaster;

import com.qnowapp.service.StateMasterService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.StateMasterCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.StateMaster}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class StateMasterResource {

    private final Logger log = LoggerFactory.getLogger(StateMasterResource.class);

    private static final String ENTITY_NAME = "stateMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StateMasterService stateMasterService;

    

    public StateMasterResource(StateMasterService stateMasterService) {
        this.stateMasterService = stateMasterService;
     
    }

    /**
     * {@code POST  /state-masters} : Create a new stateMaster.
     *
     * @param stateMaster the stateMaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stateMaster, or with status {@code 400 (Bad Request)} if the stateMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/state-masters")
    public ResponseEntity<StateMaster> createStateMaster(@RequestBody StateMaster stateMaster) throws URISyntaxException {
        log.debug("REST request to save StateMaster : {}", stateMaster);
        if (stateMaster.getId() != null) {
            throw new BadRequestAlertException("A new stateMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StateMaster result = stateMasterService.save(stateMaster);
        return ResponseEntity.created(new URI("/api/state-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /state-masters} : Updates an existing stateMaster.
     *
     * @param stateMaster the stateMaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stateMaster,
     * or with status {@code 400 (Bad Request)} if the stateMaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stateMaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/state-masters")
    public ResponseEntity<StateMaster> updateStateMaster(@RequestBody StateMaster stateMaster) throws URISyntaxException {
        log.debug("REST request to update StateMaster : {}", stateMaster);
        if (stateMaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StateMaster result = stateMasterService.save(stateMaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stateMaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /state-masters} : get all the stateMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stateMasters in body.
     */
    @CrossOrigin
    @GetMapping("/state-masters")
    public ResponseEntity<List<StateMaster>> getAllStateMasters( ) {
        log.debug("REST request to get StateMasters by criteria: {}");
        List<StateMaster> entityList = stateMasterService.findAll();
        return ResponseEntity.ok().body(entityList);
    }



    /**
     * {@code GET  /state-masters/:id} : get the "id" stateMaster.
     *
     * @param id the id of the stateMaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stateMaster, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/state-masters/{id}")
    public ResponseEntity<StateMaster> getStateMaster(@PathVariable Long id) {
        log.debug("REST request to get StateMaster : {}", id);
        Optional<StateMaster> stateMaster = stateMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stateMaster);
    }

    /**
     * {@code DELETE  /state-masters/:id} : delete the "id" stateMaster.
     *
     * @param id the id of the stateMaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/state-masters/{id}")
    public ResponseEntity<Void> deleteStateMaster(@PathVariable Long id) {
        log.debug("REST request to delete StateMaster : {}", id);
        stateMasterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
