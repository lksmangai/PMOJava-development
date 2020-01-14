package com.qnowapp.service.impl;

import com.qnowapp.service.ImEmployeeService;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.repository.ImEmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ImEmployee}.
 */
@Service
@Transactional
public class ImEmployeeServiceImpl implements ImEmployeeService {

    private final Logger log = LoggerFactory.getLogger(ImEmployeeServiceImpl.class);

    private final ImEmployeeRepository imEmployeeRepository;

    public ImEmployeeServiceImpl(ImEmployeeRepository imEmployeeRepository) {
        this.imEmployeeRepository = imEmployeeRepository;
    }

    /**
     * Save a imEmployee.
     *
     * @param imEmployee the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ImEmployee save(ImEmployee imEmployee) {
        log.debug("Request to save ImEmployee : {}", imEmployee);
        return imEmployeeRepository.save(imEmployee);
    }

    /**
     * Get all the imEmployees.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImEmployee> findAll() {
        log.debug("Request to get all ImEmployees");
        return imEmployeeRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the imEmployees with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ImEmployee> findAllWithEagerRelationships(Pageable pageable) {
        return imEmployeeRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one imEmployee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImEmployee> findOne(Long id) {
        log.debug("Request to get ImEmployee : {}", id);
        return imEmployeeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the imEmployee by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImEmployee : {}", id);
        imEmployeeRepository.deleteById(id);
    }
}
