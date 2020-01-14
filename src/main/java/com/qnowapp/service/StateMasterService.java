package com.qnowapp.service;

import com.qnowapp.domain.StateMaster;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link StateMaster}.
 */
public interface StateMasterService {

    /**
     * Save a stateMaster.
     *
     * @param stateMaster the entity to save.
     * @return the persisted entity.
     */
    StateMaster save(StateMaster stateMaster);

    /**
     * Get all the stateMasters.
     *
     * @return the list of entities.
     */
    List<StateMaster> findAll();


    /**
     * Get the "id" stateMaster.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StateMaster> findOne(Long id);

    /**
     * Delete the "id" stateMaster.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
