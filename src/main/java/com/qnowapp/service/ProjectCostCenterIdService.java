package com.qnowapp.service;

import com.qnowapp.domain.ProjectCostCenterId;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProjectCostCenterId}.
 */
public interface ProjectCostCenterIdService {

    /**
     * Save a projectCostCenterId.
     *
     * @param projectCostCenterId the entity to save.
     * @return the persisted entity.
     */
    ProjectCostCenterId save(ProjectCostCenterId projectCostCenterId);

    /**
     * Get all the projectCostCenterIds.
     *
     * @return the list of entities.
     */
    List<ProjectCostCenterId> findAll();


    /**
     * Get the "id" projectCostCenterId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectCostCenterId> findOne(Long id);

    /**
     * Delete the "id" projectCostCenterId.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
