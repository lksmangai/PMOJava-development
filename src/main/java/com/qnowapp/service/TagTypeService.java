package com.qnowapp.service;

import com.qnowapp.domain.TagType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TagType}.
 */
public interface TagTypeService {

    /**
     * Save a tagType.
     *
     * @param tagType the entity to save.
     * @return the persisted entity.
     */
    TagType save(TagType tagType);

    /**
     * Get all the tagTypes.
     *
     * @return the list of entities.
     */
    List<TagType> findAll();


    /**
     * Get the "id" tagType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TagType> findOne(Long id);

    /**
     * Delete the "id" tagType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
