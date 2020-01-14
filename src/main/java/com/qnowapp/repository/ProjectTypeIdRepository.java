package com.qnowapp.repository;

import com.qnowapp.domain.ProjectTypeId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectTypeId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTypeIdRepository extends JpaRepository<ProjectTypeId, Long>, JpaSpecificationExecutor<ProjectTypeId> {

}
