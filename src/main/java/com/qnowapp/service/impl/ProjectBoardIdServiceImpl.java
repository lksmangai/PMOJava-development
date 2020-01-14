package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectBoardIdService;
import com.qnowapp.domain.ProjectBoardId;
import com.qnowapp.repository.ProjectBoardIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectBoardId}.
 */
@Service
@Transactional
public class ProjectBoardIdServiceImpl implements ProjectBoardIdService {

    private final Logger log = LoggerFactory.getLogger(ProjectBoardIdServiceImpl.class);

    private final ProjectBoardIdRepository projectBoardIdRepository;

    public ProjectBoardIdServiceImpl(ProjectBoardIdRepository projectBoardIdRepository) {
        this.projectBoardIdRepository = projectBoardIdRepository;
    }

    /**
     * Save a projectBoardId.
     *
     * @param projectBoardId the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectBoardId save(ProjectBoardId projectBoardId) {
        log.debug("Request to save ProjectBoardId : {}", projectBoardId);
        return projectBoardIdRepository.save(projectBoardId);
    }

    /**
     * Get all the projectBoardIds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectBoardId> findAll() {
        log.debug("Request to get all ProjectBoardIds");
        return projectBoardIdRepository.findAll();
    }


    /**
     * Get one projectBoardId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectBoardId> findOne(Long id) {
        log.debug("Request to get ProjectBoardId : {}", id);
        return projectBoardIdRepository.findById(id);
    }

    /**
     * Delete the projectBoardId by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectBoardId : {}", id);
        projectBoardIdRepository.deleteById(id);
    }
}
