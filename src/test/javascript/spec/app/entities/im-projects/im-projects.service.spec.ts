/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ImProjectsService } from 'app/entities/im-projects/im-projects.service';
import { IImProjects, ImProjects } from 'app/shared/model/im-projects.model';

describe('Service Tests', () => {
  describe('ImProjects Service', () => {
    let injector: TestBed;
    let service: ImProjectsService;
    let httpMock: HttpTestingController;
    let elemDefault: IImProjects;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ImProjectsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ImProjects(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        false,
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        currentDate,
        currentDate,
        false,
        0,
        0,
        0,
        0,
        currentDate,
        0,
        currentDate,
        false,
        'AAAAAAA',
        0,
        0,
        0,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        0,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            endDate: currentDate.format(DATE_TIME_FORMAT),
            startDate: currentDate.format(DATE_TIME_FORMAT),
            confirmDate: currentDate.format(DATE_TIME_FORMAT),
            costCacheDirty: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a ImProjects', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            endDate: currentDate.format(DATE_TIME_FORMAT),
            startDate: currentDate.format(DATE_TIME_FORMAT),
            confirmDate: currentDate.format(DATE_TIME_FORMAT),
            costCacheDirty: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            endDate: currentDate,
            startDate: currentDate,
            confirmDate: currentDate,
            costCacheDirty: currentDate
          },
          returnedFromService
        );
        service
          .create(new ImProjects(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ImProjects', async () => {
        const returnedFromService = Object.assign(
          {
            projectName: 'BBBBBB',
            projectNr: 'BBBBBB',
            projectPath: 'BBBBBB',
            treeSortkey: 'BBBBBB',
            maxChildSortkey: 'BBBBBB',
            description: 'BBBBBB',
            billingTypeId: 1,
            note: 'BBBBBB',
            requiresReportP: true,
            projectBudget: 1,
            projectRisk: 'BBBBBB',
            corporateSponsor: 'BBBBBB',
            percentCompleted: 1,
            projectBudgetHours: 1,
            costQuotesCache: 1,
            costInvoicesCache: 1,
            costTimesheetPlannedCache: 1,
            costPurchaseOrdersCache: 1,
            costBillsCache: 1,
            costTimesheetLoggedCache: 1,
            endDate: currentDate.format(DATE_TIME_FORMAT),
            startDate: currentDate.format(DATE_TIME_FORMAT),
            templateP: true,
            sortOrder: 1,
            reportedHoursCache: 1,
            costExpensePlannedCache: 1,
            costExpenseLoggedCache: 1,
            confirmDate: currentDate.format(DATE_TIME_FORMAT),
            costDeliveryNotesCache: 1,
            costCacheDirty: currentDate.format(DATE_TIME_FORMAT),
            milestoneP: true,
            releaseItemP: 'BBBBBB',
            presalesProbability: 1,
            presalesValue: 1,
            reportedDaysCache: 1,
            presalesValueCurrency: 'BBBBBB',
            opportunitySalesStageId: 1,
            opportunityCampaignId: 1,
            scoreRevenue: 1,
            scoreStrategic: 1,
            scoreFinanceNpv: 1,
            scoreCustomers: 1,
            scoreFinanceCost: 1,
            costBillsPlanned: 1,
            costExpensesPlanned: 1,
            scoreRisk: 1,
            scoreCapabilities: 1,
            scoreEinanceRoi: 1,
            projectUserwiseBoard: 'BBBBBB',
            projectBringNextday: 1,
            projectBringSameboard: 'BBBBBB',
            projectNewboardEverytime: 'BBBBBB',
            projectUserwiseBoard2: 'BBBBBB',
            projectBringSameboard2: 'BBBBBB',
            projectNewboard2Everytime: 1,
            projectNewboard2Always: 'BBBBBB',
            projectReportWeekly: 'BBBBBB',
            scoreGain: 1,
            scoreLoss: 1,
            scoreDelivery: 1,
            scoreOperations: 1,
            scoreWhy: 1,
            javaServices: 'BBBBBB',
            netServices: 'BBBBBB',
            collectionLink: 'BBBBBB',
            trainingLink: 'BBBBBB',
            collectionName: 'BBBBBB',
            trainingName: 'BBBBBB',
            trainingDoc: 'BBBBBB',
            testingRichtext: 1,
            templateCategory: 'BBBBBB',
            dType: 1,
            dOption: 1,
            dFilter: 1,
            dId: 1,
            tType: 1,
            tOption: 1,
            tFilter: 1,
            tId: 1,
            risktype: 'BBBBBB',
            riskimpact: 1,
            riskprobability: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDate: currentDate,
            startDate: currentDate,
            confirmDate: currentDate,
            costCacheDirty: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of ImProjects', async () => {
        const returnedFromService = Object.assign(
          {
            projectName: 'BBBBBB',
            projectNr: 'BBBBBB',
            projectPath: 'BBBBBB',
            treeSortkey: 'BBBBBB',
            maxChildSortkey: 'BBBBBB',
            description: 'BBBBBB',
            billingTypeId: 1,
            note: 'BBBBBB',
            requiresReportP: true,
            projectBudget: 1,
            projectRisk: 'BBBBBB',
            corporateSponsor: 'BBBBBB',
            percentCompleted: 1,
            projectBudgetHours: 1,
            costQuotesCache: 1,
            costInvoicesCache: 1,
            costTimesheetPlannedCache: 1,
            costPurchaseOrdersCache: 1,
            costBillsCache: 1,
            costTimesheetLoggedCache: 1,
            endDate: currentDate.format(DATE_TIME_FORMAT),
            startDate: currentDate.format(DATE_TIME_FORMAT),
            templateP: true,
            sortOrder: 1,
            reportedHoursCache: 1,
            costExpensePlannedCache: 1,
            costExpenseLoggedCache: 1,
            confirmDate: currentDate.format(DATE_TIME_FORMAT),
            costDeliveryNotesCache: 1,
            costCacheDirty: currentDate.format(DATE_TIME_FORMAT),
            milestoneP: true,
            releaseItemP: 'BBBBBB',
            presalesProbability: 1,
            presalesValue: 1,
            reportedDaysCache: 1,
            presalesValueCurrency: 'BBBBBB',
            opportunitySalesStageId: 1,
            opportunityCampaignId: 1,
            scoreRevenue: 1,
            scoreStrategic: 1,
            scoreFinanceNpv: 1,
            scoreCustomers: 1,
            scoreFinanceCost: 1,
            costBillsPlanned: 1,
            costExpensesPlanned: 1,
            scoreRisk: 1,
            scoreCapabilities: 1,
            scoreEinanceRoi: 1,
            projectUserwiseBoard: 'BBBBBB',
            projectBringNextday: 1,
            projectBringSameboard: 'BBBBBB',
            projectNewboardEverytime: 'BBBBBB',
            projectUserwiseBoard2: 'BBBBBB',
            projectBringSameboard2: 'BBBBBB',
            projectNewboard2Everytime: 1,
            projectNewboard2Always: 'BBBBBB',
            projectReportWeekly: 'BBBBBB',
            scoreGain: 1,
            scoreLoss: 1,
            scoreDelivery: 1,
            scoreOperations: 1,
            scoreWhy: 1,
            javaServices: 'BBBBBB',
            netServices: 'BBBBBB',
            collectionLink: 'BBBBBB',
            trainingLink: 'BBBBBB',
            collectionName: 'BBBBBB',
            trainingName: 'BBBBBB',
            trainingDoc: 'BBBBBB',
            testingRichtext: 1,
            templateCategory: 'BBBBBB',
            dType: 1,
            dOption: 1,
            dFilter: 1,
            dId: 1,
            tType: 1,
            tOption: 1,
            tFilter: 1,
            tId: 1,
            risktype: 'BBBBBB',
            riskimpact: 1,
            riskprobability: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            endDate: currentDate,
            startDate: currentDate,
            confirmDate: currentDate,
            costCacheDirty: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ImProjects', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
