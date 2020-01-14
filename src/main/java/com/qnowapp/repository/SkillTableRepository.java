package com.qnowapp.repository;

import com.qnowapp.domain.SkillTable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SkillTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillTableRepository extends JpaRepository<SkillTable, Long>, JpaSpecificationExecutor<SkillTable> {

}
