package com.qnowapp.service;

import com.qnowapp.domain.ProjectTag;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectTag}.
 */
public interface ProjectTagService {

    /**
     * Save a projectTag.
     *
     * @param projectTag the entity to save.
     * @return the persisted entity.
     */
    ProjectTag save(ProjectTag projectTag);

    /**
     * Get all the projectTags.
     *
     * @return the list of entities.
     */
    List<ProjectTag> findAll();


    /**
     * Get the "id" projectTag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectTag> findOne(Long id);

    /**
     * Delete the "id" projectTag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
