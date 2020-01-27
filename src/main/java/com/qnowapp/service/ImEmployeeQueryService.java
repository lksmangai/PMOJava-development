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

import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ImEmployeeRepository;
import com.qnowapp.service.dto.ImEmployeeCriteria;

/**
 * Service for executing complex queries for {@link ImEmployee} entities in the database.
 * The main input is a {@link ImEmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ImEmployee} or a {@link Page} of {@link ImEmployee} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImEmployeeQueryService extends QueryService<ImEmployee> {

    private final Logger log = LoggerFactory.getLogger(ImEmployeeQueryService.class);

    private final ImEmployeeRepository imEmployeeRepository;

    public ImEmployeeQueryService(ImEmployeeRepository imEmployeeRepository) {
        this.imEmployeeRepository = imEmployeeRepository;
    }

    /**
     * Return a {@link List} of {@link ImEmployee} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ImEmployee> findByCriteria(ImEmployeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ImEmployee> specification = createSpecification(criteria);
        return imEmployeeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ImEmployee} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ImEmployee> findByCriteria(ImEmployeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ImEmployee> specification = createSpecification(criteria);
        return imEmployeeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImEmployeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ImEmployee> specification = createSpecification(criteria);
        return imEmployeeRepository.count(specification);
    }

    /**
     * Function to convert ImEmployeeCriteria to a {@link Specification}.
     */
    private Specification<ImEmployee> createSpecification(ImEmployeeCriteria criteria) {
        Specification<ImEmployee> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ImEmployee_.id));
            }
            if (criteria.getJobTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobTitle(), ImEmployee_.jobTitle));
            }
            if (criteria.getJobDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobDescription(), ImEmployee_.jobDescription));
            }
            if (criteria.getAvailability() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailability(), ImEmployee_.availability));
            }
            if (criteria.getSsNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSsNumber(), ImEmployee_.ssNumber));
            }
            if (criteria.getSalary() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalary(), ImEmployee_.salary));
            }
            if (criteria.getSocialSecurity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSocialSecurity(), ImEmployee_.socialSecurity));
            }
            if (criteria.getInsurance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInsurance(), ImEmployee_.insurance));
            }
            if (criteria.getOtherCosts() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOtherCosts(), ImEmployee_.otherCosts));
            }
            if (criteria.getCurrency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrency(), ImEmployee_.currency));
            }
            if (criteria.getDependantP() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDependantP(), ImEmployee_.dependantP));
            }
            if (criteria.getOnlyJobP() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOnlyJobP(), ImEmployee_.onlyJobP));
            }
            if (criteria.getMarriedP() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarriedP(), ImEmployee_.marriedP));
            }
            if (criteria.getHeadOfHouseholdP() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHeadOfHouseholdP(), ImEmployee_.headOfHouseholdP));
            }
            if (criteria.getBirthdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthdate(), ImEmployee_.birthdate));
            }
            if (criteria.getHourlyCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHourlyCost(), ImEmployee_.hourlyCost));
            }
            if (criteria.getQnowUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getQnowUserId(),
                    root -> root.join(ImEmployee_.qnowUser, JoinType.LEFT).get(QnowUser_.id)));
            }
            if (criteria.getEmployeeStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeStatusId(),
                    root -> root.join(ImEmployee_.employeeStatus, JoinType.LEFT).get(EmployeeStatus_.id)));
            }
            if (criteria.getDepartmentIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentIdId(),
                    root -> root.join(ImEmployee_.departmentId, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getSupervisorIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupervisorIdId(),
                    root -> root.join(ImEmployee_.supervisorId, JoinType.LEFT).get(ImEmployee_.id)));
            }
            if (criteria.getUserContactId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserContactId(),
                    root -> root.join(ImEmployee_.userContacts, JoinType.LEFT).get(UserContact_.id)));
            }
        }
        return specification;
    }
}
