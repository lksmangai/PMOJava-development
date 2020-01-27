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

import com.qnowapp.domain.ProjectStatusId;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectStatusIdRepository;
import com.qnowapp.service.dto.ProjectStatusIdCriteria;

/**
 * Service for executing complex queries for {@link ProjectStatusId} entities in the database.
 * The main input is a {@link ProjectStatusIdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectStatusId} or a {@link Page} of {@link ProjectStatusId} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectStatusIdQueryService extends QueryService<ProjectStatusId> {

    private final Logger log = LoggerFactory.getLogger(ProjectStatusIdQueryService.class);

    private final ProjectStatusIdRepository projectStatusIdRepository;

    public ProjectStatusIdQueryService(ProjectStatusIdRepository projectStatusIdRepository) {
        this.projectStatusIdRepository = projectStatusIdRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectStatusId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectStatusId> findByCriteria(ProjectStatusIdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectStatusId> specification = createSpecification(criteria);
        return projectStatusIdRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectStatusId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectStatusId> findByCriteria(ProjectStatusIdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectStatusId> specification = createSpecification(criteria);
        return projectStatusIdRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectStatusIdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectStatusId> specification = createSpecification(criteria);
        return projectStatusIdRepository.count(specification);
    }

    /**
     * Function to convert ProjectStatusIdCriteria to a {@link Specification}.
     */
    private Specification<ProjectStatusId> createSpecification(ProjectStatusIdCriteria criteria) {
        Specification<ProjectStatusId> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectStatusId_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectStatusId_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectStatusId_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectStatusId_.description));
            }
        }
        return specification;
    }
}
