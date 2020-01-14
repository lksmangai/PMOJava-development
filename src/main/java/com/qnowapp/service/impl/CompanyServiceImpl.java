package com.qnowapp.service.impl;

import com.qnowapp.service.CompanyService;
import com.qnowapp.domain.Company;
import com.qnowapp.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Company}.
 */
@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * Save a company.
     *
     * @param company the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Company save(Company company) {
        log.debug("Request to save Company : {}", company);
        return companyRepository.save(company);
    }

    /**
     * Get all the companies.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Company> findAll() {
        log.debug("Request to get all Companies");
        return companyRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the companies with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Company> findAllWithEagerRelationships(Pageable pageable) {
        return companyRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one company by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Company> findOne(Long id) {
        log.debug("Request to get Company : {}", id);
        return companyRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the company by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Company : {}", id);
        companyRepository.deleteById(id);
    }
}
