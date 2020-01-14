package com.qnowapp.service.impl;

import com.qnowapp.service.EmployeeStatusService;
import com.qnowapp.domain.EmployeeStatus;
import com.qnowapp.repository.EmployeeStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link EmployeeStatus}.
 */
@Service
@Transactional
public class EmployeeStatusServiceImpl implements EmployeeStatusService {

    private final Logger log = LoggerFactory.getLogger(EmployeeStatusServiceImpl.class);

    private final EmployeeStatusRepository employeeStatusRepository;

    public EmployeeStatusServiceImpl(EmployeeStatusRepository employeeStatusRepository) {
        this.employeeStatusRepository = employeeStatusRepository;
    }

    /**
     * Save a employeeStatus.
     *
     * @param employeeStatus the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmployeeStatus save(EmployeeStatus employeeStatus) {
        log.debug("Request to save EmployeeStatus : {}", employeeStatus);
        return employeeStatusRepository.save(employeeStatus);
    }

    /**
     * Get all the employeeStatuses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeStatus> findAll() {
        log.debug("Request to get all EmployeeStatuses");
        return employeeStatusRepository.findAll();
    }


    /**
     * Get one employeeStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeStatus> findOne(Long id) {
        log.debug("Request to get EmployeeStatus : {}", id);
        return employeeStatusRepository.findById(id);
    }

    /**
     * Delete the employeeStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeeStatus : {}", id);
        employeeStatusRepository.deleteById(id);
    }
}
