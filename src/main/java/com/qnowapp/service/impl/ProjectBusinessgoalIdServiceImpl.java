package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectBusinessgoalIdService;
import com.qnowapp.domain.ProjectBusinessgoalId;
import com.qnowapp.repository.ProjectBusinessgoalIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectBusinessgoalId}.
 */
@Service
@Transactional
public class ProjectBusinessgoalIdServiceImpl implements ProjectBusinessgoalIdService {

    private final Logger log = LoggerFactory.getLogger(ProjectBusinessgoalIdServiceImpl.class);

    private final ProjectBusinessgoalIdRepository projectBusinessgoalIdRepository;

    public ProjectBusinessgoalIdServiceImpl(ProjectBusinessgoalIdRepository projectBusinessgoalIdRepository) {
        this.projectBusinessgoalIdRepository = projectBusinessgoalIdRepository;
    }

    /**
     * Save a projectBusinessgoalId.
     *
     * @param projectBusinessgoalId the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectBusinessgoalId save(ProjectBusinessgoalId projectBusinessgoalId) {
        log.debug("Request to save ProjectBusinessgoalId : {}", projectBusinessgoalId);
        return projectBusinessgoalIdRepository.save(projectBusinessgoalId);
    }

    /**
     * Get all the projectBusinessgoalIds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectBusinessgoalId> findAll() {
        log.debug("Request to get all ProjectBusinessgoalIds");
        return projectBusinessgoalIdRepository.findAll();
    }


    /**
     * Get one projectBusinessgoalId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectBusinessgoalId> findOne(Long id) {
        log.debug("Request to get ProjectBusinessgoalId : {}", id);
        return projectBusinessgoalIdRepository.findById(id);
    }

    /**
     * Delete the projectBusinessgoalId by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectBusinessgoalId : {}", id);
        projectBusinessgoalIdRepository.deleteById(id);
    }
}
