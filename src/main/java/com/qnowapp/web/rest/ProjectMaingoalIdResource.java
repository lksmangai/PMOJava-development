package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.domain.ProjectMaingoalId;
import com.qnowapp.repository.ProjectMaingoalIdRepository;
import com.qnowapp.service.ProjectMaingoalIdQueryService;
import com.qnowapp.service.ProjectMaingoalIdService;
import com.qnowapp.service.dto.ProjectMaingoalIdCriteria;
import com.qnowapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qnowapp.domain.ProjectMaingoalId}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectMaingoalIdResource {

    private final Logger log = LoggerFactory.getLogger(ProjectMaingoalIdResource.class);

    private static final String ENTITY_NAME = "projectMaingoalId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
@Autowired
    ProjectMaingoalIdRepository projectMaingoalIdRepository;
    private static Boolean fromTesting = true;


  
    private final ProjectMaingoalIdService projectMaingoalIdService;


    private final ProjectMaingoalIdQueryService projectMaingoalIdQueryService;

    public ProjectMaingoalIdResource(ProjectMaingoalIdService projectMaingoalIdService, ProjectMaingoalIdQueryService projectMaingoalIdQueryService) {
        this.projectMaingoalIdService = projectMaingoalIdService;
        this.projectMaingoalIdQueryService = projectMaingoalIdQueryService;
        
    }
    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }

    /**
     * {@code POST  /project-maingoal-ids} : Create a new projectMaingoalId.
     *
     * @param projectMaingoalId the projectMaingoalId to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectMaingoalId, or with status {@code 400 (Bad Request)} if the projectMaingoalId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-maingoal-ids")
    public ResponseEntity<ProjectMaingoalId> createProjectMaingoalId(@RequestBody ProjectMaingoalId projectMaingoalId) throws URISyntaxException {
        log.debug("REST request to save ProjectMaingoalId : {}", projectMaingoalId);
        if (projectMaingoalId.getId() != null) {
            throw new BadRequestAlertException("A new projectMaingoalId cannot already have an ID", ENTITY_NAME, "idexists");
        }else {
  
            ProjectMaingoalId result = projectMaingoalIdRepository.save(projectMaingoalId);
            ResponseEntity<ProjectMaingoalId> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);

            if (fromTesting == false) {
                System.out.println(projectMaingoalId.getName());
                String csvFile1 = "src\\main\\resources\\maingoal.csv";
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ";";

                try {
                    br = new BufferedReader(new FileReader(csvFile1));
                    int count = 0;
                    while ((line = br.readLine()) != null) {
                        count++;
                        System.out.println(+count);
                        if (count == 1)
                            continue;
                        String[] country = line.split(cvsSplitBy);
                        try {
                            if (country.length > 3 ) {
                                System.out.println("1");
                                projectMaingoalId.setId(null);
                                projectMaingoalId.setCode(country[0]);
                                System.out.println("2");
                                projectMaingoalId.setName(country[1]);
                                System.out.println("3");
                                projectMaingoalId.setDescription(country[2]);
                                ProjectMaingoalId result2 = projectMaingoalIdRepository.save(projectMaingoalId);    
                                System.out.println(result2.getId());
                                System.out.println(projectMaingoalId + "new project created");
                            }
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    
                    }
                }
            }
        return a;

        	
        }
    }

    /**
     * {@code PUT  /project-maingoal-ids} : Updates an existing projectMaingoalId.
     *
     * @param projectMaingoalId the projectMaingoalId to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectMaingoalId,
     * or with status {@code 400 (Bad Request)} if the projectMaingoalId is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectMaingoalId couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-maingoal-ids")
    public ResponseEntity<ProjectMaingoalId> updateProjectMaingoalId(@RequestBody ProjectMaingoalId projectMaingoalId) throws URISyntaxException {
        log.debug("REST request to update ProjectMaingoalId : {}", projectMaingoalId);
        if (projectMaingoalId.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectMaingoalId result = projectMaingoalIdService.save(projectMaingoalId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectMaingoalId.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-maingoal-ids} : get all the projectMaingoalIds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectMaingoalIds in body.
     */
    @CrossOrigin
    @GetMapping("/project-maingoal-ids")
    public ResponseEntity<List<ProjectMaingoalId>> getAllProjectMaingoalIds(ProjectMaingoalIdCriteria criteria) {
        log.debug("REST request to get ProjectMaingoalIds by criteria: {}", criteria);
        List<ProjectMaingoalId> entityList = projectMaingoalIdQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /project-maingoal-ids/count} : count all the projectMaingoalIds.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/project-maingoal-ids/count")
    public ResponseEntity<Long> countProjectMaingoalIds(ProjectMaingoalIdCriteria criteria) {
        log.debug("REST request to count ProjectMaingoalIds by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectMaingoalIdQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-maingoal-ids/:id} : get the "id" projectMaingoalId.
     *
     * @param id the id of the projectMaingoalId to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectMaingoalId, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-maingoal-ids/{id}")
    public ResponseEntity<ProjectMaingoalId> getProjectMaingoalId(@PathVariable Long id) {
        log.debug("REST request to get ProjectMaingoalId : {}", id);
        Optional<ProjectMaingoalId> projectMaingoalId = projectMaingoalIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectMaingoalId);
    }

    /**
     * {@code DELETE  /project-maingoal-ids/:id} : delete the "id" projectMaingoalId.
     *
     * @param id the id of the projectMaingoalId to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-maingoal-ids/{id}")
    public ResponseEntity<Void> deleteProjectMaingoalId(@PathVariable Long id) {
        log.debug("REST request to delete ProjectMaingoalId : {}", id);
        projectMaingoalIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
