package com.qnowapp.repository;

import com.qnowapp.domain.ProjectCostCenterId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectCostCenterId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectCostCenterIdRepository extends JpaRepository<ProjectCostCenterId, Long>, JpaSpecificationExecutor<ProjectCostCenterId> {

}
