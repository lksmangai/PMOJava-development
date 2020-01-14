package com.qnowapp.repository;

import com.qnowapp.domain.Skills;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Skills entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long>, JpaSpecificationExecutor<Skills> {

}
