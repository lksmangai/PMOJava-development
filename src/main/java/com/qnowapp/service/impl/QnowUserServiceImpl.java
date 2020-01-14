package com.qnowapp.service.impl;

import com.qnowapp.service.QnowUserService;
import com.qnowapp.domain.QnowUser;
import com.qnowapp.repository.QnowUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link QnowUser}.
 */
@Service
@Transactional
public class QnowUserServiceImpl implements QnowUserService {

    private final Logger log = LoggerFactory.getLogger(QnowUserServiceImpl.class);

    private final QnowUserRepository qnowUserRepository;

    public QnowUserServiceImpl(QnowUserRepository qnowUserRepository) {
        this.qnowUserRepository = qnowUserRepository;
    }

    /**
     * Save a qnowUser.
     *
     * @param qnowUser the entity to save.
     * @return the persisted entity.
     */
    @Override
    public QnowUser save(QnowUser qnowUser) {
        log.debug("Request to save QnowUser : {}", qnowUser);
        return qnowUserRepository.save(qnowUser);
    }

    /**
     * Get all the qnowUsers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<QnowUser> findAll() {
        log.debug("Request to get all QnowUsers");
        return qnowUserRepository.findAll();
    }


    /**
     * Get one qnowUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QnowUser> findOne(Long id) {
        log.debug("Request to get QnowUser : {}", id);
        return qnowUserRepository.findById(id);
    }

    /**
     * Delete the qnowUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QnowUser : {}", id);
        qnowUserRepository.deleteById(id);
    }
}
