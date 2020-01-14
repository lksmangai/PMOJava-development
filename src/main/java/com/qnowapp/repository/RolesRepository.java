package com.qnowapp.repository;

import com.qnowapp.domain.Roles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Roles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RolesRepository extends JpaRepository<Roles, Long>, JpaSpecificationExecutor<Roles> {

}
