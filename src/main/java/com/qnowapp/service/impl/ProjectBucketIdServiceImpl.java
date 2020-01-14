package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectBucketIdService;
import com.qnowapp.domain.ProjectBucketId;
import com.qnowapp.repository.ProjectBucketIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectBucketId}.
 */
@Service
@Transactional
public class ProjectBucketIdServiceImpl implements ProjectBucketIdService {

    private final Logger log = LoggerFactory.getLogger(ProjectBucketIdServiceImpl.class);

    private final ProjectBucketIdRepository projectBucketIdRepository;

    public ProjectBucketIdServiceImpl(ProjectBucketIdRepository projectBucketIdRepository) {
        this.projectBucketIdRepository = projectBucketIdRepository;
    }

    /**
     * Save a projectBucketId.
     *
     * @param projectBucketId the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectBucketId save(ProjectBucketId projectBucketId) {
        log.debug("Request to save ProjectBucketId : {}", projectBucketId);
        return projectBucketIdRepository.save(projectBucketId);
    }

    /**
     * Get all the projectBucketIds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectBucketId> findAll() {
        log.debug("Request to get all ProjectBucketIds");
        return projectBucketIdRepository.findAll();
    }


    /**
     * Get one projectBucketId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectBucketId> findOne(Long id) {
        log.debug("Request to get ProjectBucketId : {}", id);
        return projectBucketIdRepository.findById(id);
    }

    /**
     * Delete the projectBucketId by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectBucketId : {}", id);
        projectBucketIdRepository.deleteById(id);
    }
}
