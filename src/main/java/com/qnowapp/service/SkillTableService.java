package com.qnowapp.service;

import com.qnowapp.domain.SkillTable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SkillTable}.
 */
public interface SkillTableService {

    /**
     * Save a skillTable.
     *
     * @param skillTable the entity to save.
     * @return the persisted entity.
     */
    SkillTable save(SkillTable skillTable);

    /**
     * Get all the skillTables.
     *
     * @return the list of entities.
     */
    List<SkillTable> findAll();


    /**
     * Get the "id" skillTable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SkillTable> findOne(Long id);

    /**
     * Delete the "id" skillTable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
