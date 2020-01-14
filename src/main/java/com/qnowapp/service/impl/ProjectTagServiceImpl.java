package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectTagService;
import com.qnowapp.domain.ProjectTag;
import com.qnowapp.repository.ProjectTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectTag}.
 */
@Service
@Transactional
public class ProjectTagServiceImpl implements ProjectTagService {

    private final Logger log = LoggerFactory.getLogger(ProjectTagServiceImpl.class);

    private final ProjectTagRepository projectTagRepository;

    public ProjectTagServiceImpl(ProjectTagRepository projectTagRepository) {
        this.projectTagRepository = projectTagRepository;
    }

    /**
     * Save a projectTag.
     *
     * @param projectTag the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectTag save(ProjectTag projectTag) {
        log.debug("Request to save ProjectTag : {}", projectTag);
        return projectTagRepository.save(projectTag);
    }

    /**
     * Get all the projectTags.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectTag> findAll() {
        log.debug("Request to get all ProjectTags");
        return projectTagRepository.findAll();
    }


    /**
     * Get one projectTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectTag> findOne(Long id) {
        log.debug("Request to get ProjectTag : {}", id);
        return projectTagRepository.findById(id);
    }

    /**
     * Delete the projectTag by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectTag : {}", id);
        projectTagRepository.deleteById(id);
    }
}
