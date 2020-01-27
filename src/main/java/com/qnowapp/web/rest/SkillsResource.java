package com.qnowapp.web.rest;

import com.qnowapp.domain.Skills;
import com.qnowapp.service.SkillsService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.SkillsCriteria;
import com.qnowapp.service.SkillsQueryService;

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
 * REST controller for managing {@link com.qnowapp.domain.Skills}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class SkillsResource {

    private final Logger log = LoggerFactory.getLogger(SkillsResource.class);

    private static final String ENTITY_NAME = "skills";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillsService skillsService;

    private final SkillsQueryService skillsQueryService;

    public SkillsResource(SkillsService skillsService, SkillsQueryService skillsQueryService) {
        this.skillsService = skillsService;
        this.skillsQueryService = skillsQueryService;
    }

    /**
     * {@code POST  /skills} : Create a new skills.
     *
     * @param skills the skills to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skills, or with status {@code 400 (Bad Request)} if the skills has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/skills")
    public ResponseEntity<Skills> createSkills(@RequestBody Skills skills) throws URISyntaxException {
        log.debug("REST request to save Skills : {}", skills);
        if (skills.getId() != null) {
            throw new BadRequestAlertException("A new skills cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Skills result = skillsService.save(skills);
        return ResponseEntity.created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skills} : Updates an existing skills.
     *
     * @param skills the skills to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skills,
     * or with status {@code 400 (Bad Request)} if the skills is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skills couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/skills")
    public ResponseEntity<Skills> updateSkills(@RequestBody Skills skills) throws URISyntaxException {
        log.debug("REST request to update Skills : {}", skills);
        if (skills.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Skills result = skillsService.save(skills);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skills.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skills} : get all the skills.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skills in body.
     */
    @CrossOrigin
    @GetMapping("/skills")
    public ResponseEntity<List<Skills>> getAllSkills(SkillsCriteria criteria) {
        log.debug("REST request to get Skills by criteria: {}", criteria);
        List<Skills> entityList = skillsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /skills/count} : count all the skills.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/skills/count")
    public ResponseEntity<Long> countSkills(SkillsCriteria criteria) {
        log.debug("REST request to count Skills by criteria: {}", criteria);
        return ResponseEntity.ok().body(skillsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /skills/:id} : get the "id" skills.
     *
     * @param id the id of the skills to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skills, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/skills/{id}")
    public ResponseEntity<Skills> getSkills(@PathVariable Long id) {
        log.debug("REST request to get Skills : {}", id);
        Optional<Skills> skills = skillsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skills);
    }

    /**
     * {@code DELETE  /skills/:id} : delete the "id" skills.
     *
     * @param id the id of the skills to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> deleteSkills(@PathVariable Long id) {
        log.debug("REST request to delete Skills : {}", id);
        skillsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
