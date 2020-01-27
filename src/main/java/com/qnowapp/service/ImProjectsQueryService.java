package com.qnowapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.qnowapp.domain.ImProjects;
import com.qnowapp.domain.*; // for static metamodels
import com.qnowapp.repository.ImProjectsRepository;
import com.qnowapp.service.dto.ImProjectsCriteria;

/**
 * Service for executing complex queries for {@link ImProjects} entities in the database.
 * The main input is a {@link ImProjectsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ImProjects} or a {@link Page} of {@link ImProjects} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImProjectsQueryService extends QueryService<ImProjects> {

    private final Logger log = LoggerFactory.getLogger(ImProjectsQueryService.class);

    private final ImProjectsRepository imProjectsRepository;

    public ImProjectsQueryService(ImProjectsRepository imProjectsRepository) {
        this.imProjectsRepository = imProjectsRepository;
    }

    /**
     * Return a {@link List} of {@link ImProjects} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ImProjects> findByCriteria(ImProjectsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ImProjects> specification = createSpecification(criteria);
        return imProjectsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ImProjects} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ImProjects> findByCriteria(ImProjectsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ImProjects> specification = createSpecification(criteria);
        return imProjectsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImProjectsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ImProjects> specification = createSpecification(criteria);
        return imProjectsRepository.count(specification);
    }

    /**
     * Function to convert ImProjectsCriteria to a {@link Specification}.
     */
    private Specification<ImProjects> createSpecification(ImProjectsCriteria criteria) {
        Specification<ImProjects> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ImProjects_.id));
            }
            if (criteria.getProjectName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectName(), ImProjects_.projectName));
            }
            if (criteria.getProjectNr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectNr(), ImProjects_.projectNr));
            }
            if (criteria.getProjectPath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectPath(), ImProjects_.projectPath));
            }
            if (criteria.getTreeSortkey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTreeSortkey(), ImProjects_.treeSortkey));
            }
            if (criteria.getMaxChildSortkey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaxChildSortkey(), ImProjects_.maxChildSortkey));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ImProjects_.description));
            }
            if (criteria.getBillingTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBillingTypeId(), ImProjects_.billingTypeId));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), ImProjects_.note));
            }
            if (criteria.getRequiresReportP() != null) {
                specification = specification.and(buildSpecification(criteria.getRequiresReportP(), ImProjects_.requiresReportP));
            }
            if (criteria.getProjectBudget() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectBudget(), ImProjects_.projectBudget));
            }
            if (criteria.getProjectRisk() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectRisk(), ImProjects_.projectRisk));
            }
            if (criteria.getCorporateSponsor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCorporateSponsor(), ImProjects_.corporateSponsor));
            }
            if (criteria.getPercentCompleted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentCompleted(), ImProjects_.percentCompleted));
            }
            if (criteria.getProjectBudgetHours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectBudgetHours(), ImProjects_.projectBudgetHours));
            }
            if (criteria.getCostQuotesCache() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostQuotesCache(), ImProjects_.costQuotesCache));
            }
            if (criteria.getCostInvoicesCache() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostInvoicesCache(), ImProjects_.costInvoicesCache));
            }
            if (criteria.getCostTimesheetPlannedCache() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostTimesheetPlannedCache(), ImProjects_.costTimesheetPlannedCache));
            }
            if (criteria.getCostPurchaseOrdersCache() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostPurchaseOrdersCache(), ImProjects_.costPurchaseOrdersCache));
            }
            if (criteria.getCostBillsCache() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostBillsCache(), ImProjects_.costBillsCache));
            }
            if (criteria.getCostTimesheetLoggedCache() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostTimesheetLoggedCache(), ImProjects_.costTimesheetLoggedCache));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), ImProjects_.endDate));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), ImProjects_.startDate));
            }
            if (criteria.getTemplateP() != null) {
                specification = specification.and(buildSpecification(criteria.getTemplateP(), ImProjects_.templateP));
            }
            if (criteria.getSortOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortOrder(), ImProjects_.sortOrder));
            }
            if (criteria.getReportedHoursCache() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReportedHoursCache(), ImProjects_.reportedHoursCache));
            }
            if (criteria.getCostExpensePlannedCache() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostExpensePlannedCache(), ImProjects_.costExpensePlannedCache));
            }
            if (criteria.getCostExpenseLoggedCache() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostExpenseLoggedCache(), ImProjects_.costExpenseLoggedCache));
            }
            if (criteria.getConfirmDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConfirmDate(), ImProjects_.confirmDate));
            }
            if (criteria.getCostDeliveryNotesCache() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostDeliveryNotesCache(), ImProjects_.costDeliveryNotesCache));
            }
            if (criteria.getCostCacheDirty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostCacheDirty(), ImProjects_.costCacheDirty));
            }
            if (criteria.getMilestoneP() != null) {
                specification = specification.and(buildSpecification(criteria.getMilestoneP(), ImProjects_.milestoneP));
            }
            if (criteria.getReleaseItemP() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReleaseItemP(), ImProjects_.releaseItemP));
            }
            if (criteria.getPresalesProbability() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPresalesProbability(), ImProjects_.presalesProbability));
            }
            if (criteria.getPresalesValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPresalesValue(), ImProjects_.presalesValue));
            }
            if (criteria.getReportedDaysCache() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReportedDaysCache(), ImProjects_.reportedDaysCache));
            }
            if (criteria.getPresalesValueCurrency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPresalesValueCurrency(), ImProjects_.presalesValueCurrency));
            }
            if (criteria.getOpportunitySalesStageId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOpportunitySalesStageId(), ImProjects_.opportunitySalesStageId));
            }
            if (criteria.getOpportunityCampaignId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOpportunityCampaignId(), ImProjects_.opportunityCampaignId));
            }
            if (criteria.getScoreRevenue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreRevenue(), ImProjects_.scoreRevenue));
            }
            if (criteria.getScoreStrategic() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreStrategic(), ImProjects_.scoreStrategic));
            }
            if (criteria.getScoreFinanceNpv() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreFinanceNpv(), ImProjects_.scoreFinanceNpv));
            }
            if (criteria.getScoreCustomers() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreCustomers(), ImProjects_.scoreCustomers));
            }
            if (criteria.getScoreFinanceCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreFinanceCost(), ImProjects_.scoreFinanceCost));
            }
            if (criteria.getCostBillsPlanned() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostBillsPlanned(), ImProjects_.costBillsPlanned));
            }
            if (criteria.getCostExpensesPlanned() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostExpensesPlanned(), ImProjects_.costExpensesPlanned));
            }
            if (criteria.getScoreRisk() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreRisk(), ImProjects_.scoreRisk));
            }
            if (criteria.getScoreCapabilities() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreCapabilities(), ImProjects_.scoreCapabilities));
            }
            if (criteria.getScoreEinanceRoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreEinanceRoi(), ImProjects_.scoreEinanceRoi));
            }
            if (criteria.getProjectUserwiseBoard() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectUserwiseBoard(), ImProjects_.projectUserwiseBoard));
            }
            if (criteria.getProjectBringNextday() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectBringNextday(), ImProjects_.projectBringNextday));
            }
            if (criteria.getProjectBringSameboard() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectBringSameboard(), ImProjects_.projectBringSameboard));
            }
            if (criteria.getProjectNewboardEverytime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectNewboardEverytime(), ImProjects_.projectNewboardEverytime));
            }
            if (criteria.getProjectUserwiseBoard2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectUserwiseBoard2(), ImProjects_.projectUserwiseBoard2));
            }
            if (criteria.getProjectBringSameboard2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectBringSameboard2(), ImProjects_.projectBringSameboard2));
            }
            if (criteria.getProjectNewboard2Everytime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProjectNewboard2Everytime(), ImProjects_.projectNewboard2Everytime));
            }
            if (criteria.getProjectNewboard2Always() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectNewboard2Always(), ImProjects_.projectNewboard2Always));
            }
            if (criteria.getProjectReportWeekly() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectReportWeekly(), ImProjects_.projectReportWeekly));
            }
            if (criteria.getScoreGain() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreGain(), ImProjects_.scoreGain));
            }
            if (criteria.getScoreLoss() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreLoss(), ImProjects_.scoreLoss));
            }
            if (criteria.getScoreDelivery() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreDelivery(), ImProjects_.scoreDelivery));
            }
            if (criteria.getScoreOperations() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreOperations(), ImProjects_.scoreOperations));
            }
            if (criteria.getScoreWhy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScoreWhy(), ImProjects_.scoreWhy));
            }
            if (criteria.getJavaServices() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJavaServices(), ImProjects_.javaServices));
            }
            if (criteria.getNetServices() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNetServices(), ImProjects_.netServices));
            }
            if (criteria.getCollectionLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCollectionLink(), ImProjects_.collectionLink));
            }
            if (criteria.getTrainingLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTrainingLink(), ImProjects_.trainingLink));
            }
            if (criteria.getCollectionName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCollectionName(), ImProjects_.collectionName));
            }
            if (criteria.getTrainingName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTrainingName(), ImProjects_.trainingName));
            }
            if (criteria.getTrainingDoc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTrainingDoc(), ImProjects_.trainingDoc));
            }
            if (criteria.getTestingRichtext() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTestingRichtext(), ImProjects_.testingRichtext));
            }
            if (criteria.getTemplateCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTemplateCategory(), ImProjects_.templateCategory));
            }
            if (criteria.getdType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getdType(), ImProjects_.dType));
            }
            if (criteria.getdOption() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getdOption(), ImProjects_.dOption));
            }
            if (criteria.getdFilter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getdFilter(), ImProjects_.dFilter));
            }
            if (criteria.getdId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getdId(), ImProjects_.dId));
            }
            if (criteria.gettType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.gettType(), ImProjects_.tType));
            }
            if (criteria.gettOption() != null) {
                specification = specification.and(buildRangeSpecification(criteria.gettOption(), ImProjects_.tOption));
            }
            if (criteria.gettFilter() != null) {
                specification = specification.and(buildRangeSpecification(criteria.gettFilter(), ImProjects_.tFilter));
            }
            if (criteria.gettId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.gettId(), ImProjects_.tId));
            }
            if (criteria.getRisktype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRisktype(), ImProjects_.risktype));
            }
            if (criteria.getRiskimpact() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRiskimpact(), ImProjects_.riskimpact));
            }
            if (criteria.getRiskprobability() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRiskprobability(), ImProjects_.riskprobability));
            }
            if (criteria.getProjectInitiativeIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectInitiativeIdId(),
                    root -> root.join(ImProjects_.projectInitiativeId, JoinType.LEFT).get(ProjectInitiativeId_.id)));
            }
            if (criteria.getProjectBusinessgoalIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectBusinessgoalIdId(),
                    root -> root.join(ImProjects_.projectBusinessgoalId, JoinType.LEFT).get(ProjectBusinessgoalId_.id)));
            }
            if (criteria.getProjectSubgoalIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectSubgoalIdId(),
                    root -> root.join(ImProjects_.projectSubgoalId, JoinType.LEFT).get(ProjectSubgoalId_.id)));
            }
            if (criteria.getProjectMaingoalIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectMaingoalIdId(),
                    root -> root.join(ImProjects_.projectMaingoalId, JoinType.LEFT).get(ProjectMaingoalId_.id)));
            }
            if (criteria.getProjectBucketIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectBucketIdId(),
                    root -> root.join(ImProjects_.projectBucketId, JoinType.LEFT).get(ProjectBucketId_.id)));
            }
            if (criteria.getProjectCostCenterIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectCostCenterIdId(),
                    root -> root.join(ImProjects_.projectCostCenterId, JoinType.LEFT).get(ProjectCostCenterId_.id)));
            }
            if (criteria.getOpportunityPriorityIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getOpportunityPriorityIdId(),
                    root -> root.join(ImProjects_.opportunityPriorityId, JoinType.LEFT).get(OpportunityPriorityId_.id)));
            }
            if (criteria.getBacklogPracticeId() != null) {
                specification = specification.and(buildSpecification(criteria.getBacklogPracticeId(),
                    root -> root.join(ImProjects_.backlogPractice, JoinType.LEFT).get(BacklogPractice_.id)));
            }
            if (criteria.getProjectThemeId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectThemeId(),
                    root -> root.join(ImProjects_.projectTheme, JoinType.LEFT).get(ProjectTheme_.id)));
            }
            if (criteria.getProjectClassId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectClassId(),
                    root -> root.join(ImProjects_.projectClass, JoinType.LEFT).get(ProjectClass_.id)));
            }
            if (criteria.getProjectVerticalId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectVerticalId(),
                    root -> root.join(ImProjects_.projectVertical, JoinType.LEFT).get(ProjectVertical_.id)));
            }
            if (criteria.getProjectBoardIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectBoardIdId(),
                    root -> root.join(ImProjects_.projectBoardId, JoinType.LEFT).get(ProjectBoardId_.id)));
            }
            if (criteria.getProjectBoard2IdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectBoard2IdId(),
                    root -> root.join(ImProjects_.projectBoard2Id, JoinType.LEFT).get(ProjectBoardId_.id)));
            }
            if (criteria.getProjectStatusIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectStatusIdId(),
                    root -> root.join(ImProjects_.projectStatusId, JoinType.LEFT).get(ProjectStatusId_.id)));
            }
            if (criteria.getProjectTypeIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectTypeIdId(),
                    root -> root.join(ImProjects_.projectTypeId, JoinType.LEFT).get(ProjectTypeId_.id)));
            }
            if (criteria.getProjectLeadIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectLeadIdId(),
                    root -> root.join(ImProjects_.projectLeadId, JoinType.LEFT).get(ImEmployee_.id)));
            }
            if (criteria.getParentIdId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentIdId(),
                    root -> root.join(ImProjects_.parentId, JoinType.LEFT).get(ImProjects_.id)));
            }
        }
        return specification;
    }
}
