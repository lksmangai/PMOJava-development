import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBacklogPractice, BacklogPractice } from 'app/shared/model/backlog-practice.model';
import { BacklogPracticeService } from './backlog-practice.service';

@Component({
  selector: 'jhi-backlog-practice-update',
  templateUrl: './backlog-practice-update.component.html'
})
export class BacklogPracticeUpdateComponent implements OnInit {
  backlogPractice: IBacklogPractice;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(
    protected backlogPracticeService: BacklogPracticeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ backlogPractice }) => {
      this.updateForm(backlogPractice);
      this.backlogPractice = backlogPractice;
    });
  }

  updateForm(backlogPractice: IBacklogPractice) {
    this.editForm.patchValue({
      id: backlogPractice.id,
      code: backlogPractice.code,
      name: backlogPractice.name,
      description: backlogPractice.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const backlogPractice = this.createFromForm();
    if (backlogPractice.id !== undefined) {
      this.subscribeToSaveResponse(this.backlogPracticeService.update(backlogPractice));
    } else {
      this.subscribeToSaveResponse(this.backlogPracticeService.create(backlogPractice));
    }
  }

  private createFromForm(): IBacklogPractice {
    const entity = {
      ...new BacklogPractice(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBacklogPractice>>) {
    result.subscribe((res: HttpResponse<IBacklogPractice>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
