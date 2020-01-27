import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRoles, Roles } from 'app/shared/model/roles.model';
import { RolesService } from './roles.service';
import { IGroupMembers } from 'app/shared/model/group-members.model';
import { GroupMembersService } from 'app/entities/group-members';

@Component({
  selector: 'jhi-roles-update',
  templateUrl: './roles-update.component.html'
})
export class RolesUpdateComponent implements OnInit {
  roles: IRoles;
  isSaving: boolean;

  groupmembers: IGroupMembers[];

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected rolesService: RolesService,
    protected groupMembersService: GroupMembersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ roles }) => {
      this.updateForm(roles);
      this.roles = roles;
    });
    this.groupMembersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IGroupMembers[]>) => mayBeOk.ok),
        map((response: HttpResponse<IGroupMembers[]>) => response.body)
      )
      .subscribe((res: IGroupMembers[]) => (this.groupmembers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(roles: IRoles) {
    this.editForm.patchValue({
      id: roles.id,
      code: roles.code,
      name: roles.name,
      description: roles.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const roles = this.createFromForm();
    if (roles.id !== undefined) {
      this.subscribeToSaveResponse(this.rolesService.update(roles));
    } else {
      this.subscribeToSaveResponse(this.rolesService.create(roles));
    }
  }

  private createFromForm(): IRoles {
    const entity = {
      ...new Roles(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoles>>) {
    result.subscribe((res: HttpResponse<IRoles>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackGroupMembersById(index: number, item: IGroupMembers) {
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
