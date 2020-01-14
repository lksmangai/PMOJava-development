package com.qnowapp.web.rest;

import com.qnowapp.domain.Roles;
import com.qnowapp.service.RolesService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.RolesCriteria;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qnowapp.domain.Roles}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class RolesResource {

    private final Logger log = LoggerFactory.getLogger(RolesResource.class);

    private static final String ENTITY_NAME = "roles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RolesService rolesService;

    

    public RolesResource(RolesService rolesService) {
        this.rolesService = rolesService;
      
    }

    /**
     * {@code POST  /roles} : Create a new roles.
     *
     * @param roles the roles to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roles, or with status {@code 400 (Bad Request)} if the roles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/roles")
    public ResponseEntity<Roles> createRoles(@RequestBody Roles roles) throws URISyntaxException {
        log.debug("REST request to save Roles : {}", roles);
        if (roles.getId() != null) {
            throw new BadRequestAlertException("A new roles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Roles result = rolesService.save(roles);
        return ResponseEntity.created(new URI("/api/roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /roles} : Updates an existing roles.
     *
     * @param roles the roles to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roles,
     * or with status {@code 400 (Bad Request)} if the roles is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roles couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/roles")
    public ResponseEntity<Roles> updateRoles(@RequestBody Roles roles) throws URISyntaxException {
        log.debug("REST request to update Roles : {}", roles);
        if (roles.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Roles result = rolesService.save(roles);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, roles.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /roles} : get all the roles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roles in body.
     */
    @CrossOrigin
    @GetMapping("/roles")
    public ResponseEntity<List<Roles>> getAllRoles(RolesCriteria criteria) {
        log.debug("REST request to get Roles by criteria: {}", criteria);
        List<Roles> entityList = rolesService.findAll();
        return ResponseEntity.ok().body(entityList);
    }



    /**
     * {@code GET  /roles/:id} : get the "id" roles.
     *
     * @param id the id of the roles to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roles, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/roles/{id}")
    public ResponseEntity<Roles> getRoles(@PathVariable Long id) {
        log.debug("REST request to get Roles : {}", id);
        Optional<Roles> roles = rolesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roles);
    }

    /**
     * {@code DELETE  /roles/:id} : delete the "id" roles.
     *
     * @param id the id of the roles to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRoles(@PathVariable Long id) {
        log.debug("REST request to delete Roles : {}", id);
        rolesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
