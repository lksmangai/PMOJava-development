package com.qnowapp.web.rest;

import com.qnowapp.domain.TagType;
import com.qnowapp.service.TagTypeService;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.service.dto.TagTypeCriteria;
import com.qnowapp.service.TagTypeQueryService;

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
 * REST controller for managing {@link com.qnowapp.domain.TagType}.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class TagTypeResource {

    private final Logger log = LoggerFactory.getLogger(TagTypeResource.class);

    private static final String ENTITY_NAME = "tagType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagTypeService tagTypeService;

    private final TagTypeQueryService tagTypeQueryService;

    public TagTypeResource(TagTypeService tagTypeService, TagTypeQueryService tagTypeQueryService) {
        this.tagTypeService = tagTypeService;
        this.tagTypeQueryService = tagTypeQueryService;
    }

    /**
     * {@code POST  /tag-types} : Create a new tagType.
     *
     * @param tagType the tagType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagType, or with status {@code 400 (Bad Request)} if the tagType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PostMapping("/tag-types")
    public ResponseEntity<TagType> createTagType(@RequestBody TagType tagType) throws URISyntaxException {
        log.debug("REST request to save TagType : {}", tagType);
        if (tagType.getId() != null) {
            throw new BadRequestAlertException("A new tagType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagType result = tagTypeService.save(tagType);
        return ResponseEntity.created(new URI("/api/tag-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tag-types} : Updates an existing tagType.
     *
     * @param tagType the tagType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagType,
     * or with status {@code 400 (Bad Request)} if the tagType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @CrossOrigin
    @PutMapping("/tag-types")
    public ResponseEntity<TagType> updateTagType(@RequestBody TagType tagType) throws URISyntaxException {
        log.debug("REST request to update TagType : {}", tagType);
        if (tagType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TagType result = tagTypeService.save(tagType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tagType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tag-types} : get all the tagTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tagTypes in body.
     */
    @CrossOrigin
    @GetMapping("/tag-types")
    public ResponseEntity<List<TagType>> getAllTagTypes(TagTypeCriteria criteria) {
        log.debug("REST request to get TagTypes by criteria: {}", criteria);
        List<TagType> entityList = tagTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * {@code GET  /tag-types/count} : count all the tagTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @CrossOrigin
    @GetMapping("/tag-types/count")
    public ResponseEntity<Long> countTagTypes(TagTypeCriteria criteria) {
        log.debug("REST request to count TagTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(tagTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tag-types/:id} : get the "id" tagType.
     *
     * @param id the id of the tagType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagType, or with status {@code 404 (Not Found)}.
     */
    @CrossOrigin
    @GetMapping("/tag-types/{id}")
    public ResponseEntity<TagType> getTagType(@PathVariable Long id) {
        log.debug("REST request to get TagType : {}", id);
        Optional<TagType> tagType = tagTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tagType);
    }

    /**
     * {@code DELETE  /tag-types/:id} : delete the "id" tagType.
     *
     * @param id the id of the tagType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @CrossOrigin
    @DeleteMapping("/tag-types/{id}")
    public ResponseEntity<Void> deleteTagType(@PathVariable Long id) {
        log.debug("REST request to delete TagType : {}", id);
        tagTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
