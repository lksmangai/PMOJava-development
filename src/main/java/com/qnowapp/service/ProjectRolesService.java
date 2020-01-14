package com.qnowapp.service;

import com.qnowapp.domain.ProjectRoles;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectRoles}.
 */
public interface ProjectRolesService {

    /**
     * Save a projectRoles.
     *
     * @param projectRoles the entity to save.
     * @return the persisted entity.
     */
    ProjectRoles save(ProjectRoles projectRoles);

    /**
     * Get all the projectRoles.
     *
     * @return the list of entities.
     */
    List<ProjectRoles> findAll();


    /**
     * Get the "id" projectRoles.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectRoles> findOne(Long id);

    /**
     * Delete the "id" projectRoles.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
