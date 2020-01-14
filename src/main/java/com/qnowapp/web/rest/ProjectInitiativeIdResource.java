package com.qnowapp.web.rest;

import com.qnowapp.domain.ImProjects;

import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.repository.ProjectInitiativeIdRepository;

import com.qnowapp.service.ProjectInitiativeIdService;
import com.qnowapp.service.dto.ProjectInitiativeIdCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.ProjectInitiativeId}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectInitiativeIdResource {

    private final Logger log = LoggerFactory.getLogger(ProjectInitiativeIdResource.class);

    private static final String ENTITY_NAME = "projectInitiativeId";
    private static Boolean fromTesting = true;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
 @Autowired
 ProjectInitiativeIdRepository projectInitiativeIdRepository;

   
    private final ProjectInitiativeIdService projectInitiativeIdService;

   
    public ProjectInitiativeIdResource( ProjectInitiativeIdService projectInitiativeIdService) {
        this.projectInitiativeIdService = projectInitiativeIdService;

    }


    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }

    /**
     * {@code POST  /project-initiative-ids} : Create a new projectInitiativeId.
     *
     * @param projectInitiativeId the projectInitiativeId to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectInitiativeId, or with status {@code 400 (Bad Request)} if the projectInitiativeId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-initiative-ids")
    public ResponseEntity<ProjectInitiativeId> createProjectInitiativeId(@RequestBody ProjectInitiativeId projectInitiativeId) throws URISyntaxException {
        log.debug("REST request to save ProjectInitiativeId : {}", projectInitiativeId);
        if (projectInitiativeId.getId() != null) {
            throw new BadRequestAlertException("A new projectInitiativeId cannot already have an ID", ENTITY_NAME, "idexists");
        }else {
            ProjectInitiativeId result = projectInitiativeIdRepository.save(projectInitiativeId);
            ResponseEntity<ProjectInitiativeId> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);

            if (fromTesting == false) {
               
                String csvFile1 = "src\\main\\resources\\initiative.csv";
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
                        
                                projectInitiativeId.setId(null);
                                projectInitiativeId.setCode(country[0]);
                                
                                projectInitiativeId.setName(country[1]);
                             
                                projectInitiativeId.setDescription(country[2]);
                                ProjectInitiativeId result2 = projectInitiativeIdRepository.save(projectInitiativeId);  
                             
                            }
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
                    
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
     * {@code PUT  /project-initiative-ids} : Updates an existing projectInitiativeId.
     *
     * @param projectInitiativeId the projectInitiativeId to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectInitiativeId,
     * or with status {@code 400 (Bad Request)} if the projectInitiativeId is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectInitiativeId couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-initiative-ids")
    public ResponseEntity<ProjectInitiativeId> updateProjectInitiativeId(@RequestBody ProjectInitiativeId projectInitiativeId) throws URISyntaxException {
        log.debug("REST request to update ProjectInitiativeId : {}", projectInitiativeId);
        if (projectInitiativeId.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectInitiativeId result = projectInitiativeIdService.save(projectInitiativeId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectInitiativeId.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-initiative-ids} : get all the projectInitiativeIds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectInitiativeIds in body.
     */
    @CrossOrigin
    @GetMapping("/project-initiative-ids")
    public ResponseEntity<List<ProjectInitiativeId>> getAllProjectInitiativeIds( ) {
        log.debug("REST request to get ProjectInitiativeIds by criteria: {}");
        List<ProjectInitiativeId> entityList = projectInitiativeIdService.findAll();
        return ResponseEntity.ok().body(entityList);
    }



    /**
     * {@code GET  /project-initiative-ids/:id} : get the "id" projectInitiativeId.
     *
     * @param id the id of the projectInitiativeId to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectInitiativeId, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-initiative-ids/{id}")
    public ResponseEntity<ProjectInitiativeId> getProjectInitiativeId(@PathVariable Long id) {
        log.debug("REST request to get ProjectInitiativeId : {}", id);
        Optional<ProjectInitiativeId> projectInitiativeId = projectInitiativeIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectInitiativeId);
    }

    /**
     * {@code DELETE  /project-initiative-ids/:id} : delete the "id" projectInitiativeId.
     *
     * @param id the id of the projectInitiativeId to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-initiative-ids/{id}")
    public ResponseEntity<Void> deleteProjectInitiativeId(@PathVariable Long id) {
        log.debug("REST request to delete ProjectInitiativeId : {}", id);
        projectInitiativeIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
