package com.qnowapp.web.rest;

import com.qnowapp.domain.FileStorage;
import com.qnowapp.service.FileStorageService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.FileStorageCriteria;
import com.qnowapp.service.FileStorageQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.qnowapp.domain.FileStorage}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class FileStorageResource {

    private final Logger log = LoggerFactory.getLogger(FileStorageResource.class);

    private static final String ENTITY_NAME = "fileStorage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileStorageService fileStorageService;

    private final FileStorageQueryService fileStorageQueryService;

    public FileStorageResource(FileStorageService fileStorageService, FileStorageQueryService fileStorageQueryService) {
        this.fileStorageService = fileStorageService;
        this.fileStorageQueryService = fileStorageQueryService;
    }

    /**
     * {@code POST  /file-storages} : Create a new fileStorage.
     *
     * @param fileStorage the fileStorage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileStorage, or with status {@code 400 (Bad Request)} if the fileStorage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/file-storages")
    public ResponseEntity<FileStorage> createFileStorage(@RequestBody FileStorage fileStorage) throws URISyntaxException {
        log.debug("REST request to save FileStorage : {}", fileStorage);
        if (fileStorage.getId() != null) {
            throw new BadRequestAlertException("A new fileStorage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileStorage result = fileStorageService.save(fileStorage);
        return ResponseEntity.created(new URI("/api/file-storages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-storages} : Updates an existing fileStorage.
     *
     * @param fileStorage the fileStorage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileStorage,
     * or with status {@code 400 (Bad Request)} if the fileStorage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileStorage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/file-storages")
    public ResponseEntity<FileStorage> updateFileStorage(@RequestBody FileStorage fileStorage) throws URISyntaxException {
        log.debug("REST request to update FileStorage : {}", fileStorage);
        if (fileStorage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FileStorage result = fileStorageService.save(fileStorage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileStorage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /file-storages} : get all the fileStorages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileStorages in body.
     */
    @CrossOrigin
    @GetMapping("/file-storages")
    public ResponseEntity<List<FileStorage>> getAllFileStorages(FileStorageCriteria criteria) {
        log.debug("REST request to get FileStorages by criteria: {}", criteria);
        List<FileStorage> entityList = fileStorageQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /file-storages/count} : count all the fileStorages.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/file-storages/count")
    public ResponseEntity<Long> countFileStorages(FileStorageCriteria criteria) {
        log.debug("REST request to count FileStorages by criteria: {}", criteria);
        return ResponseEntity.ok().body(fileStorageQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /file-storages/:id} : get the "id" fileStorage.
     *
     * @param id the id of the fileStorage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileStorage, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/file-storages/{id}")
    public ResponseEntity<FileStorage> getFileStorage(@PathVariable Long id) {
        log.debug("REST request to get FileStorage : {}", id);
        Optional<FileStorage> fileStorage = fileStorageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileStorage);
    }

    /**
     * {@code DELETE  /file-storages/:id} : delete the "id" fileStorage.
     *
     * @param id the id of the fileStorage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/file-storages/{id}")
    public ResponseEntity<Void> deleteFileStorage(@PathVariable Long id) {
        log.debug("REST request to delete FileStorage : {}", id);
        fileStorageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
