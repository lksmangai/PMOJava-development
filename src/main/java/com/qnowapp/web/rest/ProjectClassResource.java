package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectClass;

import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.repository.ProjectClassRepository;

import com.qnowapp.service.ProjectClassService;
import com.qnowapp.service.dto.ProjectClassCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.ProjectClass}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectClassResource {

    private final Logger log = LoggerFactory.getLogger(ProjectClassResource.class);

    private static final String ENTITY_NAME = "projectClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
@Autowired
    ProjectClassRepository projectClassRepository;
    private static Boolean fromTesting = true;
 
    
    private final ProjectClassService projectClassService;


   
    public ProjectClassResource( ProjectClassService projectClassService) {
        this.projectClassService = projectClassService;

        

    }

    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }
    /**
     * {@code POST  /project-classes} : Create a new projectClass.
     *
     * @param projectClass the projectClass to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectClass, or with status {@code 400 (Bad Request)} if the projectClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-classes")
    public ResponseEntity<ProjectClass> createProjectClass(@RequestBody ProjectClass projectClass) throws URISyntaxException {
        log.debug("REST request to save ProjectClass : {}", projectClass);
        if (projectClass.getId() != null) {
            throw new BadRequestAlertException("A new projectClass cannot already have an ID", ENTITY_NAME, "idexists");
        }else {
  
            ProjectClass result = projectClassRepository.save(projectClass);
            ResponseEntity<ProjectClass> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
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
                           
                                projectClass.setId(null);
                                projectClass.setCode(country[0]);
                      
                                projectClass.setName(country[1]);
                                
                                projectClass.setDescription(country[2]);
                                ProjectClass result2 = projectClassRepository.save(projectClass);   
                              
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
     * {@code PUT  /project-classes} : Updates an existing projectClass.
     *
     * @param projectClass the projectClass to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectClass,
     * or with status {@code 400 (Bad Request)} if the projectClass is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectClass couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-classes")
    public ResponseEntity<ProjectClass> updateProjectClass(@RequestBody ProjectClass projectClass) throws URISyntaxException {
        log.debug("REST request to update ProjectClass : {}", projectClass);
        if (projectClass.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectClass result = projectClassService.save(projectClass);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectClass.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-classes} : get all the projectClasses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectClasses in body.
     */
    @CrossOrigin
    @GetMapping("/project-classes")
    public ResponseEntity<List<ProjectClass>> getAllProjectClasses( ) {
        log.debug("REST request to get ProjectClasses by criteria: {}");
        List<ProjectClass> entityList = projectClassService.findAll();
        return ResponseEntity.ok().body(entityList);
    }


    /**
     * {@code GET  /project-classes/:id} : get the "id" projectClass.
     *
     * @param id the id of the projectClass to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectClass, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-classes/{id}")
    public ResponseEntity<ProjectClass> getProjectClass(@PathVariable Long id) {
        log.debug("REST request to get ProjectClass : {}", id);
        Optional<ProjectClass> projectClass = projectClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectClass);
    }

    /**
     * {@code DELETE  /project-classes/:id} : delete the "id" projectClass.
     *
     * @param id the id of the projectClass to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-classes/{id}")
    public ResponseEntity<Void> deleteProjectClass(@PathVariable Long id) {
        log.debug("REST request to delete ProjectClass : {}", id);
        projectClassService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
