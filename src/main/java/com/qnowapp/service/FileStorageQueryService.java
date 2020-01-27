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

import com.qnowapp.domain.FileStorage;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.FileStorageRepository;
import com.qnowapp.service.dto.FileStorageCriteria;

/**
 * Service for executing complex queries for {@link FileStorage} entities in the database.
 * The main input is a {@link FileStorageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FileStorage} or a {@link Page} of {@link FileStorage} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FileStorageQueryService extends QueryService<FileStorage> {

    private final Logger log = LoggerFactory.getLogger(FileStorageQueryService.class);

    private final FileStorageRepository fileStorageRepository;

    public FileStorageQueryService(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }

    /**
     * Return a {@link List} of {@link FileStorage} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FileStorage> findByCriteria(FileStorageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FileStorage> specification = createSpecification(criteria);
        return fileStorageRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FileStorage} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FileStorage> findByCriteria(FileStorageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FileStorage> specification = createSpecification(criteria);
        return fileStorageRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FileStorageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FileStorage> specification = createSpecification(criteria);
        return fileStorageRepository.count(specification);
    }

    /**
     * Function to convert FileStorageCriteria to a {@link Specification}.
     */
    private Specification<FileStorage> createSpecification(FileStorageCriteria criteria) {
        Specification<FileStorage> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FileStorage_.id));
            }
            if (criteria.getFilename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFilename(), FileStorage_.filename));
            }
            if (criteria.getCaption() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCaption(), FileStorage_.caption));
            }
            if (criteria.getUserContactId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserContactId(),
                    root -> root.join(FileStorage_.userContact, JoinType.LEFT).get(UserContact_.id)));
            }
            if (criteria.getImProjectsId() != null) {
                specification = specification.and(buildSpecification(criteria.getImProjectsId(),
                    root -> root.join(FileStorage_.imProjects, JoinType.LEFT).get(ImProjects_.id)));
            }
        }
        return specification;
    }
}
