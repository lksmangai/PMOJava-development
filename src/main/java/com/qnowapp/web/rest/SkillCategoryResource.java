package com.qnowapp.web.rest;

import com.qnowapp.domain.SkillCategory;
import com.qnowapp.service.SkillCategoryService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.SkillCategoryCriteria;
import com.qnowapp.service.SkillCategoryQueryService;

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
 * REST controller for managing {@link com.qnowapp.domain.SkillCategory}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class SkillCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SkillCategoryResource.class);

    private static final String ENTITY_NAME = "skillCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillCategoryService skillCategoryService;

    private final SkillCategoryQueryService skillCategoryQueryService;

    public SkillCategoryResource(SkillCategoryService skillCategoryService, SkillCategoryQueryService skillCategoryQueryService) {
        this.skillCategoryService = skillCategoryService;
        this.skillCategoryQueryService = skillCategoryQueryService;
    }

    /**
     * {@code POST  /skill-categories} : Create a new skillCategory.
     *
     * @param skillCategory the skillCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillCategory, or with status {@code 400 (Bad Request)} if the skillCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/skill-categories")
    public ResponseEntity<SkillCategory> createSkillCategory(@RequestBody SkillCategory skillCategory) throws URISyntaxException {
        log.debug("REST request to save SkillCategory : {}", skillCategory);
        if (skillCategory.getId() != null) {
            throw new BadRequestAlertException("A new skillCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillCategory result = skillCategoryService.save(skillCategory);
        return ResponseEntity.created(new URI("/api/skill-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skill-categories} : Updates an existing skillCategory.
     *
     * @param skillCategory the skillCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillCategory,
     * or with status {@code 400 (Bad Request)} if the skillCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/skill-categories")
    public ResponseEntity<SkillCategory> updateSkillCategory(@RequestBody SkillCategory skillCategory) throws URISyntaxException {
        log.debug("REST request to update SkillCategory : {}", skillCategory);
        if (skillCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkillCategory result = skillCategoryService.save(skillCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skillCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skill-categories} : get all the skillCategories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillCategories in body.
     */
    @CrossOrigin
    @GetMapping("/skill-categories")
    public ResponseEntity<List<SkillCategory>> getAllSkillCategories(SkillCategoryCriteria criteria) {
        log.debug("REST request to get SkillCategories by criteria: {}", criteria);
        List<SkillCategory> entityList = skillCategoryQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /skill-categories/count} : count all the skillCategories.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/skill-categories/count")
    public ResponseEntity<Long> countSkillCategories(SkillCategoryCriteria criteria) {
        log.debug("REST request to count SkillCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(skillCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /skill-categories/:id} : get the "id" skillCategory.
     *
     * @param id the id of the skillCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillCategory, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/skill-categories/{id}")
    public ResponseEntity<SkillCategory> getSkillCategory(@PathVariable Long id) {
        log.debug("REST request to get SkillCategory : {}", id);
        Optional<SkillCategory> skillCategory = skillCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillCategory);
    }

    /**
     * {@code DELETE  /skill-categories/:id} : delete the "id" skillCategory.
     *
     * @param id the id of the skillCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/skill-categories/{id}")
    public ResponseEntity<Void> deleteSkillCategory(@PathVariable Long id) {
        log.debug("REST request to delete SkillCategory : {}", id);
        skillCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
