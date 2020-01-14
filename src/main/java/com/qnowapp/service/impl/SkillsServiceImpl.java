package com.qnowapp.service.impl;

import com.qnowapp.service.SkillsService;
import com.qnowapp.domain.Skills;
import com.qnowapp.repository.SkillsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Skills}.
 */
@Service
@Transactional
public class SkillsServiceImpl implements SkillsService {

    private final Logger log = LoggerFactory.getLogger(SkillsServiceImpl.class);

    private final SkillsRepository skillsRepository;

    public SkillsServiceImpl(SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    /**
     * Save a skills.
     *
     * @param skills the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Skills save(Skills skills) {
        log.debug("Request to save Skills : {}", skills);
        return skillsRepository.save(skills);
    }

    /**
     * Get all the skills.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Skills> findAll() {
        log.debug("Request to get all Skills");
        return skillsRepository.findAll();
    }


    /**
     * Get one skills by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Skills> findOne(Long id) {
        log.debug("Request to get Skills : {}", id);
        return skillsRepository.findById(id);
    }

    /**
     * Delete the skills by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Skills : {}", id);
        skillsRepository.deleteById(id);
    }
}
