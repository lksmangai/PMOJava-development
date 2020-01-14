package com.qnowapp.service;

import com.qnowapp.domain.Skills;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Skills}.
 */
public interface SkillsService {

    /**
     * Save a skills.
     *
     * @param skills the entity to save.
     * @return the persisted entity.
     */
    Skills save(Skills skills);

    /**
     * Get all the skills.
     *
     * @return the list of entities.
     */
    List<Skills> findAll();


    /**
     * Get the "id" skills.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Skills> findOne(Long id);

    /**
     * Delete the "id" skills.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
