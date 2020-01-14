package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectTypeIdService;
import com.qnowapp.domain.ProjectTypeId;
import com.qnowapp.repository.ProjectTypeIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectTypeId}.
 */
@Service
@Transactional
public class ProjectTypeIdServiceImpl implements ProjectTypeIdService {

    private final Logger log = LoggerFactory.getLogger(ProjectTypeIdServiceImpl.class);

    private final ProjectTypeIdRepository projectTypeIdRepository;

    public ProjectTypeIdServiceImpl(ProjectTypeIdRepository projectTypeIdRepository) {
        this.projectTypeIdRepository = projectTypeIdRepository;
    }

    /**
     * Save a projectTypeId.
     *
     * @param projectTypeId the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectTypeId save(ProjectTypeId projectTypeId) {
        log.debug("Request to save ProjectTypeId : {}", projectTypeId);
        return projectTypeIdRepository.save(projectTypeId);
    }

    /**
     * Get all the projectTypeIds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectTypeId> findAll() {
        log.debug("Request to get all ProjectTypeIds");
        return projectTypeIdRepository.findAll();
    }


    /**
     * Get one projectTypeId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectTypeId> findOne(Long id) {
        log.debug("Request to get ProjectTypeId : {}", id);
        return projectTypeIdRepository.findById(id);
    }

    /**
     * Delete the projectTypeId by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectTypeId : {}", id);
        projectTypeIdRepository.deleteById(id);
    }
}
