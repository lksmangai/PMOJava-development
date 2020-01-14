package com.qnowapp.service.impl;

import com.qnowapp.service.OpportunityPriorityIdService;
import com.qnowapp.domain.OpportunityPriorityId;
import com.qnowapp.repository.OpportunityPriorityIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OpportunityPriorityId}.
 */
@Service
@Transactional
public class OpportunityPriorityIdServiceImpl implements OpportunityPriorityIdService {

    private final Logger log = LoggerFactory.getLogger(OpportunityPriorityIdServiceImpl.class);

    private final OpportunityPriorityIdRepository opportunityPriorityIdRepository;

    public OpportunityPriorityIdServiceImpl(OpportunityPriorityIdRepository opportunityPriorityIdRepository) {
        this.opportunityPriorityIdRepository = opportunityPriorityIdRepository;
    }

    /**
     * Save a opportunityPriorityId.
     *
     * @param opportunityPriorityId the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OpportunityPriorityId save(OpportunityPriorityId opportunityPriorityId) {
        log.debug("Request to save OpportunityPriorityId : {}", opportunityPriorityId);
        return opportunityPriorityIdRepository.save(opportunityPriorityId);
    }

    /**
     * Get all the opportunityPriorityIds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<OpportunityPriorityId> findAll() {
        log.debug("Request to get all OpportunityPriorityIds");
        return opportunityPriorityIdRepository.findAll();
    }


    /**
     * Get one opportunityPriorityId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OpportunityPriorityId> findOne(Long id) {
        log.debug("Request to get OpportunityPriorityId : {}", id);
        return opportunityPriorityIdRepository.findById(id);
    }

    /**
     * Delete the opportunityPriorityId by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OpportunityPriorityId : {}", id);
        opportunityPriorityIdRepository.deleteById(id);
    }
}
