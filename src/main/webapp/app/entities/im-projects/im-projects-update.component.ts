import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IImProjects, ImProjects } from 'app/shared/model/im-projects.model';
import { ImProjectsService } from './im-projects.service';
import { IProjectInitiativeId } from 'app/shared/model/project-initiative-id.model';
import { ProjectInitiativeIdService } from 'app/entities/project-initiative-id';
import { IProjectBusinessgoalId } from 'app/shared/model/project-businessgoal-id.model';
import { ProjectBusinessgoalIdService } from 'app/entities/project-businessgoal-id';
import { IProjectSubgoalId } from 'app/shared/model/project-subgoal-id.model';
import { ProjectSubgoalIdService } from 'app/entities/project-subgoal-id';
import { IProjectMaingoalId } from 'app/shared/model/project-maingoal-id.model';
import { ProjectMaingoalIdService } from 'app/entities/project-maingoal-id';
import { IProjectBucketId } from 'app/shared/model/project-bucket-id.model';
import { ProjectBucketIdService } from 'app/entities/project-bucket-id';
import { IProjectCostCenterId } from 'app/shared/model/project-cost-center-id.model';
import { ProjectCostCenterIdService } from 'app/entities/project-cost-center-id';
import { IOpportunityPriorityId } from 'app/shared/model/opportunity-priority-id.model';
import { OpportunityPriorityIdService } from 'app/entities/opportunity-priority-id';
import { IBacklogPractice } from 'app/shared/model/backlog-practice.model';
import { BacklogPracticeService } from 'app/entities/backlog-practice';
import { IProjectTheme } from 'app/shared/model/project-theme.model';
import { ProjectThemeService } from 'app/entities/project-theme';
import { IProjectClass } from 'app/shared/model/project-class.model';
import { ProjectClassService } from 'app/entities/project-class';
import { IProjectVertical } from 'app/shared/model/project-vertical.model';
import { ProjectVerticalService } from 'app/entities/project-vertical';
import { IProjectBoardId } from 'app/shared/model/project-board-id.model';
import { ProjectBoardIdService } from 'app/entities/project-board-id';
import { IProjectStatusId } from 'app/shared/model/project-status-id.model';
import { ProjectStatusIdService } from 'app/entities/project-status-id';
import { IProjectTypeId } from 'app/shared/model/project-type-id.model';
import { ProjectTypeIdService } from 'app/entities/project-type-id';
import { IImEmployee } from 'app/shared/model/im-employee.model';
import { ImEmployeeService } from 'app/entities/im-employee';

@Component({
  selector: 'jhi-im-projects-update',
  templateUrl: './im-projects-update.component.html'
})
export class ImProjectsUpdateComponent implements OnInit {
  imProjects: IImProjects;
  isSaving: boolean;

  projectinitiativeids: IProjectInitiativeId[];

  projectbusinessgoalids: IProjectBusinessgoalId[];

  projectsubgoalids: IProjectSubgoalId[];

  projectmaingoalids: IProjectMaingoalId[];

  projectbucketids: IProjectBucketId[];

  projectcostcenterids: IProjectCostCenterId[];

  opportunitypriorityids: IOpportunityPriorityId[];

  backlogpractices: IBacklogPractice[];

  projectthemes: IProjectTheme[];

  projectclasses: IProjectClass[];

  projectverticals: IProjectVertical[];

  projectboardids: IProjectBoardId[];

  projectstatusids: IProjectStatusId[];

  projecttypeids: IProjectTypeId[];

  imemployees: IImEmployee[];

  improjects: IImProjects[];

