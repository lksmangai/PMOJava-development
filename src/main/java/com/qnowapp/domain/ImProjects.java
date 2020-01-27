
package com.qnowapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ImProjects.
 */
@Entity
@Table(name = "im_projects")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ImProjects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;


   @NotNull

    @Column(name = "project_name",length = 1024 , nullable = false)
    @Length(max = 1024)
    private String projectName;

    @NotNull
    @Column(name = "project_nr", nullable = false)
    private String projectNr;

    @NotNull
    @Column(name = "project_path", nullable = false)
    private String projectPath;

    @Column(name = "tree_sortkey")
    private String treeSortkey;

    @Column(name = "max_child_sortkey")
    private String maxChildSortkey;


   @Column(name = "description", length = 4096)

    @Length(max = 4096)
    private String description;

    @Column(name = "billing_type_id")
    private Integer billingTypeId;

    @Column(name = "note")
    private String note;

    @Column(name = "requires_report_p")
    private Boolean requiresReportP;

    @Column(name = "project_budget")
    private Double projectBudget;

    @Column(name = "project_risk")
    private String projectRisk;

    @Column(name = "corporate_sponsor")
    private String corporateSponsor;

    @Column(name = "percent_completed")
    private Double percentCompleted;

    @Column(name = "project_budget_hours")
    private Double projectBudgetHours;

    @Column(name = "cost_quotes_cache", precision = 21, scale = 2)
    private BigDecimal costQuotesCache;

    @Column(name = "cost_invoices_cache")
    private Integer costInvoicesCache;

    @Column(name = "cost_timesheet_planned_cache")
    private Integer costTimesheetPlannedCache;

    @Column(name = "cost_purchase_orders_cache")
    private Integer costPurchaseOrdersCache;

    @Column(name = "cost_bills_cache")
    private Integer costBillsCache;

    @Column(name = "cost_timesheet_logged_cache")
    private Integer costTimesheetLoggedCache;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "template_p")
    private Boolean templateP;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "reported_hours_cache")
    private Double reportedHoursCache;

    @Column(name = "cost_expense_planned_cache")
    private Integer costExpensePlannedCache;

    @Column(name = "cost_expense_logged_cache")
    private Integer costExpenseLoggedCache;

    @Column(name = "confirm_date")
    private Instant confirmDate;

    @Column(name = "cost_delivery_notes_cache", precision = 21, scale = 2)
    private BigDecimal costDeliveryNotesCache;

    @Column(name = "cost_cache_dirty")
    private ZonedDateTime costCacheDirty;

    @Column(name = "milestone_p")
    private Boolean milestoneP;

    @Column(name = "release_item_p")
    private String releaseItemP;

    @Column(name = "presales_probability", precision = 21, scale = 2)
    private BigDecimal presalesProbability;

    @Column(name = "presales_value", precision = 21, scale = 2)
    private BigDecimal presalesValue;

    @Column(name = "reported_days_cache", precision = 21, scale = 2)
    private BigDecimal reportedDaysCache;

    @Column(name = "presales_value_currency")
    private String presalesValueCurrency;

    @Column(name = "opportunity_sales_stage_id")
    private Integer opportunitySalesStageId;

    @Column(name = "opportunity_campaign_id")
    private Integer opportunityCampaignId;

    @Column(name = "score_revenue", precision = 21, scale = 2)
    private BigDecimal scoreRevenue;

    @Column(name = "score_strategic", precision = 21, scale = 2)
    private BigDecimal scoreStrategic;

    @Column(name = "score_finance_npv", precision = 21, scale = 2)
    private BigDecimal scoreFinanceNpv;

    @Column(name = "score_customers", precision = 21, scale = 2)
    private BigDecimal scoreCustomers;

    @Column(name = "score_finance_cost", precision = 21, scale = 2)
    private BigDecimal scoreFinanceCost;

    @Column(name = "cost_bills_planned", precision = 21, scale = 2)
    private BigDecimal costBillsPlanned;

    @Column(name = "cost_expenses_planned", precision = 21, scale = 2)
    private BigDecimal costExpensesPlanned;

    @Column(name = "score_risk", precision = 21, scale = 2)
    private BigDecimal scoreRisk;

    @Column(name = "score_capabilities", precision = 21, scale = 2)
    private BigDecimal scoreCapabilities;

    @Column(name = "score_einance_roi", precision = 21, scale = 2)
    private BigDecimal scoreEinanceRoi;

    @Column(name = "project_userwise_board")
    private String projectUserwiseBoard;

    @Column(name = "project_bring_nextday")
    private Integer projectBringNextday;

    @Column(name = "project_bring_sameboard")
    private String projectBringSameboard;

    @Column(name = "project_newboard_everytime")
    private String projectNewboardEverytime;

    @Column(name = "project_userwise_board_2")
    private String projectUserwiseBoard2;

    @Column(name = "project_bring_sameboard_2")
    private String projectBringSameboard2;

    @Column(name = "project_newboard_2_everytime")
    private Integer projectNewboard2Everytime;

    @Column(name = "project_newboard_2_always")
    private String projectNewboard2Always;

    @Column(name = "project_report_weekly")
    private String projectReportWeekly;

    @Column(name = "score_gain")
    private Double scoreGain;

    @Column(name = "score_loss")
    private Double scoreLoss;

    @Column(name = "score_delivery")
    private Double scoreDelivery;

    @Column(name = "score_operations")
    private Double scoreOperations;

    @Column(name = "score_why")
    private Integer scoreWhy;

    @Column(name = "java_services")
    private String javaServices;

    @Column(name = "net_services")
    private String netServices;

    @Column(name = "collection_link")
    private String collectionLink;

    @Column(name = "training_link")
    private String trainingLink;

    @Column(name = "collection_name")
    private String collectionName;

    @Column(name = "training_name")
    private String trainingName;

    @Column(name = "training_doc")
    private String trainingDoc;

    @Column(name = "testing_richtext")
    private Integer testingRichtext;

    @Column(name = "template_category")
    private String templateCategory;

    @Column(name = "d_type")
    private Integer dType;

    @Column(name = "d_option")
    private Integer dOption;

    @Column(name = "d_filter")
    private Integer dFilter;

    @Column(name = "d_id")
    private Integer dId;

    @Column(name = "t_type")
    private Integer tType;

    @Column(name = "t_option")
    private Integer tOption;

    @Column(name = "t_filter")
    private Integer tFilter;

    @Column(name = "t_id")
    private Integer tId;

    @Column(name = "risktype")
    private String risktype;

    @Column(name = "riskimpact")
    private Double riskimpact;

    @Column(name = "riskprobability")
    private Double riskprobability;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectInitiativeId projectInitiativeId;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectBusinessgoalId projectBusinessgoalId;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectSubgoalId projectSubgoalId;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectMaingoalId projectMaingoalId;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectBucketId projectBucketId;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectCostCenterId projectCostCenterId;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private OpportunityPriorityId opportunityPriorityId;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private BacklogPractice backlogPractice;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectTheme projectTheme;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectClass projectClass;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectVertical projectVertical;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectBoardId projectBoardId;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectBoardId projectBoard2Id;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectStatusId projectStatusId;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ProjectTypeId projectTypeId;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ImEmployee projectLeadId;

    @ManyToOne
    @JsonIgnoreProperties("imProjects")
    private ImProjects parentId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public ImProjects projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNr() {
        return projectNr;
    }

    public ImProjects projectNr(String projectNr) {
        this.projectNr = projectNr;
        return this;
    }

    public void setProjectNr(String projectNr) {
        this.projectNr = projectNr;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public ImProjects projectPath(String projectPath) {
        this.projectPath = projectPath;
        return this;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getTreeSortkey() {
        return treeSortkey;
    }

    public ImProjects treeSortkey(String treeSortkey) {
        this.treeSortkey = treeSortkey;
        return this;
    }

    public void setTreeSortkey(String treeSortkey) {
        this.treeSortkey = treeSortkey;
    }

    public String getMaxChildSortkey() {
        return maxChildSortkey;
    }

    public ImProjects maxChildSortkey(String maxChildSortkey) {
        this.maxChildSortkey = maxChildSortkey;
        return this;
    }

    public void setMaxChildSortkey(String maxChildSortkey) {
        this.maxChildSortkey = maxChildSortkey;
    }

    public String getDescription() {
        return description;
    }

    public ImProjects description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBillingTypeId() {
        return billingTypeId;
    }

    public ImProjects billingTypeId(Integer billingTypeId) {
        this.billingTypeId = billingTypeId;
        return this;
    }

    public void setBillingTypeId(Integer billingTypeId) {
        this.billingTypeId = billingTypeId;
    }

    public String getNote() {
        return note;
    }

    public ImProjects note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean isRequiresReportP() {
        return requiresReportP;
    }

    public ImProjects requiresReportP(Boolean requiresReportP) {
        this.requiresReportP = requiresReportP;
        return this;
    }

    public void setRequiresReportP(Boolean requiresReportP) {
        this.requiresReportP = requiresReportP;
    }

    public Double getProjectBudget() {
        return projectBudget;
    }

    public ImProjects projectBudget(Double projectBudget) {
        this.projectBudget = projectBudget;
        return this;
    }

    public void setProjectBudget(Double projectBudget) {
        this.projectBudget = projectBudget;
    }

    public String getProjectRisk() {
        return projectRisk;
    }

    public ImProjects projectRisk(String projectRisk) {
        this.projectRisk = projectRisk;
        return this;
    }

    public void setProjectRisk(String projectRisk) {
        this.projectRisk = projectRisk;
    }

    public String getCorporateSponsor() {
        return corporateSponsor;
    }

    public ImProjects corporateSponsor(String corporateSponsor) {
        this.corporateSponsor = corporateSponsor;
        return this;
    }

    public void setCorporateSponsor(String corporateSponsor) {
        this.corporateSponsor = corporateSponsor;
    }

    public Double getPercentCompleted() {
        return percentCompleted;
    }

    public ImProjects percentCompleted(Double percentCompleted) {
        this.percentCompleted = percentCompleted;
        return this;
    }

    public void setPercentCompleted(Double percentCompleted) {
        this.percentCompleted = percentCompleted;
    }

    public Double getProjectBudgetHours() {
        return projectBudgetHours;
    }

    public ImProjects projectBudgetHours(Double projectBudgetHours) {
        this.projectBudgetHours = projectBudgetHours;
        return this;
    }

    public void setProjectBudgetHours(Double projectBudgetHours) {
        this.projectBudgetHours = projectBudgetHours;
    }

    public BigDecimal getCostQuotesCache() {
        return costQuotesCache;
    }

    public ImProjects costQuotesCache(BigDecimal costQuotesCache) {
        this.costQuotesCache = costQuotesCache;
        return this;
    }

    public void setCostQuotesCache(BigDecimal costQuotesCache) {
        this.costQuotesCache = costQuotesCache;
    }

    public Integer getCostInvoicesCache() {
        return costInvoicesCache;
    }

    public ImProjects costInvoicesCache(Integer costInvoicesCache) {
        this.costInvoicesCache = costInvoicesCache;
        return this;
    }

    public void setCostInvoicesCache(Integer costInvoicesCache) {
        this.costInvoicesCache = costInvoicesCache;
    }

    public Integer getCostTimesheetPlannedCache() {
        return costTimesheetPlannedCache;
    }

    public ImProjects costTimesheetPlannedCache(Integer costTimesheetPlannedCache) {
        this.costTimesheetPlannedCache = costTimesheetPlannedCache;
        return this;
    }

    public void setCostTimesheetPlannedCache(Integer costTimesheetPlannedCache) {
        this.costTimesheetPlannedCache = costTimesheetPlannedCache;
    }

    public Integer getCostPurchaseOrdersCache() {
        return costPurchaseOrdersCache;
    }

    public ImProjects costPurchaseOrdersCache(Integer costPurchaseOrdersCache) {
        this.costPurchaseOrdersCache = costPurchaseOrdersCache;
        return this;
    }

    public void setCostPurchaseOrdersCache(Integer costPurchaseOrdersCache) {
        this.costPurchaseOrdersCache = costPurchaseOrdersCache;
    }

    public Integer getCostBillsCache() {
        return costBillsCache;
    }

    public ImProjects costBillsCache(Integer costBillsCache) {
        this.costBillsCache = costBillsCache;
        return this;
    }

    public void setCostBillsCache(Integer costBillsCache) {
        this.costBillsCache = costBillsCache;
    }

    public Integer getCostTimesheetLoggedCache() {
        return costTimesheetLoggedCache;
    }

    public ImProjects costTimesheetLoggedCache(Integer costTimesheetLoggedCache) {
        this.costTimesheetLoggedCache = costTimesheetLoggedCache;
        return this;
    }

    public void setCostTimesheetLoggedCache(Integer costTimesheetLoggedCache) {
        this.costTimesheetLoggedCache = costTimesheetLoggedCache;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public ImProjects endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ImProjects startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public Boolean isTemplateP() {
        return templateP;
    }

    public ImProjects templateP(Boolean templateP) {
        this.templateP = templateP;
        return this;
    }

    public void setTemplateP(Boolean templateP) {
        this.templateP = templateP;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public ImProjects sortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Double getReportedHoursCache() {
        return reportedHoursCache;
    }

    public ImProjects reportedHoursCache(Double reportedHoursCache) {
        this.reportedHoursCache = reportedHoursCache;
        return this;
    }

    public void setReportedHoursCache(Double reportedHoursCache) {
        this.reportedHoursCache = reportedHoursCache;
    }

    public Integer getCostExpensePlannedCache() {
        return costExpensePlannedCache;
    }

    public ImProjects costExpensePlannedCache(Integer costExpensePlannedCache) {
        this.costExpensePlannedCache = costExpensePlannedCache;
        return this;
    }

    public void setCostExpensePlannedCache(Integer costExpensePlannedCache) {
        this.costExpensePlannedCache = costExpensePlannedCache;
    }

    public Integer getCostExpenseLoggedCache() {
        return costExpenseLoggedCache;
    }

    public ImProjects costExpenseLoggedCache(Integer costExpenseLoggedCache) {
        this.costExpenseLoggedCache = costExpenseLoggedCache;
        return this;
    }

    public void setCostExpenseLoggedCache(Integer costExpenseLoggedCache) {
        this.costExpenseLoggedCache = costExpenseLoggedCache;
    }

    public Instant getConfirmDate() {
        return confirmDate;
    }

    public ImProjects confirmDate(Instant confirmDate) {
        this.confirmDate = confirmDate;
        return this;
    }

    public void setConfirmDate(Instant confirmDate) {
        this.confirmDate = confirmDate;
    }

    public BigDecimal getCostDeliveryNotesCache() {
        return costDeliveryNotesCache;
    }

    public ImProjects costDeliveryNotesCache(BigDecimal costDeliveryNotesCache) {
        this.costDeliveryNotesCache = costDeliveryNotesCache;
        return this;
    }

    public void setCostDeliveryNotesCache(BigDecimal costDeliveryNotesCache) {
        this.costDeliveryNotesCache = costDeliveryNotesCache;
    }

    public ZonedDateTime getCostCacheDirty() {
        return costCacheDirty;
    }

    public ImProjects costCacheDirty(ZonedDateTime costCacheDirty) {
        this.costCacheDirty = costCacheDirty;
        return this;
    }

    public void setCostCacheDirty(ZonedDateTime costCacheDirty) {
        this.costCacheDirty = costCacheDirty;
    }

    public Boolean isMilestoneP() {
        return milestoneP;
    }

    public ImProjects milestoneP(Boolean milestoneP) {
        this.milestoneP = milestoneP;
        return this;
    }

    public void setMilestoneP(Boolean milestoneP) {
        this.milestoneP = milestoneP;
    }

    public String getReleaseItemP() {
        return releaseItemP;
    }

    public ImProjects releaseItemP(String releaseItemP) {
        this.releaseItemP = releaseItemP;
        return this;
    }

    public void setReleaseItemP(String releaseItemP) {
        this.releaseItemP = releaseItemP;
    }

    public BigDecimal getPresalesProbability() {
        return presalesProbability;
    }

    public ImProjects presalesProbability(BigDecimal presalesProbability) {
        this.presalesProbability = presalesProbability;
        return this;
    }

    public void setPresalesProbability(BigDecimal presalesProbability) {
        this.presalesProbability = presalesProbability;
    }

    public BigDecimal getPresalesValue() {
        return presalesValue;
    }

    public ImProjects presalesValue(BigDecimal presalesValue) {
        this.presalesValue = presalesValue;
        return this;
    }

    public void setPresalesValue(BigDecimal presalesValue) {
        this.presalesValue = presalesValue;
    }

    public BigDecimal getReportedDaysCache() {
        return reportedDaysCache;
    }

    public ImProjects reportedDaysCache(BigDecimal reportedDaysCache) {
        this.reportedDaysCache = reportedDaysCache;
        return this;
    }

    public void setReportedDaysCache(BigDecimal reportedDaysCache) {
        this.reportedDaysCache = reportedDaysCache;
    }

    public String getPresalesValueCurrency() {
        return presalesValueCurrency;
    }

    public ImProjects presalesValueCurrency(String presalesValueCurrency) {
        this.presalesValueCurrency = presalesValueCurrency;
        return this;
    }

    public void setPresalesValueCurrency(String presalesValueCurrency) {
        this.presalesValueCurrency = presalesValueCurrency;
    }

    public Integer getOpportunitySalesStageId() {
        return opportunitySalesStageId;
    }

    public ImProjects opportunitySalesStageId(Integer opportunitySalesStageId) {
        this.opportunitySalesStageId = opportunitySalesStageId;
        return this;
    }

    public void setOpportunitySalesStageId(Integer opportunitySalesStageId) {
        this.opportunitySalesStageId = opportunitySalesStageId;
    }

    public Integer getOpportunityCampaignId() {
        return opportunityCampaignId;
    }

    public ImProjects opportunityCampaignId(Integer opportunityCampaignId) {
        this.opportunityCampaignId = opportunityCampaignId;
        return this;
    }

    public void setOpportunityCampaignId(Integer opportunityCampaignId) {
        this.opportunityCampaignId = opportunityCampaignId;
    }

    public BigDecimal getScoreRevenue() {
        return scoreRevenue;
    }

    public ImProjects scoreRevenue(BigDecimal scoreRevenue) {
        this.scoreRevenue = scoreRevenue;
        return this;
    }

    public void setScoreRevenue(BigDecimal scoreRevenue) {
        this.scoreRevenue = scoreRevenue;
    }

    public BigDecimal getScoreStrategic() {
        return scoreStrategic;
    }

    public ImProjects scoreStrategic(BigDecimal scoreStrategic) {
        this.scoreStrategic = scoreStrategic;
        return this;
    }

    public void setScoreStrategic(BigDecimal scoreStrategic) {
        this.scoreStrategic = scoreStrategic;
    }

    public BigDecimal getScoreFinanceNpv() {
        return scoreFinanceNpv;
    }

    public ImProjects scoreFinanceNpv(BigDecimal scoreFinanceNpv) {
        this.scoreFinanceNpv = scoreFinanceNpv;
        return this;
    }

    public void setScoreFinanceNpv(BigDecimal scoreFinanceNpv) {
        this.scoreFinanceNpv = scoreFinanceNpv;
    }

    public BigDecimal getScoreCustomers() {
        return scoreCustomers;
    }

    public ImProjects scoreCustomers(BigDecimal scoreCustomers) {
        this.scoreCustomers = scoreCustomers;
        return this;
    }

    public void setScoreCustomers(BigDecimal scoreCustomers) {
        this.scoreCustomers = scoreCustomers;
    }

    public BigDecimal getScoreFinanceCost() {
        return scoreFinanceCost;
    }

    public ImProjects scoreFinanceCost(BigDecimal scoreFinanceCost) {
        this.scoreFinanceCost = scoreFinanceCost;
        return this;
    }

    public void setScoreFinanceCost(BigDecimal scoreFinanceCost) {
        this.scoreFinanceCost = scoreFinanceCost;
    }

    public BigDecimal getCostBillsPlanned() {
        return costBillsPlanned;
    }

    public ImProjects costBillsPlanned(BigDecimal costBillsPlanned) {
        this.costBillsPlanned = costBillsPlanned;
        return this;
    }
    public void setCostBillsPlanned(BigDecimal costBillsPlanned) {
        this.costBillsPlanned = costBillsPlanned;
    }

    public BigDecimal getCostExpensesPlanned() {
        return costExpensesPlanned;
    }

    public ImProjects costExpensesPlanned(BigDecimal costExpensesPlanned) {
        this.costExpensesPlanned = costExpensesPlanned;
        return this;
    }

    public void setCostExpensesPlanned(BigDecimal costExpensesPlanned) {
        this.costExpensesPlanned = costExpensesPlanned;
    }

    public BigDecimal getScoreRisk() {
        return scoreRisk;
    }

    public ImProjects scoreRisk(BigDecimal scoreRisk) {
        this.scoreRisk = scoreRisk;
        return this;
    }

    public void setScoreRisk(BigDecimal scoreRisk) {
        this.scoreRisk = scoreRisk;
    }

    public BigDecimal getScoreCapabilities() {
        return scoreCapabilities;
    }

    public ImProjects scoreCapabilities(BigDecimal scoreCapabilities) {
        this.scoreCapabilities = scoreCapabilities;
        return this;
    }

    public void setScoreCapabilities(BigDecimal scoreCapabilities) {
        this.scoreCapabilities = scoreCapabilities;
    }

    public BigDecimal getScoreEinanceRoi() {
        return scoreEinanceRoi;
    }

    public ImProjects scoreEinanceRoi(BigDecimal scoreEinanceRoi) {
        this.scoreEinanceRoi = scoreEinanceRoi;
        return this;
    }

    public void setScoreEinanceRoi(BigDecimal scoreEinanceRoi) {
        this.scoreEinanceRoi = scoreEinanceRoi;
    }

    public String getProjectUserwiseBoard() {
        return projectUserwiseBoard;
    }

    public ImProjects projectUserwiseBoard(String projectUserwiseBoard) {
        this.projectUserwiseBoard = projectUserwiseBoard;
        return this;
    }

    public void setProjectUserwiseBoard(String projectUserwiseBoard) {
        this.projectUserwiseBoard = projectUserwiseBoard;
    }

    public Integer getProjectBringNextday() {
        return projectBringNextday;
    }

    public ImProjects projectBringNextday(Integer projectBringNextday) {
        this.projectBringNextday = projectBringNextday;
        return this;
    }

    public void setProjectBringNextday(Integer projectBringNextday) {
        this.projectBringNextday = projectBringNextday;
    }

    public String getProjectBringSameboard() {
        return projectBringSameboard;
    }

    public ImProjects projectBringSameboard(String projectBringSameboard) {
        this.projectBringSameboard = projectBringSameboard;
        return this;
    }

    public void setProjectBringSameboard(String projectBringSameboard) {
        this.projectBringSameboard = projectBringSameboard;
    }

    public String getProjectNewboardEverytime() {
        return projectNewboardEverytime;
    }

    public ImProjects projectNewboardEverytime(String projectNewboardEverytime) {
        this.projectNewboardEverytime = projectNewboardEverytime;
        return this;
    }

    public void setProjectNewboardEverytime(String projectNewboardEverytime) {
        this.projectNewboardEverytime = projectNewboardEverytime;
    }

    public String getProjectUserwiseBoard2() {
        return projectUserwiseBoard2;
    }

    public ImProjects projectUserwiseBoard2(String projectUserwiseBoard2) {
        this.projectUserwiseBoard2 = projectUserwiseBoard2;
        return this;
    }

    public void setProjectUserwiseBoard2(String projectUserwiseBoard2) {
        this.projectUserwiseBoard2 = projectUserwiseBoard2;
    }

    public String getProjectBringSameboard2() {
        return projectBringSameboard2;
    }

    public ImProjects projectBringSameboard2(String projectBringSameboard2) {
        this.projectBringSameboard2 = projectBringSameboard2;
        return this;
    }

    public void setProjectBringSameboard2(String projectBringSameboard2) {
        this.projectBringSameboard2 = projectBringSameboard2;
    }

    public Integer getProjectNewboard2Everytime() {
        return projectNewboard2Everytime;
    }

    public ImProjects projectNewboard2Everytime(Integer projectNewboard2Everytime) {
        this.projectNewboard2Everytime = projectNewboard2Everytime;
        return this;
    }

    public void setProjectNewboard2Everytime(Integer projectNewboard2Everytime) {
        this.projectNewboard2Everytime = projectNewboard2Everytime;
    }

    public String getProjectNewboard2Always() {
        return projectNewboard2Always;
    }

    public ImProjects projectNewboard2Always(String projectNewboard2Always) {
        this.projectNewboard2Always = projectNewboard2Always;
        return this;
    }

    public void setProjectNewboard2Always(String projectNewboard2Always) {
        this.projectNewboard2Always = projectNewboard2Always;
    }

    public String getProjectReportWeekly() {
        return projectReportWeekly;
    }

    public ImProjects projectReportWeekly(String projectReportWeekly) {
        this.projectReportWeekly = projectReportWeekly;
        return this;
    }

    public void setProjectReportWeekly(String projectReportWeekly) {
        this.projectReportWeekly = projectReportWeekly;
    }

    public Double getScoreGain() {
        return scoreGain;
    }

    public ImProjects scoreGain(Double scoreGain) {
        this.scoreGain = scoreGain;
        return this;
    }

    public void setScoreGain(Double scoreGain) {
        this.scoreGain = scoreGain;
    }

    public Double getScoreLoss() {
        return scoreLoss;
    }

    public ImProjects scoreLoss(Double scoreLoss) {
        this.scoreLoss = scoreLoss;
        return this;
    }

    public void setScoreLoss(Double scoreLoss) {
        this.scoreLoss = scoreLoss;
    }

    public Double getScoreDelivery() {
        return scoreDelivery;
    }

    public ImProjects scoreDelivery(Double scoreDelivery) {
        this.scoreDelivery = scoreDelivery;
        return this;
    }

    public void setScoreDelivery(Double scoreDelivery) {
        this.scoreDelivery = scoreDelivery;
    }

    public Double getScoreOperations() {
        return scoreOperations;
    }

    public ImProjects scoreOperations(Double scoreOperations) {
        this.scoreOperations = scoreOperations;
        return this;
    }

    public void setScoreOperations(Double scoreOperations) {
        this.scoreOperations = scoreOperations;
    }

    public Integer getScoreWhy() {
        return scoreWhy;
    }

    public ImProjects scoreWhy(Integer scoreWhy) {
        this.scoreWhy = scoreWhy;
        return this;
    }

    public void setScoreWhy(Integer scoreWhy) {
        this.scoreWhy = scoreWhy;
    }

    public String getJavaServices() {
        return javaServices;
    }

    public ImProjects javaServices(String javaServices) {
        this.javaServices = javaServices;
        return this;
    }

    public void setJavaServices(String javaServices) {
        this.javaServices = javaServices;
    }

    public String getNetServices() {
        return netServices;
    }

    public ImProjects netServices(String netServices) {
        this.netServices = netServices;
        return this;
    }

    public void setNetServices(String netServices) {
        this.netServices = netServices;
    }

    public String getCollectionLink() {
        return collectionLink;
    }

    public ImProjects collectionLink(String collectionLink) {
        this.collectionLink = collectionLink;
        return this;
    }

    public void setCollectionLink(String collectionLink) {
        this.collectionLink = collectionLink;
    }

    public String getTrainingLink() {
        return trainingLink;
    }

    public ImProjects trainingLink(String trainingLink) {
        this.trainingLink = trainingLink;
        return this;
    }

    public void setTrainingLink(String trainingLink) {
        this.trainingLink = trainingLink;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public ImProjects collectionName(String collectionName) {
        this.collectionName = collectionName;
        return this;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public ImProjects trainingName(String trainingName) {
        this.trainingName = trainingName;
        return this;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getTrainingDoc() {
        return trainingDoc;
    }

    public ImProjects trainingDoc(String trainingDoc) {
        this.trainingDoc = trainingDoc;
        return this;
    }

    public void setTrainingDoc(String trainingDoc) {
        this.trainingDoc = trainingDoc;
    }

    public Integer getTestingRichtext() {
        return testingRichtext;
    }

    public ImProjects testingRichtext(Integer testingRichtext) {
        this.testingRichtext = testingRichtext;
        return this;
    }

    public void setTestingRichtext(Integer testingRichtext) {
        this.testingRichtext = testingRichtext;
    }

    public String getTemplateCategory() {
        return templateCategory;
    }

    public ImProjects templateCategory(String templateCategory) {
        this.templateCategory = templateCategory;
        return this;
    }

    public void setTemplateCategory(String templateCategory) {
        this.templateCategory = templateCategory;
    }

    public Integer getdType() {
        return dType;
    }

    public ImProjects dType(Integer dType) {
        this.dType = dType;
        return this;
    }

    public void setdType(Integer dType) {
        this.dType = dType;
    }

    public Integer getdOption() {
        return dOption;
    }

    public ImProjects dOption(Integer dOption) {
        this.dOption = dOption;
        return this;
    }

    public void setdOption(Integer dOption) {
        this.dOption = dOption;
    }

    public Integer getdFilter() {
        return dFilter;
    }

    public ImProjects dFilter(Integer dFilter) {
        this.dFilter = dFilter;
        return this;
    }

    public void setdFilter(Integer dFilter) {
        this.dFilter = dFilter;
    }

    public Integer getdId() {
        return dId;
    }

    public ImProjects dId(Integer dId) {
        this.dId = dId;
        return this;
    }

    public void setdId(Integer dId) {
        this.dId = dId;
    }

    public Integer gettType() {
        return tType;
    }

    public ImProjects tType(Integer tType) {
        this.tType = tType;
        return this;
    }

    public void settType(Integer tType) {
        this.tType = tType;
    }

    public Integer gettOption() {
        return tOption;
    }

    public ImProjects tOption(Integer tOption) {
        this.tOption = tOption;
        return this;
    }

    public void settOption(Integer tOption) {
        this.tOption = tOption;
    }

    public Integer gettFilter() {
        return tFilter;
    }

    public ImProjects tFilter(Integer tFilter) {
        this.tFilter = tFilter;
        return this;
    }

    public void settFilter(Integer tFilter) {
        this.tFilter = tFilter;
    }

    public Integer gettId() {
        return tId;
    }

    public ImProjects tId(Integer tId) {
        this.tId = tId;
        return this;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public String getRisktype() {
        return risktype;
    }

    public ImProjects risktype(String risktype) {
        this.risktype = risktype;
        return this;
    }

    public void setRisktype(String risktype) {
        this.risktype = risktype;
    }

    public Double getRiskimpact() {
        return riskimpact;
    }

    public ImProjects riskimpact(Double riskimpact) {
        this.riskimpact = riskimpact;
        return this;
    }

    public void setRiskimpact(Double riskimpact) {
        this.riskimpact = riskimpact;
    }

    public Double getRiskprobability() {
        return riskprobability;
    }

    public ImProjects riskprobability(Double riskprobability) {
        this.riskprobability = riskprobability;
        return this;
    }

    public void setRiskprobability(Double riskprobability) {
        this.riskprobability = riskprobability;
    }

    public ProjectInitiativeId getProjectInitiativeId() {
        return projectInitiativeId;
    }

    public ImProjects projectInitiativeId(ProjectInitiativeId projectInitiativeId) {
        this.projectInitiativeId = projectInitiativeId;
        return this;
    }

    public void setProjectInitiativeId(ProjectInitiativeId projectInitiativeId) {
        this.projectInitiativeId = projectInitiativeId;
    }

    public ProjectBusinessgoalId getProjectBusinessgoalId() {
        return projectBusinessgoalId;
    }

    public ImProjects projectBusinessgoalId(ProjectBusinessgoalId projectBusinessgoalId) {
        this.projectBusinessgoalId = projectBusinessgoalId;
        return this;
    }

    public void setProjectBusinessgoalId(ProjectBusinessgoalId projectBusinessgoalId) {
        this.projectBusinessgoalId = projectBusinessgoalId;
    }

    public ProjectSubgoalId getProjectSubgoalId() {
        return projectSubgoalId;
    }

    public ImProjects projectSubgoalId(ProjectSubgoalId projectSubgoalId) {
        this.projectSubgoalId = projectSubgoalId;
        return this;
    }

    public void setProjectSubgoalId(ProjectSubgoalId projectSubgoalId) {
        this.projectSubgoalId = projectSubgoalId;
    }

    public ProjectMaingoalId getProjectMaingoalId() {
        return projectMaingoalId;
    }

    public ImProjects projectMaingoalId(ProjectMaingoalId projectMaingoalId) {
        this.projectMaingoalId = projectMaingoalId;
        return this;
    }

    public void setProjectMaingoalId(ProjectMaingoalId projectMaingoalId) {
        this.projectMaingoalId = projectMaingoalId;
    }

    public ProjectBucketId getProjectBucketId() {
        return projectBucketId;
    }

    public ImProjects projectBucketId(ProjectBucketId projectBucketId) {
        this.projectBucketId = projectBucketId;
        return this;
    }

    public void setProjectBucketId(ProjectBucketId projectBucketId) {
        this.projectBucketId = projectBucketId;
    }

    public ProjectCostCenterId getProjectCostCenterId() {
        return projectCostCenterId;
    }

    public ImProjects projectCostCenterId(ProjectCostCenterId projectCostCenterId) {
        this.projectCostCenterId = projectCostCenterId;
        return this;
    }

    public void setProjectCostCenterId(ProjectCostCenterId projectCostCenterId) {
        this.projectCostCenterId = projectCostCenterId;
    }

    public OpportunityPriorityId getOpportunityPriorityId() {
        return opportunityPriorityId;
    }

    public ImProjects opportunityPriorityId(OpportunityPriorityId opportunityPriorityId) {
        this.opportunityPriorityId = opportunityPriorityId;
        return this;
    }

    public void setOpportunityPriorityId(OpportunityPriorityId opportunityPriorityId) {
        this.opportunityPriorityId = opportunityPriorityId;
    }

    public BacklogPractice getBacklogPractice() {
        return backlogPractice;
    }

    public ImProjects backlogPractice(BacklogPractice backlogPractice) {
        this.backlogPractice = backlogPractice;
        return this;
    }

    public void setBacklogPractice(BacklogPractice backlogPractice) {
        this.backlogPractice = backlogPractice;
    }

    public ProjectTheme getProjectTheme() {
        return projectTheme;
    }

    public ImProjects projectTheme(ProjectTheme projectTheme) {
        this.projectTheme = projectTheme;
        return this;
    }

    public void setProjectTheme(ProjectTheme projectTheme) {
        this.projectTheme = projectTheme;
    }

    public ProjectClass getProjectClass() {
        return projectClass;
    }

    public ImProjects projectClass(ProjectClass projectClass) {
        this.projectClass = projectClass;
        return this;
    }

    public void setProjectClass(ProjectClass projectClass) {
        this.projectClass = projectClass;
    }

    public ProjectVertical getProjectVertical() {
        return projectVertical;
    }

    public ImProjects projectVertical(ProjectVertical projectVertical) {
        this.projectVertical = projectVertical;
        return this;
    }

    public void setProjectVertical(ProjectVertical projectVertical) {
        this.projectVertical = projectVertical;
    }

    public ProjectBoardId getProjectBoardId() {
        return projectBoardId;
    }

    public ImProjects projectBoardId(ProjectBoardId projectBoardId) {
        this.projectBoardId = projectBoardId;
        return this;
    }

    public void setProjectBoardId(ProjectBoardId projectBoardId) {
        this.projectBoardId = projectBoardId;
    }

    public ProjectBoardId getProjectBoard2Id() {
        return projectBoard2Id;
    }

    public ImProjects projectBoard2Id(ProjectBoardId projectBoardId) {
        this.projectBoard2Id = projectBoardId;
        return this;
    }

    public void setProjectBoard2Id(ProjectBoardId projectBoardId) {
        this.projectBoard2Id = projectBoardId;
    }

    public ProjectStatusId getProjectStatusId() {
        return projectStatusId;
    }

    public ImProjects projectStatusId(ProjectStatusId projectStatusId) {
        this.projectStatusId = projectStatusId;
        return this;
    }

    public void setProjectStatusId(ProjectStatusId projectStatusId) {
        this.projectStatusId = projectStatusId;
    }

    public ProjectTypeId getProjectTypeId() {
        return projectTypeId;
    }

    public ImProjects projectTypeId(ProjectTypeId projectTypeId) {
        this.projectTypeId = projectTypeId;
        return this;
    }

    public void setProjectTypeId(ProjectTypeId projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public ImEmployee getProjectLeadId() {
        return projectLeadId;
    }

    public ImProjects projectLeadId(ImEmployee imEmployee) {
        this.projectLeadId = imEmployee;
        return this;
    }

    public void setProjectLeadId(ImEmployee imEmployee) {
        this.projectLeadId = imEmployee;
    }

    public ImProjects getParentId() {
        return parentId;
    }

    public ImProjects parentId(ImProjects imProjects) {
        this.parentId = imProjects;
        return this;
    }

    public void setParentId(ImProjects imProjects) {
        this.parentId = imProjects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImProjects)) {
            return false;
        }
        return id != null && id.equals(((ImProjects) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ImProjects{" +
            "id=" + getId() +
            ", projectName='" + getProjectName() + "'" +
            ", projectNr='" + getProjectNr() + "'" +
            ", projectPath='" + getProjectPath() + "'" +
            ", treeSortkey='" + getTreeSortkey() + "'" +
            ", maxChildSortkey='" + getMaxChildSortkey() + "'" +
            ", description='" + getDescription() + "'" +
            ", billingTypeId=" + getBillingTypeId() +
            ", note='" + getNote() + "'" +
            ", requiresReportP='" + isRequiresReportP() + "'" +
            ", projectBudget=" + getProjectBudget() +
            ", projectRisk='" + getProjectRisk() + "'" +
            ", corporateSponsor='" + getCorporateSponsor() + "'" +
            ", percentCompleted=" + getPercentCompleted() +
            ", projectBudgetHours=" + getProjectBudgetHours() +
            ", costQuotesCache=" + getCostQuotesCache() +
            ", costInvoicesCache=" + getCostInvoicesCache() +
            ", costTimesheetPlannedCache=" + getCostTimesheetPlannedCache() +
            ", costPurchaseOrdersCache=" + getCostPurchaseOrdersCache() +
            ", costBillsCache=" + getCostBillsCache() +
            ", costTimesheetLoggedCache=" + getCostTimesheetLoggedCache() +
            ", endDate='" + getEndDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", templateP='" + isTemplateP() + "'" +
            ", sortOrder=" + getSortOrder() +
            ", reportedHoursCache=" + getReportedHoursCache() +
            ", costExpensePlannedCache=" + getCostExpensePlannedCache() +
            ", costExpenseLoggedCache=" + getCostExpenseLoggedCache() +
            ", confirmDate='" + getConfirmDate() + "'" +
            ", costDeliveryNotesCache=" + getCostDeliveryNotesCache() +
            ", costCacheDirty='" + getCostCacheDirty() + "'" +
            ", milestoneP='" + isMilestoneP() + "'" +
            ", releaseItemP='" + getReleaseItemP() + "'" +
            ", presalesProbability=" + getPresalesProbability() +
            ", presalesValue=" + getPresalesValue() +
            ", reportedDaysCache=" + getReportedDaysCache() +
            ", presalesValueCurrency='" + getPresalesValueCurrency() + "'" +
            ", opportunitySalesStageId=" + getOpportunitySalesStageId() +
            ", opportunityCampaignId=" + getOpportunityCampaignId() +
            ", scoreRevenue=" + getScoreRevenue() +
            ", scoreStrategic=" + getScoreStrategic() +
            ", scoreFinanceNpv=" + getScoreFinanceNpv() +
            ", scoreCustomers=" + getScoreCustomers() +
            ", scoreFinanceCost=" + getScoreFinanceCost() +
            ", costBillsPlanned=" + getCostBillsPlanned() +
            ", costExpensesPlanned=" + getCostExpensesPlanned() +
            ", scoreRisk=" + getScoreRisk() +
            ", scoreCapabilities=" + getScoreCapabilities() +
            ", scoreEinanceRoi=" + getScoreEinanceRoi() +
            ", projectUserwiseBoard='" + getProjectUserwiseBoard() + "'" +
            ", projectBringNextday=" + getProjectBringNextday() +
            ", projectBringSameboard='" + getProjectBringSameboard() + "'" +
            ", projectNewboardEverytime='" + getProjectNewboardEverytime() + "'" +
            ", projectUserwiseBoard2='" + getProjectUserwiseBoard2() + "'" +
            ", projectBringSameboard2='" + getProjectBringSameboard2() + "'" +
            ", projectNewboard2Everytime=" + getProjectNewboard2Everytime() +
            ", projectNewboard2Always='" + getProjectNewboard2Always() + "'" +
            ", projectReportWeekly='" + getProjectReportWeekly() + "'" +
            ", scoreGain=" + getScoreGain() +
            ", scoreLoss=" + getScoreLoss() +
            ", scoreDelivery=" + getScoreDelivery() +
            ", scoreOperations=" + getScoreOperations() +
            ", scoreWhy=" + getScoreWhy() +
            ", javaServices='" + getJavaServices() + "'" +
            ", netServices='" + getNetServices() + "'" +
            ", collectionLink='" + getCollectionLink() + "'" +
            ", trainingLink='" + getTrainingLink() + "'" +
            ", collectionName='" + getCollectionName() + "'" +
            ", trainingName='" + getTrainingName() + "'" +
            ", trainingDoc='" + getTrainingDoc() + "'" +
            ", testingRichtext=" + getTestingRichtext() +
            ", templateCategory='" + getTemplateCategory() + "'" +
            ", dType=" + getdType() +
            ", dOption=" + getdOption() +
            ", dFilter=" + getdFilter() +
            ", dId=" + getdId() +
            ", tType=" + gettType() +
            ", tOption=" + gettOption() +
            ", tFilter=" + gettFilter() +
            ", tId=" + gettId() +
            ", risktype='" + getRisktype() + "'" +
            ", riskimpact=" + getRiskimpact() +
            ", riskprobability=" + getRiskprobability() +
            "}";
    }
}
