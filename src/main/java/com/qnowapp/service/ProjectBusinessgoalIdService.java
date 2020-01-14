package com.qnowapp.service;

import com.qnowapp.domain.ProjectBusinessgoalId;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectBusinessgoalId}.
 */
public interface ProjectBusinessgoalIdService {

    /**
     * Save a projectBusinessgoalId.
     *
     * @param projectBusinessgoalId the entity to save.
     * @return the persisted entity.
     */
    ProjectBusinessgoalId save(ProjectBusinessgoalId projectBusinessgoalId);

    /**
     * Get all the projectBusinessgoalIds.
     *
     * @return the list of entities.
     */
    List<ProjectBusinessgoalId> findAll();


    /**
     * Get the "id" projectBusinessgoalId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectBusinessgoalId> findOne(Long id);

    /**
     * Delete the "id" projectBusinessgoalId.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
