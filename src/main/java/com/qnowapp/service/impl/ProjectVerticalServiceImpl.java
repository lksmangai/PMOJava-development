package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectVerticalService;
import com.qnowapp.domain.ProjectVertical;
import com.qnowapp.repository.ProjectVerticalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectVertical}.
 */
@Service
@Transactional
public class ProjectVerticalServiceImpl implements ProjectVerticalService {

    private final Logger log = LoggerFactory.getLogger(ProjectVerticalServiceImpl.class);

    private final ProjectVerticalRepository projectVerticalRepository;

    public ProjectVerticalServiceImpl(ProjectVerticalRepository projectVerticalRepository) {
        this.projectVerticalRepository = projectVerticalRepository;
    }

    /**
     * Save a projectVertical.
     *
     * @param projectVertical the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectVertical save(ProjectVertical projectVertical) {
        log.debug("Request to save ProjectVertical : {}", projectVertical);
        return projectVerticalRepository.save(projectVertical);
    }

    /**
     * Get all the projectVerticals.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectVertical> findAll() {
        log.debug("Request to get all ProjectVerticals");
        return projectVerticalRepository.findAll();
    }


    /**
     * Get one projectVertical by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectVertical> findOne(Long id) {
        log.debug("Request to get ProjectVertical : {}", id);
        return projectVerticalRepository.findById(id);
    }

    /**
     * Delete the projectVertical by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectVertical : {}", id);
        projectVerticalRepository.deleteById(id);
    }
}
