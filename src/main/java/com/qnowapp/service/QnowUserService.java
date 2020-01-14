package com.qnowapp.service;

import com.qnowapp.domain.QnowUser;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link QnowUser}.
 */
public interface QnowUserService {

    /**
     * Save a qnowUser.
     *
     * @param qnowUser the entity to save.
     * @return the persisted entity.
     */
    QnowUser save(QnowUser qnowUser);

    /**
     * Get all the qnowUsers.
     *
     * @return the list of entities.
     */
    List<QnowUser> findAll();


    /**
     * Get the "id" qnowUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QnowUser> findOne(Long id);

    /**
     * Delete the "id" qnowUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
