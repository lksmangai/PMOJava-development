package com.qnowapp.repository;

import com.qnowapp.domain.ProjectStatusId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectStatusId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectStatusIdRepository extends JpaRepository<ProjectStatusId, Long>, JpaSpecificationExecutor<ProjectStatusId> {

}
