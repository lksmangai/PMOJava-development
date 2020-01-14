package com.qnowapp.service.impl;

import com.qnowapp.service.TagTypeService;
import com.qnowapp.domain.TagType;
import com.qnowapp.repository.TagTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TagType}.
 */
@Service
@Transactional
public class TagTypeServiceImpl implements TagTypeService {

    private final Logger log = LoggerFactory.getLogger(TagTypeServiceImpl.class);

    private final TagTypeRepository tagTypeRepository;

    public TagTypeServiceImpl(TagTypeRepository tagTypeRepository) {
        this.tagTypeRepository = tagTypeRepository;
    }

    /**
     * Save a tagType.
     *
     * @param tagType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TagType save(TagType tagType) {
        log.debug("Request to save TagType : {}", tagType);
        return tagTypeRepository.save(tagType);
    }

    /**
     * Get all the tagTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TagType> findAll() {
        log.debug("Request to get all TagTypes");
        return tagTypeRepository.findAll();
    }


    /**
     * Get one tagType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TagType> findOne(Long id) {
        log.debug("Request to get TagType : {}", id);
        return tagTypeRepository.findById(id);
    }

    /**
     * Delete the tagType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TagType : {}", id);
        tagTypeRepository.deleteById(id);
    }
}
