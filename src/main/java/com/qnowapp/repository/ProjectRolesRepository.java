package com.qnowapp.repository;

import com.qnowapp.domain.ProjectRoles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectRoles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRolesRepository extends JpaRepository<ProjectRoles, Long>, JpaSpecificationExecutor<ProjectRoles> {

}
