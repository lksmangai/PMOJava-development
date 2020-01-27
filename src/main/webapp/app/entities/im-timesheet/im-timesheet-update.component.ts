import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IImTimesheet, ImTimesheet } from 'app/shared/model/im-timesheet.model';
import { ImTimesheetService } from './im-timesheet.service';
import { IImEmployee } from 'app/shared/model/im-employee.model';
import { ImEmployeeService } from 'app/entities/im-employee';
import { IImProjects } from 'app/shared/model/im-projects.model';
import { ImProjectsService } from 'app/entities/im-projects';

@Component({
  selector: 'jhi-im-timesheet-update',
  templateUrl: './im-timesheet-update.component.html'
})
export class ImTimesheetUpdateComponent implements OnInit {
  imTimesheet: IImTimesheet;
  isSaving: boolean;

  imemployees: IImEmployee[];

  improjects: IImProjects[];

  editForm = this.fb.group({
    id: [],
    logdate: [null, [Validators.required]],
    loghours: [],
    billhours: [],
    notes: [],
    imEmployee: [],
    imProjects: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected imTimesheetService: ImTimesheetService,
    protected imEmployeeService: ImEmployeeService,
    protected imProjectsService: ImProjectsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ imTimesheet }) => {
      this.updateForm(imTimesheet);
      this.imTimesheet = imTimesheet;
    });
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

  updateForm(imTimesheet: IImTimesheet) {
    this.editForm.patchValue({
      id: imTimesheet.id,
      logdate: imTimesheet.logdate != null ? imTimesheet.logdate.format(DATE_TIME_FORMAT) : null,
      loghours: imTimesheet.loghours,
      billhours: imTimesheet.billhours,
      notes: imTimesheet.notes,
      imEmployee: imTimesheet.imEmployee,
      imProjects: imTimesheet.imProjects
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const imTimesheet = this.createFromForm();
    if (imTimesheet.id !== undefined) {
      this.subscribeToSaveResponse(this.imTimesheetService.update(imTimesheet));
    } else {
      this.subscribeToSaveResponse(this.imTimesheetService.create(imTimesheet));
    }
  }

  private createFromForm(): IImTimesheet {
    const entity = {
      ...new ImTimesheet(),
      id: this.editForm.get(['id']).value,
      logdate: this.editForm.get(['logdate']).value != null ? moment(this.editForm.get(['logdate']).value, DATE_TIME_FORMAT) : undefined,
      loghours: this.editForm.get(['loghours']).value,
      billhours: this.editForm.get(['billhours']).value,
      notes: this.editForm.get(['notes']).value,
      imEmployee: this.editForm.get(['imEmployee']).value,
      imProjects: this.editForm.get(['imProjects']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImTimesheet>>) {
    result.subscribe((res: HttpResponse<IImTimesheet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackImEmployeeById(index: number, item: IImEmployee) {
    return item.id;
  }

  trackImProjectsById(index: number, item: IImProjects) {
    return item.id;
  }
}
