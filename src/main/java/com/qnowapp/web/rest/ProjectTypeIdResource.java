package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectClass;

import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.domain.ProjectTypeId;
import com.qnowapp.repository.ProjectTypeIdRepository;

import com.qnowapp.service.ProjectTypeIdService;
import com.qnowapp.service.dto.ProjectTypeIdCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.ProjectTypeId}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectTypeIdResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTypeIdResource.class);

    private static final String ENTITY_NAME = "projectTypeId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
 ProjectTypeIdRepository projectTypeIdRepository;
    private static Boolean fromTesting = true;

  
    
    private final ProjectTypeIdService projectTypeIdService;

  

    public ProjectTypeIdResource( ProjectTypeIdService projectTypeIdService) {
        this.projectTypeIdService = projectTypeIdService;
      

    }
    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }
    /**
     * {@code POST  /project-type-ids} : Create a new projectTypeId.
     *
     * @param projectTypeId the projectTypeId to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTypeId, or with status {@code 400 (Bad Request)} if the projectTypeId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-type-ids")
    public ResponseEntity<ProjectTypeId> createProjectTypeId(@RequestBody ProjectTypeId projectTypeId) throws URISyntaxException {
        log.debug("REST request to save ProjectTypeId : {}", projectTypeId);
        if (projectTypeId.getId() != null) {
            throw new BadRequestAlertException("A new projectTypeId cannot already have an ID", ENTITY_NAME, "idexists");
        }else {
  
            ProjectTypeId result = projectTypeIdRepository.save(projectTypeId);
            ResponseEntity<ProjectTypeId> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);

            if (fromTesting == false) {
               
                String csvFile1 = "src\\main\\resources\\projecttype.csv";
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
                                
                                projectTypeId.setId(null);
                                projectTypeId.setCode(country[0]);
                                
                                projectTypeId.setName(country[1]);
                               
                                projectTypeId.setDescription(country[2]);
                                ProjectTypeId result2 = projectTypeIdRepository.save(projectTypeId);    
                               
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
     * {@code PUT  /project-type-ids} : Updates an existing projectTypeId.
     *
     * @param projectTypeId the projectTypeId to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTypeId,
     * or with status {@code 400 (Bad Request)} if the projectTypeId is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTypeId couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-type-ids")
    public ResponseEntity<ProjectTypeId> updateProjectTypeId(@RequestBody ProjectTypeId projectTypeId) throws URISyntaxException {
        log.debug("REST request to update ProjectTypeId : {}", projectTypeId);
        if (projectTypeId.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTypeId result = projectTypeIdService.save(projectTypeId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTypeId.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-type-ids} : get all the projectTypeIds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectTypeIds in body.
     */
    @CrossOrigin
    @GetMapping("/project-type-ids")
    public ResponseEntity<List<ProjectTypeId>> getAllProjectTypeIds( ) {
        log.debug("REST request to get ProjectTypeIds by criteria: {}");
        List<ProjectTypeId> entityList = projectTypeIdService.findAll();
        return ResponseEntity.ok().body(entityList);
    }



    /**
     * {@code GET  /project-type-ids/:id} : get the "id" projectTypeId.
     *
     * @param id the id of the projectTypeId to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTypeId, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-type-ids/{id}")
    public ResponseEntity<ProjectTypeId> getProjectTypeId(@PathVariable Long id) {
        log.debug("REST request to get ProjectTypeId : {}", id);
        Optional<ProjectTypeId> projectTypeId = projectTypeIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTypeId);
    }

    /**
     * {@code DELETE  /project-type-ids/:id} : delete the "id" projectTypeId.
     *
     * @param id the id of the projectTypeId to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-type-ids/{id}")
    public ResponseEntity<Void> deleteProjectTypeId(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTypeId : {}", id);
        projectTypeIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
