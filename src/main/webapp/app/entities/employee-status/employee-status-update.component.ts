import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEmployeeStatus, EmployeeStatus } from 'app/shared/model/employee-status.model';
import { EmployeeStatusService } from './employee-status.service';

@Component({
  selector: 'jhi-employee-status-update',
  templateUrl: './employee-status-update.component.html'
})
export class EmployeeStatusUpdateComponent implements OnInit {
  employeeStatus: IEmployeeStatus;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    statusCode: [],
    statusName: []
  });

  constructor(protected employeeStatusService: EmployeeStatusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ employeeStatus }) => {
      this.updateForm(employeeStatus);
      this.employeeStatus = employeeStatus;
    });
  }

  updateForm(employeeStatus: IEmployeeStatus) {
    this.editForm.patchValue({
      id: employeeStatus.id,
      statusCode: employeeStatus.statusCode,
      statusName: employeeStatus.statusName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const employeeStatus = this.createFromForm();
    if (employeeStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeStatusService.update(employeeStatus));
    } else {
      this.subscribeToSaveResponse(this.employeeStatusService.create(employeeStatus));
    }
  }

  private createFromForm(): IEmployeeStatus {
    const entity = {
      ...new EmployeeStatus(),
      id: this.editForm.get(['id']).value,
      statusCode: this.editForm.get(['statusCode']).value,
      statusName: this.editForm.get(['statusName']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeStatus>>) {
    result.subscribe((res: HttpResponse<IEmployeeStatus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
