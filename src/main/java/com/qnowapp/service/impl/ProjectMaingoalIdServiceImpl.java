package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectMaingoalIdService;
import com.qnowapp.domain.ProjectMaingoalId;
import com.qnowapp.repository.ProjectMaingoalIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectMaingoalId}.
 */
@Service
@Transactional
public class ProjectMaingoalIdServiceImpl implements ProjectMaingoalIdService {

    private final Logger log = LoggerFactory.getLogger(ProjectMaingoalIdServiceImpl.class);

    private final ProjectMaingoalIdRepository projectMaingoalIdRepository;

    public ProjectMaingoalIdServiceImpl(ProjectMaingoalIdRepository projectMaingoalIdRepository) {
        this.projectMaingoalIdRepository = projectMaingoalIdRepository;
    }

    /**
     * Save a projectMaingoalId.
     *
     * @param projectMaingoalId the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectMaingoalId save(ProjectMaingoalId projectMaingoalId) {
        log.debug("Request to save ProjectMaingoalId : {}", projectMaingoalId);
        return projectMaingoalIdRepository.save(projectMaingoalId);
    }

    /**
     * Get all the projectMaingoalIds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectMaingoalId> findAll() {
        log.debug("Request to get all ProjectMaingoalIds");
        return projectMaingoalIdRepository.findAll();
    }


    /**
     * Get one projectMaingoalId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectMaingoalId> findOne(Long id) {
        log.debug("Request to get ProjectMaingoalId : {}", id);
        return projectMaingoalIdRepository.findById(id);
    }

    /**
     * Delete the projectMaingoalId by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectMaingoalId : {}", id);
        projectMaingoalIdRepository.deleteById(id);
    }
}
