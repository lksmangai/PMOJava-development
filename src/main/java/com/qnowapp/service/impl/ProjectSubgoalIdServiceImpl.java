package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectSubgoalIdService;
import com.qnowapp.domain.ProjectSubgoalId;
import com.qnowapp.repository.ProjectSubgoalIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectSubgoalId}.
 */
@Service
@Transactional
public class ProjectSubgoalIdServiceImpl implements ProjectSubgoalIdService {

    private final Logger log = LoggerFactory.getLogger(ProjectSubgoalIdServiceImpl.class);

    private final ProjectSubgoalIdRepository projectSubgoalIdRepository;

    public ProjectSubgoalIdServiceImpl(ProjectSubgoalIdRepository projectSubgoalIdRepository) {
        this.projectSubgoalIdRepository = projectSubgoalIdRepository;
    }

    /**
     * Save a projectSubgoalId.
     *
     * @param projectSubgoalId the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectSubgoalId save(ProjectSubgoalId projectSubgoalId) {
        log.debug("Request to save ProjectSubgoalId : {}", projectSubgoalId);
        return projectSubgoalIdRepository.save(projectSubgoalId);
    }

    /**
     * Get all the projectSubgoalIds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectSubgoalId> findAll() {
        log.debug("Request to get all ProjectSubgoalIds");
        return projectSubgoalIdRepository.findAll();
    }


    /**
     * Get one projectSubgoalId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectSubgoalId> findOne(Long id) {
        log.debug("Request to get ProjectSubgoalId : {}", id);
        return projectSubgoalIdRepository.findById(id);
    }

    /**
     * Delete the projectSubgoalId by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectSubgoalId : {}", id);
        projectSubgoalIdRepository.deleteById(id);
    }
}
