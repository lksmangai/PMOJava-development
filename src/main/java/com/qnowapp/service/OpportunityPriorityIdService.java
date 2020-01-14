package com.qnowapp.service;

import com.qnowapp.domain.OpportunityPriorityId;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link OpportunityPriorityId}.
 */
public interface OpportunityPriorityIdService {

    /**
     * Save a opportunityPriorityId.
     *
     * @param opportunityPriorityId the entity to save.
     * @return the persisted entity.
     */
    OpportunityPriorityId save(OpportunityPriorityId opportunityPriorityId);

    /**
     * Get all the opportunityPriorityIds.
     *
     * @return the list of entities.
     */
    List<OpportunityPriorityId> findAll();


    /**
     * Get the "id" opportunityPriorityId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OpportunityPriorityId> findOne(Long id);

    /**
     * Delete the "id" opportunityPriorityId.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
