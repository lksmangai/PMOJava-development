package com.qnowapp.repository;

import com.qnowapp.domain.ProjectClass;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectClassRepository extends JpaRepository<ProjectClass, Long>, JpaSpecificationExecutor<ProjectClass> {

}
