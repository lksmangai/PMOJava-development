package com.qnowapp.repository;

import com.qnowapp.domain.ImEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ImEmployee entity.
 */
@Repository
public interface ImEmployeeRepository extends JpaRepository<ImEmployee, Long>, JpaSpecificationExecutor<ImEmployee> {

    @Query(value = "select distinct imEmployee from ImEmployee imEmployee left join fetch imEmployee.userContacts",
        countQuery = "select count(distinct imEmployee) from ImEmployee imEmployee")
    Page<ImEmployee> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct imEmployee from ImEmployee imEmployee left join fetch imEmployee.userContacts")
    List<ImEmployee> findAllWithEagerRelationships();

    @Query("select imEmployee from ImEmployee imEmployee left join fetch imEmployee.userContacts where imEmployee.id =:id")
    Optional<ImEmployee> findOneWithEagerRelationships(@Param("id") Long id);

}
