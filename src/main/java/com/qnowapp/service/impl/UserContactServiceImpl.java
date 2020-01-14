package com.qnowapp.service.impl;

import com.qnowapp.service.UserContactService;
import com.qnowapp.domain.UserContact;
import com.qnowapp.repository.UserContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UserContact}.
 */
@Service
@Transactional
public class UserContactServiceImpl implements UserContactService {

    private final Logger log = LoggerFactory.getLogger(UserContactServiceImpl.class);

    private final UserContactRepository userContactRepository;

    public UserContactServiceImpl(UserContactRepository userContactRepository) {
        this.userContactRepository = userContactRepository;
    }

    /**
     * Save a userContact.
     *
     * @param userContact the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserContact save(UserContact userContact) {
        log.debug("Request to save UserContact : {}", userContact);
        return userContactRepository.save(userContact);
    }

    /**
     * Get all the userContacts.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserContact> findAll() {
        log.debug("Request to get all UserContacts");
        return userContactRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the userContacts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UserContact> findAllWithEagerRelationships(Pageable pageable) {
        return userContactRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one userContact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserContact> findOne(Long id) {
        log.debug("Request to get UserContact : {}", id);
        return userContactRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the userContact by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserContact : {}", id);
        userContactRepository.deleteById(id);
    }
}
