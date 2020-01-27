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

import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectInitiativeIdRepository;
import com.qnowapp.service.dto.ProjectInitiativeIdCriteria;

/**
 * Service for executing complex queries for {@link ProjectInitiativeId} entities in the database.
 * The main input is a {@link ProjectInitiativeIdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectInitiativeId} or a {@link Page} of {@link ProjectInitiativeId} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectInitiativeIdQueryService extends QueryService<ProjectInitiativeId> {

    private final Logger log = LoggerFactory.getLogger(ProjectInitiativeIdQueryService.class);

    private final ProjectInitiativeIdRepository projectInitiativeIdRepository;

    public ProjectInitiativeIdQueryService(ProjectInitiativeIdRepository projectInitiativeIdRepository) {
        this.projectInitiativeIdRepository = projectInitiativeIdRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectInitiativeId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectInitiativeId> findByCriteria(ProjectInitiativeIdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectInitiativeId> specification = createSpecification(criteria);
        return projectInitiativeIdRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectInitiativeId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectInitiativeId> findByCriteria(ProjectInitiativeIdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectInitiativeId> specification = createSpecification(criteria);
        return projectInitiativeIdRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectInitiativeIdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectInitiativeId> specification = createSpecification(criteria);
        return projectInitiativeIdRepository.count(specification);
    }

    /**
     * Function to convert ProjectInitiativeIdCriteria to a {@link Specification}.
     */
    private Specification<ProjectInitiativeId> createSpecification(ProjectInitiativeIdCriteria criteria) {
        Specification<ProjectInitiativeId> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectInitiativeId_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectInitiativeId_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectInitiativeId_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectInitiativeId_.description));
            }
        }
        return specification;
    }
}
