package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectTag;
import com.qnowapp.service.ProjectTagService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.ProjectTagCriteria;
import com.qnowapp.service.ProjectTagQueryService;

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
 * REST controller for managing {@link com.qnowapp.domain.ProjectTag}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectTagResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTagResource.class);

    private static final String ENTITY_NAME = "projectTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectTagService projectTagService;

    private final ProjectTagQueryService projectTagQueryService;

    public ProjectTagResource(ProjectTagService projectTagService, ProjectTagQueryService projectTagQueryService) {
        this.projectTagService = projectTagService;
        this.projectTagQueryService = projectTagQueryService;
    }

    /**
     * {@code POST  /project-tags} : Create a new projectTag.
     *
     * @param projectTag the projectTag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTag, or with status {@code 400 (Bad Request)} if the projectTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-tags")
    public ResponseEntity<ProjectTag> createProjectTag(@RequestBody ProjectTag projectTag) throws URISyntaxException {
        log.debug("REST request to save ProjectTag : {}", projectTag);
        if (projectTag.getId() != null) {
            throw new BadRequestAlertException("A new projectTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTag result = projectTagService.save(projectTag);
        return ResponseEntity.created(new URI("/api/project-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-tags} : Updates an existing projectTag.
     *
     * @param projectTag the projectTag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTag,
     * or with status {@code 400 (Bad Request)} if the projectTag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-tags")
    public ResponseEntity<ProjectTag> updateProjectTag(@RequestBody ProjectTag projectTag) throws URISyntaxException {
        log.debug("REST request to update ProjectTag : {}", projectTag);
        if (projectTag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTag result = projectTagService.save(projectTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTag.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-tags} : get all the projectTags.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTags in body.
     */
    @CrossOrigin
    @GetMapping("/project-tags")
    public ResponseEntity<List<ProjectTag>> getAllProjectTags(ProjectTagCriteria criteria) {
        log.debug("REST request to get ProjectTags by criteria: {}", criteria);
        List<ProjectTag> entityList = projectTagQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /project-tags/count} : count all the projectTags.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/project-tags/count")
    public ResponseEntity<Long> countProjectTags(ProjectTagCriteria criteria) {
        log.debug("REST request to count ProjectTags by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectTagQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-tags/:id} : get the "id" projectTag.
     *
     * @param id the id of the projectTag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTag, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-tags/{id}")
    public ResponseEntity<ProjectTag> getProjectTag(@PathVariable Long id) {
        log.debug("REST request to get ProjectTag : {}", id);
        Optional<ProjectTag> projectTag = projectTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTag);
    }

    /**
     * {@code DELETE  /project-tags/:id} : delete the "id" projectTag.
     *
     * @param id the id of the projectTag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-tags/{id}")
    public ResponseEntity<Void> deleteProjectTag(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTag : {}", id);
        projectTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
