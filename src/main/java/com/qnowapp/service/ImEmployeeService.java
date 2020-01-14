package com.qnowapp.service;

import com.qnowapp.domain.ImEmployee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ImEmployee}.
 */
public interface ImEmployeeService {

    /**
     * Save a imEmployee.
     *
     * @param imEmployee the entity to save.
     * @return the persisted entity.
     */
    ImEmployee save(ImEmployee imEmployee);

    /**
     * Get all the imEmployees.
     *
     * @return the list of entities.
     */
    List<ImEmployee> findAll();

    /**
     * Get all the imEmployees with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ImEmployee> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" imEmployee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImEmployee> findOne(Long id);

    /**
     * Delete the "id" imEmployee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
