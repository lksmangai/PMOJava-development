package com.qnowapp.service.impl;

import com.qnowapp.service.StateMasterService;
import com.qnowapp.domain.StateMaster;
import com.qnowapp.repository.StateMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link StateMaster}.
 */
@Service
@Transactional
public class StateMasterServiceImpl implements StateMasterService {

    private final Logger log = LoggerFactory.getLogger(StateMasterServiceImpl.class);

    private final StateMasterRepository stateMasterRepository;

    public StateMasterServiceImpl(StateMasterRepository stateMasterRepository) {
        this.stateMasterRepository = stateMasterRepository;
    }

    /**
     * Save a stateMaster.
     *
     * @param stateMaster the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StateMaster save(StateMaster stateMaster) {
        log.debug("Request to save StateMaster : {}", stateMaster);
        return stateMasterRepository.save(stateMaster);
    }

    /**
     * Get all the stateMasters.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StateMaster> findAll() {
        log.debug("Request to get all StateMasters");
        return stateMasterRepository.findAll();
    }


    /**
     * Get one stateMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StateMaster> findOne(Long id) {
        log.debug("Request to get StateMaster : {}", id);
        return stateMasterRepository.findById(id);
    }

    /**
     * Delete the stateMaster by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StateMaster : {}", id);
        stateMasterRepository.deleteById(id);
    }
}
