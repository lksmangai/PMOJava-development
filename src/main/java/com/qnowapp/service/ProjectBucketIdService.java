package com.qnowapp.service;

import com.qnowapp.domain.ProjectBucketId;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectBucketId}.
 */
public interface ProjectBucketIdService {

    /**
     * Save a projectBucketId.
     *
     * @param projectBucketId the entity to save.
     * @return the persisted entity.
     */
    ProjectBucketId save(ProjectBucketId projectBucketId);

    /**
     * Get all the projectBucketIds.
     *
     * @return the list of entities.
     */
    List<ProjectBucketId> findAll();


    /**
     * Get the "id" projectBucketId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectBucketId> findOne(Long id);

    /**
     * Delete the "id" projectBucketId.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
