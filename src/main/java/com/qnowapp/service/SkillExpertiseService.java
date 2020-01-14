package com.qnowapp.service;

import com.qnowapp.domain.SkillExpertise;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link SkillExpertise}.
 */
public interface SkillExpertiseService {

    /**
     * Save a skillExpertise.
     *
     * @param skillExpertise the entity to save.
     * @return the persisted entity.
     */
    SkillExpertise save(SkillExpertise skillExpertise);

    /**
     * Get all the skillExpertises.
     *
     * @return the list of entities.
     */
    List<SkillExpertise> findAll();


    /**
     * Get the "id" skillExpertise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SkillExpertise> findOne(Long id);

    /**
     * Delete the "id" skillExpertise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
