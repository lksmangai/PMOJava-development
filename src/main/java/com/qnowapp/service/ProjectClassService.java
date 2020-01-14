package com.qnowapp.service;

import com.qnowapp.domain.ProjectClass;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectClass}.
 */
public interface ProjectClassService {

    /**
     * Save a projectClass.
     *
     * @param projectClass the entity to save.
     * @return the persisted entity.
     */
    ProjectClass save(ProjectClass projectClass);

    /**
     * Get all the projectClasses.
     *
     * @return the list of entities.
     */
    List<ProjectClass> findAll();


    /**
     * Get the "id" projectClass.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectClass> findOne(Long id);

    /**
     * Delete the "id" projectClass.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
