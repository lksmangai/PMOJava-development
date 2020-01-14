package com.qnowapp.service.impl;

import com.qnowapp.service.GroupMembersService;
import com.qnowapp.domain.GroupMembers;
import com.qnowapp.repository.GroupMembersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupMembers}.
 */
@Service
@Transactional
public class GroupMembersServiceImpl implements GroupMembersService {

    private final Logger log = LoggerFactory.getLogger(GroupMembersServiceImpl.class);

    private final GroupMembersRepository groupMembersRepository;

    public GroupMembersServiceImpl(GroupMembersRepository groupMembersRepository) {
        this.groupMembersRepository = groupMembersRepository;
    }

    /**
     * Save a groupMembers.
     *
     * @param groupMembers the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GroupMembers save(GroupMembers groupMembers) {
        log.debug("Request to save GroupMembers : {}", groupMembers);
        return groupMembersRepository.save(groupMembers);
    }

    /**
     * Get all the groupMembers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<GroupMembers> findAll() {
        log.debug("Request to get all GroupMembers");
        return groupMembersRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the groupMembers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GroupMembers> findAllWithEagerRelationships(Pageable pageable) {
        return groupMembersRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one groupMembers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GroupMembers> findOne(Long id) {
        log.debug("Request to get GroupMembers : {}", id);
        return groupMembersRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the groupMembers by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupMembers : {}", id);
        groupMembersRepository.deleteById(id);
    }
}
