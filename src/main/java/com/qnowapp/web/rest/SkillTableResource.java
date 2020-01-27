package com.qnowapp.web.rest;

import com.qnowapp.domain.SkillTable;
import com.qnowapp.service.SkillTableService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.SkillTableCriteria;
import com.qnowapp.service.SkillTableQueryService;

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
 * REST controller for managing {@link com.qnowapp.domain.SkillTable}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class SkillTableResource {

    private final Logger log = LoggerFactory.getLogger(SkillTableResource.class);

    private static final String ENTITY_NAME = "skillTable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillTableService skillTableService;

    private final SkillTableQueryService skillTableQueryService;

    public SkillTableResource(SkillTableService skillTableService, SkillTableQueryService skillTableQueryService) {
        this.skillTableService = skillTableService;
        this.skillTableQueryService = skillTableQueryService;
    }

    /**
     * {@code POST  /skill-tables} : Create a new skillTable.
     *
     * @param skillTable the skillTable to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillTable, or with status {@code 400 (Bad Request)} if the skillTable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/skill-tables")
    public ResponseEntity<SkillTable> createSkillTable(@RequestBody SkillTable skillTable) throws URISyntaxException {
        log.debug("REST request to save SkillTable : {}", skillTable);
        if (skillTable.getId() != null) {
            throw new BadRequestAlertException("A new skillTable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillTable result = skillTableService.save(skillTable);
        return ResponseEntity.created(new URI("/api/skill-tables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skill-tables} : Updates an existing skillTable.
     *
     * @param skillTable the skillTable to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillTable,
     * or with status {@code 400 (Bad Request)} if the skillTable is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillTable couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/skill-tables")
    public ResponseEntity<SkillTable> updateSkillTable(@RequestBody SkillTable skillTable) throws URISyntaxException {
        log.debug("REST request to update SkillTable : {}", skillTable);
        if (skillTable.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkillTable result = skillTableService.save(skillTable);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skillTable.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skill-tables} : get all the skillTables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillTables in body.
     */
    @CrossOrigin
    @GetMapping("/skill-tables")
    public ResponseEntity<List<SkillTable>> getAllSkillTables(SkillTableCriteria criteria) {
        log.debug("REST request to get SkillTables by criteria: {}", criteria);
        List<SkillTable> entityList = skillTableQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /skill-tables/count} : count all the skillTables.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/skill-tables/count")
    public ResponseEntity<Long> countSkillTables(SkillTableCriteria criteria) {
        log.debug("REST request to count SkillTables by criteria: {}", criteria);
        return ResponseEntity.ok().body(skillTableQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /skill-tables/:id} : get the "id" skillTable.
     *
     * @param id the id of the skillTable to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillTable, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/skill-tables/{id}")
    public ResponseEntity<SkillTable> getSkillTable(@PathVariable Long id) {
        log.debug("REST request to get SkillTable : {}", id);
        Optional<SkillTable> skillTable = skillTableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillTable);
    }

    /**
     * {@code DELETE  /skill-tables/:id} : delete the "id" skillTable.
     *
     * @param id the id of the skillTable to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/skill-tables/{id}")
    public ResponseEntity<Void> deleteSkillTable(@PathVariable Long id) {
        log.debug("REST request to delete SkillTable : {}", id);
        skillTableService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
