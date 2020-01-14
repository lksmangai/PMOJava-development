package com.qnowapp.repository;

import com.qnowapp.domain.ProjectTheme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProjectTheme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectThemeRepository extends JpaRepository<ProjectTheme, Long>, JpaSpecificationExecutor<ProjectTheme> {

}
