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

import com.qnowapp.domain.BacklogPractice;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.BacklogPracticeRepository;
import com.qnowapp.service.dto.BacklogPracticeCriteria;

/**
 * Service for executing complex queries for {@link BacklogPractice} entities in the database.
 * The main input is a {@link BacklogPracticeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BacklogPractice} or a {@link Page} of {@link BacklogPractice} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BacklogPracticeQueryService extends QueryService<BacklogPractice> {

    private final Logger log = LoggerFactory.getLogger(BacklogPracticeQueryService.class);

    private final BacklogPracticeRepository backlogPracticeRepository;

    public BacklogPracticeQueryService(BacklogPracticeRepository backlogPracticeRepository) {
        this.backlogPracticeRepository = backlogPracticeRepository;
    }

    /**
     * Return a {@link List} of {@link BacklogPractice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BacklogPractice> findByCriteria(BacklogPracticeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BacklogPractice> specification = createSpecification(criteria);
        return backlogPracticeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BacklogPractice} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BacklogPractice> findByCriteria(BacklogPracticeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BacklogPractice> specification = createSpecification(criteria);
        return backlogPracticeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BacklogPracticeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BacklogPractice> specification = createSpecification(criteria);
        return backlogPracticeRepository.count(specification);
    }

    /**
     * Function to convert BacklogPracticeCriteria to a {@link Specification}.
     */
    private Specification<BacklogPractice> createSpecification(BacklogPracticeCriteria criteria) {
        Specification<BacklogPractice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BacklogPractice_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), BacklogPractice_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BacklogPractice_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), BacklogPractice_.description));
            }
        }
        return specification;
    }
}
