package com.qnowapp.service.impl;

import com.qnowapp.service.ImProjectsService;
import com.qnowapp.domain.ImProjects;
import com.qnowapp.repository.ImProjectsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ImProjects}.
 */
@Service
@Transactional
public class ImProjectsServiceImpl implements ImProjectsService {

    private final Logger log = LoggerFactory.getLogger(ImProjectsServiceImpl.class);

    private final ImProjectsRepository imProjectsRepository;

    public ImProjectsServiceImpl(ImProjectsRepository imProjectsRepository) {
        this.imProjectsRepository = imProjectsRepository;
    }

    /**
     * Save a imProjects.
     *
     * @param imProjects the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ImProjects save(ImProjects imProjects) {
        log.debug("Request to save ImProjects : {}", imProjects);
        return imProjectsRepository.save(imProjects);
    }

    /**
     * Get all the imProjects.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImProjects> findAll() {
        log.debug("Request to get all ImProjects");
        return imProjectsRepository.findAll();
    }


    /**
     * Get one imProjects by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImProjects> findOne(Long id) {
        log.debug("Request to get ImProjects : {}", id);
        return imProjectsRepository.findById(id);
    }

    /**
     * Delete the imProjects by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImProjects : {}", id);
        imProjectsRepository.deleteById(id);
    }
}
