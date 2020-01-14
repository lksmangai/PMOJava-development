package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectBucketId;

import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.repository.ProjectBucketIdRepository;

import com.qnowapp.service.ProjectBucketIdService;
import com.qnowapp.service.dto.ProjectBucketIdCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.ProjectBucketId}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectBucketIdResource {

    private final Logger log = LoggerFactory.getLogger(ProjectBucketIdResource.class);

    private static final String ENTITY_NAME = "projectBucketId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
@Autowired
ProjectBucketIdRepository projectBucketIdRepository;
    private static Boolean fromTesting = true;



    private final ProjectBucketIdService projectBucketIdService;

   

    public ProjectBucketIdResource( ProjectBucketIdService projectBucketIdService) {
        this.projectBucketIdService = projectBucketIdService;
      
       
    }
    
    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }

    /**
     * {@code POST  /project-bucket-ids} : Create a new projectBucketId.
     *
     * @param projectBucketId the projectBucketId to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectBucketId, or with status {@code 400 (Bad Request)} if the projectBucketId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-bucket-ids")
    public ResponseEntity<ProjectBucketId> createProjectBucketId(@RequestBody ProjectBucketId projectBucketId) throws URISyntaxException {
        log.debug("REST request to save ProjectBucketId : {}", projectBucketId);
        if (projectBucketId.getId() != null) {
            throw new BadRequestAlertException("A new projectBucketId cannot already have an ID", ENTITY_NAME, "idexists");
        }else {
  
            ProjectBucketId result = projectBucketIdRepository.save(projectBucketId);
            ResponseEntity<ProjectBucketId> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);

            if (fromTesting == false) {
               
                String csvFile1 = "src\\main\\resources\\bucket.csv";
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
                               
                                projectBucketId.setId(null);
                                projectBucketId.setCode(country[0]);
                               
                                projectBucketId.setName(country[1]);
                              
                                projectBucketId.setDescription(country[2]);
                                ProjectBucketId result2 = projectBucketIdRepository.save(projectBucketId);  
                                
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
     * {@code PUT  /project-bucket-ids} : Updates an existing projectBucketId.
     *
     * @param projectBucketId the projectBucketId to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectBucketId,
     * or with status {@code 400 (Bad Request)} if the projectBucketId is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectBucketId couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-bucket-ids")
    public ResponseEntity<ProjectBucketId> updateProjectBucketId(@RequestBody ProjectBucketId projectBucketId) throws URISyntaxException {
        log.debug("REST request to update ProjectBucketId : {}", projectBucketId);
        if (projectBucketId.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectBucketId result = projectBucketIdService.save(projectBucketId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectBucketId.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-bucket-ids} : get all the projectBucketIds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectBucketIds in body.
     */
    @CrossOrigin
    @GetMapping("/project-bucket-ids")
    public ResponseEntity<List<ProjectBucketId>> getAllProjectBucketIds( ) {
        log.debug("REST request to get ProjectBucketIds by criteria: {}");
        List<ProjectBucketId> entityList = projectBucketIdService.findAll();
        return ResponseEntity.ok().body(entityList);
    }



    /**
     * {@code GET  /project-bucket-ids/:id} : get the "id" projectBucketId.
     *
     * @param id the id of the projectBucketId to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectBucketId, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-bucket-ids/{id}")
    public ResponseEntity<ProjectBucketId> getProjectBucketId(@PathVariable Long id) {
        log.debug("REST request to get ProjectBucketId : {}", id);
        Optional<ProjectBucketId> projectBucketId = projectBucketIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectBucketId);
    }

    /**
     * {@code DELETE  /project-bucket-ids/:id} : delete the "id" projectBucketId.
     *
     * @param id the id of the projectBucketId to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-bucket-ids/{id}")
    public ResponseEntity<Void> deleteProjectBucketId(@PathVariable Long id) {
        log.debug("REST request to delete ProjectBucketId : {}", id);
        projectBucketIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