  editForm = this.fb.group({
    id: [],
    projectName: [null, [Validators.required]],
    projectNr: [null, [Validators.required]],
    projectPath: [null, [Validators.required]],
    treeSortkey: [],
    maxChildSortkey: [],
    description: [],
    billingTypeId: [],
    note: [],
    requiresReportP: [],
    projectBudget: [],
    projectRisk: [],
    corporateSponsor: [],
    percentCompleted: [],
    projectBudgetHours: [],
    costQuotesCache: [],
    costInvoicesCache: [],
    costTimesheetPlannedCache: [],
    costPurchaseOrdersCache: [],
    costBillsCache: [],
    costTimesheetLoggedCache: [],
    endDate: [],
    startDate: [],
    templateP: [],
    sortOrder: [],
    reportedHoursCache: [],
    costExpensePlannedCache: [],
    costExpenseLoggedCache: [],
    confirmDate: [],
    costDeliveryNotesCache: [],
    costCacheDirty: [],
    milestoneP: [],
    releaseItemP: [],
    presalesProbability: [],
    presalesValue: [],
    reportedDaysCache: [],
    presalesValueCurrency: [],
    opportunitySalesStageId: [],
    opportunityCampaignId: [],
    scoreRevenue: [],
    scoreStrategic: [],
    scoreFinanceNpv: [],
    scoreCustomers: [],
    scoreFinanceCost: [],
    costBillsPlanned: [],
    costExpensesPlanned: [],
    scoreRisk: [],
    scoreCapabilities: [],
    scoreEinanceRoi: [],
    projectUserwiseBoard: [],
    projectBringNextday: [],
    projectBringSameboard: [],
    projectNewboardEverytime: [],
    projectUserwiseBoard2: [],
    projectBringSameboard2: [],
    projectNewboard2Everytime: [],
    projectNewboard2Always: [],
    projectReportWeekly: [],
    scoreGain: [],
    scoreLoss: [],
    scoreDelivery: [],
    scoreOperations: [],
    scoreWhy: [],
    javaServices: [],
    netServices: [],
    collectionLink: [],
    trainingLink: [],
    collectionName: [],
    trainingName: [],
    trainingDoc: [],
    testingRichtext: [],
    templateCategory: [],
    dType: [],
    dOption: [],
    dFilter: [],
    dId: [],
    tType: [],
    tOption: [],
    tFilter: [],
    tId: [],
    risktype: [],
    riskimpact: [],
    riskprobability: [],
    projectInitiativeId: [],
    projectBusinessgoalId: [],
    projectSubgoalId: [],
    projectMaingoalId: [],
    projectBucketId: [],
    projectCostCenterId: [],
    opportunityPriorityId: [],
    backlogPractice: [],
    projectTheme: [],
    projectClass: [],
    projectVertical: [],
    projectBoardId: [],
    projectBoard2Id: [],
    projectStatusId: [],
    projectTypeId: [],
    projectLeadId: [],
    parentId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected imProjectsService: ImProjectsService,
    protected projectInitiativeIdService: ProjectInitiativeIdService,
    protected projectBusinessgoalIdService: ProjectBusinessgoalIdService,
    protected projectSubgoalIdService: ProjectSubgoalIdService,
    protected projectMaingoalIdService: ProjectMaingoalIdService,
    protected projectBucketIdService: ProjectBucketIdService,
    protected projectCostCenterIdService: ProjectCostCenterIdService,
    protected opportunityPriorityIdService: OpportunityPriorityIdService,
    protected backlogPracticeService: BacklogPracticeService,
    protected projectThemeService: ProjectThemeService,
    protected projectClassService: ProjectClassService,
    protected projectVerticalService: ProjectVerticalService,
    protected projectBoardIdService: ProjectBoardIdService,
    protected projectStatusIdService: ProjectStatusIdService,
    protected projectTypeIdService: ProjectTypeIdService,
    protected imEmployeeService: ImEmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ imProjects }) => {
      this.updateForm(imProjects);
      this.imProjects = imProjects;
    });
    this.projectInitiativeIdService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectInitiativeId[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectInitiativeId[]>) => response.body)
      )
      .subscribe((res: IProjectInitiativeId[]) => (this.projectinitiativeids = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.projectBusinessgoalIdService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectBusinessgoalId[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectBusinessgoalId[]>) => response.body)
      )
      .subscribe(
        (res: IProjectBusinessgoalId[]) => (this.projectbusinessgoalids = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.projectSubgoalIdService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectSubgoalId[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectSubgoalId[]>) => response.body)
      )
      .subscribe((res: IProjectSubgoalId[]) => (this.projectsubgoalids = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.projectMaingoalIdService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectMaingoalId[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectMaingoalId[]>) => response.body)
      )
      .subscribe((res: IProjectMaingoalId[]) => (this.projectmaingoalids = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.projectBucketIdService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectBucketId[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectBucketId[]>) => response.body)
      )
      .subscribe((res: IProjectBucketId[]) => (this.projectbucketids = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.projectCostCenterIdService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectCostCenterId[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectCostCenterId[]>) => response.body)
      )
      .subscribe((res: IProjectCostCenterId[]) => (this.projectcostcenterids = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.opportunityPriorityIdService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOpportunityPriorityId[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOpportunityPriorityId[]>) => response.body)
      )
      .subscribe(
        (res: IOpportunityPriorityId[]) => (this.opportunitypriorityids = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.backlogPracticeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBacklogPractice[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBacklogPractice[]>) => response.body)
      )
      .subscribe((res: IBacklogPractice[]) => (this.backlogpractices = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.projectThemeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectTheme[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectTheme[]>) => response.body)
      )
      .subscribe((res: IProjectTheme[]) => (this.projectthemes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.projectClassService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectClass[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectClass[]>) => response.body)
      )
      .subscribe((res: IProjectClass[]) => (this.projectclasses = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.projectVerticalService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectVertical[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectVertical[]>) => response.body)
      )
      .subscribe((res: IProjectVertical[]) => (this.projectverticals = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.projectBoardIdService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectBoardId[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectBoardId[]>) => response.body)
      )
      .subscribe((res: IProjectBoardId[]) => (this.projectboardids = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.projectStatusIdService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectStatusId[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectStatusId[]>) => response.body)
      )
      .subscribe((res: IProjectStatusId[]) => (this.projectstatusids = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.projectTypeIdService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectTypeId[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectTypeId[]>) => response.body)
      )
      .subscribe((res: IProjectTypeId[]) => (this.projecttypeids = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.imEmployeeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IImEmployee[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImEmployee[]>) => response.body)
      )
      .subscribe((res: IImEmployee[]) => (this.imemployees = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.imProjectsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IImProjects[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImProjects[]>) => response.body)
      )
      .subscribe((res: IImProjects[]) => (this.improjects = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(imProjects: IImProjects) {
    this.editForm.patchValue({
      id: imProjects.id,
      projectName: imProjects.projectName,
      projectNr: imProjects.projectNr,
      projectPath: imProjects.projectPath,
      treeSortkey: imProjects.treeSortkey,
      maxChildSortkey: imProjects.maxChildSortkey,
      description: imProjects.description,
      billingTypeId: imProjects.billingTypeId,
      note: imProjects.note,
      requiresReportP: imProjects.requiresReportP,
      projectBudget: imProjects.projectBudget,
      projectRisk: imProjects.projectRisk,
      corporateSponsor: imProjects.corporateSponsor,
      percentCompleted: imProjects.percentCompleted,
      projectBudgetHours: imProjects.projectBudgetHours,
      costQuotesCache: imProjects.costQuotesCache,
      costInvoicesCache: imProjects.costInvoicesCache,
      costTimesheetPlannedCache: imProjects.costTimesheetPlannedCache,
      costPurchaseOrdersCache: imProjects.costPurchaseOrdersCache,
      costBillsCache: imProjects.costBillsCache,
      costTimesheetLoggedCache: imProjects.costTimesheetLoggedCache,
      endDate: imProjects.endDate != null ? imProjects.endDate.format(DATE_TIME_FORMAT) : null,
      startDate: imProjects.startDate != null ? imProjects.startDate.format(DATE_TIME_FORMAT) : null,
      templateP: imProjects.templateP,
      sortOrder: imProjects.sortOrder,
      reportedHoursCache: imProjects.reportedHoursCache,
      costExpensePlannedCache: imProjects.costExpensePlannedCache,
      costExpenseLoggedCache: imProjects.costExpenseLoggedCache,
      confirmDate: imProjects.confirmDate != null ? imProjects.confirmDate.format(DATE_TIME_FORMAT) : null,
      costDeliveryNotesCache: imProjects.costDeliveryNotesCache,
      costCacheDirty: imProjects.costCacheDirty != null ? imProjects.costCacheDirty.format(DATE_TIME_FORMAT) : null,
      milestoneP: imProjects.milestoneP,
      releaseItemP: imProjects.releaseItemP,
      presalesProbability: imProjects.presalesProbability,
      presalesValue: imProjects.presalesValue,
      reportedDaysCache: imProjects.reportedDaysCache,
      presalesValueCurrency: imProjects.presalesValueCurrency,
      opportunitySalesStageId: imProjects.opportunitySalesStageId,
      opportunityCampaignId: imProjects.opportunityCampaignId,
      scoreRevenue: imProjects.scoreRevenue,
      scoreStrategic: imProjects.scoreStrategic,
      scoreFinanceNpv: imProjects.scoreFinanceNpv,
      scoreCustomers: imProjects.scoreCustomers,
      scoreFinanceCost: imProjects.scoreFinanceCost,
      costBillsPlanned: imProjects.costBillsPlanned,
      costExpensesPlanned: imProjects.costExpensesPlanned,
      scoreRisk: imProjects.scoreRisk,
      scoreCapabilities: imProjects.scoreCapabilities,
      scoreEinanceRoi: imProjects.scoreEinanceRoi,
      projectUserwiseBoard: imProjects.projectUserwiseBoard,
      projectBringNextday: imProjects.projectBringNextday,
      projectBringSameboard: imProjects.projectBringSameboard,
      projectNewboardEverytime: imProjects.projectNewboardEverytime,
      projectUserwiseBoard2: imProjects.projectUserwiseBoard2,
      projectBringSameboard2: imProjects.projectBringSameboard2,
      projectNewboard2Everytime: imProjects.projectNewboard2Everytime,
      projectNewboard2Always: imProjects.projectNewboard2Always,
      projectReportWeekly: imProjects.projectReportWeekly,
      scoreGain: imProjects.scoreGain,
      scoreLoss: imProjects.scoreLoss,
      scoreDelivery: imProjects.scoreDelivery,
      scoreOperations: imProjects.scoreOperations,
      scoreWhy: imProjects.scoreWhy,
      javaServices: imProjects.javaServices,
      netServices: imProjects.netServices,
      collectionLink: imProjects.collectionLink,
      trainingLink: imProjects.trainingLink,
      collectionName: imProjects.collectionName,
      trainingName: imProjects.trainingName,
      trainingDoc: imProjects.trainingDoc,
      testingRichtext: imProjects.testingRichtext,
      templateCategory: imProjects.templateCategory,
      dType: imProjects.dType,
      dOption: imProjects.dOption,
      dFilter: imProjects.dFilter,
      dId: imProjects.dId,
      tType: imProjects.tType,
      tOption: imProjects.tOption,
      tFilter: imProjects.tFilter,
      tId: imProjects.tId,
      risktype: imProjects.risktype,
      riskimpact: imProjects.riskimpact,
      riskprobability: imProjects.riskprobability,
      projectInitiativeId: imProjects.projectInitiativeId,
      projectBusinessgoalId: imProjects.projectBusinessgoalId,
      projectSubgoalId: imProjects.projectSubgoalId,
      projectMaingoalId: imProjects.projectMaingoalId,
      projectBucketId: imProjects.projectBucketId,
      projectCostCenterId: imProjects.projectCostCenterId,
      opportunityPriorityId: imProjects.opportunityPriorityId,
      backlogPractice: imProjects.backlogPractice,
      projectTheme: imProjects.projectTheme,
      projectClass: imProjects.projectClass,
      projectVertical: imProjects.projectVertical,
      projectBoardId: imProjects.projectBoardId,
      projectBoard2Id: imProjects.projectBoard2Id,
      projectStatusId: imProjects.projectStatusId,
      projectTypeId: imProjects.projectTypeId,
      projectLeadId: imProjects.projectLeadId,
      parentId: imProjects.parentId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const imProjects = this.createFromForm();
    if (imProjects.id !== undefined) {
      this.subscribeToSaveResponse(this.imProjectsService.update(imProjects));
    } else {
      this.subscribeToSaveResponse(this.imProjectsService.create(imProjects));
    }
  }

  private createFromForm(): IImProjects {
    const entity = {
      ...new ImProjects(),
      id: this.editForm.get(['id']).value,
      projectName: this.editForm.get(['projectName']).value,
      projectNr: this.editForm.get(['projectNr']).value,
      projectPath: this.editForm.get(['projectPath']).value,
      treeSortkey: this.editForm.get(['treeSortkey']).value,
      maxChildSortkey: this.editForm.get(['maxChildSortkey']).value,
      description: this.editForm.get(['description']).value,
      billingTypeId: this.editForm.get(['billingTypeId']).value,
      note: this.editForm.get(['note']).value,
      requiresReportP: this.editForm.get(['requiresReportP']).value,
      projectBudget: this.editForm.get(['projectBudget']).value,
      projectRisk: this.editForm.get(['projectRisk']).value,
      corporateSponsor: this.editForm.get(['corporateSponsor']).value,
      percentCompleted: this.editForm.get(['percentCompleted']).value,
      projectBudgetHours: this.editForm.get(['projectBudgetHours']).value,
      costQuotesCache: this.editForm.get(['costQuotesCache']).value,
      costInvoicesCache: this.editForm.get(['costInvoicesCache']).value,
      costTimesheetPlannedCache: this.editForm.get(['costTimesheetPlannedCache']).value,
      costPurchaseOrdersCache: this.editForm.get(['costPurchaseOrdersCache']).value,
      costBillsCache: this.editForm.get(['costBillsCache']).value,
      costTimesheetLoggedCache: this.editForm.get(['costTimesheetLoggedCache']).value,
      endDate: this.editForm.get(['endDate']).value != null ? moment(this.editForm.get(['endDate']).value, DATE_TIME_FORMAT) : undefined,
      startDate:
        this.editForm.get(['startDate']).value != null ? moment(this.editForm.get(['startDate']).value, DATE_TIME_FORMAT) : undefined,
      templateP: this.editForm.get(['templateP']).value,
      sortOrder: this.editForm.get(['sortOrder']).value,
      reportedHoursCache: this.editForm.get(['reportedHoursCache']).value,
      costExpensePlannedCache: this.editForm.get(['costExpensePlannedCache']).value,
      costExpenseLoggedCache: this.editForm.get(['costExpenseLoggedCache']).value,
      confirmDate:
        this.editForm.get(['confirmDate']).value != null ? moment(this.editForm.get(['confirmDate']).value, DATE_TIME_FORMAT) : undefined,
      costDeliveryNotesCache: this.editForm.get(['costDeliveryNotesCache']).value,
      costCacheDirty:
        this.editForm.get(['costCacheDirty']).value != null
          ? moment(this.editForm.get(['costCacheDirty']).value, DATE_TIME_FORMAT)
          : undefined,
      milestoneP: this.editForm.get(['milestoneP']).value,
      releaseItemP: this.editForm.get(['releaseItemP']).value,
      presalesProbability: this.editForm.get(['presalesProbability']).value,
      presalesValue: this.editForm.get(['presalesValue']).value,
      reportedDaysCache: this.editForm.get(['reportedDaysCache']).value,
      presalesValueCurrency: this.editForm.get(['presalesValueCurrency']).value,
      opportunitySalesStageId: this.editForm.get(['opportunitySalesStageId']).value,
      opportunityCampaignId: this.editForm.get(['opportunityCampaignId']).value,
      scoreRevenue: this.editForm.get(['scoreRevenue']).value,
      scoreStrategic: this.editForm.get(['scoreStrategic']).value,
      scoreFinanceNpv: this.editForm.get(['scoreFinanceNpv']).value,
      scoreCustomers: this.editForm.get(['scoreCustomers']).value,
      scoreFinanceCost: this.editForm.get(['scoreFinanceCost']).value,
      costBillsPlanned: this.editForm.get(['costBillsPlanned']).value,
      costExpensesPlanned: this.editForm.get(['costExpensesPlanned']).value,
      scoreRisk: this.editForm.get(['scoreRisk']).value,
      scoreCapabilities: this.editForm.get(['scoreCapabilities']).value,
      scoreEinanceRoi: this.editForm.get(['scoreEinanceRoi']).value,
      projectUserwiseBoard: this.editForm.get(['projectUserwiseBoard']).value,
      projectBringNextday: this.editForm.get(['projectBringNextday']).value,
      projectBringSameboard: this.editForm.get(['projectBringSameboard']).value,
      projectNewboardEverytime: this.editForm.get(['projectNewboardEverytime']).value,
      projectUserwiseBoard2: this.editForm.get(['projectUserwiseBoard2']).value,
      projectBringSameboard2: this.editForm.get(['projectBringSameboard2']).value,
      projectNewboard2Everytime: this.editForm.get(['projectNewboard2Everytime']).value,
      projectNewboard2Always: this.editForm.get(['projectNewboard2Always']).value,
      projectReportWeekly: this.editForm.get(['projectReportWeekly']).value,
      scoreGain: this.editForm.get(['scoreGain']).value,
      scoreLoss: this.editForm.get(['scoreLoss']).value,
      scoreDelivery: this.editForm.get(['scoreDelivery']).value,
      scoreOperations: this.editForm.get(['scoreOperations']).value,
      scoreWhy: this.editForm.get(['scoreWhy']).value,
      javaServices: this.editForm.get(['javaServices']).value,
      netServices: this.editForm.get(['netServices']).value,
      collectionLink: this.editForm.get(['collectionLink']).value,
      trainingLink: this.editForm.get(['trainingLink']).value,
      collectionName: this.editForm.get(['collectionName']).value,
      trainingName: this.editForm.get(['trainingName']).value,
      trainingDoc: this.editForm.get(['trainingDoc']).value,
      testingRichtext: this.editForm.get(['testingRichtext']).value,
      templateCategory: this.editForm.get(['templateCategory']).value,
      dType: this.editForm.get(['dType']).value,
      dOption: this.editForm.get(['dOption']).value,
      dFilter: this.editForm.get(['dFilter']).value,
      dId: this.editForm.get(['dId']).value,
      tType: this.editForm.get(['tType']).value,
      tOption: this.editForm.get(['tOption']).value,
      tFilter: this.editForm.get(['tFilter']).value,
      tId: this.editForm.get(['tId']).value,
      risktype: this.editForm.get(['risktype']).value,
      riskimpact: this.editForm.get(['riskimpact']).value,
      riskprobability: this.editForm.get(['riskprobability']).value,
      projectInitiativeId: this.editForm.get(['projectInitiativeId']).value,
      projectBusinessgoalId: this.editForm.get(['projectBusinessgoalId']).value,
      projectSubgoalId: this.editForm.get(['projectSubgoalId']).value,
      projectMaingoalId: this.editForm.get(['projectMaingoalId']).value,
      projectBucketId: this.editForm.get(['projectBucketId']).value,
      projectCostCenterId: this.editForm.get(['projectCostCenterId']).value,
      opportunityPriorityId: this.editForm.get(['opportunityPriorityId']).value,
      backlogPractice: this.editForm.get(['backlogPractice']).value,
      projectTheme: this.editForm.get(['projectTheme']).value,
      projectClass: this.editForm.get(['projectClass']).value,
      projectVertical: this.editForm.get(['projectVertical']).value,
      projectBoardId: this.editForm.get(['projectBoardId']).value,
      projectBoard2Id: this.editForm.get(['projectBoard2Id']).value,
      projectStatusId: this.editForm.get(['projectStatusId']).value,
      projectTypeId: this.editForm.get(['projectTypeId']).value,
      projectLeadId: this.editForm.get(['projectLeadId']).value,
      parentId: this.editForm.get(['parentId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImProjects>>) {
    result.subscribe((res: HttpResponse<IImProjects>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProjectInitiativeIdById(index: number, item: IProjectInitiativeId) {
    return item.id;
  }

  trackProjectBusinessgoalIdById(index: number, item: IProjectBusinessgoalId) {
    return item.id;
  }

  trackProjectSubgoalIdById(index: number, item: IProjectSubgoalId) {
    return item.id;
  }

  trackProjectMaingoalIdById(index: number, item: IProjectMaingoalId) {
    return item.id;
  }

  trackProjectBucketIdById(index: number, item: IProjectBucketId) {
    return item.id;
  }

  trackProjectCostCenterIdById(index: number, item: IProjectCostCenterId) {
    return item.id;
  }

  trackOpportunityPriorityIdById(index: number, item: IOpportunityPriorityId) {
    return item.id;
  }

  trackBacklogPracticeById(index: number, item: IBacklogPractice) {
    return item.id;
  }

  trackProjectThemeById(index: number, item: IProjectTheme) {
    return item.id;
  }

  trackProjectClassById(index: number, item: IProjectClass) {
    return item.id;
  }

  trackProjectVerticalById(index: number, item: IProjectVertical) {
    return item.id;
  }

  trackProjectBoardIdById(index: number, item: IProjectBoardId) {
    return item.id;
  }

  trackProjectStatusIdById(index: number, item: IProjectStatusId) {
    return item.id;
  }

  trackProjectTypeIdById(index: number, item: IProjectTypeId) {
    return item.id;
  }

  trackImEmployeeById(index: number, item: IImEmployee) {
    return item.id;
  }

  trackImProjectsById(index: number, item: IImProjects) {
    return item.id;
  }
}
