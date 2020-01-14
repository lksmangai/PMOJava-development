package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectAllocationService;
import com.qnowapp.domain.ProjectAllocation;
import com.qnowapp.repository.ProjectAllocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectAllocation}.
 */
@Service
@Transactional
public class ProjectAllocationServiceImpl implements ProjectAllocationService {

    private final Logger log = LoggerFactory.getLogger(ProjectAllocationServiceImpl.class);

    private final ProjectAllocationRepository projectAllocationRepository;

    public ProjectAllocationServiceImpl(ProjectAllocationRepository projectAllocationRepository) {
        this.projectAllocationRepository = projectAllocationRepository;
    }

    /**
     * Save a projectAllocation.
     *
     * @param projectAllocation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectAllocation save(ProjectAllocation projectAllocation) {
        log.debug("Request to save ProjectAllocation : {}", projectAllocation);
        return projectAllocationRepository.save(projectAllocation);
    }

    /**
     * Get all the projectAllocations.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectAllocation> findAll() {
        log.debug("Request to get all ProjectAllocations");
        return projectAllocationRepository.findAll();
    }


    /**
     * Get one projectAllocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectAllocation> findOne(Long id) {
        log.debug("Request to get ProjectAllocation : {}", id);
        return projectAllocationRepository.findById(id);
    }

    /**
     * Delete the projectAllocation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectAllocation : {}", id);
        projectAllocationRepository.deleteById(id);
    }
}
