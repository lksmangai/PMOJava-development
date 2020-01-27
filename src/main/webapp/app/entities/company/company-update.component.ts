import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICompany, Company } from 'app/shared/model/company.model';
import { CompanyService } from './company.service';
import { IProjectCostCenterId } from 'app/shared/model/project-cost-center-id.model';
import { ProjectCostCenterIdService } from 'app/entities/project-cost-center-id';

@Component({
  selector: 'jhi-company-update',
  templateUrl: './company-update.component.html'
})
export class CompanyUpdateComponent implements OnInit {
  company: ICompany;
  isSaving: boolean;

  projectcostcenterids: IProjectCostCenterId[];

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: [],
    projectCostCenterIds: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected companyService: CompanyService,
    protected projectCostCenterIdService: ProjectCostCenterIdService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ company }) => {
      this.updateForm(company);
      this.company = company;
    });
    this.projectCostCenterIdService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProjectCostCenterId[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProjectCostCenterId[]>) => response.body)
      )
      .subscribe((res: IProjectCostCenterId[]) => (this.projectcostcenterids = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(company: ICompany) {
    this.editForm.patchValue({
      id: company.id,
      code: company.code,
      name: company.name,
      description: company.description,
      projectCostCenterIds: company.projectCostCenterIds
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const company = this.createFromForm();
    if (company.id !== undefined) {
      this.subscribeToSaveResponse(this.companyService.update(company));
    } else {
      this.subscribeToSaveResponse(this.companyService.create(company));
    }
  }

  private createFromForm(): ICompany {
    const entity = {
      ...new Company(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      projectCostCenterIds: this.editForm.get(['projectCostCenterIds']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompany>>) {
    result.subscribe((res: HttpResponse<ICompany>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackProjectCostCenterIdById(index: number, item: IProjectCostCenterId) {
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
