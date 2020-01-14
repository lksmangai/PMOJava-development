package com.qnowapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.qnowapp.domain.ImProjects} entity. This class is used
 * in {@link com.qnowapp.web.rest.ImProjectsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /im-projects?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ImProjectsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter projectName;

    private StringFilter projectNr;

    private StringFilter projectPath;

    private StringFilter treeSortkey;

    private StringFilter maxChildSortkey;

    private StringFilter description;

    private IntegerFilter billingTypeId;

    private StringFilter note;

    private BooleanFilter requiresReportP;

    private DoubleFilter projectBudget;

    private StringFilter projectRisk;

    private StringFilter corporateSponsor;

    private DoubleFilter percentCompleted;

    private DoubleFilter projectBudgetHours;

    private BigDecimalFilter costQuotesCache;

    private IntegerFilter costInvoicesCache;

    private IntegerFilter costTimesheetPlannedCache;

    private IntegerFilter costPurchaseOrdersCache;

    private IntegerFilter costBillsCache;

    private IntegerFilter costTimesheetLoggedCache;

    private ZonedDateTimeFilter endDate;

    private ZonedDateTimeFilter startDate;

    private BooleanFilter templateP;

    private IntegerFilter sortOrder;

    private DoubleFilter reportedHoursCache;

    private IntegerFilter costExpensePlannedCache;

    private IntegerFilter costExpenseLoggedCache;

    private InstantFilter confirmDate;

    private BigDecimalFilter costDeliveryNotesCache;

    private ZonedDateTimeFilter costCacheDirty;

    private BooleanFilter milestoneP;

    private StringFilter releaseItemP;

    private BigDecimalFilter presalesProbability;

    private BigDecimalFilter presalesValue;

    private BigDecimalFilter reportedDaysCache;

    private StringFilter presalesValueCurrency;

    private IntegerFilter opportunitySalesStageId;

    private IntegerFilter opportunityCampaignId;

    private BigDecimalFilter scoreRevenue;

    private BigDecimalFilter scoreStrategic;

    private BigDecimalFilter scoreFinanceNpv;

    private BigDecimalFilter scoreCustomers;

    private BigDecimalFilter scoreFinanceCost;

    private BigDecimalFilter costBillsPlanned;

    private BigDecimalFilter costExpensesPlanned;

    private BigDecimalFilter scoreRisk;

    private BigDecimalFilter scoreCapabilities;

    private BigDecimalFilter scoreEinanceRoi;

    private StringFilter projectUserwiseBoard;

    private IntegerFilter projectBringNextday;

    private StringFilter projectBringSameboard;

    private StringFilter projectNewboardEverytime;

    private StringFilter projectUserwiseBoard2;

    private StringFilter projectBringSameboard2;

    private IntegerFilter projectNewboard2Everytime;

    private StringFilter projectNewboard2Always;

    private StringFilter projectReportWeekly;

    private DoubleFilter scoreGain;

    private DoubleFilter scoreLoss;

    private DoubleFilter scoreDelivery;

    private DoubleFilter scoreOperations;

    private IntegerFilter scoreWhy;

    private StringFilter javaServices;

    private StringFilter netServices;

    private StringFilter collectionLink;

    private StringFilter trainingLink;

    private StringFilter collectionName;

    private StringFilter trainingName;

    private StringFilter trainingDoc;

    private IntegerFilter testingRichtext;

    private StringFilter templateCategory;

    private IntegerFilter dType;

    private IntegerFilter dOption;

    private IntegerFilter dFilter;

    private IntegerFilter dId;

    private IntegerFilter tType;

    private IntegerFilter tOption;

    private IntegerFilter tFilter;

    private IntegerFilter tId;

    private StringFilter risktype;

    private DoubleFilter riskimpact;

    private DoubleFilter riskprobability;

    private LongFilter projectInitiativeIdId;

    private LongFilter projectBusinessgoalIdId;

    private LongFilter projectSubgoalIdId;

    private LongFilter projectMaingoalIdId;

    private LongFilter projectBucketIdId;

    private LongFilter projectCostCenterIdId;

    private LongFilter opportunityPriorityIdId;

    private LongFilter backlogPracticeId;

    private LongFilter projectThemeId;

    private LongFilter projectClassId;

    private LongFilter projectVerticalId;

    private LongFilter projectBoardIdId;

    private LongFilter projectBoard2IdId;

    private LongFilter projectStatusIdId;

    private LongFilter projectTypeIdId;

    private LongFilter projectLeadIdId;

    private LongFilter parentIdId;

    public ImProjectsCriteria(){
    }

    public ImProjectsCriteria(ImProjectsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.projectName = other.projectName == null ? null : other.projectName.copy();
        this.projectNr = other.projectNr == null ? null : other.projectNr.copy();
        this.projectPath = other.projectPath == null ? null : other.projectPath.copy();
        this.treeSortkey = other.treeSortkey == null ? null : other.treeSortkey.copy();
        this.maxChildSortkey = other.maxChildSortkey == null ? null : other.maxChildSortkey.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.billingTypeId = other.billingTypeId == null ? null : other.billingTypeId.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.requiresReportP = other.requiresReportP == null ? null : other.requiresReportP.copy();
        this.projectBudget = other.projectBudget == null ? null : other.projectBudget.copy();
        this.projectRisk = other.projectRisk == null ? null : other.projectRisk.copy();
        this.corporateSponsor = other.corporateSponsor == null ? null : other.corporateSponsor.copy();
        this.percentCompleted = other.percentCompleted == null ? null : other.percentCompleted.copy();
        this.projectBudgetHours = other.projectBudgetHours == null ? null : other.projectBudgetHours.copy();
        this.costQuotesCache = other.costQuotesCache == null ? null : other.costQuotesCache.copy();
        this.costInvoicesCache = other.costInvoicesCache == null ? null : other.costInvoicesCache.copy();
        this.costTimesheetPlannedCache = other.costTimesheetPlannedCache == null ? null : other.costTimesheetPlannedCache.copy();
        this.costPurchaseOrdersCache = other.costPurchaseOrdersCache == null ? null : other.costPurchaseOrdersCache.copy();
        this.costBillsCache = other.costBillsCache == null ? null : other.costBillsCache.copy();
        this.costTimesheetLoggedCache = other.costTimesheetLoggedCache == null ? null : other.costTimesheetLoggedCache.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.templateP = other.templateP == null ? null : other.templateP.copy();
        this.sortOrder = other.sortOrder == null ? null : other.sortOrder.copy();
        this.reportedHoursCache = other.reportedHoursCache == null ? null : other.reportedHoursCache.copy();
        this.costExpensePlannedCache = other.costExpensePlannedCache == null ? null : other.costExpensePlannedCache.copy();
        this.costExpenseLoggedCache = other.costExpenseLoggedCache == null ? null : other.costExpenseLoggedCache.copy();
        this.confirmDate = other.confirmDate == null ? null : other.confirmDate.copy();
        this.costDeliveryNotesCache = other.costDeliveryNotesCache == null ? null : other.costDeliveryNotesCache.copy();
        this.costCacheDirty = other.costCacheDirty == null ? null : other.costCacheDirty.copy();
        this.milestoneP = other.milestoneP == null ? null : other.milestoneP.copy();
        this.releaseItemP = other.releaseItemP == null ? null : other.releaseItemP.copy();
        this.presalesProbability = other.presalesProbability == null ? null : other.presalesProbability.copy();
        this.presalesValue = other.presalesValue == null ? null : other.presalesValue.copy();
        this.reportedDaysCache = other.reportedDaysCache == null ? null : other.reportedDaysCache.copy();
        this.presalesValueCurrency = other.presalesValueCurrency == null ? null : other.presalesValueCurrency.copy();
        this.opportunitySalesStageId = other.opportunitySalesStageId == null ? null : other.opportunitySalesStageId.copy();
        this.opportunityCampaignId = other.opportunityCampaignId == null ? null : other.opportunityCampaignId.copy();
        this.scoreRevenue = other.scoreRevenue == null ? null : other.scoreRevenue.copy();
        this.scoreStrategic = other.scoreStrategic == null ? null : other.scoreStrategic.copy();
        this.scoreFinanceNpv = other.scoreFinanceNpv == null ? null : other.scoreFinanceNpv.copy();
        this.scoreCustomers = other.scoreCustomers == null ? null : other.scoreCustomers.copy();
        this.scoreFinanceCost = other.scoreFinanceCost == null ? null : other.scoreFinanceCost.copy();
        this.costBillsPlanned = other.costBillsPlanned == null ? null : other.costBillsPlanned.copy();
        this.costExpensesPlanned = other.costExpensesPlanned == null ? null : other.costExpensesPlanned.copy();
        this.scoreRisk = other.scoreRisk == null ? null : other.scoreRisk.copy();
        this.scoreCapabilities = other.scoreCapabilities == null ? null : other.scoreCapabilities.copy();
        this.scoreEinanceRoi = other.scoreEinanceRoi == null ? null : other.scoreEinanceRoi.copy();
        this.projectUserwiseBoard = other.projectUserwiseBoard == null ? null : other.projectUserwiseBoard.copy();
        this.projectBringNextday = other.projectBringNextday == null ? null : other.projectBringNextday.copy();
        this.projectBringSameboard = other.projectBringSameboard == null ? null : other.projectBringSameboard.copy();
        this.projectNewboardEverytime = other.projectNewboardEverytime == null ? null : other.projectNewboardEverytime.copy();
        this.projectUserwiseBoard2 = other.projectUserwiseBoard2 == null ? null : other.projectUserwiseBoard2.copy();
        this.projectBringSameboard2 = other.projectBringSameboard2 == null ? null : other.projectBringSameboard2.copy();
        this.projectNewboard2Everytime = other.projectNewboard2Everytime == null ? null : other.projectNewboard2Everytime.copy();
        this.projectNewboard2Always = other.projectNewboard2Always == null ? null : other.projectNewboard2Always.copy();
        this.projectReportWeekly = other.projectReportWeekly == null ? null : other.projectReportWeekly.copy();
        this.scoreGain = other.scoreGain == null ? null : other.scoreGain.copy();
        this.scoreLoss = other.scoreLoss == null ? null : other.scoreLoss.copy();
        this.scoreDelivery = other.scoreDelivery == null ? null : other.scoreDelivery.copy();
        this.scoreOperations = other.scoreOperations == null ? null : other.scoreOperations.copy();
        this.scoreWhy = other.scoreWhy == null ? null : other.scoreWhy.copy();
        this.javaServices = other.javaServices == null ? null : other.javaServices.copy();
        this.netServices = other.netServices == null ? null : other.netServices.copy();
        this.collectionLink = other.collectionLink == null ? null : other.collectionLink.copy();
        this.trainingLink = other.trainingLink == null ? null : other.trainingLink.copy();
        this.collectionName = other.collectionName == null ? null : other.collectionName.copy();
        this.trainingName = other.trainingName == null ? null : other.trainingName.copy();
        this.trainingDoc = other.trainingDoc == null ? null : other.trainingDoc.copy();
        this.testingRichtext = other.testingRichtext == null ? null : other.testingRichtext.copy();
        this.templateCategory = other.templateCategory == null ? null : other.templateCategory.copy();
        this.dType = other.dType == null ? null : other.dType.copy();
        this.dOption = other.dOption == null ? null : other.dOption.copy();
        this.dFilter = other.dFilter == null ? null : other.dFilter.copy();
        this.dId = other.dId == null ? null : other.dId.copy();
        this.tType = other.tType == null ? null : other.tType.copy();
        this.tOption = other.tOption == null ? null : other.tOption.copy();
        this.tFilter = other.tFilter == null ? null : other.tFilter.copy();
        this.tId = other.tId == null ? null : other.tId.copy();
        this.risktype = other.risktype == null ? null : other.risktype.copy();
        this.riskimpact = other.riskimpact == null ? null : other.riskimpact.copy();
        this.riskprobability = other.riskprobability == null ? null : other.riskprobability.copy();
        this.projectInitiativeIdId = other.projectInitiativeIdId == null ? null : other.projectInitiativeIdId.copy();
        this.projectBusinessgoalIdId = other.projectBusinessgoalIdId == null ? null : other.projectBusinessgoalIdId.copy();
        this.projectSubgoalIdId = other.projectSubgoalIdId == null ? null : other.projectSubgoalIdId.copy();
        this.projectMaingoalIdId = other.projectMaingoalIdId == null ? null : other.projectMaingoalIdId.copy();
        this.projectBucketIdId = other.projectBucketIdId == null ? null : other.projectBucketIdId.copy();
        this.projectCostCenterIdId = other.projectCostCenterIdId == null ? null : other.projectCostCenterIdId.copy();
        this.opportunityPriorityIdId = other.opportunityPriorityIdId == null ? null : other.opportunityPriorityIdId.copy();
        this.backlogPracticeId = other.backlogPracticeId == null ? null : other.backlogPracticeId.copy();
        this.projectThemeId = other.projectThemeId == null ? null : other.projectThemeId.copy();
        this.projectClassId = other.projectClassId == null ? null : other.projectClassId.copy();
        this.projectVerticalId = other.projectVerticalId == null ? null : other.projectVerticalId.copy();
        this.projectBoardIdId = other.projectBoardIdId == null ? null : other.projectBoardIdId.copy();
        this.projectBoard2IdId = other.projectBoard2IdId == null ? null : other.projectBoard2IdId.copy();
        this.projectStatusIdId = other.projectStatusIdId == null ? null : other.projectStatusIdId.copy();
        this.projectTypeIdId = other.projectTypeIdId == null ? null : other.projectTypeIdId.copy();
        this.projectLeadIdId = other.projectLeadIdId == null ? null : other.projectLeadIdId.copy();
        this.parentIdId = other.parentIdId == null ? null : other.parentIdId.copy();
    }

    @Override
    public ImProjectsCriteria copy() {
        return new ImProjectsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getProjectName() {
        return projectName;
    }

    public void setProjectName(StringFilter projectName) {
        this.projectName = projectName;
    }

    public StringFilter getProjectNr() {
        return projectNr;
    }

    public void setProjectNr(StringFilter projectNr) {
        this.projectNr = projectNr;
    }

    public StringFilter getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(StringFilter projectPath) {
        this.projectPath = projectPath;
    }

    public StringFilter getTreeSortkey() {
        return treeSortkey;
    }

    public void setTreeSortkey(StringFilter treeSortkey) {
        this.treeSortkey = treeSortkey;
    }

    public StringFilter getMaxChildSortkey() {
        return maxChildSortkey;
    }

    public void setMaxChildSortkey(StringFilter maxChildSortkey) {
        this.maxChildSortkey = maxChildSortkey;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public IntegerFilter getBillingTypeId() {
        return billingTypeId;
    }

    public void setBillingTypeId(IntegerFilter billingTypeId) {
        this.billingTypeId = billingTypeId;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public BooleanFilter getRequiresReportP() {
        return requiresReportP;
    }

    public void setRequiresReportP(BooleanFilter requiresReportP) {
        this.requiresReportP = requiresReportP;
    }

    public DoubleFilter getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(DoubleFilter projectBudget) {
        this.projectBudget = projectBudget;
    }

    public StringFilter getProjectRisk() {
        return projectRisk;
    }

    public void setProjectRisk(StringFilter projectRisk) {
        this.projectRisk = projectRisk;
    }

    public StringFilter getCorporateSponsor() {
        return corporateSponsor;
    }

    public void setCorporateSponsor(StringFilter corporateSponsor) {
        this.corporateSponsor = corporateSponsor;
    }

    public DoubleFilter getPercentCompleted() {
        return percentCompleted;
    }

    public void setPercentCompleted(DoubleFilter percentCompleted) {
        this.percentCompleted = percentCompleted;
    }

    public DoubleFilter getProjectBudgetHours() {
        return projectBudgetHours;
    }

    public void setProjectBudgetHours(DoubleFilter projectBudgetHours) {
        this.projectBudgetHours = projectBudgetHours;
    }

    public BigDecimalFilter getCostQuotesCache() {
        return costQuotesCache;
    }

    public void setCostQuotesCache(BigDecimalFilter costQuotesCache) {
        this.costQuotesCache = costQuotesCache;
    }

    public IntegerFilter getCostInvoicesCache() {
        return costInvoicesCache;
    }

    public void setCostInvoicesCache(IntegerFilter costInvoicesCache) {
        this.costInvoicesCache = costInvoicesCache;
    }

    public IntegerFilter getCostTimesheetPlannedCache() {
        return costTimesheetPlannedCache;
    }

    public void setCostTimesheetPlannedCache(IntegerFilter costTimesheetPlannedCache) {
        this.costTimesheetPlannedCache = costTimesheetPlannedCache;
    }

    public IntegerFilter getCostPurchaseOrdersCache() {
        return costPurchaseOrdersCache;
    }

    public void setCostPurchaseOrdersCache(IntegerFilter costPurchaseOrdersCache) {
        this.costPurchaseOrdersCache = costPurchaseOrdersCache;
    }

    public IntegerFilter getCostBillsCache() {
        return costBillsCache;
    }

    public void setCostBillsCache(IntegerFilter costBillsCache) {
        this.costBillsCache = costBillsCache;
    }

    public IntegerFilter getCostTimesheetLoggedCache() {
        return costTimesheetLoggedCache;
    }

    public void setCostTimesheetLoggedCache(IntegerFilter costTimesheetLoggedCache) {
        this.costTimesheetLoggedCache = costTimesheetLoggedCache;
    }

    public ZonedDateTimeFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTimeFilter endDate) {
        this.endDate = endDate;
    }

    public ZonedDateTimeFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTimeFilter startDate) {
        this.startDate = startDate;
    }

    public BooleanFilter getTemplateP() {
        return templateP;
    }

    public void setTemplateP(BooleanFilter templateP) {
        this.templateP = templateP;
    }

    public IntegerFilter getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(IntegerFilter sortOrder) {
        this.sortOrder = sortOrder;
    }

    public DoubleFilter getReportedHoursCache() {
        return reportedHoursCache;
    }

    public void setReportedHoursCache(DoubleFilter reportedHoursCache) {
        this.reportedHoursCache = reportedHoursCache;
    }

    public IntegerFilter getCostExpensePlannedCache() {
        return costExpensePlannedCache;
    }

    public void setCostExpensePlannedCache(IntegerFilter costExpensePlannedCache) {
        this.costExpensePlannedCache = costExpensePlannedCache;
    }

    public IntegerFilter getCostExpenseLoggedCache() {
        return costExpenseLoggedCache;
    }

    public void setCostExpenseLoggedCache(IntegerFilter costExpenseLoggedCache) {
        this.costExpenseLoggedCache = costExpenseLoggedCache;
    }

    public InstantFilter getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(InstantFilter confirmDate) {
        this.confirmDate = confirmDate;
    }

    public BigDecimalFilter getCostDeliveryNotesCache() {
        return costDeliveryNotesCache;
    }

    public void setCostDeliveryNotesCache(BigDecimalFilter costDeliveryNotesCache) {
        this.costDeliveryNotesCache = costDeliveryNotesCache;
    }

    public ZonedDateTimeFilter getCostCacheDirty() {
        return costCacheDirty;
    }

    public void setCostCacheDirty(ZonedDateTimeFilter costCacheDirty) {
        this.costCacheDirty = costCacheDirty;
    }

    public BooleanFilter getMilestoneP() {
        return milestoneP;
    }

    public void setMilestoneP(BooleanFilter milestoneP) {
        this.milestoneP = milestoneP;
    }

    public StringFilter getReleaseItemP() {
        return releaseItemP;
    }

    public void setReleaseItemP(StringFilter releaseItemP) {
        this.releaseItemP = releaseItemP;
    }

    public BigDecimalFilter getPresalesProbability() {
        return presalesProbability;
    }

    public void setPresalesProbability(BigDecimalFilter presalesProbability) {
        this.presalesProbability = presalesProbability;
    }

    public BigDecimalFilter getPresalesValue() {
        return presalesValue;
    }

    public void setPresalesValue(BigDecimalFilter presalesValue) {
        this.presalesValue = presalesValue;
    }

    public BigDecimalFilter getReportedDaysCache() {
        return reportedDaysCache;
    }

    public void setReportedDaysCache(BigDecimalFilter reportedDaysCache) {
        this.reportedDaysCache = reportedDaysCache;
    }

    public StringFilter getPresalesValueCurrency() {
        return presalesValueCurrency;
    }

    public void setPresalesValueCurrency(StringFilter presalesValueCurrency) {
        this.presalesValueCurrency = presalesValueCurrency;
    }

    public IntegerFilter getOpportunitySalesStageId() {
        return opportunitySalesStageId;
    }

    public void setOpportunitySalesStageId(IntegerFilter opportunitySalesStageId) {
        this.opportunitySalesStageId = opportunitySalesStageId;
    }

    public IntegerFilter getOpportunityCampaignId() {
        return opportunityCampaignId;
    }

    public void setOpportunityCampaignId(IntegerFilter opportunityCampaignId) {
        this.opportunityCampaignId = opportunityCampaignId;
    }

    public BigDecimalFilter getScoreRevenue() {
        return scoreRevenue;
    }

    public void setScoreRevenue(BigDecimalFilter scoreRevenue) {
        this.scoreRevenue = scoreRevenue;
    }

    public BigDecimalFilter getScoreStrategic() {
        return scoreStrategic;
    }

    public void setScoreStrategic(BigDecimalFilter scoreStrategic) {
        this.scoreStrategic = scoreStrategic;
    }

    public BigDecimalFilter getScoreFinanceNpv() {
        return scoreFinanceNpv;
    }

    public void setScoreFinanceNpv(BigDecimalFilter scoreFinanceNpv) {
        this.scoreFinanceNpv = scoreFinanceNpv;
    }

    public BigDecimalFilter getScoreCustomers() {
        return scoreCustomers;
    }

    public void setScoreCustomers(BigDecimalFilter scoreCustomers) {
        this.scoreCustomers = scoreCustomers;
    }

    public BigDecimalFilter getScoreFinanceCost() {
        return scoreFinanceCost;
    }

    public void setScoreFinanceCost(BigDecimalFilter scoreFinanceCost) {
        this.scoreFinanceCost = scoreFinanceCost;
    }

    public BigDecimalFilter getCostBillsPlanned() {
        return costBillsPlanned;
    }

    public void setCostBillsPlanned(BigDecimalFilter costBillsPlanned) {
        this.costBillsPlanned = costBillsPlanned;
    }

    public BigDecimalFilter getCostExpensesPlanned() {
        return costExpensesPlanned;
    }

    public void setCostExpensesPlanned(BigDecimalFilter costExpensesPlanned) {
        this.costExpensesPlanned = costExpensesPlanned;
    }

    public BigDecimalFilter getScoreRisk() {
        return scoreRisk;
    }

    public void setScoreRisk(BigDecimalFilter scoreRisk) {
        this.scoreRisk = scoreRisk;
    }

    public BigDecimalFilter getScoreCapabilities() {
        return scoreCapabilities;
    }

    public void setScoreCapabilities(BigDecimalFilter scoreCapabilities) {
        this.scoreCapabilities = scoreCapabilities;
    }

    public BigDecimalFilter getScoreEinanceRoi() {
        return scoreEinanceRoi;
    }

    public void setScoreEinanceRoi(BigDecimalFilter scoreEinanceRoi) {
        this.scoreEinanceRoi = scoreEinanceRoi;
    }

    public StringFilter getProjectUserwiseBoard() {
        return projectUserwiseBoard;
    }

    public void setProjectUserwiseBoard(StringFilter projectUserwiseBoard) {
        this.projectUserwiseBoard = projectUserwiseBoard;
    }

    public IntegerFilter getProjectBringNextday() {
        return projectBringNextday;
    }

    public void setProjectBringNextday(IntegerFilter projectBringNextday) {
        this.projectBringNextday = projectBringNextday;
    }

    public StringFilter getProjectBringSameboard() {
        return projectBringSameboard;
    }

    public void setProjectBringSameboard(StringFilter projectBringSameboard) {
        this.projectBringSameboard = projectBringSameboard;
    }

    public StringFilter getProjectNewboardEverytime() {
        return projectNewboardEverytime;
    }

    public void setProjectNewboardEverytime(StringFilter projectNewboardEverytime) {
        this.projectNewboardEverytime = projectNewboardEverytime;
    }

    public StringFilter getProjectUserwiseBoard2() {
        return projectUserwiseBoard2;
    }

    public void setProjectUserwiseBoard2(StringFilter projectUserwiseBoard2) {
        this.projectUserwiseBoard2 = projectUserwiseBoard2;
    }

    public StringFilter getProjectBringSameboard2() {
        return projectBringSameboard2;
    }

    public void setProjectBringSameboard2(StringFilter projectBringSameboard2) {
        this.projectBringSameboard2 = projectBringSameboard2;
    }

    public IntegerFilter getProjectNewboard2Everytime() {
        return projectNewboard2Everytime;
    }

    public void setProjectNewboard2Everytime(IntegerFilter projectNewboard2Everytime) {
        this.projectNewboard2Everytime = projectNewboard2Everytime;
    }

    public StringFilter getProjectNewboard2Always() {
        return projectNewboard2Always;
    }

    public void setProjectNewboard2Always(StringFilter projectNewboard2Always) {
        this.projectNewboard2Always = projectNewboard2Always;
    }

    public StringFilter getProjectReportWeekly() {
        return projectReportWeekly;
    }

    public void setProjectReportWeekly(StringFilter projectReportWeekly) {
        this.projectReportWeekly = projectReportWeekly;
    }

    public DoubleFilter getScoreGain() {
        return scoreGain;
    }

    public void setScoreGain(DoubleFilter scoreGain) {
        this.scoreGain = scoreGain;
    }

    public DoubleFilter getScoreLoss() {
        return scoreLoss;
    }

    public void setScoreLoss(DoubleFilter scoreLoss) {
        this.scoreLoss = scoreLoss;
    }

    public DoubleFilter getScoreDelivery() {
        return scoreDelivery;
    }

    public void setScoreDelivery(DoubleFilter scoreDelivery) {
        this.scoreDelivery = scoreDelivery;
    }

    public DoubleFilter getScoreOperations() {
        return scoreOperations;
    }

    public void setScoreOperations(DoubleFilter scoreOperations) {
        this.scoreOperations = scoreOperations;
    }

    public IntegerFilter getScoreWhy() {
        return scoreWhy;
    }

    public void setScoreWhy(IntegerFilter scoreWhy) {
        this.scoreWhy = scoreWhy;
    }

    public StringFilter getJavaServices() {
        return javaServices;
    }

    public void setJavaServices(StringFilter javaServices) {
        this.javaServices = javaServices;
    }

    public StringFilter getNetServices() {
        return netServices;
    }

    public void setNetServices(StringFilter netServices) {
        this.netServices = netServices;
    }

    public StringFilter getCollectionLink() {
        return collectionLink;
    }

    public void setCollectionLink(StringFilter collectionLink) {
        this.collectionLink = collectionLink;
    }

    public StringFilter getTrainingLink() {
        return trainingLink;
    }

    public void setTrainingLink(StringFilter trainingLink) {
        this.trainingLink = trainingLink;
    }

    public StringFilter getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(StringFilter collectionName) {
        this.collectionName = collectionName;
    }

    public StringFilter getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(StringFilter trainingName) {
        this.trainingName = trainingName;
    }

    public StringFilter getTrainingDoc() {
        return trainingDoc;
    }

    public void setTrainingDoc(StringFilter trainingDoc) {
        this.trainingDoc = trainingDoc;
    }

    public IntegerFilter getTestingRichtext() {
        return testingRichtext;
    }

    public void setTestingRichtext(IntegerFilter testingRichtext) {
        this.testingRichtext = testingRichtext;
    }

    public StringFilter getTemplateCategory() {
        return templateCategory;
    }

    public void setTemplateCategory(StringFilter templateCategory) {
        this.templateCategory = templateCategory;
    }

    public IntegerFilter getdType() {
        return dType;
    }

    public void setdType(IntegerFilter dType) {
        this.dType = dType;
    }

    public IntegerFilter getdOption() {
        return dOption;
    }

    public void setdOption(IntegerFilter dOption) {
        this.dOption = dOption;
    }

    public IntegerFilter getdFilter() {
        return dFilter;
    }

    public void setdFilter(IntegerFilter dFilter) {
        this.dFilter = dFilter;
    }

    public IntegerFilter getdId() {
        return dId;
    }

    public void setdId(IntegerFilter dId) {
        this.dId = dId;
    }

    public IntegerFilter gettType() {
        return tType;
    }

    public void settType(IntegerFilter tType) {
        this.tType = tType;
    }

    public IntegerFilter gettOption() {
        return tOption;
    }

    public void settOption(IntegerFilter tOption) {
        this.tOption = tOption;
    }

    public IntegerFilter gettFilter() {
        return tFilter;
    }

    public void settFilter(IntegerFilter tFilter) {
        this.tFilter = tFilter;
    }

    public IntegerFilter gettId() {
        return tId;
    }

    public void settId(IntegerFilter tId) {
        this.tId = tId;
    }

    public StringFilter getRisktype() {
        return risktype;
    }

    public void setRisktype(StringFilter risktype) {
        this.risktype = risktype;
    }

    public DoubleFilter getRiskimpact() {
        return riskimpact;
    }

    public void setRiskimpact(DoubleFilter riskimpact) {
        this.riskimpact = riskimpact;
    }

    public DoubleFilter getRiskprobability() {
        return riskprobability;
    }

    public void setRiskprobability(DoubleFilter riskprobability) {
        this.riskprobability = riskprobability;
    }

    public LongFilter getProjectInitiativeIdId() {
        return projectInitiativeIdId;
    }

    public void setProjectInitiativeIdId(LongFilter projectInitiativeIdId) {
        this.projectInitiativeIdId = projectInitiativeIdId;
    }

    public LongFilter getProjectBusinessgoalIdId() {
        return projectBusinessgoalIdId;
    }

    public void setProjectBusinessgoalIdId(LongFilter projectBusinessgoalIdId) {
        this.projectBusinessgoalIdId = projectBusinessgoalIdId;
    }

    public LongFilter getProjectSubgoalIdId() {
        return projectSubgoalIdId;
    }

    public void setProjectSubgoalIdId(LongFilter projectSubgoalIdId) {
        this.projectSubgoalIdId = projectSubgoalIdId;
    }

    public LongFilter getProjectMaingoalIdId() {
        return projectMaingoalIdId;
    }

    public void setProjectMaingoalIdId(LongFilter projectMaingoalIdId) {
        this.projectMaingoalIdId = projectMaingoalIdId;
    }

    public LongFilter getProjectBucketIdId() {
        return projectBucketIdId;
    }

    public void setProjectBucketIdId(LongFilter projectBucketIdId) {
        this.projectBucketIdId = projectBucketIdId;
    }

    public LongFilter getProjectCostCenterIdId() {
        return projectCostCenterIdId;
    }

    public void setProjectCostCenterIdId(LongFilter projectCostCenterIdId) {
        this.projectCostCenterIdId = projectCostCenterIdId;
    }

    public LongFilter getOpportunityPriorityIdId() {
        return opportunityPriorityIdId;
    }

    public void setOpportunityPriorityIdId(LongFilter opportunityPriorityIdId) {
        this.opportunityPriorityIdId = opportunityPriorityIdId;
    }

    public LongFilter getBacklogPracticeId() {
        return backlogPracticeId;
    }

    public void setBacklogPracticeId(LongFilter backlogPracticeId) {
        this.backlogPracticeId = backlogPracticeId;
    }

    public LongFilter getProjectThemeId() {
        return projectThemeId;
    }

    public void setProjectThemeId(LongFilter projectThemeId) {
        this.projectThemeId = projectThemeId;
    }

    public LongFilter getProjectClassId() {
        return projectClassId;
    }

    public void setProjectClassId(LongFilter projectClassId) {
        this.projectClassId = projectClassId;
    }

    public LongFilter getProjectVerticalId() {
        return projectVerticalId;
    }

    public void setProjectVerticalId(LongFilter projectVerticalId) {
        this.projectVerticalId = projectVerticalId;
    }

    public LongFilter getProjectBoardIdId() {
        return projectBoardIdId;
    }

    public void setProjectBoardIdId(LongFilter projectBoardIdId) {
        this.projectBoardIdId = projectBoardIdId;
    }

    public LongFilter getProjectBoard2IdId() {
        return projectBoard2IdId;
    }

    public void setProjectBoard2IdId(LongFilter projectBoard2IdId) {
        this.projectBoard2IdId = projectBoard2IdId;
    }

    public LongFilter getProjectStatusIdId() {
        return projectStatusIdId;
    }

    public void setProjectStatusIdId(LongFilter projectStatusIdId) {
        this.projectStatusIdId = projectStatusIdId;
    }

    public LongFilter getProjectTypeIdId() {
        return projectTypeIdId;
    }

    public void setProjectTypeIdId(LongFilter projectTypeIdId) {
        this.projectTypeIdId = projectTypeIdId;
    }

    public LongFilter getProjectLeadIdId() {
        return projectLeadIdId;
    }

    public void setProjectLeadIdId(LongFilter projectLeadIdId) {
        this.projectLeadIdId = projectLeadIdId;
    }

    public LongFilter getParentIdId() {
        return parentIdId;
    }

    public void setParentIdId(LongFilter parentIdId) {
        this.parentIdId = parentIdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImProjectsCriteria that = (ImProjectsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(projectName, that.projectName) &&
            Objects.equals(projectNr, that.projectNr) &&
            Objects.equals(projectPath, that.projectPath) &&
            Objects.equals(treeSortkey, that.treeSortkey) &&
            Objects.equals(maxChildSortkey, that.maxChildSortkey) &&
            Objects.equals(description, that.description) &&
            Objects.equals(billingTypeId, that.billingTypeId) &&
            Objects.equals(note, that.note) &&
            Objects.equals(requiresReportP, that.requiresReportP) &&
            Objects.equals(projectBudget, that.projectBudget) &&
            Objects.equals(projectRisk, that.projectRisk) &&
            Objects.equals(corporateSponsor, that.corporateSponsor) &&
            Objects.equals(percentCompleted, that.percentCompleted) &&
            Objects.equals(projectBudgetHours, that.projectBudgetHours) &&
            Objects.equals(costQuotesCache, that.costQuotesCache) &&
            Objects.equals(costInvoicesCache, that.costInvoicesCache) &&
            Objects.equals(costTimesheetPlannedCache, that.costTimesheetPlannedCache) &&
            Objects.equals(costPurchaseOrdersCache, that.costPurchaseOrdersCache) &&
            Objects.equals(costBillsCache, that.costBillsCache) &&
            Objects.equals(costTimesheetLoggedCache, that.costTimesheetLoggedCache) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(templateP, that.templateP) &&
            Objects.equals(sortOrder, that.sortOrder) &&
            Objects.equals(reportedHoursCache, that.reportedHoursCache) &&
            Objects.equals(costExpensePlannedCache, that.costExpensePlannedCache) &&
            Objects.equals(costExpenseLoggedCache, that.costExpenseLoggedCache) &&
            Objects.equals(confirmDate, that.confirmDate) &&
            Objects.equals(costDeliveryNotesCache, that.costDeliveryNotesCache) &&
            Objects.equals(costCacheDirty, that.costCacheDirty) &&
            Objects.equals(milestoneP, that.milestoneP) &&
            Objects.equals(releaseItemP, that.releaseItemP) &&
            Objects.equals(presalesProbability, that.presalesProbability) &&
            Objects.equals(presalesValue, that.presalesValue) &&
            Objects.equals(reportedDaysCache, that.reportedDaysCache) &&
            Objects.equals(presalesValueCurrency, that.presalesValueCurrency) &&
            Objects.equals(opportunitySalesStageId, that.opportunitySalesStageId) &&
            Objects.equals(opportunityCampaignId, that.opportunityCampaignId) &&
            Objects.equals(scoreRevenue, that.scoreRevenue) &&
            Objects.equals(scoreStrategic, that.scoreStrategic) &&
            Objects.equals(scoreFinanceNpv, that.scoreFinanceNpv) &&
            Objects.equals(scoreCustomers, that.scoreCustomers) &&
            Objects.equals(scoreFinanceCost, that.scoreFinanceCost) &&
            Objects.equals(costBillsPlanned, that.costBillsPlanned) &&
            Objects.equals(costExpensesPlanned, that.costExpensesPlanned) &&
            Objects.equals(scoreRisk, that.scoreRisk) &&
            Objects.equals(scoreCapabilities, that.scoreCapabilities) &&
            Objects.equals(scoreEinanceRoi, that.scoreEinanceRoi) &&
            Objects.equals(projectUserwiseBoard, that.projectUserwiseBoard) &&
            Objects.equals(projectBringNextday, that.projectBringNextday) &&
            Objects.equals(projectBringSameboard, that.projectBringSameboard) &&
            Objects.equals(projectNewboardEverytime, that.projectNewboardEverytime) &&
            Objects.equals(projectUserwiseBoard2, that.projectUserwiseBoard2) &&
            Objects.equals(projectBringSameboard2, that.projectBringSameboard2) &&
            Objects.equals(projectNewboard2Everytime, that.projectNewboard2Everytime) &&
            Objects.equals(projectNewboard2Always, that.projectNewboard2Always) &&
            Objects.equals(projectReportWeekly, that.projectReportWeekly) &&
            Objects.equals(scoreGain, that.scoreGain) &&
            Objects.equals(scoreLoss, that.scoreLoss) &&
            Objects.equals(scoreDelivery, that.scoreDelivery) &&
            Objects.equals(scoreOperations, that.scoreOperations) &&
            Objects.equals(scoreWhy, that.scoreWhy) &&
            Objects.equals(javaServices, that.javaServices) &&
            Objects.equals(netServices, that.netServices) &&
            Objects.equals(collectionLink, that.collectionLink) &&
            Objects.equals(trainingLink, that.trainingLink) &&
            Objects.equals(collectionName, that.collectionName) &&
            Objects.equals(trainingName, that.trainingName) &&
            Objects.equals(trainingDoc, that.trainingDoc) &&
            Objects.equals(testingRichtext, that.testingRichtext) &&
            Objects.equals(templateCategory, that.templateCategory) &&
            Objects.equals(dType, that.dType) &&
            Objects.equals(dOption, that.dOption) &&
            Objects.equals(dFilter, that.dFilter) &&
            Objects.equals(dId, that.dId) &&
            Objects.equals(tType, that.tType) &&
            Objects.equals(tOption, that.tOption) &&
            Objects.equals(tFilter, that.tFilter) &&
            Objects.equals(tId, that.tId) &&
            Objects.equals(risktype, that.risktype) &&
            Objects.equals(riskimpact, that.riskimpact) &&
            Objects.equals(riskprobability, that.riskprobability) &&
            Objects.equals(projectInitiativeIdId, that.projectInitiativeIdId) &&
            Objects.equals(projectBusinessgoalIdId, that.projectBusinessgoalIdId) &&
            Objects.equals(projectSubgoalIdId, that.projectSubgoalIdId) &&
            Objects.equals(projectMaingoalIdId, that.projectMaingoalIdId) &&
            Objects.equals(projectBucketIdId, that.projectBucketIdId) &&
            Objects.equals(projectCostCenterIdId, that.projectCostCenterIdId) &&
            Objects.equals(opportunityPriorityIdId, that.opportunityPriorityIdId) &&
            Objects.equals(backlogPracticeId, that.backlogPracticeId) &&
            Objects.equals(projectThemeId, that.projectThemeId) &&
            Objects.equals(projectClassId, that.projectClassId) &&
            Objects.equals(projectVerticalId, that.projectVerticalId) &&
            Objects.equals(projectBoardIdId, that.projectBoardIdId) &&
            Objects.equals(projectBoard2IdId, that.projectBoard2IdId) &&
            Objects.equals(projectStatusIdId, that.projectStatusIdId) &&
            Objects.equals(projectTypeIdId, that.projectTypeIdId) &&
            Objects.equals(projectLeadIdId, that.projectLeadIdId) &&
            Objects.equals(parentIdId, that.parentIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        projectName,
        projectNr,
        projectPath,
        treeSortkey,
        maxChildSortkey,
        description,
        billingTypeId,
        note,
        requiresReportP,
        projectBudget,
        projectRisk,
        corporateSponsor,
        percentCompleted,
        projectBudgetHours,
        costQuotesCache,
        costInvoicesCache,
        costTimesheetPlannedCache,
        costPurchaseOrdersCache,
        costBillsCache,
        costTimesheetLoggedCache,
        endDate,
        startDate,
        templateP,
        sortOrder,
        reportedHoursCache,
        costExpensePlannedCache,
        costExpenseLoggedCache,
        confirmDate,
        costDeliveryNotesCache,
        costCacheDirty,
        milestoneP,
        releaseItemP,
        presalesProbability,
        presalesValue,
        reportedDaysCache,
        presalesValueCurrency,
        opportunitySalesStageId,
        opportunityCampaignId,
        scoreRevenue,
        scoreStrategic,
        scoreFinanceNpv,
        scoreCustomers,
        scoreFinanceCost,
        costBillsPlanned,
        costExpensesPlanned,
        scoreRisk,
        scoreCapabilities,
        scoreEinanceRoi,
        projectUserwiseBoard,
        projectBringNextday,
        projectBringSameboard,
        projectNewboardEverytime,
        projectUserwiseBoard2,
        projectBringSameboard2,
        projectNewboard2Everytime,
        projectNewboard2Always,
        projectReportWeekly,
        scoreGain,
        scoreLoss,
        scoreDelivery,
        scoreOperations,
        scoreWhy,
        javaServices,
        netServices,
        collectionLink,
        trainingLink,
        collectionName,
        trainingName,
        trainingDoc,
        testingRichtext,
        templateCategory,
        dType,
        dOption,
        dFilter,
        dId,
        tType,
        tOption,
        tFilter,
        tId,
        risktype,
        riskimpact,
        riskprobability,
        projectInitiativeIdId,
        projectBusinessgoalIdId,
        projectSubgoalIdId,
        projectMaingoalIdId,
        projectBucketIdId,
        projectCostCenterIdId,
        opportunityPriorityIdId,
        backlogPracticeId,
        projectThemeId,
        projectClassId,
        projectVerticalId,
        projectBoardIdId,
        projectBoard2IdId,
        projectStatusIdId,
        projectTypeIdId,
        projectLeadIdId,
        parentIdId
        );
    }

    @Override
    public String toString() {
        return "ImProjectsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (projectName != null ? "projectName=" + projectName + ", " : "") +
                (projectNr != null ? "projectNr=" + projectNr + ", " : "") +
                (projectPath != null ? "projectPath=" + projectPath + ", " : "") +
                (treeSortkey != null ? "treeSortkey=" + treeSortkey + ", " : "") +
                (maxChildSortkey != null ? "maxChildSortkey=" + maxChildSortkey + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (billingTypeId != null ? "billingTypeId=" + billingTypeId + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (requiresReportP != null ? "requiresReportP=" + requiresReportP + ", " : "") +
                (projectBudget != null ? "projectBudget=" + projectBudget + ", " : "") +
                (projectRisk != null ? "projectRisk=" + projectRisk + ", " : "") +
                (corporateSponsor != null ? "corporateSponsor=" + corporateSponsor + ", " : "") +
                (percentCompleted != null ? "percentCompleted=" + percentCompleted + ", " : "") +
                (projectBudgetHours != null ? "projectBudgetHours=" + projectBudgetHours + ", " : "") +
                (costQuotesCache != null ? "costQuotesCache=" + costQuotesCache + ", " : "") +
                (costInvoicesCache != null ? "costInvoicesCache=" + costInvoicesCache + ", " : "") +
                (costTimesheetPlannedCache != null ? "costTimesheetPlannedCache=" + costTimesheetPlannedCache + ", " : "") +
                (costPurchaseOrdersCache != null ? "costPurchaseOrdersCache=" + costPurchaseOrdersCache + ", " : "") +
                (costBillsCache != null ? "costBillsCache=" + costBillsCache + ", " : "") +
                (costTimesheetLoggedCache != null ? "costTimesheetLoggedCache=" + costTimesheetLoggedCache + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (templateP != null ? "templateP=" + templateP + ", " : "") +
                (sortOrder != null ? "sortOrder=" + sortOrder + ", " : "") +
                (reportedHoursCache != null ? "reportedHoursCache=" + reportedHoursCache + ", " : "") +
                (costExpensePlannedCache != null ? "costExpensePlannedCache=" + costExpensePlannedCache + ", " : "") +
                (costExpenseLoggedCache != null ? "costExpenseLoggedCache=" + costExpenseLoggedCache + ", " : "") +
                (confirmDate != null ? "confirmDate=" + confirmDate + ", " : "") +
                (costDeliveryNotesCache != null ? "costDeliveryNotesCache=" + costDeliveryNotesCache + ", " : "") +
                (costCacheDirty != null ? "costCacheDirty=" + costCacheDirty + ", " : "") +
                (milestoneP != null ? "milestoneP=" + milestoneP + ", " : "") +
                (releaseItemP != null ? "releaseItemP=" + releaseItemP + ", " : "") +
                (presalesProbability != null ? "presalesProbability=" + presalesProbability + ", " : "") +
                (presalesValue != null ? "presalesValue=" + presalesValue + ", " : "") +
                (reportedDaysCache != null ? "reportedDaysCache=" + reportedDaysCache + ", " : "") +
                (presalesValueCurrency != null ? "presalesValueCurrency=" + presalesValueCurrency + ", " : "") +
                (opportunitySalesStageId != null ? "opportunitySalesStageId=" + opportunitySalesStageId + ", " : "") +
                (opportunityCampaignId != null ? "opportunityCampaignId=" + opportunityCampaignId + ", " : "") +
                (scoreRevenue != null ? "scoreRevenue=" + scoreRevenue + ", " : "") +
                (scoreStrategic != null ? "scoreStrategic=" + scoreStrategic + ", " : "") +
                (scoreFinanceNpv != null ? "scoreFinanceNpv=" + scoreFinanceNpv + ", " : "") +
                (scoreCustomers != null ? "scoreCustomers=" + scoreCustomers + ", " : "") +
                (scoreFinanceCost != null ? "scoreFinanceCost=" + scoreFinanceCost + ", " : "") +
                (costBillsPlanned != null ? "costBillsPlanned=" + costBillsPlanned + ", " : "") +
                (costExpensesPlanned != null ? "costExpensesPlanned=" + costExpensesPlanned + ", " : "") +
                (scoreRisk != null ? "scoreRisk=" + scoreRisk + ", " : "") +
                (scoreCapabilities != null ? "scoreCapabilities=" + scoreCapabilities + ", " : "") +
                (scoreEinanceRoi != null ? "scoreEinanceRoi=" + scoreEinanceRoi + ", " : "") +
                (projectUserwiseBoard != null ? "projectUserwiseBoard=" + projectUserwiseBoard + ", " : "") +
                (projectBringNextday != null ? "projectBringNextday=" + projectBringNextday + ", " : "") +
                (projectBringSameboard != null ? "projectBringSameboard=" + projectBringSameboard + ", " : "") +
                (projectNewboardEverytime != null ? "projectNewboardEverytime=" + projectNewboardEverytime + ", " : "") +
                (projectUserwiseBoard2 != null ? "projectUserwiseBoard2=" + projectUserwiseBoard2 + ", " : "") +
                (projectBringSameboard2 != null ? "projectBringSameboard2=" + projectBringSameboard2 + ", " : "") +
                (projectNewboard2Everytime != null ? "projectNewboard2Everytime=" + projectNewboard2Everytime + ", " : "") +
                (projectNewboard2Always != null ? "projectNewboard2Always=" + projectNewboard2Always + ", " : "") +
                (projectReportWeekly != null ? "projectReportWeekly=" + projectReportWeekly + ", " : "") +
                (scoreGain != null ? "scoreGain=" + scoreGain + ", " : "") +
                (scoreLoss != null ? "scoreLoss=" + scoreLoss + ", " : "") +
                (scoreDelivery != null ? "scoreDelivery=" + scoreDelivery + ", " : "") +
                (scoreOperations != null ? "scoreOperations=" + scoreOperations + ", " : "") +
                (scoreWhy != null ? "scoreWhy=" + scoreWhy + ", " : "") +
                (javaServices != null ? "javaServices=" + javaServices + ", " : "") +
                (netServices != null ? "netServices=" + netServices + ", " : "") +
                (collectionLink != null ? "collectionLink=" + collectionLink + ", " : "") +
                (trainingLink != null ? "trainingLink=" + trainingLink + ", " : "") +
                (collectionName != null ? "collectionName=" + collectionName + ", " : "") +
                (trainingName != null ? "trainingName=" + trainingName + ", " : "") +
                (trainingDoc != null ? "trainingDoc=" + trainingDoc + ", " : "") +
                (testingRichtext != null ? "testingRichtext=" + testingRichtext + ", " : "") +
                (templateCategory != null ? "templateCategory=" + templateCategory + ", " : "") +
                (dType != null ? "dType=" + dType + ", " : "") +
                (dOption != null ? "dOption=" + dOption + ", " : "") +
                (dFilter != null ? "dFilter=" + dFilter + ", " : "") +
                (dId != null ? "dId=" + dId + ", " : "") +
                (tType != null ? "tType=" + tType + ", " : "") +
                (tOption != null ? "tOption=" + tOption + ", " : "") +
                (tFilter != null ? "tFilter=" + tFilter + ", " : "") +
                (tId != null ? "tId=" + tId + ", " : "") +
                (risktype != null ? "risktype=" + risktype + ", " : "") +
                (riskimpact != null ? "riskimpact=" + riskimpact + ", " : "") +
                (riskprobability != null ? "riskprobability=" + riskprobability + ", " : "") +
                (projectInitiativeIdId != null ? "projectInitiativeIdId=" + projectInitiativeIdId + ", " : "") +
                (projectBusinessgoalIdId != null ? "projectBusinessgoalIdId=" + projectBusinessgoalIdId + ", " : "") +
                (projectSubgoalIdId != null ? "projectSubgoalIdId=" + projectSubgoalIdId + ", " : "") +
                (projectMaingoalIdId != null ? "projectMaingoalIdId=" + projectMaingoalIdId + ", " : "") +
                (projectBucketIdId != null ? "projectBucketIdId=" + projectBucketIdId + ", " : "") +
                (projectCostCenterIdId != null ? "projectCostCenterIdId=" + projectCostCenterIdId + ", " : "") +
                (opportunityPriorityIdId != null ? "opportunityPriorityIdId=" + opportunityPriorityIdId + ", " : "") +
                (backlogPracticeId != null ? "backlogPracticeId=" + backlogPracticeId + ", " : "") +
                (projectThemeId != null ? "projectThemeId=" + projectThemeId + ", " : "") +
                (projectClassId != null ? "projectClassId=" + projectClassId + ", " : "") +
                (projectVerticalId != null ? "projectVerticalId=" + projectVerticalId + ", " : "") +
                (projectBoardIdId != null ? "projectBoardIdId=" + projectBoardIdId + ", " : "") +
                (projectBoard2IdId != null ? "projectBoard2IdId=" + projectBoard2IdId + ", " : "") +
                (projectStatusIdId != null ? "projectStatusIdId=" + projectStatusIdId + ", " : "") +
                (projectTypeIdId != null ? "projectTypeIdId=" + projectTypeIdId + ", " : "") +
                (projectLeadIdId != null ? "projectLeadIdId=" + projectLeadIdId + ", " : "") +
                (parentIdId != null ? "parentIdId=" + parentIdId + ", " : "") +
            "}";
    }

}
