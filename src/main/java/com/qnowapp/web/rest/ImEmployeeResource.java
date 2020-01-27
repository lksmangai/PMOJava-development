package com.qnowapp.web.rest;

import com.qnowapp.domain.ImEmployee;
import com.qnowapp.service.ImEmployeeService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.ImEmployeeCriteria;
import com.qnowapp.service.ImEmployeeQueryService;

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
 * REST controller for managing {@link com.qnowapp.domain.ImEmployee}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ImEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(ImEmployeeResource.class);

    private static final String ENTITY_NAME = "imEmployee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImEmployeeService imEmployeeService;

    private final ImEmployeeQueryService imEmployeeQueryService;

    public ImEmployeeResource(ImEmployeeService imEmployeeService, ImEmployeeQueryService imEmployeeQueryService) {
        this.imEmployeeService = imEmployeeService;
        this.imEmployeeQueryService = imEmployeeQueryService;
    }

    /**
     * {@code POST  /im-employees} : Create a new imEmployee.
     *
     * @param imEmployee the imEmployee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imEmployee, or with status {@code 400 (Bad Request)} if the imEmployee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/im-employees")
    public ResponseEntity<ImEmployee> createImEmployee(@RequestBody ImEmployee imEmployee) throws URISyntaxException {
        log.debug("REST request to save ImEmployee : {}", imEmployee);
        if (imEmployee.getId() != null) {
            throw new BadRequestAlertException("A new imEmployee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImEmployee result = imEmployeeService.save(imEmployee);
        return ResponseEntity.created(new URI("/api/im-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /im-employees} : Updates an existing imEmployee.
     *
     * @param imEmployee the imEmployee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imEmployee,
     * or with status {@code 400 (Bad Request)} if the imEmployee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imEmployee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/im-employees")
    public ResponseEntity<ImEmployee> updateImEmployee(@RequestBody ImEmployee imEmployee) throws URISyntaxException {
        log.debug("REST request to update ImEmployee : {}", imEmployee);
        if (imEmployee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImEmployee result = imEmployeeService.save(imEmployee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imEmployee.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /im-employees} : get all the imEmployees.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imEmployees in body.
     */
    @CrossOrigin
    @GetMapping("/im-employees")
    public ResponseEntity<List<ImEmployee>> getAllImEmployees(ImEmployeeCriteria criteria) {
        log.debug("REST request to get ImEmployees by criteria: {}", criteria);
        List<ImEmployee> entityList = imEmployeeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /im-employees/count} : count all the imEmployees.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/im-employees/count")
    public ResponseEntity<Long> countImEmployees(ImEmployeeCriteria criteria) {
        log.debug("REST request to count ImEmployees by criteria: {}", criteria);
        return ResponseEntity.ok().body(imEmployeeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /im-employees/:id} : get the "id" imEmployee.
     *
     * @param id the id of the imEmployee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imEmployee, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/im-employees/{id}")
    public ResponseEntity<ImEmployee> getImEmployee(@PathVariable Long id) {
        log.debug("REST request to get ImEmployee : {}", id);
        Optional<ImEmployee> imEmployee = imEmployeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imEmployee);
    }

    /**
     * {@code DELETE  /im-employees/:id} : delete the "id" imEmployee.
     *
     * @param id the id of the imEmployee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/im-employees/{id}")
    public ResponseEntity<Void> deleteImEmployee(@PathVariable Long id) {
        log.debug("REST request to delete ImEmployee : {}", id);
        imEmployeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
