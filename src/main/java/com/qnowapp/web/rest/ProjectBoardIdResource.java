package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectBoardId;

import com.qnowapp.service.ProjectBoardIdService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.ProjectBoardIdCriteria;

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
 * REST controller for managing {@link com.qnowapp.domain.ProjectBoardId}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectBoardIdResource {

    private final Logger log = LoggerFactory.getLogger(ProjectBoardIdResource.class);

    private static final String ENTITY_NAME = "projectBoardId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectBoardIdService projectBoardIdService;



    public ProjectBoardIdResource(ProjectBoardIdService projectBoardIdService) {
        this.projectBoardIdService = projectBoardIdService;

    }

    /**
     * {@code POST  /project-board-ids} : Create a new projectBoardId.
     *
     * @param projectBoardId the projectBoardId to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectBoardId, or with status {@code 400 (Bad Request)} if the projectBoardId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-board-ids")
    public ResponseEntity<ProjectBoardId> createProjectBoardId(@RequestBody ProjectBoardId projectBoardId) throws URISyntaxException {
        log.debug("REST request to save ProjectBoardId : {}", projectBoardId);
        if (projectBoardId.getId() != null) {
            throw new BadRequestAlertException("A new projectBoardId cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectBoardId result = projectBoardIdService.save(projectBoardId);
        return ResponseEntity.created(new URI("/api/project-board-ids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-board-ids} : Updates an existing projectBoardId.
     *
     * @param projectBoardId the projectBoardId to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectBoardId,
     * or with status {@code 400 (Bad Request)} if the projectBoardId is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectBoardId couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-board-ids")
    public ResponseEntity<ProjectBoardId> updateProjectBoardId(@RequestBody ProjectBoardId projectBoardId) throws URISyntaxException {
        log.debug("REST request to update ProjectBoardId : {}", projectBoardId);
        if (projectBoardId.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectBoardId result = projectBoardIdService.save(projectBoardId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectBoardId.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-board-ids} : get all the projectBoardIds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectBoardIds in body.
     */
    @CrossOrigin
    @GetMapping("/project-board-ids")
    public ResponseEntity<List<ProjectBoardId>> getAllProjectBoardIds( ) {
        log.debug("REST request to get ProjectBoardIds by criteria: {}");
        List<ProjectBoardId> entityList = projectBoardIdService.findAll();
        return ResponseEntity.ok().body(entityList);
    }


    /**
     * {@code GET  /project-board-ids/:id} : get the "id" projectBoardId.
     *
     * @param id the id of the projectBoardId to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectBoardId, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-board-ids/{id}")
    public ResponseEntity<ProjectBoardId> getProjectBoardId(@PathVariable Long id) {
        log.debug("REST request to get ProjectBoardId : {}", id);
        Optional<ProjectBoardId> projectBoardId = projectBoardIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectBoardId);
    }

    /**
     * {@code DELETE  /project-board-ids/:id} : delete the "id" projectBoardId.
     *
     * @param id the id of the projectBoardId to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-board-ids/{id}")
    public ResponseEntity<Void> deleteProjectBoardId(@PathVariable Long id) {
        log.debug("REST request to delete ProjectBoardId : {}", id);
        projectBoardIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
