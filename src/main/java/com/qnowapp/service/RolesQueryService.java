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

import com.qnowapp.domain.Roles;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.RolesRepository;
import com.qnowapp.service.dto.RolesCriteria;

/**
 * Service for executing complex queries for {@link Roles} entities in the database.
 * The main input is a {@link RolesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Roles} or a {@link Page} of {@link Roles} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RolesQueryService extends QueryService<Roles> {

    private final Logger log = LoggerFactory.getLogger(RolesQueryService.class);

    private final RolesRepository rolesRepository;

    public RolesQueryService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    /**
     * Return a {@link List} of {@link Roles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Roles> findByCriteria(RolesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Roles> specification = createSpecification(criteria);
        return rolesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Roles} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Roles> findByCriteria(RolesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Roles> specification = createSpecification(criteria);
        return rolesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RolesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Roles> specification = createSpecification(criteria);
        return rolesRepository.count(specification);
    }

    /**
     * Function to convert RolesCriteria to a {@link Specification}.
     */
    private Specification<Roles> createSpecification(RolesCriteria criteria) {
        Specification<Roles> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Roles_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Roles_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Roles_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Roles_.description));
            }
            if (criteria.getGroupMembersId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupMembersId(),
                    root -> root.join(Roles_.groupMembers, JoinType.LEFT).get(GroupMembers_.id)));
            }
        }
        return specification;
    }
}
