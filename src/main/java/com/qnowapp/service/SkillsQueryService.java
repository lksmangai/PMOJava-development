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

import com.qnowapp.domain.Skills;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.SkillsRepository;
import com.qnowapp.service.dto.SkillsCriteria;

/**
 * Service for executing complex queries for {@link Skills} entities in the database.
 * The main input is a {@link SkillsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Skills} or a {@link Page} of {@link Skills} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillsQueryService extends QueryService<Skills> {

    private final Logger log = LoggerFactory.getLogger(SkillsQueryService.class);

    private final SkillsRepository skillsRepository;

    public SkillsQueryService(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    /**
     * Return a {@link List} of {@link Skills} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Skills> findByCriteria(SkillsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Skills> specification = createSpecification(criteria);
        return skillsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Skills} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Skills> findByCriteria(SkillsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Skills> specification = createSpecification(criteria);
        return skillsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SkillsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Skills> specification = createSpecification(criteria);
        return skillsRepository.count(specification);
    }

    /**
     * Function to convert SkillsCriteria to a {@link Specification}.
     */
    private Specification<Skills> createSpecification(SkillsCriteria criteria) {
        Specification<Skills> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Skills_.id));
            }
            if (criteria.getSkillCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSkillCode(), Skills_.skillCode));
            }
            if (criteria.getSkillName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSkillName(), Skills_.skillName));
            }
            if (criteria.getParentSkillsIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentSkillsIdId(),
                    root -> root.join(Skills_.parentSkillsId, JoinType.LEFT).get(Skills_.id)));
            }
            if (criteria.getSkillCategoryIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getSkillCategoryIdId(),
                    root -> root.join(Skills_.skillCategoryId, JoinType.LEFT).get(SkillCategory_.id)));
            }
        }
        return specification;
    }
}
