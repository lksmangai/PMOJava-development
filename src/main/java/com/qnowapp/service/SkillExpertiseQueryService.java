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

import com.qnowapp.domain.SkillExpertise;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.SkillExpertiseRepository;
import com.qnowapp.service.dto.SkillExpertiseCriteria;

/**
 * Service for executing complex queries for {@link SkillExpertise} entities in the database.
 * The main input is a {@link SkillExpertiseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SkillExpertise} or a {@link Page} of {@link SkillExpertise} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SkillExpertiseQueryService extends QueryService<SkillExpertise> {

    private final Logger log = LoggerFactory.getLogger(SkillExpertiseQueryService.class);

    private final SkillExpertiseRepository skillExpertiseRepository;

    public SkillExpertiseQueryService(SkillExpertiseRepository skillExpertiseRepository) {
        this.skillExpertiseRepository = skillExpertiseRepository;
    }

    /**
     * Return a {@link List} of {@link SkillExpertise} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SkillExpertise> findByCriteria(SkillExpertiseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SkillExpertise> specification = createSpecification(criteria);
        return skillExpertiseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SkillExpertise} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SkillExpertise> findByCriteria(SkillExpertiseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SkillExpertise> specification = createSpecification(criteria);
        return skillExpertiseRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SkillExpertiseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SkillExpertise> specification = createSpecification(criteria);
        return skillExpertiseRepository.count(specification);
    }

    /**
     * Function to convert SkillExpertiseCriteria to a {@link Specification}.
     */
    private Specification<SkillExpertise> createSpecification(SkillExpertiseCriteria criteria) {
        Specification<SkillExpertise> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SkillExpertise_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), SkillExpertise_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SkillExpertise_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), SkillExpertise_.description));
            }
        }
        return specification;
    }
}
