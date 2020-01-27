/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ImEmployeeService } from 'app/entities/im-employee/im-employee.service';
import { IImEmployee, ImEmployee } from 'app/shared/model/im-employee.model';

describe('Service Tests', () => {
  describe('ImEmployee Service', () => {
    let injector: TestBed;
    let service: ImEmployeeService;
    let httpMock: HttpTestingController;
    let elemDefault: IImEmployee;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ImEmployeeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ImEmployee(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            birthdate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a ImEmployee', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthdate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            birthdate: currentDate
          },
          returnedFromService
        );
        service
          .create(new ImEmployee(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ImEmployee', async () => {
        const returnedFromService = Object.assign(
          {
            jobTitle: 'BBBBBB',
            jobDescription: 'BBBBBB',
            availability: 1,
            ssNumber: 'BBBBBB',
            salary: 1,
            socialSecurity: 1,
            insurance: 1,
            otherCosts: 1,
            currency: 'BBBBBB',
            dependantP: 'BBBBBB',
            onlyJobP: 'BBBBBB',
            marriedP: 'BBBBBB',
            headOfHouseholdP: 'BBBBBB',
            birthdate: currentDate.format(DATE_TIME_FORMAT),
            hourlyCost: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthdate: currentDate
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

      it('should return a list of ImEmployee', async () => {
        const returnedFromService = Object.assign(
          {
            jobTitle: 'BBBBBB',
            jobDescription: 'BBBBBB',
            availability: 1,
            ssNumber: 'BBBBBB',
            salary: 1,
            socialSecurity: 1,
            insurance: 1,
            otherCosts: 1,
            currency: 'BBBBBB',
            dependantP: 'BBBBBB',
            onlyJobP: 'BBBBBB',
            marriedP: 'BBBBBB',
            headOfHouseholdP: 'BBBBBB',
            birthdate: currentDate.format(DATE_TIME_FORMAT),
            hourlyCost: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            birthdate: currentDate
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

      it('should delete a ImEmployee', async () => {
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
