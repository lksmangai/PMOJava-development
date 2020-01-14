package com.qnowapp.repository;

import com.qnowapp.domain.ProjectVertical;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectVertical entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectVerticalRepository extends JpaRepository<ProjectVertical, Long>, JpaSpecificationExecutor<ProjectVertical> {

}
