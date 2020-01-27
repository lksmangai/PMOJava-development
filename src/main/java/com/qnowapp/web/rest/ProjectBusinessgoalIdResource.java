package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectBusinessgoalId;
import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.repository.ProjectBusinessgoalIdRepository;
import com.qnowapp.service.ProjectBusinessgoalIdQueryService;
import com.qnowapp.service.ProjectBusinessgoalIdService;
import com.qnowapp.service.dto.ProjectBusinessgoalIdCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.ProjectBusinessgoalId}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectBusinessgoalIdResource {

    private final Logger log = LoggerFactory.getLogger(ProjectBusinessgoalIdResource.class);

    private static final String ENTITY_NAME = "projectBusinessgoalId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
   ProjectBusinessgoalIdRepository projectBusinessgoalIdRepository;
    private static Boolean fromTesting = true;


    
    private final ProjectBusinessgoalIdService projectBusinessgoalIdService;


    private final ProjectBusinessgoalIdQueryService projectBusinessgoalIdQueryService;

    public ProjectBusinessgoalIdResource(ProjectBusinessgoalIdService projectBusinessgoalIdService, ProjectBusinessgoalIdQueryService projectBusinessgoalIdQueryService) {
        this.projectBusinessgoalIdService = projectBusinessgoalIdService;
        this.projectBusinessgoalIdQueryService = projectBusinessgoalIdQueryService;
       
    }
   
    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }

    /**
     * {@code POST  /project-businessgoal-ids} : Create a new projectBusinessgoalId.
     *
     * @param projectBusinessgoalId the projectBusinessgoalId to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectBusinessgoalId, or with status {@code 400 (Bad Request)} if the projectBusinessgoalId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-businessgoal-ids")
    public ResponseEntity<ProjectBusinessgoalId> createProjectBusinessgoalId(@RequestBody ProjectBusinessgoalId projectBusinessgoalId) throws URISyntaxException {
        log.debug("REST request to save ProjectBusinessgoalId : {}", projectBusinessgoalId);
        if (projectBusinessgoalId.getId() != null) {
            throw new BadRequestAlertException("A new projectBusinessgoalId cannot already have an ID", ENTITY_NAME, "idexists");
        }else {
  
            ProjectBusinessgoalId result = projectBusinessgoalIdRepository.save(projectBusinessgoalId);
            ResponseEntity<ProjectBusinessgoalId> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);

            if (fromTesting == false) {
                System.out.println(projectBusinessgoalId.getName());
                String csvFile1 = "src\\main\\resources\\businessgoal.csv";
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
                                projectBusinessgoalId.setId(null);
                                projectBusinessgoalId.setCode(country[0]);
                                System.out.println("2");
                                projectBusinessgoalId.setName(country[1]);
                                System.out.println("3");
                                projectBusinessgoalId.setDescription(country[2]);
                                ProjectBusinessgoalId result2 = projectBusinessgoalIdRepository.save(projectBusinessgoalId);    
                                System.out.println(result2.getId());
                                System.out.println(projectBusinessgoalId + "new project created");
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
     * {@code PUT  /project-businessgoal-ids} : Updates an existing projectBusinessgoalId.
     *
     * @param projectBusinessgoalId the projectBusinessgoalId to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectBusinessgoalId,
     * or with status {@code 400 (Bad Request)} if the projectBusinessgoalId is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectBusinessgoalId couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-businessgoal-ids")
    public ResponseEntity<ProjectBusinessgoalId> updateProjectBusinessgoalId(@RequestBody ProjectBusinessgoalId projectBusinessgoalId) throws URISyntaxException {
        log.debug("REST request to update ProjectBusinessgoalId : {}", projectBusinessgoalId);
        if (projectBusinessgoalId.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectBusinessgoalId result = projectBusinessgoalIdService.save(projectBusinessgoalId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectBusinessgoalId.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-businessgoal-ids} : get all the projectBusinessgoalIds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectBusinessgoalIds in body.
     */
    @CrossOrigin
    @GetMapping("/project-businessgoal-ids")
    public ResponseEntity<List<ProjectBusinessgoalId>> getAllProjectBusinessgoalIds(ProjectBusinessgoalIdCriteria criteria) {
        log.debug("REST request to get ProjectBusinessgoalIds by criteria: {}", criteria);
        List<ProjectBusinessgoalId> entityList = projectBusinessgoalIdQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /project-businessgoal-ids/count} : count all the projectBusinessgoalIds.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/project-businessgoal-ids/count")
    public ResponseEntity<Long> countProjectBusinessgoalIds(ProjectBusinessgoalIdCriteria criteria) {
        log.debug("REST request to count ProjectBusinessgoalIds by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectBusinessgoalIdQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-businessgoal-ids/:id} : get the "id" projectBusinessgoalId.
     *
     * @param id the id of the projectBusinessgoalId to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectBusinessgoalId, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-businessgoal-ids/{id}")
    public ResponseEntity<ProjectBusinessgoalId> getProjectBusinessgoalId(@PathVariable Long id) {
        log.debug("REST request to get ProjectBusinessgoalId : {}", id);
        Optional<ProjectBusinessgoalId> projectBusinessgoalId = projectBusinessgoalIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectBusinessgoalId);
    }

    /**
     * {@code DELETE  /project-businessgoal-ids/:id} : delete the "id" projectBusinessgoalId.
     *
     * @param id the id of the projectBusinessgoalId to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-businessgoal-ids/{id}")
    public ResponseEntity<Void> deleteProjectBusinessgoalId(@PathVariable Long id) {
        log.debug("REST request to delete ProjectBusinessgoalId : {}", id);
        projectBusinessgoalIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
