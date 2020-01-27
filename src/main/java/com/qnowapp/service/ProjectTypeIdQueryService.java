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

import com.qnowapp.domain.ProjectTypeId;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectTypeIdRepository;
import com.qnowapp.service.dto.ProjectTypeIdCriteria;

/**
 * Service for executing complex queries for {@link ProjectTypeId} entities in the database.
 * The main input is a {@link ProjectTypeIdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTypeId} or a {@link Page} of {@link ProjectTypeId} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectTypeIdQueryService extends QueryService<ProjectTypeId> {

    private final Logger log = LoggerFactory.getLogger(ProjectTypeIdQueryService.class);

    private final ProjectTypeIdRepository projectTypeIdRepository;

    public ProjectTypeIdQueryService(ProjectTypeIdRepository projectTypeIdRepository) {
        this.projectTypeIdRepository = projectTypeIdRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectTypeId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTypeId> findByCriteria(ProjectTypeIdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTypeId> specification = createSpecification(criteria);
        return projectTypeIdRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectTypeId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTypeId> findByCriteria(ProjectTypeIdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTypeId> specification = createSpecification(criteria);
        return projectTypeIdRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectTypeIdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTypeId> specification = createSpecification(criteria);
        return projectTypeIdRepository.count(specification);
    }

    /**
     * Function to convert ProjectTypeIdCriteria to a {@link Specification}.
     */
    private Specification<ProjectTypeId> createSpecification(ProjectTypeIdCriteria criteria) {
        Specification<ProjectTypeId> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectTypeId_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectTypeId_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectTypeId_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectTypeId_.description));
            }
        }
        return specification;
    }
}
