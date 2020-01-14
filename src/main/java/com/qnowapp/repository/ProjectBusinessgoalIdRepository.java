package com.qnowapp.repository;

import com.qnowapp.domain.ProjectBusinessgoalId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectBusinessgoalId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectBusinessgoalIdRepository extends JpaRepository<ProjectBusinessgoalId, Long>, JpaSpecificationExecutor<ProjectBusinessgoalId> {

}
