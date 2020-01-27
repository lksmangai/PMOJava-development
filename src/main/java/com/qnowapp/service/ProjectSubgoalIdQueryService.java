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

import com.qnowapp.domain.ProjectSubgoalId;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectSubgoalIdRepository;
import com.qnowapp.service.dto.ProjectSubgoalIdCriteria;

/**
 * Service for executing complex queries for {@link ProjectSubgoalId} entities in the database.
 * The main input is a {@link ProjectSubgoalIdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectSubgoalId} or a {@link Page} of {@link ProjectSubgoalId} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectSubgoalIdQueryService extends QueryService<ProjectSubgoalId> {

    private final Logger log = LoggerFactory.getLogger(ProjectSubgoalIdQueryService.class);

    private final ProjectSubgoalIdRepository projectSubgoalIdRepository;

    public ProjectSubgoalIdQueryService(ProjectSubgoalIdRepository projectSubgoalIdRepository) {
        this.projectSubgoalIdRepository = projectSubgoalIdRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectSubgoalId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectSubgoalId> findByCriteria(ProjectSubgoalIdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectSubgoalId> specification = createSpecification(criteria);
        return projectSubgoalIdRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectSubgoalId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectSubgoalId> findByCriteria(ProjectSubgoalIdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectSubgoalId> specification = createSpecification(criteria);
        return projectSubgoalIdRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectSubgoalIdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectSubgoalId> specification = createSpecification(criteria);
        return projectSubgoalIdRepository.count(specification);
    }

    /**
     * Function to convert ProjectSubgoalIdCriteria to a {@link Specification}.
     */
    private Specification<ProjectSubgoalId> createSpecification(ProjectSubgoalIdCriteria criteria) {
        Specification<ProjectSubgoalId> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectSubgoalId_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectSubgoalId_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectSubgoalId_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectSubgoalId_.description));
            }
        }
        return specification;
    }
}
