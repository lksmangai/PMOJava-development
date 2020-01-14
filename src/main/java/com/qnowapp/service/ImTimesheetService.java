package com.qnowapp.service;

import com.qnowapp.domain.ImTimesheet;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ImTimesheet}.
 */
public interface ImTimesheetService {

    /**
     * Save a imTimesheet.
     *
     * @param imTimesheet the entity to save.
     * @return the persisted entity.
     */
    ImTimesheet save(ImTimesheet imTimesheet);

    /**
     * Get all the imTimesheets.
     *
     * @return the list of entities.
     */
    List<ImTimesheet> findAll();


    /**
     * Get the "id" imTimesheet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImTimesheet> findOne(Long id);

    /**
     * Delete the "id" imTimesheet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
