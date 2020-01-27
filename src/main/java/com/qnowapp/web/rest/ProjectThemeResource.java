package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.domain.ProjectTheme;
import com.qnowapp.repository.ProjectThemeRepository;
import com.qnowapp.service.ProjectThemeQueryService;
import com.qnowapp.service.ProjectThemeService;
import com.qnowapp.service.dto.ProjectThemeCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.ProjectTheme}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectThemeResource {

    private final Logger log = LoggerFactory.getLogger(ProjectThemeResource.class);

    private static final String ENTITY_NAME = "projectTheme";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
   ProjectThemeRepository projectThemeRepository;
    private static Boolean fromTesting = true;
   
    private final ProjectThemeService projectThemeService;

  
    private final ProjectThemeQueryService projectThemeQueryService;

    public ProjectThemeResource( ProjectThemeService projectThemeService, ProjectThemeQueryService projectThemeQueryService) {
        this.projectThemeService = projectThemeService;
        this.projectThemeQueryService = projectThemeQueryService;
       

    }
    public static void setFromTesting(Boolean bState) {
        fromTesting = bState;
    }
    /**
     * {@code POST  /project-themes} : Create a new projectTheme.
     *
     * @param projectTheme the projectTheme to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectTheme, or with status {@code 400 (Bad Request)} if the projectTheme has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-themes")
    public ResponseEntity<ProjectTheme> createProjectTheme(@RequestBody ProjectTheme projectTheme) throws URISyntaxException {
        log.debug("REST request to save ProjectTheme : {}", projectTheme);
        if (projectTheme.getId() != null) {
            throw new BadRequestAlertException("A new projectTheme cannot already have an ID", ENTITY_NAME, "idexists");
        }else {

            ProjectTheme result = projectThemeRepository.save(projectTheme);
            ResponseEntity<ProjectTheme> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);


            if (fromTesting == false) {
                System.out.println(projectTheme.getName());
                String csvFile1 = "src\\main\\resources\\theam.csv";
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
                                projectTheme.setId(null);
                                projectTheme.setCode(country[0]);
                                System.out.println("2");
                                projectTheme.setName(country[1]);
                                System.out.println("3");
                                projectTheme.setDescription(country[2]);
                                ProjectTheme result2 = projectThemeRepository.save(projectTheme);   
                                System.out.println(result2.getId());
                                System.out.println(projectTheme + "new project created");
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
     * {@code PUT  /project-themes} : Updates an existing projectTheme.
     *
     * @param projectTheme the projectTheme to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectTheme,
     * or with status {@code 400 (Bad Request)} if the projectTheme is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectTheme couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-themes")
    public ResponseEntity<ProjectTheme> updateProjectTheme(@RequestBody ProjectTheme projectTheme) throws URISyntaxException {
        log.debug("REST request to update ProjectTheme : {}", projectTheme);
        if (projectTheme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectTheme result = projectThemeService.save(projectTheme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectTheme.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-themes} : get all the projectThemes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectThemes in body.
     */
    @CrossOrigin
    @GetMapping("/project-themes")
    public ResponseEntity<List<ProjectTheme>> getAllProjectThemes(ProjectThemeCriteria criteria) {
        log.debug("REST request to get ProjectThemes by criteria: {}", criteria);
        List<ProjectTheme> entityList = projectThemeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /project-themes/count} : count all the projectThemes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/project-themes/count")
    public ResponseEntity<Long> countProjectThemes(ProjectThemeCriteria criteria) {
        log.debug("REST request to count ProjectThemes by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectThemeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-themes/:id} : get the "id" projectTheme.
     *
     * @param id the id of the projectTheme to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectTheme, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-themes/{id}")
    public ResponseEntity<ProjectTheme> getProjectTheme(@PathVariable Long id) {
        log.debug("REST request to get ProjectTheme : {}", id);
        Optional<ProjectTheme> projectTheme = projectThemeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectTheme);
    }

    /**
     * {@code DELETE  /project-themes/:id} : delete the "id" projectTheme.
     *
     * @param id the id of the projectTheme to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-themes/{id}")
    public ResponseEntity<Void> deleteProjectTheme(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTheme : {}", id);
        projectThemeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
