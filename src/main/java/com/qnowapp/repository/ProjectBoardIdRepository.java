package com.qnowapp.repository;

import com.qnowapp.domain.ProjectBoardId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectBoardId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectBoardIdRepository extends JpaRepository<ProjectBoardId, Long>, JpaSpecificationExecutor<ProjectBoardId> {

}
