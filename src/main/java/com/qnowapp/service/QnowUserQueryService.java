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

import com.qnowapp.domain.QnowUser;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.QnowUserRepository;
import com.qnowapp.service.dto.QnowUserCriteria;

/**
 * Service for executing complex queries for {@link QnowUser} entities in the database.
 * The main input is a {@link QnowUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QnowUser} or a {@link Page} of {@link QnowUser} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QnowUserQueryService extends QueryService<QnowUser> {

    private final Logger log = LoggerFactory.getLogger(QnowUserQueryService.class);

    private final QnowUserRepository qnowUserRepository;

    public QnowUserQueryService(QnowUserRepository qnowUserRepository) {
        this.qnowUserRepository = qnowUserRepository;
    }

    /**
     * Return a {@link List} of {@link QnowUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QnowUser> findByCriteria(QnowUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QnowUser> specification = createSpecification(criteria);
        return qnowUserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link QnowUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QnowUser> findByCriteria(QnowUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QnowUser> specification = createSpecification(criteria);
        return qnowUserRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QnowUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QnowUser> specification = createSpecification(criteria);
        return qnowUserRepository.count(specification);
    }

    /**
     * Function to convert QnowUserCriteria to a {@link Specification}.
     */
    private Specification<QnowUser> createSpecification(QnowUserCriteria criteria) {
        Specification<QnowUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), QnowUser_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(QnowUser_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
