package com.qnowapp.repository;

import com.qnowapp.domain.ProjectSubgoalId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectSubgoalId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectSubgoalIdRepository extends JpaRepository<ProjectSubgoalId, Long>, JpaSpecificationExecutor<ProjectSubgoalId> {

}
