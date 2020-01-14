package com.qnowapp.repository;

import com.qnowapp.domain.SkillExpertise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SkillExpertise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillExpertiseRepository extends JpaRepository<SkillExpertise, Long>, JpaSpecificationExecutor<SkillExpertise> {

}
