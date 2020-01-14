package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectClassService;
import com.qnowapp.domain.ProjectClass;
import com.qnowapp.repository.ProjectClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectClass}.
 */
@Service
@Transactional
public class ProjectClassServiceImpl implements ProjectClassService {

    private final Logger log = LoggerFactory.getLogger(ProjectClassServiceImpl.class);

    private final ProjectClassRepository projectClassRepository;

    public ProjectClassServiceImpl(ProjectClassRepository projectClassRepository) {
        this.projectClassRepository = projectClassRepository;
    }

    /**
     * Save a projectClass.
     *
     * @param projectClass the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectClass save(ProjectClass projectClass) {
        log.debug("Request to save ProjectClass : {}", projectClass);
        return projectClassRepository.save(projectClass);
    }

    /**
     * Get all the projectClasses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectClass> findAll() {
        log.debug("Request to get all ProjectClasses");
        return projectClassRepository.findAll();
    }


    /**
     * Get one projectClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectClass> findOne(Long id) {
        log.debug("Request to get ProjectClass : {}", id);
        return projectClassRepository.findById(id);
    }

    /**
     * Delete the projectClass by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectClass : {}", id);
        projectClassRepository.deleteById(id);
    }
}
