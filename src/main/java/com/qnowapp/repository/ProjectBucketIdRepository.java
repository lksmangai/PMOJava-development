package com.qnowapp.repository;

import com.qnowapp.domain.ProjectBucketId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectBucketId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectBucketIdRepository extends JpaRepository<ProjectBucketId, Long>, JpaSpecificationExecutor<ProjectBucketId> {

}
