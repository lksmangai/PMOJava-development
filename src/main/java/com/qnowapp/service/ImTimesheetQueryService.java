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

import com.qnowapp.domain.ImTimesheet;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ImTimesheetRepository;
import com.qnowapp.service.dto.ImTimesheetCriteria;

/**
 * Service for executing complex queries for {@link ImTimesheet} entities in the database.
 * The main input is a {@link ImTimesheetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ImTimesheet} or a {@link Page} of {@link ImTimesheet} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImTimesheetQueryService extends QueryService<ImTimesheet> {

    private final Logger log = LoggerFactory.getLogger(ImTimesheetQueryService.class);

    private final ImTimesheetRepository imTimesheetRepository;

    public ImTimesheetQueryService(ImTimesheetRepository imTimesheetRepository) {
        this.imTimesheetRepository = imTimesheetRepository;
    }

    /**
     * Return a {@link List} of {@link ImTimesheet} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ImTimesheet> findByCriteria(ImTimesheetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ImTimesheet> specification = createSpecification(criteria);
        return imTimesheetRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ImTimesheet} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ImTimesheet> findByCriteria(ImTimesheetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ImTimesheet> specification = createSpecification(criteria);
        return imTimesheetRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImTimesheetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ImTimesheet> specification = createSpecification(criteria);
        return imTimesheetRepository.count(specification);
    }

    /**
     * Function to convert ImTimesheetCriteria to a {@link Specification}.
     */
    private Specification<ImTimesheet> createSpecification(ImTimesheetCriteria criteria) {
        Specification<ImTimesheet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ImTimesheet_.id));
            }
            if (criteria.getLogdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLogdate(), ImTimesheet_.logdate));
            }
            if (criteria.getLoghours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoghours(), ImTimesheet_.loghours));
            }
            if (criteria.getBillhours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBillhours(), ImTimesheet_.billhours));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), ImTimesheet_.notes));
            }
            if (criteria.getImEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getImEmployeeId(),
                    root -> root.join(ImTimesheet_.imEmployee, JoinType.LEFT).get(ImEmployee_.id)));
            }
            if (criteria.getImProjectsId() != null) {
                specification = specification.and(buildSpecification(criteria.getImProjectsId(),
                    root -> root.join(ImTimesheet_.imProjects, JoinType.LEFT).get(ImProjects_.id)));
            }
        }
        return specification;
    }
}
