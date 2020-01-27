/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ImTimesheetService } from 'app/entities/im-timesheet/im-timesheet.service';
import { IImTimesheet, ImTimesheet } from 'app/shared/model/im-timesheet.model';

describe('Service Tests', () => {
  describe('ImTimesheet Service', () => {
    let injector: TestBed;
    let service: ImTimesheetService;
    let httpMock: HttpTestingController;
    let elemDefault: IImTimesheet;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ImTimesheetService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ImTimesheet(0, currentDate, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            logdate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a ImTimesheet', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            logdate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            logdate: currentDate
          },
          returnedFromService
        );
        service
          .create(new ImTimesheet(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ImTimesheet', async () => {
        const returnedFromService = Object.assign(
          {
            logdate: currentDate.format(DATE_TIME_FORMAT),
            loghours: 1,
            billhours: 1,
            notes: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            logdate: currentDate
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

      it('should return a list of ImTimesheet', async () => {
        const returnedFromService = Object.assign(
          {
            logdate: currentDate.format(DATE_TIME_FORMAT),
            loghours: 1,
            billhours: 1,
            notes: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            logdate: currentDate
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

      it('should delete a ImTimesheet', async () => {
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
