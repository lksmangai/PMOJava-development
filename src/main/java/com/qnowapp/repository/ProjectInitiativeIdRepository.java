package com.qnowapp.repository;

import com.qnowapp.domain.ProjectInitiativeId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectInitiativeId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectInitiativeIdRepository extends JpaRepository<ProjectInitiativeId, Long>, JpaSpecificationExecutor<ProjectInitiativeId> {

}
