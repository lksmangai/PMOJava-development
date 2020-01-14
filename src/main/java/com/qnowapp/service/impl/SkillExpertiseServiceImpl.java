package com.qnowapp.service.impl;

import com.qnowapp.service.SkillExpertiseService;
import com.qnowapp.domain.SkillExpertise;
import com.qnowapp.repository.SkillExpertiseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link SkillExpertise}.
 */
@Service
@Transactional
public class SkillExpertiseServiceImpl implements SkillExpertiseService {

    private final Logger log = LoggerFactory.getLogger(SkillExpertiseServiceImpl.class);

    private final SkillExpertiseRepository skillExpertiseRepository;

    public SkillExpertiseServiceImpl(SkillExpertiseRepository skillExpertiseRepository) {
        this.skillExpertiseRepository = skillExpertiseRepository;
    }

    /**
     * Save a skillExpertise.
     *
     * @param skillExpertise the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SkillExpertise save(SkillExpertise skillExpertise) {
        log.debug("Request to save SkillExpertise : {}", skillExpertise);
        return skillExpertiseRepository.save(skillExpertise);
    }

    /**
     * Get all the skillExpertises.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SkillExpertise> findAll() {
        log.debug("Request to get all SkillExpertises");
        return skillExpertiseRepository.findAll();
    }


    /**
     * Get one skillExpertise by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SkillExpertise> findOne(Long id) {
        log.debug("Request to get SkillExpertise : {}", id);
        return skillExpertiseRepository.findById(id);
    }

    /**
     * Delete the skillExpertise by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SkillExpertise : {}", id);
        skillExpertiseRepository.deleteById(id);
    }
}
