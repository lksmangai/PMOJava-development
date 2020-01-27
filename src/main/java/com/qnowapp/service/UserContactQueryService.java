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

import com.qnowapp.domain.UserContact;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.UserContactRepository;
import com.qnowapp.service.dto.UserContactCriteria;

/**
 * Service for executing complex queries for {@link UserContact} entities in the database.
 * The main input is a {@link UserContactCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserContact} or a {@link Page} of {@link UserContact} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserContactQueryService extends QueryService<UserContact> {

    private final Logger log = LoggerFactory.getLogger(UserContactQueryService.class);

    private final UserContactRepository userContactRepository;

    public UserContactQueryService(UserContactRepository userContactRepository) {
        this.userContactRepository = userContactRepository;
    }

    /**
     * Return a {@link List} of {@link UserContact} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserContact> findByCriteria(UserContactCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserContact> specification = createSpecification(criteria);
        return userContactRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserContact} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserContact> findByCriteria(UserContactCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserContact> specification = createSpecification(criteria);
        return userContactRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserContactCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserContact> specification = createSpecification(criteria);
        return userContactRepository.count(specification);
    }

    /**
     * Function to convert UserContactCriteria to a {@link Specification}.
     */
    private Specification<UserContact> createSpecification(UserContactCriteria criteria) {
        Specification<UserContact> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserContact_.id));
            }
            if (criteria.getHomePhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHomePhone(), UserContact_.homePhone));
            }
            if (criteria.getWorkPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWorkPhone(), UserContact_.workPhone));
            }
            if (criteria.getCellPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCellPhone(), UserContact_.cellPhone));
            }
            if (criteria.getPermentAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPermentAddress(), UserContact_.permentAddress));
            }
            if (criteria.getHaLine1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHaLine1(), UserContact_.haLine1));
            }
            if (criteria.getHaLine2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHaLine2(), UserContact_.haLine2));
            }
            if (criteria.getHaPostal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHaPostal(), UserContact_.haPostal));
            }
            if (criteria.getWaLine1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWaLine1(), UserContact_.waLine1));
            }
            if (criteria.getWaLine2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWaLine2(), UserContact_.waLine2));
            }
            if (criteria.getWaPostal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWaPostal(), UserContact_.waPostal));
            }
            if (criteria.getUcNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUcNote(), UserContact_.ucNote));
            }
            if (criteria.getPrimaryRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrimaryRole(), UserContact_.primaryRole));
            }
            if (criteria.getSecondaryRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSecondaryRole(), UserContact_.secondaryRole));
            }
            if (criteria.getInitiative() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInitiative(), UserContact_.initiative));
            }
            if (criteria.getTechnology() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTechnology(), UserContact_.technology));
            }
            if (criteria.getTeamName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeamName(), UserContact_.teamName));
            }
            if (criteria.getQnowUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getQnowUserId(),
                    root -> root.join(UserContact_.qnowUser, JoinType.LEFT).get(QnowUser_.id)));
            }
            if (criteria.getWaCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getWaCityId(),
                    root -> root.join(UserContact_.waCity, JoinType.LEFT).get(City_.id)));
            }
            if (criteria.getHaCityId() != null) {
                specification = specification.and(buildSpecification(criteria.getHaCityId(),
                    root -> root.join(UserContact_.haCity, JoinType.LEFT).get(City_.id)));
            }
            if (criteria.getWaStateId() != null) {
                specification = specification.and(buildSpecification(criteria.getWaStateId(),
                    root -> root.join(UserContact_.waState, JoinType.LEFT).get(StateMaster_.id)));
            }
            if (criteria.getHaStateId() != null) {
                specification = specification.and(buildSpecification(criteria.getHaStateId(),
                    root -> root.join(UserContact_.haState, JoinType.LEFT).get(StateMaster_.id)));
            }
            if (criteria.getWaCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getWaCountryId(),
                    root -> root.join(UserContact_.waCountry, JoinType.LEFT).get(Country_.id)));
            }
            if (criteria.getHaCountryId() != null) {
                specification = specification.and(buildSpecification(criteria.getHaCountryId(),
                    root -> root.join(UserContact_.haCountry, JoinType.LEFT).get(Country_.id)));
            }
            if (criteria.getGroupMembersId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupMembersId(),
                    root -> root.join(UserContact_.groupMembers, JoinType.LEFT).get(GroupMembers_.id)));
            }
            if (criteria.getImEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getImEmployeeId(),
                    root -> root.join(UserContact_.imEmployees, JoinType.LEFT).get(ImEmployee_.id)));
            }
        }
        return specification;
    }
}
