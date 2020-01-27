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

import com.qnowapp.domain.ProjectTheme;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectThemeRepository;
import com.qnowapp.service.dto.ProjectThemeCriteria;

/**
 * Service for executing complex queries for {@link ProjectTheme} entities in the database.
 * The main input is a {@link ProjectThemeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTheme} or a {@link Page} of {@link ProjectTheme} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectThemeQueryService extends QueryService<ProjectTheme> {

    private final Logger log = LoggerFactory.getLogger(ProjectThemeQueryService.class);

    private final ProjectThemeRepository projectThemeRepository;

    public ProjectThemeQueryService(ProjectThemeRepository projectThemeRepository) {
        this.projectThemeRepository = projectThemeRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectTheme} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTheme> findByCriteria(ProjectThemeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTheme> specification = createSpecification(criteria);
        return projectThemeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectTheme} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTheme> findByCriteria(ProjectThemeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTheme> specification = createSpecification(criteria);
        return projectThemeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectThemeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTheme> specification = createSpecification(criteria);
        return projectThemeRepository.count(specification);
    }

    /**
     * Function to convert ProjectThemeCriteria to a {@link Specification}.
     */
    private Specification<ProjectTheme> createSpecification(ProjectThemeCriteria criteria) {
        Specification<ProjectTheme> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectTheme_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectTheme_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectTheme_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectTheme_.description));
            }
        }
        return specification;
    }
}
