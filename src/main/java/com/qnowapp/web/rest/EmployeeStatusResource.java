package com.qnowapp.web.rest;

import com.qnowapp.domain.EmployeeStatus;
import com.qnowapp.service.EmployeeStatusService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.EmployeeStatusCriteria;
import com.qnowapp.service.EmployeeStatusQueryService;

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
 * REST controller for managing {@link com.qnowapp.domain.EmployeeStatus}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class EmployeeStatusResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeStatusResource.class);

    private static final String ENTITY_NAME = "employeeStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeStatusService employeeStatusService;

    private final EmployeeStatusQueryService employeeStatusQueryService;

    public EmployeeStatusResource(EmployeeStatusService employeeStatusService, EmployeeStatusQueryService employeeStatusQueryService) {
        this.employeeStatusService = employeeStatusService;
        this.employeeStatusQueryService = employeeStatusQueryService;
    }

    /**
     * {@code POST  /employee-statuses} : Create a new employeeStatus.
     *
     * @param employeeStatus the employeeStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeStatus, or with status {@code 400 (Bad Request)} if the employeeStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/employee-statuses")
    public ResponseEntity<EmployeeStatus> createEmployeeStatus(@RequestBody EmployeeStatus employeeStatus) throws URISyntaxException {
        log.debug("REST request to save EmployeeStatus : {}", employeeStatus);
        if (employeeStatus.getId() != null) {
            throw new BadRequestAlertException("A new employeeStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeStatus result = employeeStatusService.save(employeeStatus);
        return ResponseEntity.created(new URI("/api/employee-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-statuses} : Updates an existing employeeStatus.
     *
     * @param employeeStatus the employeeStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeStatus,
     * or with status {@code 400 (Bad Request)} if the employeeStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/employee-statuses")
    public ResponseEntity<EmployeeStatus> updateEmployeeStatus(@RequestBody EmployeeStatus employeeStatus) throws URISyntaxException {
        log.debug("REST request to update EmployeeStatus : {}", employeeStatus);
        if (employeeStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeStatus result = employeeStatusService.save(employeeStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-statuses} : get all the employeeStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeStatuses in body.
     */
    @CrossOrigin
    @GetMapping("/employee-statuses")
    public ResponseEntity<List<EmployeeStatus>> getAllEmployeeStatuses(EmployeeStatusCriteria criteria) {
        log.debug("REST request to get EmployeeStatuses by criteria: {}", criteria);
        List<EmployeeStatus> entityList = employeeStatusQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /employee-statuses/count} : count all the employeeStatuses.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/employee-statuses/count")
    public ResponseEntity<Long> countEmployeeStatuses(EmployeeStatusCriteria criteria) {
        log.debug("REST request to count EmployeeStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(employeeStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /employee-statuses/:id} : get the "id" employeeStatus.
     *
     * @param id the id of the employeeStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeStatus, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/employee-statuses/{id}")
    public ResponseEntity<EmployeeStatus> getEmployeeStatus(@PathVariable Long id) {
        log.debug("REST request to get EmployeeStatus : {}", id);
        Optional<EmployeeStatus> employeeStatus = employeeStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeStatus);
    }

    /**
     * {@code DELETE  /employee-statuses/:id} : delete the "id" employeeStatus.
     *
     * @param id the id of the employeeStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/employee-statuses/{id}")
    public ResponseEntity<Void> deleteEmployeeStatus(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeStatus : {}", id);
        employeeStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
