package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectBusinessgoalId;
import com.qnowapp.domain.ProjectSubgoalId;
import com.qnowapp.repository.ProjectSubgoalIdRepository;
import com.qnowapp.service.ProjectSubgoalIdQueryService;
import com.qnowapp.service.ProjectSubgoalIdService;
import com.qnowapp.service.dto.ProjectSubgoalIdCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.ProjectSubgoalId}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectSubgoalIdResource {

    private final Logger log = LoggerFactory.getLogger(ProjectSubgoalIdResource.class);

    private static final String ENTITY_NAME = "projectSubgoalId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
@Autowired
   ProjectSubgoalIdRepository projectSubgoalIdRepository;
    private static Boolean fromTesting = true;
    private final ProjectSubgoalIdService projectSubgoalIdService;

  
    private final ProjectSubgoalIdQueryService projectSubgoalIdQueryService;


    public ProjectSubgoalIdResource( ProjectSubgoalIdService projectSubgoalIdService, ProjectSubgoalIdQueryService projectSubgoalIdQueryService) {
        this.projectSubgoalIdService = projectSubgoalIdService;
        this.projectSubgoalIdQueryService = projectSubgoalIdQueryService;
    
    }
   

    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }

    /**
     * {@code POST  /project-subgoal-ids} : Create a new projectSubgoalId.
     *
     * @param projectSubgoalId the projectSubgoalId to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectSubgoalId, or with status {@code 400 (Bad Request)} if the projectSubgoalId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-subgoal-ids")
    public ResponseEntity<ProjectSubgoalId> createProjectSubgoalId(@RequestBody ProjectSubgoalId projectSubgoalId) throws URISyntaxException {
        log.debug("REST request to save ProjectSubgoalId : {}", projectSubgoalId);
        if (projectSubgoalId.getId() != null) {
            throw new BadRequestAlertException("A new projectSubgoalId cannot already have an ID", ENTITY_NAME, "idexists");
        }else {
  
            ProjectSubgoalId result = projectSubgoalIdRepository.save(projectSubgoalId);
            ResponseEntity<ProjectSubgoalId> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);

            if (fromTesting == false) {
                System.out.println(projectSubgoalId.getName());
                String csvFile1 = "src\\main\\resources\\subgoal.csv";
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
                                projectSubgoalId.setId(null);
                                projectSubgoalId.setCode(country[0]);
                                System.out.println("2");
                                projectSubgoalId.setName(country[1]);
                                System.out.println("3");
                                projectSubgoalId.setDescription(country[2]);
                                ProjectSubgoalId result2 = projectSubgoalIdRepository.save(projectSubgoalId);   
                                System.out.println(result2.getId());
                                System.out.println(projectSubgoalId + "new project created");
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
     * {@code PUT  /project-subgoal-ids} : Updates an existing projectSubgoalId.
     *
     * @param projectSubgoalId the projectSubgoalId to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectSubgoalId,
     * or with status {@code 400 (Bad Request)} if the projectSubgoalId is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectSubgoalId couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-subgoal-ids")
    public ResponseEntity<ProjectSubgoalId> updateProjectSubgoalId(@RequestBody ProjectSubgoalId projectSubgoalId) throws URISyntaxException {
        log.debug("REST request to update ProjectSubgoalId : {}", projectSubgoalId);
        if (projectSubgoalId.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectSubgoalId result = projectSubgoalIdService.save(projectSubgoalId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectSubgoalId.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-subgoal-ids} : get all the projectSubgoalIds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectSubgoalIds in body.
     */
    @CrossOrigin
    @GetMapping("/project-subgoal-ids")
    public ResponseEntity<List<ProjectSubgoalId>> getAllProjectSubgoalIds(ProjectSubgoalIdCriteria criteria) {
        log.debug("REST request to get ProjectSubgoalIds by criteria: {}", criteria);
        List<ProjectSubgoalId> entityList = projectSubgoalIdQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /project-subgoal-ids/count} : count all the projectSubgoalIds.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/project-subgoal-ids/count")
    public ResponseEntity<Long> countProjectSubgoalIds(ProjectSubgoalIdCriteria criteria) {
        log.debug("REST request to count ProjectSubgoalIds by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectSubgoalIdQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-subgoal-ids/:id} : get the "id" projectSubgoalId.
     *
     * @param id the id of the projectSubgoalId to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectSubgoalId, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-subgoal-ids/{id}")
    public ResponseEntity<ProjectSubgoalId> getProjectSubgoalId(@PathVariable Long id) {
        log.debug("REST request to get ProjectSubgoalId : {}", id);
        Optional<ProjectSubgoalId> projectSubgoalId = projectSubgoalIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectSubgoalId);
    }

    /**
     * {@code DELETE  /project-subgoal-ids/:id} : delete the "id" projectSubgoalId.
     *
     * @param id the id of the projectSubgoalId to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-subgoal-ids/{id}")
    public ResponseEntity<Void> deleteProjectSubgoalId(@PathVariable Long id) {
        log.debug("REST request to delete ProjectSubgoalId : {}", id);
        projectSubgoalIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
