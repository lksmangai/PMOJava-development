package com.qnowapp.repository;

import com.qnowapp.domain.GroupMembers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GroupMembers entity.
 */
@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers, Long>, JpaSpecificationExecutor<GroupMembers> {

    @Query(value = "select distinct groupMembers from GroupMembers groupMembers left join fetch groupMembers.roles",
        countQuery = "select count(distinct groupMembers) from GroupMembers groupMembers")
    Page<GroupMembers> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct groupMembers from GroupMembers groupMembers left join fetch groupMembers.roles")
    List<GroupMembers> findAllWithEagerRelationships();

    @Query("select groupMembers from GroupMembers groupMembers left join fetch groupMembers.roles where groupMembers.id =:id")
    Optional<GroupMembers> findOneWithEagerRelationships(@Param("id") Long id);

}
