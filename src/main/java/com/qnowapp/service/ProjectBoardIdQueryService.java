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

import com.qnowapp.domain.ProjectBoardId;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ProjectBoardIdRepository;
import com.qnowapp.service.dto.ProjectBoardIdCriteria;

/**
 * Service for executing complex queries for {@link ProjectBoardId} entities in the database.
 * The main input is a {@link ProjectBoardIdCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectBoardId} or a {@link Page} of {@link ProjectBoardId} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectBoardIdQueryService extends QueryService<ProjectBoardId> {

    private final Logger log = LoggerFactory.getLogger(ProjectBoardIdQueryService.class);

    private final ProjectBoardIdRepository projectBoardIdRepository;

    public ProjectBoardIdQueryService(ProjectBoardIdRepository projectBoardIdRepository) {
        this.projectBoardIdRepository = projectBoardIdRepository;
    }

    /**
     * Return a {@link List} of {@link ProjectBoardId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectBoardId> findByCriteria(ProjectBoardIdCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProjectBoardId> specification = createSpecification(criteria);
        return projectBoardIdRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProjectBoardId} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectBoardId> findByCriteria(ProjectBoardIdCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProjectBoardId> specification = createSpecification(criteria);
        return projectBoardIdRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectBoardIdCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProjectBoardId> specification = createSpecification(criteria);
        return projectBoardIdRepository.count(specification);
    }

    /**
     * Function to convert ProjectBoardIdCriteria to a {@link Specification}.
     */
    private Specification<ProjectBoardId> createSpecification(ProjectBoardIdCriteria criteria) {
        Specification<ProjectBoardId> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProjectBoardId_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), ProjectBoardId_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProjectBoardId_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ProjectBoardId_.description));
            }
        }
        return specification;
    }
}
