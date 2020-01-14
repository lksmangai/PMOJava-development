package com.qnowapp.service;

import com.qnowapp.domain.ProjectSubgoalId;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectSubgoalId}.
 */
public interface ProjectSubgoalIdService {

    /**
     * Save a projectSubgoalId.
     *
     * @param projectSubgoalId the entity to save.
     * @return the persisted entity.
     */
    ProjectSubgoalId save(ProjectSubgoalId projectSubgoalId);

    /**
     * Get all the projectSubgoalIds.
     *
     * @return the list of entities.
     */
    List<ProjectSubgoalId> findAll();


    /**
     * Get the "id" projectSubgoalId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectSubgoalId> findOne(Long id);

    /**
     * Delete the "id" projectSubgoalId.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
