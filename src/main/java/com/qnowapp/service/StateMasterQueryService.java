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

import com.qnowapp.domain.StateMaster;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.StateMasterRepository;
import com.qnowapp.service.dto.StateMasterCriteria;

/**
 * Service for executing complex queries for {@link StateMaster} entities in the database.
 * The main input is a {@link StateMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StateMaster} or a {@link Page} of {@link StateMaster} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StateMasterQueryService extends QueryService<StateMaster> {

    private final Logger log = LoggerFactory.getLogger(StateMasterQueryService.class);

    private final StateMasterRepository stateMasterRepository;

    public StateMasterQueryService(StateMasterRepository stateMasterRepository) {
        this.stateMasterRepository = stateMasterRepository;
    }

    /**
     * Return a {@link List} of {@link StateMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StateMaster> findByCriteria(StateMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StateMaster> specification = createSpecification(criteria);
        return stateMasterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StateMaster} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StateMaster> findByCriteria(StateMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StateMaster> specification = createSpecification(criteria);
        return stateMasterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StateMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StateMaster> specification = createSpecification(criteria);
        return stateMasterRepository.count(specification);
    }

    /**
     * Function to convert StateMasterCriteria to a {@link Specification}.
     */
    private Specification<StateMaster> createSpecification(StateMasterCriteria criteria) {
        Specification<StateMaster> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StateMaster_.id));
            }
            if (criteria.getStateCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateCode(), StateMaster_.stateCode));
            }
            if (criteria.getStateName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateName(), StateMaster_.stateName));
            }
        }
        return specification;
    }
}
