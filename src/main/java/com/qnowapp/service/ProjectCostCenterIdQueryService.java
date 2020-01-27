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

import com.qnowapp.domain.ProjectCostCenterId;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectCostCenterIdRepository;
import com.qnowapp.service.dto.ProjectCostCenterIdCriteria;

/**
 * Service for executing complex queries for {@link ProjectCostCenterId} entities in the database.
 * The main input is a {@link ProjectCostCenterIdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectCostCenterId} or a {@link Page} of {@link ProjectCostCenterId} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectCostCenterIdQueryService extends QueryService<ProjectCostCenterId> {

    private final Logger log = LoggerFactory.getLogger(ProjectCostCenterIdQueryService.class);

    private final ProjectCostCenterIdRepository projectCostCenterIdRepository;

    public ProjectCostCenterIdQueryService(ProjectCostCenterIdRepository projectCostCenterIdRepository) {
        this.projectCostCenterIdRepository = projectCostCenterIdRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectCostCenterId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectCostCenterId> findByCriteria(ProjectCostCenterIdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectCostCenterId> specification = createSpecification(criteria);
        return projectCostCenterIdRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectCostCenterId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectCostCenterId> findByCriteria(ProjectCostCenterIdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectCostCenterId> specification = createSpecification(criteria);
        return projectCostCenterIdRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectCostCenterIdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectCostCenterId> specification = createSpecification(criteria);
        return projectCostCenterIdRepository.count(specification);
    }

    /**
     * Function to convert ProjectCostCenterIdCriteria to a {@link Specification}.
     */
    private Specification<ProjectCostCenterId> createSpecification(ProjectCostCenterIdCriteria criteria) {
        Specification<ProjectCostCenterId> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectCostCenterId_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectCostCenterId_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectCostCenterId_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectCostCenterId_.description));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(ProjectCostCenterId_.companies, JoinType.LEFT).get(Company_.id)));
            }
        }
        return specification;
    }
}
