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

import com.qnowapp.domain.ProjectBucketId;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectBucketIdRepository;
import com.qnowapp.service.dto.ProjectBucketIdCriteria;

/**
 * Service for executing complex queries for {@link ProjectBucketId} entities in the database.
 * The main input is a {@link ProjectBucketIdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectBucketId} or a {@link Page} of {@link ProjectBucketId} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectBucketIdQueryService extends QueryService<ProjectBucketId> {

    private final Logger log = LoggerFactory.getLogger(ProjectBucketIdQueryService.class);

    private final ProjectBucketIdRepository projectBucketIdRepository;

    public ProjectBucketIdQueryService(ProjectBucketIdRepository projectBucketIdRepository) {
        this.projectBucketIdRepository = projectBucketIdRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectBucketId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectBucketId> findByCriteria(ProjectBucketIdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectBucketId> specification = createSpecification(criteria);
        return projectBucketIdRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectBucketId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectBucketId> findByCriteria(ProjectBucketIdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectBucketId> specification = createSpecification(criteria);
        return projectBucketIdRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectBucketIdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectBucketId> specification = createSpecification(criteria);
        return projectBucketIdRepository.count(specification);
    }

    /**
     * Function to convert ProjectBucketIdCriteria to a {@link Specification}.
     */
    private Specification<ProjectBucketId> createSpecification(ProjectBucketIdCriteria criteria) {
        Specification<ProjectBucketId> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectBucketId_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectBucketId_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectBucketId_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectBucketId_.description));
            }
        }
        return specification;
    }
}
