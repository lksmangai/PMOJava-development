package com.qnowapp.web.rest;

import com.qnowapp.domain.SkillExpertise;
import com.qnowapp.service.SkillExpertiseService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.SkillExpertiseCriteria;
import com.qnowapp.service.SkillExpertiseQueryService;

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
 * REST controller for managing {@link com.qnowapp.domain.SkillExpertise}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class SkillExpertiseResource {

    private final Logger log = LoggerFactory.getLogger(SkillExpertiseResource.class);

    private static final String ENTITY_NAME = "skillExpertise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillExpertiseService skillExpertiseService;

    private final SkillExpertiseQueryService skillExpertiseQueryService;

    public SkillExpertiseResource(SkillExpertiseService skillExpertiseService, SkillExpertiseQueryService skillExpertiseQueryService) {
        this.skillExpertiseService = skillExpertiseService;
        this.skillExpertiseQueryService = skillExpertiseQueryService;
    }

    /**
     * {@code POST  /skill-expertises} : Create a new skillExpertise.
     *
     * @param skillExpertise the skillExpertise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillExpertise, or with status {@code 400 (Bad Request)} if the skillExpertise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/skill-expertises")
    public ResponseEntity<SkillExpertise> createSkillExpertise(@RequestBody SkillExpertise skillExpertise) throws URISyntaxException {
        log.debug("REST request to save SkillExpertise : {}", skillExpertise);
        if (skillExpertise.getId() != null) {
            throw new BadRequestAlertException("A new skillExpertise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillExpertise result = skillExpertiseService.save(skillExpertise);
        return ResponseEntity.created(new URI("/api/skill-expertises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skill-expertises} : Updates an existing skillExpertise.
     *
     * @param skillExpertise the skillExpertise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillExpertise,
     * or with status {@code 400 (Bad Request)} if the skillExpertise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillExpertise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/skill-expertises")
    public ResponseEntity<SkillExpertise> updateSkillExpertise(@RequestBody SkillExpertise skillExpertise) throws URISyntaxException {
        log.debug("REST request to update SkillExpertise : {}", skillExpertise);
        if (skillExpertise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkillExpertise result = skillExpertiseService.save(skillExpertise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skillExpertise.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skill-expertises} : get all the skillExpertises.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillExpertises in body.
     */
    @CrossOrigin
    @GetMapping("/skill-expertises")
    public ResponseEntity<List<SkillExpertise>> getAllSkillExpertises(SkillExpertiseCriteria criteria) {
        log.debug("REST request to get SkillExpertises by criteria: {}", criteria);
        List<SkillExpertise> entityList = skillExpertiseQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /skill-expertises/count} : count all the skillExpertises.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/skill-expertises/count")
    public ResponseEntity<Long> countSkillExpertises(SkillExpertiseCriteria criteria) {
        log.debug("REST request to count SkillExpertises by criteria: {}", criteria);
        return ResponseEntity.ok().body(skillExpertiseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /skill-expertises/:id} : get the "id" skillExpertise.
     *
     * @param id the id of the skillExpertise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillExpertise, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/skill-expertises/{id}")
    public ResponseEntity<SkillExpertise> getSkillExpertise(@PathVariable Long id) {
        log.debug("REST request to get SkillExpertise : {}", id);
        Optional<SkillExpertise> skillExpertise = skillExpertiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillExpertise);
    }

    /**
     * {@code DELETE  /skill-expertises/:id} : delete the "id" skillExpertise.
     *
     * @param id the id of the skillExpertise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/skill-expertises/{id}")
    public ResponseEntity<Void> deleteSkillExpertise(@PathVariable Long id) {
        log.debug("REST request to delete SkillExpertise : {}", id);
        skillExpertiseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
