package com.qnowapp.service.impl;

import com.qnowapp.service.BacklogPracticeService;
import com.qnowapp.domain.BacklogPractice;
import com.qnowapp.repository.BacklogPracticeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BacklogPractice}.
 */
@Service
@Transactional
public class BacklogPracticeServiceImpl implements BacklogPracticeService {

    private final Logger log = LoggerFactory.getLogger(BacklogPracticeServiceImpl.class);

    private final BacklogPracticeRepository backlogPracticeRepository;

    public BacklogPracticeServiceImpl(BacklogPracticeRepository backlogPracticeRepository) {
        this.backlogPracticeRepository = backlogPracticeRepository;
    }

    /**
     * Save a backlogPractice.
     *
     * @param backlogPractice the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BacklogPractice save(BacklogPractice backlogPractice) {
        log.debug("Request to save BacklogPractice : {}", backlogPractice);
        return backlogPracticeRepository.save(backlogPractice);
    }

    /**
     * Get all the backlogPractices.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BacklogPractice> findAll() {
        log.debug("Request to get all BacklogPractices");
        return backlogPracticeRepository.findAll();
    }


    /**
     * Get one backlogPractice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BacklogPractice> findOne(Long id) {
        log.debug("Request to get BacklogPractice : {}", id);
        return backlogPracticeRepository.findById(id);
    }

    /**
     * Delete the backlogPractice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BacklogPractice : {}", id);
        backlogPracticeRepository.deleteById(id);
    }
}
