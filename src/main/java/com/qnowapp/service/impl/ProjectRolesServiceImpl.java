package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectRolesService;
import com.qnowapp.domain.ProjectRoles;
import com.qnowapp.repository.ProjectRolesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectRoles}.
 */
@Service
@Transactional
public class ProjectRolesServiceImpl implements ProjectRolesService {

    private final Logger log = LoggerFactory.getLogger(ProjectRolesServiceImpl.class);

    private final ProjectRolesRepository projectRolesRepository;

    public ProjectRolesServiceImpl(ProjectRolesRepository projectRolesRepository) {
        this.projectRolesRepository = projectRolesRepository;
    }

    /**
     * Save a projectRoles.
     *
     * @param projectRoles the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectRoles save(ProjectRoles projectRoles) {
        log.debug("Request to save ProjectRoles : {}", projectRoles);
        return projectRolesRepository.save(projectRoles);
    }

    /**
     * Get all the projectRoles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectRoles> findAll() {
        log.debug("Request to get all ProjectRoles");
        return projectRolesRepository.findAll();
    }


    /**
     * Get one projectRoles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectRoles> findOne(Long id) {
        log.debug("Request to get ProjectRoles : {}", id);
        return projectRolesRepository.findById(id);
    }

    /**
     * Delete the projectRoles by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectRoles : {}", id);
        projectRolesRepository.deleteById(id);
    }
}
