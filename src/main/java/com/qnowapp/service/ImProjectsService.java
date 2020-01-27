package com.qnowapp.service;

import com.qnowapp.domain.ImProjects;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ImProjects}.
 */
public interface ImProjectsService {

    /**
     * Save a imProjects.
     *
     * @param imProjects the entity to save.
     * @return the persisted entity.
     */
    ImProjects save(ImProjects imProjects);

    /**
     * Get all the imProjects.
     *
     * @return the list of entities.
     */
    List<ImProjects> findAll();


    /**
     * Get the "id" imProjects.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImProjects> findOne(Long id);

    /**
     * Delete the "id" imProjects.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
