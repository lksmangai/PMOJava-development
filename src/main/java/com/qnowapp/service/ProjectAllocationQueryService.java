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

import com.qnowapp.domain.ProjectAllocation;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectAllocationRepository;
import com.qnowapp.service.dto.ProjectAllocationCriteria;

/**
 * Service for executing complex queries for {@link ProjectAllocation} entities in the database.
 * The main input is a {@link ProjectAllocationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectAllocation} or a {@link Page} of {@link ProjectAllocation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectAllocationQueryService extends QueryService<ProjectAllocation> {

    private final Logger log = LoggerFactory.getLogger(ProjectAllocationQueryService.class);

    private final ProjectAllocationRepository projectAllocationRepository;

    public ProjectAllocationQueryService(ProjectAllocationRepository projectAllocationRepository) {
        this.projectAllocationRepository = projectAllocationRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectAllocation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectAllocation> findByCriteria(ProjectAllocationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectAllocation> specification = createSpecification(criteria);
        return projectAllocationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectAllocation} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectAllocation> findByCriteria(ProjectAllocationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectAllocation> specification = createSpecification(criteria);
        return projectAllocationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectAllocationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectAllocation> specification = createSpecification(criteria);
        return projectAllocationRepository.count(specification);
    }

    /**
     * Function to convert ProjectAllocationCriteria to a {@link Specification}.
     */
    private Specification<ProjectAllocation> createSpecification(ProjectAllocationCriteria criteria) {
        Specification<ProjectAllocation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectAllocation_.id));
            }
            if (criteria.getPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentage(), ProjectAllocation_.percentage));
            }
            if (criteria.getImEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getImEmployeeId(),
                    root -> root.join(ProjectAllocation_.imEmployee, JoinType.LEFT).get(ImEmployee_.id)));
            }
            if (criteria.getImProjectsId() != null) {
                specification = specification.and(buildSpecification(criteria.getImProjectsId(),
                    root -> root.join(ProjectAllocation_.imProjects, JoinType.LEFT).get(ImProjects_.id)));
            }
            if (criteria.getProjectRolesId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectRolesId(),
                    root -> root.join(ProjectAllocation_.projectRoles, JoinType.LEFT).get(ProjectRoles_.id)));
            }
        }
        return specification;
    }
}
