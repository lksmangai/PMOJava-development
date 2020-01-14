package com.qnowapp.service;

import com.qnowapp.domain.GroupMembers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link GroupMembers}.
 */
public interface GroupMembersService {

    /**
     * Save a groupMembers.
     *
     * @param groupMembers the entity to save.
     * @return the persisted entity.
     */
    GroupMembers save(GroupMembers groupMembers);

    /**
     * Get all the groupMembers.
     *
     * @return the list of entities.
     */
    List<GroupMembers> findAll();

    /**
     * Get all the groupMembers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<GroupMembers> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" groupMembers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupMembers> findOne(Long id);

    /**
     * Delete the "id" groupMembers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
