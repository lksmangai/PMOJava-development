package com.qnowapp.service;

import com.qnowapp.domain.ProjectBoardId;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectBoardId}.
 */
public interface ProjectBoardIdService {

    /**
     * Save a projectBoardId.
     *
     * @param projectBoardId the entity to save.
     * @return the persisted entity.
     */
    ProjectBoardId save(ProjectBoardId projectBoardId);

    /**
     * Get all the projectBoardIds.
     *
     * @return the list of entities.
     */
    List<ProjectBoardId> findAll();


    /**
     * Get the "id" projectBoardId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectBoardId> findOne(Long id);

    /**
     * Delete the "id" projectBoardId.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
