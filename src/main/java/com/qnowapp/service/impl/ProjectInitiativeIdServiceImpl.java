package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectInitiativeIdService;
import com.qnowapp.domain.ProjectInitiativeId;
import com.qnowapp.repository.ProjectInitiativeIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectInitiativeId}.
 */
@Service
@Transactional
public class ProjectInitiativeIdServiceImpl implements ProjectInitiativeIdService {

    private final Logger log = LoggerFactory.getLogger(ProjectInitiativeIdServiceImpl.class);

    private final ProjectInitiativeIdRepository projectInitiativeIdRepository;

    public ProjectInitiativeIdServiceImpl(ProjectInitiativeIdRepository projectInitiativeIdRepository) {
        this.projectInitiativeIdRepository = projectInitiativeIdRepository;
    }

    /**
     * Save a projectInitiativeId.
     *
     * @param projectInitiativeId the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectInitiativeId save(ProjectInitiativeId projectInitiativeId) {
        log.debug("Request to save ProjectInitiativeId : {}", projectInitiativeId);
        return projectInitiativeIdRepository.save(projectInitiativeId);
    }

    /**
     * Get all the projectInitiativeIds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectInitiativeId> findAll() {
        log.debug("Request to get all ProjectInitiativeIds");
        return projectInitiativeIdRepository.findAll();
    }


    /**
     * Get one projectInitiativeId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectInitiativeId> findOne(Long id) {
        log.debug("Request to get ProjectInitiativeId : {}", id);
        return projectInitiativeIdRepository.findById(id);
    }

    /**
     * Delete the projectInitiativeId by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectInitiativeId : {}", id);
        projectInitiativeIdRepository.deleteById(id);
    }
}
