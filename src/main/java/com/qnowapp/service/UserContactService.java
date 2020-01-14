package com.qnowapp.service;

import com.qnowapp.domain.UserContact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link UserContact}.
 */
public interface UserContactService {

    /**
     * Save a userContact.
     *
     * @param userContact the entity to save.
     * @return the persisted entity.
     */
    UserContact save(UserContact userContact);

    /**
     * Get all the userContacts.
     *
     * @return the list of entities.
     */
    List<UserContact> findAll();

    /**
     * Get all the userContacts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<UserContact> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" userContact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserContact> findOne(Long id);

    /**
     * Delete the "id" userContact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
