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

import com.qnowapp.domain.ProjectBusinessgoalId;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectBusinessgoalIdRepository;
import com.qnowapp.service.dto.ProjectBusinessgoalIdCriteria;

/**
 * Service for executing complex queries for {@link ProjectBusinessgoalId} entities in the database.
 * The main input is a {@link ProjectBusinessgoalIdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectBusinessgoalId} or a {@link Page} of {@link ProjectBusinessgoalId} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectBusinessgoalIdQueryService extends QueryService<ProjectBusinessgoalId> {

    private final Logger log = LoggerFactory.getLogger(ProjectBusinessgoalIdQueryService.class);

    private final ProjectBusinessgoalIdRepository projectBusinessgoalIdRepository;

    public ProjectBusinessgoalIdQueryService(ProjectBusinessgoalIdRepository projectBusinessgoalIdRepository) {
        this.projectBusinessgoalIdRepository = projectBusinessgoalIdRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectBusinessgoalId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectBusinessgoalId> findByCriteria(ProjectBusinessgoalIdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectBusinessgoalId> specification = createSpecification(criteria);
        return projectBusinessgoalIdRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectBusinessgoalId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectBusinessgoalId> findByCriteria(ProjectBusinessgoalIdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectBusinessgoalId> specification = createSpecification(criteria);
        return projectBusinessgoalIdRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectBusinessgoalIdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectBusinessgoalId> specification = createSpecification(criteria);
        return projectBusinessgoalIdRepository.count(specification);
    }

    /**
     * Function to convert ProjectBusinessgoalIdCriteria to a {@link Specification}.
     */
    private Specification<ProjectBusinessgoalId> createSpecification(ProjectBusinessgoalIdCriteria criteria) {
        Specification<ProjectBusinessgoalId> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectBusinessgoalId_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectBusinessgoalId_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectBusinessgoalId_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectBusinessgoalId_.description));
            }
        }
        return specification;
    }
}
