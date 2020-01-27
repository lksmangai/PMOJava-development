package com.qnowapp.web.rest;

import com.qnowapp.domain.ProjectClass;
import com.qnowapp.domain.ProjectRoles;
import com.qnowapp.repository.ProjectRolesRepository;
import com.qnowapp.service.ProjectRolesQueryService;
import com.qnowapp.service.ProjectRolesService;
import com.qnowapp.service.dto.ProjectRolesCriteria;
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
 * REST controller for managing {@link com.qnowapp.domain.ProjectRoles}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProjectRolesResource {

    private final Logger log = LoggerFactory.getLogger(ProjectRolesResource.class);

    private static final String ENTITY_NAME = "projectRoles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
    ProjectRolesRepository projectRolesRepository;
    private static Boolean fromTesting = true;




    private final ProjectRolesService projectRolesService;

    private final ProjectRolesQueryService projectRolesQueryService;

    public ProjectRolesResource(ProjectRolesService projectRolesService, ProjectRolesQueryService projectRolesQueryService) {
        this.projectRolesService = projectRolesService;
        this.projectRolesQueryService = projectRolesQueryService;
     
    }
    /**
     * {@code POST  /project-roles} : Create a new projectRoles.
     *
     * @param projectRoles the projectRoles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectRoles, or with status {@code 400 (Bad Request)} if the projectRoles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/project-roles")
    public ResponseEntity<ProjectRoles> createProjectRoles(@RequestBody ProjectRoles projectRoles) throws URISyntaxException {
        log.debug("REST request to save ProjectRoles : {}", projectRoles);
        if (projectRoles.getId() != null) {
            throw new BadRequestAlertException("A new projectRoles cannot already have an ID", ENTITY_NAME, "idexists");
        }else {
  
            ProjectRoles result = projectRolesRepository.save(projectRoles);
            ResponseEntity<ProjectRoles> a = ResponseEntity.created(new URI("/api/project-initiative-ids/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);

            if (fromTesting == false) {
                System.out.println(projectRoles.getName());
                String csvFile1 = "src\\main\\resources\\roles.csv";
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
                                projectRoles.setId(null);
                                projectRoles.setCode(country[0]);
                                System.out.println("2");
                                projectRoles.setName(country[1]);
                                System.out.println("3");
                                projectRoles.setDescription(country[2]);
                                ProjectRoles result2 = projectRolesRepository.save(projectRoles);   
                                System.out.println(result2.getId());
                                System.out.println(projectRoles + "new project created");
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
     * {@code PUT  /project-roles} : Updates an existing projectRoles.
     *
     * @param projectRoles the projectRoles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectRoles,
     * or with status {@code 400 (Bad Request)} if the projectRoles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectRoles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/project-roles")
    public ResponseEntity<ProjectRoles> updateProjectRoles(@RequestBody ProjectRoles projectRoles) throws URISyntaxException {
        log.debug("REST request to update ProjectRoles : {}", projectRoles);
        if (projectRoles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectRoles result = projectRolesService.save(projectRoles);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectRoles.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /project-roles} : get all the projectRoles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectRoles in body.
     */
    @CrossOrigin
    @GetMapping("/project-roles")
    public ResponseEntity<List<ProjectRoles>> getAllProjectRoles(ProjectRolesCriteria criteria) {
        log.debug("REST request to get ProjectRoles by criteria: {}", criteria);
        List<ProjectRoles> entityList = projectRolesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /project-roles/count} : count all the projectRoles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/project-roles/count")
    public ResponseEntity<Long> countProjectRoles(ProjectRolesCriteria criteria) {
        log.debug("REST request to count ProjectRoles by criteria: {}", criteria);
        return ResponseEntity.ok().body(projectRolesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /project-roles/:id} : get the "id" projectRoles.
     *
     * @param id the id of the projectRoles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectRoles, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/project-roles/{id}")
    public ResponseEntity<ProjectRoles> getProjectRoles(@PathVariable Long id) {
        log.debug("REST request to get ProjectRoles : {}", id);
        Optional<ProjectRoles> projectRoles = projectRolesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectRoles);
    }

    /**
     * {@code DELETE  /project-roles/:id} : delete the "id" projectRoles.
     *
     * @param id the id of the projectRoles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/project-roles/{id}")
    public ResponseEntity<Void> deleteProjectRoles(@PathVariable Long id) {
        log.debug("REST request to delete ProjectRoles : {}", id);
        projectRolesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
