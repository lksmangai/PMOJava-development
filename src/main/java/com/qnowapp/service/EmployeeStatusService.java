package com.qnowapp.service;

import com.qnowapp.domain.EmployeeStatus;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link EmployeeStatus}.
 */
public interface EmployeeStatusService {

    /**
     * Save a employeeStatus.
     *
     * @param employeeStatus the entity to save.
     * @return the persisted entity.
     */
    EmployeeStatus save(EmployeeStatus employeeStatus);

    /**
     * Get all the employeeStatuses.
     *
     * @return the list of entities.
     */
    List<EmployeeStatus> findAll();


    /**
     * Get the "id" employeeStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeStatus> findOne(Long id);

    /**
     * Delete the "id" employeeStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
