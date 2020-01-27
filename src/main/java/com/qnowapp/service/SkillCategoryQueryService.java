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

import com.qnowapp.domain.SkillCategory;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.SkillCategoryRepository;
import com.qnowapp.service.dto.SkillCategoryCriteria;

/**
 * Service for executing complex queries for {@link SkillCategory} entities in the database.
 * The main input is a {@link SkillCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SkillCategory} or a {@link Page} of {@link SkillCategory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillCategoryQueryService extends QueryService<SkillCategory> {

    private final Logger log = LoggerFactory.getLogger(SkillCategoryQueryService.class);

    private final SkillCategoryRepository skillCategoryRepository;

    public SkillCategoryQueryService(SkillCategoryRepository skillCategoryRepository) {
        this.skillCategoryRepository = skillCategoryRepository;
    }

    /**
     * Return a {@link List} of {@link SkillCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SkillCategory> findByCriteria(SkillCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SkillCategory> specification = createSpecification(criteria);
        return skillCategoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SkillCategory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillCategory> findByCriteria(SkillCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SkillCategory> specification = createSpecification(criteria);
        return skillCategoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SkillCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SkillCategory> specification = createSpecification(criteria);
        return skillCategoryRepository.count(specification);
    }

    /**
     * Function to convert SkillCategoryCriteria to a {@link Specification}.
     */
    private Specification<SkillCategory> createSpecification(SkillCategoryCriteria criteria) {
        Specification<SkillCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SkillCategory_.id));
            }
            if (criteria.getSkillCategoryCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSkillCategoryCode(), SkillCategory_.skillCategoryCode));
            }
            if (criteria.getSkillCategoryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSkillCategoryName(), SkillCategory_.skillCategoryName));
            }
        }
        return specification;
    }
}
