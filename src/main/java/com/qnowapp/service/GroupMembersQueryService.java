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

import com.qnowapp.domain.GroupMembers;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.GroupMembersRepository;
import com.qnowapp.service.dto.GroupMembersCriteria;

/**
 * Service for executing complex queries for {@link GroupMembers} entities in the database.
 * The main input is a {@link GroupMembersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GroupMembers} or a {@link Page} of {@link GroupMembers} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GroupMembersQueryService extends QueryService<GroupMembers> {

    private final Logger log = LoggerFactory.getLogger(GroupMembersQueryService.class);

    private final GroupMembersRepository groupMembersRepository;

    public GroupMembersQueryService(GroupMembersRepository groupMembersRepository) {
        this.groupMembersRepository = groupMembersRepository;
    }

    /**
     * Return a {@link List} of {@link GroupMembers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GroupMembers> findByCriteria(GroupMembersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GroupMembers> specification = createSpecification(criteria);
        return groupMembersRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GroupMembers} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupMembers> findByCriteria(GroupMembersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GroupMembers> specification = createSpecification(criteria);
        return groupMembersRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GroupMembersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GroupMembers> specification = createSpecification(criteria);
        return groupMembersRepository.count(specification);
    }

    /**
     * Function to convert GroupMembersCriteria to a {@link Specification}.
     */
    private Specification<GroupMembers> createSpecification(GroupMembersCriteria criteria) {
        Specification<GroupMembers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), GroupMembers_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), GroupMembers_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), GroupMembers_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), GroupMembers_.description));
            }
            if (criteria.getRolesId() != null) {
                specification = specification.and(buildSpecification(criteria.getRolesId(),
                    root -> root.join(GroupMembers_.roles, JoinType.LEFT).get(Roles_.id)));
            }
            if (criteria.getUserContactId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserContactId(),
                    root -> root.join(GroupMembers_.userContacts, JoinType.LEFT).get(UserContact_.id)));
            }
        }
        return specification;
    }
}
