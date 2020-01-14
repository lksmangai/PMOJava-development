package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectStatusIdService;
import com.qnowapp.domain.ProjectStatusId;
import com.qnowapp.repository.ProjectStatusIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectStatusId}.
 */
@Service
@Transactional
public class ProjectStatusIdServiceImpl implements ProjectStatusIdService {

    private final Logger log = LoggerFactory.getLogger(ProjectStatusIdServiceImpl.class);

    private final ProjectStatusIdRepository projectStatusIdRepository;

    public ProjectStatusIdServiceImpl(ProjectStatusIdRepository projectStatusIdRepository) {
        this.projectStatusIdRepository = projectStatusIdRepository;
    }

    /**
     * Save a projectStatusId.
     *
     * @param projectStatusId the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectStatusId save(ProjectStatusId projectStatusId) {
        log.debug("Request to save ProjectStatusId : {}", projectStatusId);
        return projectStatusIdRepository.save(projectStatusId);
    }

    /**
     * Get all the projectStatusIds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectStatusId> findAll() {
        log.debug("Request to get all ProjectStatusIds");
        return projectStatusIdRepository.findAll();
    }


    /**
     * Get one projectStatusId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectStatusId> findOne(Long id) {
        log.debug("Request to get ProjectStatusId : {}", id);
        return projectStatusIdRepository.findById(id);
    }

    /**
     * Delete the projectStatusId by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectStatusId : {}", id);
        projectStatusIdRepository.deleteById(id);
    }
}
