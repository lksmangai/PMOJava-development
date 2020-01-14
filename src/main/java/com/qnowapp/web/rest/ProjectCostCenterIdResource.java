package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectCostCenterId;


import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.repository.ProjectCostCenterIdRepository;

import com.qnowapp.service.ProjectCostCenterIdService;
import com.qnowapp.service.dto.ProjectCostCenterIdCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.ProjectCostCenterId}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectCostCenterIdResource {

    private final Logger log = LoggerFactory.getLogger(ProjectCostCenterIdResource.class);

    private static final String ENTITY_NAME = "projectCostCenterId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
  @Autowired
  ProjectCostCenterIdRepository projectCostCenterIdRepository;
    private static Boolean fromTesting = true;


    private final ProjectCostCenterIdService projectCostCenterIdService;


  
    public ProjectCostCenterIdResource( ProjectCostCenterIdService projectCostCenterIdService) {
        this.projectCostCenterIdService = projectCostCenterIdService;
      
       
    }
  
    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }

    /**
     * {@code POST  /project-cost-center-ids} : Create a new projectCostCenterId.
     *
     * @param projectCostCenterId the projectCostCenterId to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectCostCenterId, or with status {@code 400 (Bad Request)} if the projectCostCenterId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-cost-center-ids")
    public ResponseEntity<ProjectCostCenterId> createProjectCostCenterId(@RequestBody ProjectCostCenterId projectCostCenterId) throws URISyntaxException {
        log.debug("REST request to save ProjectCostCenterId : {}", projectCostCenterId);
        if (projectCostCenterId.getId() != null) {
            throw new BadRequestAlertException("A new projectCostCenterId cannot already have an ID", ENTITY_NAME, "idexists");
        }else {
  
            ProjectCostCenterId result = projectCostCenterIdRepository.save(projectCostCenterId);
            ResponseEntity<ProjectCostCenterId> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);

            if (fromTesting == false) {
               
                String csvFile1 = "src\\main\\resources\\vertical.csv";
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ";";

                try {
                    br = new BufferedReader(new FileReader(csvFile1));
                    int count = 0;
                    while ((line = br.readLine()) != null) {
                        count++;
                    
                        if (count == 1)
                            continue;
                        String[] country = line.split(cvsSplitBy);
                        try {
                            if (country.length > 3 ) {
                               
                                projectCostCenterId.setId(null);
                                projectCostCenterId.setCode(country[0]);
                               
                                projectCostCenterId.setName(country[1]);
                               
                                projectCostCenterId.setDescription(country[2]);
                                ProjectCostCenterId result2 = projectCostCenterIdRepository.save(projectCostCenterId);  
                              
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
     * {@code PUT  /project-cost-center-ids} : Updates an existing projectCostCenterId.
     *
     * @param projectCostCenterId the projectCostCenterId to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectCostCenterId,
     * or with status {@code 400 (Bad Request)} if the projectCostCenterId is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectCostCenterId couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-cost-center-ids")
    public ResponseEntity<ProjectCostCenterId> updateProjectCostCenterId(@RequestBody ProjectCostCenterId projectCostCenterId) throws URISyntaxException {
        log.debug("REST request to update ProjectCostCenterId : {}", projectCostCenterId);
        if (projectCostCenterId.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectCostCenterId result = projectCostCenterIdService.save(projectCostCenterId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectCostCenterId.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-cost-center-ids} : get all the projectCostCenterIds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectCostCenterIds in body.
     */
    @CrossOrigin
    @GetMapping("/project-cost-center-ids")
    public ResponseEntity<List<ProjectCostCenterId>> getAllProjectCostCenterIds( ) {
        log.debug("REST request to get ProjectCostCenterIds by criteria: {}");
        List<ProjectCostCenterId> entityList = projectCostCenterIdService.findAll();
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /project-cost-center-ids/:id} : get the "id" projectCostCenterId.
     *
     * @param id the id of the projectCostCenterId to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectCostCenterId, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-cost-center-ids/{id}")
    public ResponseEntity<ProjectCostCenterId> getProjectCostCenterId(@PathVariable Long id) {
        log.debug("REST request to get ProjectCostCenterId : {}", id);
        Optional<ProjectCostCenterId> projectCostCenterId = projectCostCenterIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectCostCenterId);
    }

    /**
     * {@code DELETE  /project-cost-center-ids/:id} : delete the "id" projectCostCenterId.
     *
     * @param id the id of the projectCostCenterId to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-cost-center-ids/{id}")
    public ResponseEntity<Void> deleteProjectCostCenterId(@PathVariable Long id) {
        log.debug("REST request to delete ProjectCostCenterId : {}", id);
        projectCostCenterIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
