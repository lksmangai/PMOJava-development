package com.qnowapp.service;

import com.qnowapp.domain.ProjectInitiativeId;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectInitiativeId}.
 */
public interface ProjectInitiativeIdService {

    /**
     * Save a projectInitiativeId.
     *
     * @param projectInitiativeId the entity to save.
     * @return the persisted entity.
     */
    ProjectInitiativeId save(ProjectInitiativeId projectInitiativeId);

    /**
     * Get all the projectInitiativeIds.
     *
     * @return the list of entities.
     */
    List<ProjectInitiativeId> findAll();


    /**
     * Get the "id" projectInitiativeId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectInitiativeId> findOne(Long id);

    /**
     * Delete the "id" projectInitiativeId.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
