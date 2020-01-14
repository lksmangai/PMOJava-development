package com.qnowapp.service.impl;

import com.qnowapp.service.ImTimesheetService;
import com.qnowapp.domain.ImTimesheet;
import com.qnowapp.repository.ImTimesheetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ImTimesheet}.
 */
@Service
@Transactional
public class ImTimesheetServiceImpl implements ImTimesheetService {

    private final Logger log = LoggerFactory.getLogger(ImTimesheetServiceImpl.class);

    private final ImTimesheetRepository imTimesheetRepository;

    public ImTimesheetServiceImpl(ImTimesheetRepository imTimesheetRepository) {
        this.imTimesheetRepository = imTimesheetRepository;
    }

    /**
     * Save a imTimesheet.
     *
     * @param imTimesheet the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ImTimesheet save(ImTimesheet imTimesheet) {
        log.debug("Request to save ImTimesheet : {}", imTimesheet);
        return imTimesheetRepository.save(imTimesheet);
    }

    /**
     * Get all the imTimesheets.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImTimesheet> findAll() {
        log.debug("Request to get all ImTimesheets");
        return imTimesheetRepository.findAll();
    }


    /**
     * Get one imTimesheet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImTimesheet> findOne(Long id) {
        log.debug("Request to get ImTimesheet : {}", id);
        return imTimesheetRepository.findById(id);
    }

    /**
     * Delete the imTimesheet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImTimesheet : {}", id);
        imTimesheetRepository.deleteById(id);
    }
}
