package com.qnowapp.service;

import com.qnowapp.domain.ProjectAllocation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectAllocation}.
 */
public interface ProjectAllocationService {

    /**
     * Save a projectAllocation.
     *
     * @param projectAllocation the entity to save.
     * @return the persisted entity.
     */
    ProjectAllocation save(ProjectAllocation projectAllocation);

    /**
     * Get all the projectAllocations.
     *
     * @return the list of entities.
     */
    List<ProjectAllocation> findAll();


    /**
     * Get the "id" projectAllocation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectAllocation> findOne(Long id);

    /**
     * Delete the "id" projectAllocation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
