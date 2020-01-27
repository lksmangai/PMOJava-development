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

import com.qnowapp.domain.ProjectRoles;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectRolesRepository;
import com.qnowapp.service.dto.ProjectRolesCriteria;

/**
 * Service for executing complex queries for {@link ProjectRoles} entities in the database.
 * The main input is a {@link ProjectRolesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectRoles} or a {@link Page} of {@link ProjectRoles} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectRolesQueryService extends QueryService<ProjectRoles> {

    private final Logger log = LoggerFactory.getLogger(ProjectRolesQueryService.class);

    private final ProjectRolesRepository projectRolesRepository;

    public ProjectRolesQueryService(ProjectRolesRepository projectRolesRepository) {
        this.projectRolesRepository = projectRolesRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectRoles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectRoles> findByCriteria(ProjectRolesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectRoles> specification = createSpecification(criteria);
        return projectRolesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectRoles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectRoles> findByCriteria(ProjectRolesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectRoles> specification = createSpecification(criteria);
        return projectRolesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectRolesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectRoles> specification = createSpecification(criteria);
        return projectRolesRepository.count(specification);
    }

    /**
     * Function to convert ProjectRolesCriteria to a {@link Specification}.
     */
    private Specification<ProjectRoles> createSpecification(ProjectRolesCriteria criteria) {
        Specification<ProjectRoles> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectRoles_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectRoles_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectRoles_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectRoles_.description));
            }
        }
        return specification;
    }
}
