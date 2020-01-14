package com.qnowapp.service;

import com.qnowapp.domain.ProjectStatusId;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectStatusId}.
 */
public interface ProjectStatusIdService {

    /**
     * Save a projectStatusId.
     *
     * @param projectStatusId the entity to save.
     * @return the persisted entity.
     */
    ProjectStatusId save(ProjectStatusId projectStatusId);

    /**
     * Get all the projectStatusIds.
     *
     * @return the list of entities.
     */
    List<ProjectStatusId> findAll();


    /**
     * Get the "id" projectStatusId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectStatusId> findOne(Long id);

    /**
     * Delete the "id" projectStatusId.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
