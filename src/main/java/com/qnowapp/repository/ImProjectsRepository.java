package com.qnowapp.repository;

import com.qnowapp.domain.ImProjects;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ImProjects entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImProjectsRepository extends JpaRepository<ImProjects, Long>, JpaSpecificationExecutor<ImProjects> {

}
