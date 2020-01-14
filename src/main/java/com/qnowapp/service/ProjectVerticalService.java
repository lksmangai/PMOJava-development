package com.qnowapp.service;

import com.qnowapp.domain.ProjectVertical;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectVertical}.
 */
public interface ProjectVerticalService {

    /**
     * Save a projectVertical.
     *
     * @param projectVertical the entity to save.
     * @return the persisted entity.
     */
    ProjectVertical save(ProjectVertical projectVertical);

    /**
     * Get all the projectVerticals.
     *
     * @return the list of entities.
     */
    List<ProjectVertical> findAll();


    /**
     * Get the "id" projectVertical.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectVertical> findOne(Long id);

    /**
     * Delete the "id" projectVertical.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
