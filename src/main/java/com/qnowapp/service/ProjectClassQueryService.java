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

import com.qnowapp.domain.ProjectClass;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectClassRepository;
import com.qnowapp.service.dto.ProjectClassCriteria;

/**
 * Service for executing complex queries for {@link ProjectClass} entities in the database.
 * The main input is a {@link ProjectClassCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectClass} or a {@link Page} of {@link ProjectClass} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectClassQueryService extends QueryService<ProjectClass> {

    private final Logger log = LoggerFactory.getLogger(ProjectClassQueryService.class);

    private final ProjectClassRepository projectClassRepository;

    public ProjectClassQueryService(ProjectClassRepository projectClassRepository) {
        this.projectClassRepository = projectClassRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectClass} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectClass> findByCriteria(ProjectClassCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectClass> specification = createSpecification(criteria);
        return projectClassRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectClass} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectClass> findByCriteria(ProjectClassCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectClass> specification = createSpecification(criteria);
        return projectClassRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectClassCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectClass> specification = createSpecification(criteria);
        return projectClassRepository.count(specification);
    }

    /**
     * Function to convert ProjectClassCriteria to a {@link Specification}.
     */
    private Specification<ProjectClass> createSpecification(ProjectClassCriteria criteria) {
        Specification<ProjectClass> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectClass_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectClass_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectClass_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectClass_.description));
            }
        }
        return specification;
    }
}
