package com.qnowapp.service;

import com.qnowapp.domain.Company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Company}.
 */
public interface CompanyService {

    /**
     * Save a company.
     *
     * @param company the entity to save.
     * @return the persisted entity.
     */
    Company save(Company company);

    /**
     * Get all the companies.
     *
     * @return the list of entities.
     */
    List<Company> findAll();

    /**
     * Get all the companies with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Company> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" company.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Company> findOne(Long id);

    /**
     * Delete the "id" company.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
