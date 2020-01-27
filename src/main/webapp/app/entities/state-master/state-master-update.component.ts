import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IStateMaster, StateMaster } from 'app/shared/model/state-master.model';
import { StateMasterService } from './state-master.service';

@Component({
  selector: 'jhi-state-master-update',
  templateUrl: './state-master-update.component.html'
})
export class StateMasterUpdateComponent implements OnInit {
  stateMaster: IStateMaster;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    stateCode: [],
    stateName: []
  });

  constructor(protected stateMasterService: StateMasterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stateMaster }) => {
      this.updateForm(stateMaster);
      this.stateMaster = stateMaster;
    });
  }

  updateForm(stateMaster: IStateMaster) {
    this.editForm.patchValue({
      id: stateMaster.id,
      stateCode: stateMaster.stateCode,
      stateName: stateMaster.stateName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const stateMaster = this.createFromForm();
    if (stateMaster.id !== undefined) {
      this.subscribeToSaveResponse(this.stateMasterService.update(stateMaster));
    } else {
      this.subscribeToSaveResponse(this.stateMasterService.create(stateMaster));
    }
  }

  private createFromForm(): IStateMaster {
    const entity = {
      ...new StateMaster(),
      id: this.editForm.get(['id']).value,
      stateCode: this.editForm.get(['stateCode']).value,
      stateName: this.editForm.get(['stateName']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStateMaster>>) {
    result.subscribe((res: HttpResponse<IStateMaster>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
