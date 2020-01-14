package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectCostCenterIdService;
import com.qnowapp.domain.ProjectCostCenterId;
import com.qnowapp.repository.ProjectCostCenterIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectCostCenterId}.
 */
@Service
@Transactional
public class ProjectCostCenterIdServiceImpl implements ProjectCostCenterIdService {

    private final Logger log = LoggerFactory.getLogger(ProjectCostCenterIdServiceImpl.class);

    private final ProjectCostCenterIdRepository projectCostCenterIdRepository;

    public ProjectCostCenterIdServiceImpl(ProjectCostCenterIdRepository projectCostCenterIdRepository) {
        this.projectCostCenterIdRepository = projectCostCenterIdRepository;
    }

    /**
     * Save a projectCostCenterId.
     *
     * @param projectCostCenterId the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectCostCenterId save(ProjectCostCenterId projectCostCenterId) {
        log.debug("Request to save ProjectCostCenterId : {}", projectCostCenterId);
        return projectCostCenterIdRepository.save(projectCostCenterId);
    }

    /**
     * Get all the projectCostCenterIds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectCostCenterId> findAll() {
        log.debug("Request to get all ProjectCostCenterIds");
        return projectCostCenterIdRepository.findAll();
    }


    /**
     * Get one projectCostCenterId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectCostCenterId> findOne(Long id) {
        log.debug("Request to get ProjectCostCenterId : {}", id);
        return projectCostCenterIdRepository.findById(id);
    }

    /**
     * Delete the projectCostCenterId by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectCostCenterId : {}", id);
        projectCostCenterIdRepository.deleteById(id);
    }
}
