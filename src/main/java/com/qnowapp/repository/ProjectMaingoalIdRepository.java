package com.qnowapp.repository;

import com.qnowapp.domain.ProjectMaingoalId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectMaingoalId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectMaingoalIdRepository extends JpaRepository<ProjectMaingoalId, Long>, JpaSpecificationExecutor<ProjectMaingoalId> {

}
