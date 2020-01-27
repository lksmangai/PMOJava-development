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

import com.qnowapp.domain.EmployeeStatus;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.EmployeeStatusRepository;
import com.qnowapp.service.dto.EmployeeStatusCriteria;

/**
 * Service for executing complex queries for {@link EmployeeStatus} entities in the database.
 * The main input is a {@link EmployeeStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeStatus} or a {@link Page} of {@link EmployeeStatus} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeStatusQueryService extends QueryService<EmployeeStatus> {

    private final Logger log = LoggerFactory.getLogger(EmployeeStatusQueryService.class);

    private final EmployeeStatusRepository employeeStatusRepository;

    public EmployeeStatusQueryService(EmployeeStatusRepository employeeStatusRepository) {
        this.employeeStatusRepository = employeeStatusRepository;
    }

    /**
     * Return a {@link List} of {@link EmployeeStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeStatus> findByCriteria(EmployeeStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeStatus> specification = createSpecification(criteria);
        return employeeStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link EmployeeStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeStatus> findByCriteria(EmployeeStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeStatus> specification = createSpecification(criteria);
        return employeeStatusRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeStatus> specification = createSpecification(criteria);
        return employeeStatusRepository.count(specification);
    }

    /**
     * Function to convert EmployeeStatusCriteria to a {@link Specification}.
     */
    private Specification<EmployeeStatus> createSpecification(EmployeeStatusCriteria criteria) {
        Specification<EmployeeStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), EmployeeStatus_.id));
            }
            if (criteria.getStatusCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusCode(), EmployeeStatus_.statusCode));
            }
            if (criteria.getStatusName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusName(), EmployeeStatus_.statusName));
            }
        }
        return specification;
    }
}
