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

import com.qnowapp.domain.SkillTable;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.SkillTableRepository;
import com.qnowapp.service.dto.SkillTableCriteria;

/**
 * Service for executing complex queries for {@link SkillTable} entities in the database.
 * The main input is a {@link SkillTableCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SkillTable} or a {@link Page} of {@link SkillTable} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillTableQueryService extends QueryService<SkillTable> {

    private final Logger log = LoggerFactory.getLogger(SkillTableQueryService.class);

    private final SkillTableRepository skillTableRepository;

    public SkillTableQueryService(SkillTableRepository skillTableRepository) {
        this.skillTableRepository = skillTableRepository;
    }

    /**
     * Return a {@link List} of {@link SkillTable} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SkillTable> findByCriteria(SkillTableCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SkillTable> specification = createSpecification(criteria);
        return skillTableRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SkillTable} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillTable> findByCriteria(SkillTableCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SkillTable> specification = createSpecification(criteria);
        return skillTableRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SkillTableCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SkillTable> specification = createSpecification(criteria);
        return skillTableRepository.count(specification);
    }

    /**
     * Function to convert SkillTableCriteria to a {@link Specification}.
     */
    private Specification<SkillTable> createSpecification(SkillTableCriteria criteria) {
        Specification<SkillTable> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SkillTable_.id));
            }
            if (criteria.getSkillExpertiseId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillExpertiseId(),
                    root -> root.join(SkillTable_.skillExpertise, JoinType.LEFT).get(SkillExpertise_.id)));
            }
            if (criteria.getUserContactId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserContactId(),
                    root -> root.join(SkillTable_.userContact, JoinType.LEFT).get(UserContact_.id)));
            }
            if (criteria.getImProjectsId() != null) {
                specification = specification.and(buildSpecification(criteria.getImProjectsId(),
                    root -> root.join(SkillTable_.imProjects, JoinType.LEFT).get(ImProjects_.id)));
            }
            if (criteria.getSkillsId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillsId(),
                    root -> root.join(SkillTable_.skills, JoinType.LEFT).get(Skills_.id)));
            }
        }
        return specification;
    }
}
