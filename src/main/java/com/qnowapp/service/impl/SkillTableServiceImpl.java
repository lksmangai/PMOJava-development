package com.qnowapp.service.impl;

import com.qnowapp.service.SkillTableService;
import com.qnowapp.domain.SkillTable;
import com.qnowapp.repository.SkillTableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SkillTable}.
 */
@Service
@Transactional
public class SkillTableServiceImpl implements SkillTableService {

    private final Logger log = LoggerFactory.getLogger(SkillTableServiceImpl.class);

    private final SkillTableRepository skillTableRepository;

    public SkillTableServiceImpl(SkillTableRepository skillTableRepository) {
        this.skillTableRepository = skillTableRepository;
    }

    /**
     * Save a skillTable.
     *
     * @param skillTable the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SkillTable save(SkillTable skillTable) {
        log.debug("Request to save SkillTable : {}", skillTable);
        return skillTableRepository.save(skillTable);
    }

    /**
     * Get all the skillTables.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SkillTable> findAll() {
        log.debug("Request to get all SkillTables");
        return skillTableRepository.findAll();
    }


    /**
     * Get one skillTable by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SkillTable> findOne(Long id) {
        log.debug("Request to get SkillTable : {}", id);
        return skillTableRepository.findById(id);
    }

    /**
     * Delete the skillTable by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SkillTable : {}", id);
        skillTableRepository.deleteById(id);
    }
}
