package com.qnowapp.service;

import com.qnowapp.domain.ProjectTypeId;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectTypeId}.
 */
public interface ProjectTypeIdService {

    /**
     * Save a projectTypeId.
     *
     * @param projectTypeId the entity to save.
     * @return the persisted entity.
     */
    ProjectTypeId save(ProjectTypeId projectTypeId);

    /**
     * Get all the projectTypeIds.
     *
     * @return the list of entities.
     */
    List<ProjectTypeId> findAll();


    /**
     * Get the "id" projectTypeId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectTypeId> findOne(Long id);

    /**
     * Delete the "id" projectTypeId.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
