package com.qnowapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.qnowapp.domain.OpportunityPriorityId;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.OpportunityPriorityIdRepository;
import com.qnowapp.service.dto.OpportunityPriorityIdCriteria;

/**
 * Service for executing complex queries for {@link OpportunityPriorityId} entities in the database.
 * The main input is a {@link OpportunityPriorityIdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OpportunityPriorityId} or a {@link Page} of {@link OpportunityPriorityId} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OpportunityPriorityIdQueryService extends QueryService<OpportunityPriorityId> {

    private final Logger log = LoggerFactory.getLogger(OpportunityPriorityIdQueryService.class);

    private final OpportunityPriorityIdRepository opportunityPriorityIdRepository;

    public OpportunityPriorityIdQueryService(OpportunityPriorityIdRepository opportunityPriorityIdRepository) {
        this.opportunityPriorityIdRepository = opportunityPriorityIdRepository;
    }

    /**
     * Return a {@link List} of {@link OpportunityPriorityId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OpportunityPriorityId> findByCriteria(OpportunityPriorityIdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OpportunityPriorityId> specification = createSpecification(criteria);
        return opportunityPriorityIdRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link OpportunityPriorityId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OpportunityPriorityId> findByCriteria(OpportunityPriorityIdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OpportunityPriorityId> specification = createSpecification(criteria);
        return opportunityPriorityIdRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OpportunityPriorityIdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OpportunityPriorityId> specification = createSpecification(criteria);
        return opportunityPriorityIdRepository.count(specification);
    }

    /**
     * Function to convert OpportunityPriorityIdCriteria to a {@link Specification}.
     */
    private Specification<OpportunityPriorityId> createSpecification(OpportunityPriorityIdCriteria criteria) {
        Specification<OpportunityPriorityId> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), OpportunityPriorityId_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), OpportunityPriorityId_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), OpportunityPriorityId_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), OpportunityPriorityId_.description));
            }
        }
        return specification;
    }
}
