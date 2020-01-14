package com.qnowapp.service;

import com.qnowapp.domain.Roles;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Roles}.
 */
public interface RolesService {

    /**
     * Save a roles.
     *
     * @param roles the entity to save.
     * @return the persisted entity.
     */
    Roles save(Roles roles);

    /**
     * Get all the roles.
     *
     * @return the list of entities.
     */
    List<Roles> findAll();


    /**
     * Get the "id" roles.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Roles> findOne(Long id);

    /**
     * Delete the "id" roles.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
