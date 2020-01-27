import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IImEmployee, ImEmployee } from 'app/shared/model/im-employee.model';
import { ImEmployeeService } from './im-employee.service';
import { IQnowUser } from 'app/shared/model/qnow-user.model';
import { QnowUserService } from 'app/entities/qnow-user';
import { IEmployeeStatus } from 'app/shared/model/employee-status.model';
import { EmployeeStatusService } from 'app/entities/employee-status';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department';
import { IUserContact } from 'app/shared/model/user-contact.model';
import { UserContactService } from 'app/entities/user-contact';

@Component({
  selector: 'jhi-im-employee-update',
  templateUrl: './im-employee-update.component.html'
})
export class ImEmployeeUpdateComponent implements OnInit {
  imEmployee: IImEmployee;
  isSaving: boolean;

  qnowusers: IQnowUser[];

  employeestatuses: IEmployeeStatus[];

  departments: IDepartment[];

  imemployees: IImEmployee[];

  usercontacts: IUserContact[];

  editForm = this.fb.group({
    id: [],
    jobTitle: [],
    jobDescription: [],
    availability: [],
    ssNumber: [],
    salary: [],
    socialSecurity: [],
    insurance: [],
    otherCosts: [],
    currency: [],
    dependantP: [],
    onlyJobP: [],
    marriedP: [],
    headOfHouseholdP: [],
    birthdate: [],
    hourlyCost: [],
    qnowUser: [],
    employeeStatus: [],
    departmentId: [],
    supervisorId: [],
    userContacts: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected imEmployeeService: ImEmployeeService,
    protected qnowUserService: QnowUserService,
    protected employeeStatusService: EmployeeStatusService,
    protected departmentService: DepartmentService,
    protected userContactService: UserContactService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ imEmployee }) => {
      this.updateForm(imEmployee);
      this.imEmployee = imEmployee;
    });
    this.qnowUserService
      .query({ 'imEmployeeId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IQnowUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQnowUser[]>) => response.body)
      )
      .subscribe(
        (res: IQnowUser[]) => {
          if (!this.imEmployee.qnowUser || !this.imEmployee.qnowUser.id) {
            this.qnowusers = res;
          } else {
            this.qnowUserService
              .find(this.imEmployee.qnowUser.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IQnowUser>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IQnowUser>) => subResponse.body)
              )
              .subscribe(
                (subRes: IQnowUser) => (this.qnowusers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.employeeStatusService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmployeeStatus[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmployeeStatus[]>) => response.body)
      )
      .subscribe((res: IEmployeeStatus[]) => (this.employeestatuses = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.departmentService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDepartment[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDepartment[]>) => response.body)
      )
      .subscribe((res: IDepartment[]) => (this.departments = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.imEmployeeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IImEmployee[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImEmployee[]>) => response.body)
      )
      .subscribe((res: IImEmployee[]) => (this.imemployees = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userContactService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUserContact[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserContact[]>) => response.body)
      )
      .subscribe((res: IUserContact[]) => (this.usercontacts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(imEmployee: IImEmployee) {
    this.editForm.patchValue({
      id: imEmployee.id,
      jobTitle: imEmployee.jobTitle,
      jobDescription: imEmployee.jobDescription,
      availability: imEmployee.availability,
      ssNumber: imEmployee.ssNumber,
      salary: imEmployee.salary,
      socialSecurity: imEmployee.socialSecurity,
      insurance: imEmployee.insurance,
      otherCosts: imEmployee.otherCosts,
      currency: imEmployee.currency,
      dependantP: imEmployee.dependantP,
      onlyJobP: imEmployee.onlyJobP,
      marriedP: imEmployee.marriedP,
      headOfHouseholdP: imEmployee.headOfHouseholdP,
      birthdate: imEmployee.birthdate != null ? imEmployee.birthdate.format(DATE_TIME_FORMAT) : null,
      hourlyCost: imEmployee.hourlyCost,
      qnowUser: imEmployee.qnowUser,
      employeeStatus: imEmployee.employeeStatus,
      departmentId: imEmployee.departmentId,
      supervisorId: imEmployee.supervisorId,
      userContacts: imEmployee.userContacts
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const imEmployee = this.createFromForm();
    if (imEmployee.id !== undefined) {
      this.subscribeToSaveResponse(this.imEmployeeService.update(imEmployee));
    } else {
      this.subscribeToSaveResponse(this.imEmployeeService.create(imEmployee));
    }
  }

  private createFromForm(): IImEmployee {
    const entity = {
      ...new ImEmployee(),
      id: this.editForm.get(['id']).value,
      jobTitle: this.editForm.get(['jobTitle']).value,
      jobDescription: this.editForm.get(['jobDescription']).value,
      availability: this.editForm.get(['availability']).value,
      ssNumber: this.editForm.get(['ssNumber']).value,
      salary: this.editForm.get(['salary']).value,
      socialSecurity: this.editForm.get(['socialSecurity']).value,
      insurance: this.editForm.get(['insurance']).value,
      otherCosts: this.editForm.get(['otherCosts']).value,
      currency: this.editForm.get(['currency']).value,
      dependantP: this.editForm.get(['dependantP']).value,
      onlyJobP: this.editForm.get(['onlyJobP']).value,
      marriedP: this.editForm.get(['marriedP']).value,
      headOfHouseholdP: this.editForm.get(['headOfHouseholdP']).value,
      birthdate:
        this.editForm.get(['birthdate']).value != null ? moment(this.editForm.get(['birthdate']).value, DATE_TIME_FORMAT) : undefined,
      hourlyCost: this.editForm.get(['hourlyCost']).value,
      qnowUser: this.editForm.get(['qnowUser']).value,
      employeeStatus: this.editForm.get(['employeeStatus']).value,
      departmentId: this.editForm.get(['departmentId']).value,
      supervisorId: this.editForm.get(['supervisorId']).value,
      userContacts: this.editForm.get(['userContacts']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImEmployee>>) {
    result.subscribe((res: HttpResponse<IImEmployee>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackQnowUserById(index: number, item: IQnowUser) {
    return item.id;
  }

  trackEmployeeStatusById(index: number, item: IEmployeeStatus) {
    return item.id;
  }

  trackDepartmentById(index: number, item: IDepartment) {
    return item.id;
  }

  trackImEmployeeById(index: number, item: IImEmployee) {
    return item.id;
  }

  trackUserContactById(index: number, item: IUserContact) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
