import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProjectCostCenterId, ProjectCostCenterId } from 'app/shared/model/project-cost-center-id.model';
import { ProjectCostCenterIdService } from './project-cost-center-id.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';

@Component({
  selector: 'jhi-project-cost-center-id-update',
  templateUrl: './project-cost-center-id-update.component.html'
})
export class ProjectCostCenterIdUpdateComponent implements OnInit {
  projectCostCenterId: IProjectCostCenterId;
  isSaving: boolean;

  companies: ICompany[];

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected projectCostCenterIdService: ProjectCostCenterIdService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ projectCostCenterId }) => {
      this.updateForm(projectCostCenterId);
      this.projectCostCenterId = projectCostCenterId;
    });
    this.companyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompany[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompany[]>) => response.body)
      )
      .subscribe((res: ICompany[]) => (this.companies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(projectCostCenterId: IProjectCostCenterId) {
    this.editForm.patchValue({
      id: projectCostCenterId.id,
      code: projectCostCenterId.code,
      name: projectCostCenterId.name,
      description: projectCostCenterId.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const projectCostCenterId = this.createFromForm();
    if (projectCostCenterId.id !== undefined) {
      this.subscribeToSaveResponse(this.projectCostCenterIdService.update(projectCostCenterId));
    } else {
      this.subscribeToSaveResponse(this.projectCostCenterIdService.create(projectCostCenterId));
    }
  }

  private createFromForm(): IProjectCostCenterId {
    const entity = {
      ...new ProjectCostCenterId(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectCostCenterId>>) {
    result.subscribe((res: HttpResponse<IProjectCostCenterId>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCompanyById(index: number, item: ICompany) {
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
