import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IOpportunityPriorityId, OpportunityPriorityId } from 'app/shared/model/opportunity-priority-id.model';
import { OpportunityPriorityIdService } from './opportunity-priority-id.service';

@Component({
  selector: 'jhi-opportunity-priority-id-update',
  templateUrl: './opportunity-priority-id-update.component.html'
})
export class OpportunityPriorityIdUpdateComponent implements OnInit {
  opportunityPriorityId: IOpportunityPriorityId;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(
    protected opportunityPriorityIdService: OpportunityPriorityIdService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ opportunityPriorityId }) => {
      this.updateForm(opportunityPriorityId);
      this.opportunityPriorityId = opportunityPriorityId;
    });
  }

  updateForm(opportunityPriorityId: IOpportunityPriorityId) {
    this.editForm.patchValue({
      id: opportunityPriorityId.id,
      code: opportunityPriorityId.code,
      name: opportunityPriorityId.name,
      description: opportunityPriorityId.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const opportunityPriorityId = this.createFromForm();
    if (opportunityPriorityId.id !== undefined) {
      this.subscribeToSaveResponse(this.opportunityPriorityIdService.update(opportunityPriorityId));
    } else {
      this.subscribeToSaveResponse(this.opportunityPriorityIdService.create(opportunityPriorityId));
    }
  }

  private createFromForm(): IOpportunityPriorityId {
    const entity = {
      ...new OpportunityPriorityId(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOpportunityPriorityId>>) {
    result.subscribe((res: HttpResponse<IOpportunityPriorityId>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
