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

import com.qnowapp.domain.ProjectTag;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectTagRepository;
import com.qnowapp.service.dto.ProjectTagCriteria;

/**
 * Service for executing complex queries for {@link ProjectTag} entities in the database.
 * The main input is a {@link ProjectTagCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectTag} or a {@link Page} of {@link ProjectTag} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectTagQueryService extends QueryService<ProjectTag> {

    private final Logger log = LoggerFactory.getLogger(ProjectTagQueryService.class);

    private final ProjectTagRepository projectTagRepository;

    public ProjectTagQueryService(ProjectTagRepository projectTagRepository) {
        this.projectTagRepository = projectTagRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectTag} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectTag> findByCriteria(ProjectTagCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectTag> specification = createSpecification(criteria);
        return projectTagRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectTag} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectTag> findByCriteria(ProjectTagCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectTag> specification = createSpecification(criteria);
        return projectTagRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectTagCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectTag> specification = createSpecification(criteria);
        return projectTagRepository.count(specification);
    }

    /**
     * Function to convert ProjectTagCriteria to a {@link Specification}.
     */
    private Specification<ProjectTag> createSpecification(ProjectTagCriteria criteria) {
        Specification<ProjectTag> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectTag_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectTag_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectTag_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectTag_.description));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFlag(), ProjectTag_.flag));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColor(), ProjectTag_.color));
            }
            if (criteria.getTagTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getTagTypeId(),
                    root -> root.join(ProjectTag_.tagType, JoinType.LEFT).get(TagType_.id)));
            }
            if (criteria.getImEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getImEmployeeId(),
                    root -> root.join(ProjectTag_.imEmployee, JoinType.LEFT).get(ImEmployee_.id)));
            }
            if (criteria.getImProjectsId() != null) {
                specification = specification.and(buildSpecification(criteria.getImProjectsId(),
                    root -> root.join(ProjectTag_.imProjects, JoinType.LEFT).get(ImProjects_.id)));
            }
        }
        return specification;
    }
}
