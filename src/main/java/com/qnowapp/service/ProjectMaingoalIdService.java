package com.qnowapp.service;

import com.qnowapp.domain.ProjectMaingoalId;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectMaingoalId}.
 */
public interface ProjectMaingoalIdService {

    /**
     * Save a projectMaingoalId.
     *
     * @param projectMaingoalId the entity to save.
     * @return the persisted entity.
     */
    ProjectMaingoalId save(ProjectMaingoalId projectMaingoalId);

    /**
     * Get all the projectMaingoalIds.
     *
     * @return the list of entities.
     */
    List<ProjectMaingoalId> findAll();


    /**
     * Get the "id" projectMaingoalId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectMaingoalId> findOne(Long id);

    /**
     * Delete the "id" projectMaingoalId.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
