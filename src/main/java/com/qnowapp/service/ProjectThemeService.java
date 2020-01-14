package com.qnowapp.service;

import com.qnowapp.domain.ProjectTheme;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectTheme}.
 */
public interface ProjectThemeService {

    /**
     * Save a projectTheme.
     *
     * @param projectTheme the entity to save.
     * @return the persisted entity.
     */
    ProjectTheme save(ProjectTheme projectTheme);

    /**
     * Get all the projectThemes.
     *
     * @return the list of entities.
     */
    List<ProjectTheme> findAll();


    /**
     * Get the "id" projectTheme.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectTheme> findOne(Long id);

    /**
     * Delete the "id" projectTheme.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
