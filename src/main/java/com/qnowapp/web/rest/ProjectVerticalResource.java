package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectInitiativeId;

import com.qnowapp.domain.ProjectVertical;
import com.qnowapp.repository.ProjectVerticalRepository;

import com.qnowapp.service.ProjectVerticalService;
import com.qnowapp.service.dto.ProjectVerticalCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.ProjectVertical}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectVerticalResource {

    private final Logger log = LoggerFactory.getLogger(ProjectVerticalResource.class);

    private static final String ENTITY_NAME = "projectVertical";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
 ProjectVerticalRepository projectVerticalRepository;
    private static Boolean fromTesting = true;



   
    private final ProjectVerticalService projectVerticalService;

   

    public ProjectVerticalResource(ProjectVerticalService projectVerticalService) {
        this.projectVerticalService = projectVerticalService;
       

    }
    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }

    /**
     * {@code POST  /project-verticals} : Create a new projectVertical.
     *
     * @param projectVertical the projectVertical to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectVertical, or with status {@code 400 (Bad Request)} if the projectVertical has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-verticals")
    public ResponseEntity<ProjectVertical> createProjectVertical(@RequestBody ProjectVertical projectVertical) throws URISyntaxException {
        log.debug("REST request to save ProjectVertical : {}", projectVertical);
        if (projectVertical.getId() != null) {
            throw new BadRequestAlertException("A new projectVertical cannot already have an ID", ENTITY_NAME, "idexists");
        }else {
  
            ProjectVertical result = projectVerticalRepository.save(projectVertical);
            ResponseEntity<ProjectVertical> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
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
                             
                                projectVertical.setId(null);
                                projectVertical.setCode(country[0]);
                              
                                projectVertical.setName(country[1]);
                                
                                projectVertical.setDescription(country[2]);
                                ProjectVertical result2 = projectVerticalRepository.save(projectVertical);  
                                
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
     * {@code PUT  /project-verticals} : Updates an existing projectVertical.
     *
     * @param projectVertical the projectVertical to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectVertical,
     * or with status {@code 400 (Bad Request)} if the projectVertical is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectVertical couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-verticals")
    public ResponseEntity<ProjectVertical> updateProjectVertical(@RequestBody ProjectVertical projectVertical) throws URISyntaxException {
        log.debug("REST request to update ProjectVertical : {}", projectVertical);
        if (projectVertical.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectVertical result = projectVerticalService.save(projectVertical);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectVertical.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-verticals} : get all the projectVerticals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectVerticals in body.
     */
    @CrossOrigin
    @GetMapping("/project-verticals")
    public ResponseEntity<List<ProjectVertical>> getAllProjectVerticals( ) {
        log.debug("REST request to get ProjectVerticals by criteria: {}");
        List<ProjectVertical> entityList = projectVerticalService.findAll();
        return ResponseEntity.ok().body(entityList);
    }



    /**
     * {@code GET  /project-verticals/:id} : get the "id" projectVertical.
     *
     * @param id the id of the projectVertical to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectVertical, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-verticals/{id}")
    public ResponseEntity<ProjectVertical> getProjectVertical(@PathVariable Long id) {
        log.debug("REST request to get ProjectVertical : {}", id);
        Optional<ProjectVertical> projectVertical = projectVerticalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectVertical);
    }

    /**
     * {@code DELETE  /project-verticals/:id} : delete the "id" projectVertical.
     *
     * @param id the id of the projectVertical to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-verticals/{id}")
    public ResponseEntity<Void> deleteProjectVertical(@PathVariable Long id) {
        log.debug("REST request to delete ProjectVertical : {}", id);
        projectVerticalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
