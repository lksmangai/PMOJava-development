package com.qnowapp.service;

import com.qnowapp.domain.FileStorage;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FileStorage}.
 */
public interface FileStorageService {

    /**
     * Save a fileStorage.
     *
     * @param fileStorage the entity to save.
     * @return the persisted entity.
     */
    FileStorage save(FileStorage fileStorage);

    /**
     * Get all the fileStorages.
     *
     * @return the list of entities.
     */
    List<FileStorage> findAll();


    /**
     * Get the "id" fileStorage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileStorage> findOne(Long id);

    /**
     * Delete the "id" fileStorage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
