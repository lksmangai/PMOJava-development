package com.qnowapp.service;

import com.qnowapp.domain.BacklogPractice;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BacklogPractice}.
 */
public interface BacklogPracticeService {

    /**
     * Save a backlogPractice.
     *
     * @param backlogPractice the entity to save.
     * @return the persisted entity.
     */
    BacklogPractice save(BacklogPractice backlogPractice);

    /**
     * Get all the backlogPractices.
     *
     * @return the list of entities.
     */
    List<BacklogPractice> findAll();


    /**
     * Get the "id" backlogPractice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BacklogPractice> findOne(Long id);

    /**
     * Delete the "id" backlogPractice.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
