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

import com.qnowapp.domain.ProjectVertical;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectVerticalRepository;
import com.qnowapp.service.dto.ProjectVerticalCriteria;

/**
 * Service for executing complex queries for {@link ProjectVertical} entities in the database.
 * The main input is a {@link ProjectVerticalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectVertical} or a {@link Page} of {@link ProjectVertical} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectVerticalQueryService extends QueryService<ProjectVertical> {

    private final Logger log = LoggerFactory.getLogger(ProjectVerticalQueryService.class);

    private final ProjectVerticalRepository projectVerticalRepository;

    public ProjectVerticalQueryService(ProjectVerticalRepository projectVerticalRepository) {
        this.projectVerticalRepository = projectVerticalRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectVertical} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectVertical> findByCriteria(ProjectVerticalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectVertical> specification = createSpecification(criteria);
        return projectVerticalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectVertical} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectVertical> findByCriteria(ProjectVerticalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectVertical> specification = createSpecification(criteria);
        return projectVerticalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectVerticalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectVertical> specification = createSpecification(criteria);
        return projectVerticalRepository.count(specification);
    }

    /**
     * Function to convert ProjectVerticalCriteria to a {@link Specification}.
     */
    private Specification<ProjectVertical> createSpecification(ProjectVerticalCriteria criteria) {
        Specification<ProjectVertical> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectVertical_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectVertical_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectVertical_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectVertical_.description));
            }
        }
        return specification;
    }
}
