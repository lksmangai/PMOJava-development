package com.qnowapp.service.impl;

import com.qnowapp.service.FileStorageService;
import com.qnowapp.domain.FileStorage;
import com.qnowapp.repository.FileStorageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link FileStorage}.
 */
@Service
@Transactional
public class FileStorageServiceImpl implements FileStorageService {

    private final Logger log = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    private final FileStorageRepository fileStorageRepository;

    public FileStorageServiceImpl(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }

    /**
     * Save a fileStorage.
     *
     * @param fileStorage the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FileStorage save(FileStorage fileStorage) {
        log.debug("Request to save FileStorage : {}", fileStorage);
        return fileStorageRepository.save(fileStorage);
    }

    /**
     * Get all the fileStorages.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FileStorage> findAll() {
        log.debug("Request to get all FileStorages");
        return fileStorageRepository.findAll();
    }


    /**
     * Get one fileStorage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FileStorage> findOne(Long id) {
        log.debug("Request to get FileStorage : {}", id);
        return fileStorageRepository.findById(id);
    }

    /**
     * Delete the fileStorage by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileStorage : {}", id);
        fileStorageRepository.deleteById(id);
    }
}
