package com.qnowapp.repository;

import com.qnowapp.domain.ProjectTag;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTagRepository extends JpaRepository<ProjectTag, Long>, JpaSpecificationExecutor<ProjectTag> {

}
