package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.domain.ProjectStatusId;
import com.qnowapp.repository.ProjectStatusIdRepository;
import com.qnowapp.service.ProjectStatusIdQueryService;
import com.qnowapp.service.ProjectStatusIdService;
import com.qnowapp.service.dto.ProjectStatusIdCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.ProjectStatusId}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectStatusIdResource {

    private final Logger log = LoggerFactory.getLogger(ProjectStatusIdResource.class);

    private static final String ENTITY_NAME = "projectStatusId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
     ProjectStatusIdRepository projectStatusIdRepository;
    private static Boolean fromTesting = true;


    
    private final ProjectStatusIdService projectStatusIdService;

    private final ProjectStatusIdQueryService projectStatusIdQueryService;

    public ProjectStatusIdResource( ProjectStatusIdService projectStatusIdService, ProjectStatusIdQueryService projectStatusIdQueryService) {
        this.projectStatusIdService = projectStatusIdService;
        this.projectStatusIdQueryService = projectStatusIdQueryService;
      
    }
    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }
    /**
     * {@code POST  /project-status-ids} : Create a new projectStatusId.
     *
     * @param projectStatusId the projectStatusId to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectStatusId, or with status {@code 400 (Bad Request)} if the projectStatusId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-status-ids")
    public ResponseEntity<ProjectStatusId> createProjectStatusId(@RequestBody ProjectStatusId projectStatusId) throws URISyntaxException {
        log.debug("REST request to save ProjectStatusId : {}", projectStatusId);
        if (projectStatusId.getId() != null) {
            throw new BadRequestAlertException("A new projectStatusId cannot already have an ID", ENTITY_NAME, "idexists");
        }else {
  
            ProjectStatusId result = projectStatusIdRepository.save(projectStatusId);
            ResponseEntity<ProjectStatusId> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);

            if (fromTesting == false) {
                System.out.println(projectStatusId.getName());
                String csvFile1 = "src\\main\\resources\\projectstatus.csv";
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
                                projectStatusId.setId(null);
                                projectStatusId.setCode(country[0]);
                                System.out.println("2");
                                projectStatusId.setName(country[1]);
                                System.out.println("3");
                                projectStatusId.setDescription(country[2]);
                                ProjectStatusId result2 = projectStatusIdRepository.save(projectStatusId);  
                                System.out.println(result2.getId());
                                System.out.println(projectStatusId + "new project created");
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
     * {@code PUT  /project-status-ids} : Updates an existing projectStatusId.
     *
     * @param projectStatusId the projectStatusId to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectStatusId,
     * or with status {@code 400 (Bad Request)} if the projectStatusId is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectStatusId couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-status-ids")
    public ResponseEntity<ProjectStatusId> updateProjectStatusId(@RequestBody ProjectStatusId projectStatusId) throws URISyntaxException {
        log.debug("REST request to update ProjectStatusId : {}", projectStatusId);
        if (projectStatusId.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectStatusId result = projectStatusIdService.save(projectStatusId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectStatusId.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-status-ids} : get all the projectStatusIds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectStatusIds in body.
     */
    @CrossOrigin
    @GetMapping("/project-status-ids")
    public ResponseEntity<List<ProjectStatusId>> getAllProjectStatusIds(ProjectStatusIdCriteria criteria) {
        log.debug("REST request to get ProjectStatusIds by criteria: {}", criteria);
        List<ProjectStatusId> entityList = projectStatusIdQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /project-status-ids/count} : count all the projectStatusIds.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/project-status-ids/count")
    public ResponseEntity<Long> countProjectStatusIds(ProjectStatusIdCriteria criteria) {
        log.debug("REST request to count ProjectStatusIds by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectStatusIdQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-status-ids/:id} : get the "id" projectStatusId.
     *
     * @param id the id of the projectStatusId to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectStatusId, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-status-ids/{id}")
    public ResponseEntity<ProjectStatusId> getProjectStatusId(@PathVariable Long id) {
        log.debug("REST request to get ProjectStatusId : {}", id);
        Optional<ProjectStatusId> projectStatusId = projectStatusIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectStatusId);
    }

    /**
     * {@code DELETE  /project-status-ids/:id} : delete the "id" projectStatusId.
     *
     * @param id the id of the projectStatusId to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-status-ids/{id}")
    public ResponseEntity<Void> deleteProjectStatusId(@PathVariable Long id) {
        log.debug("REST request to delete ProjectStatusId : {}", id);
        projectStatusIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
