package com.qnowapp.service.impl;

import com.qnowapp.service.ProjectThemeService;
import com.qnowapp.domain.ProjectTheme;
import com.qnowapp.repository.ProjectThemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProjectTheme}.
 */
@Service
@Transactional
public class ProjectThemeServiceImpl implements ProjectThemeService {

    private final Logger log = LoggerFactory.getLogger(ProjectThemeServiceImpl.class);

    private final ProjectThemeRepository projectThemeRepository;

    public ProjectThemeServiceImpl(ProjectThemeRepository projectThemeRepository) {
        this.projectThemeRepository = projectThemeRepository;
    }

    /**
     * Save a projectTheme.
     *
     * @param projectTheme the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProjectTheme save(ProjectTheme projectTheme) {
        log.debug("Request to save ProjectTheme : {}", projectTheme);
        return projectThemeRepository.save(projectTheme);
    }

    /**
     * Get all the projectThemes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectTheme> findAll() {
        log.debug("Request to get all ProjectThemes");
        return projectThemeRepository.findAll();
    }


    /**
     * Get one projectTheme by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectTheme> findOne(Long id) {
        log.debug("Request to get ProjectTheme : {}", id);
        return projectThemeRepository.findById(id);
    }

    /**
     * Delete the projectTheme by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectTheme : {}", id);
        projectThemeRepository.deleteById(id);
    }
}
