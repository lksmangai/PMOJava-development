import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IGroupMembers, GroupMembers } from 'app/shared/model/group-members.model';
import { GroupMembersService } from './group-members.service';
import { IRoles } from 'app/shared/model/roles.model';
import { RolesService } from 'app/entities/roles';
import { IUserContact } from 'app/shared/model/user-contact.model';
import { UserContactService } from 'app/entities/user-contact';

@Component({
  selector: 'jhi-group-members-update',
  templateUrl: './group-members-update.component.html'
})
export class GroupMembersUpdateComponent implements OnInit {
  groupMembers: IGroupMembers;
  isSaving: boolean;

  roles: IRoles[];

  usercontacts: IUserContact[];

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: [],
    roles: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected groupMembersService: GroupMembersService,
    protected rolesService: RolesService,
    protected userContactService: UserContactService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ groupMembers }) => {
      this.updateForm(groupMembers);
      this.groupMembers = groupMembers;
    });
    this.rolesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRoles[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRoles[]>) => response.body)
      )
      .subscribe((res: IRoles[]) => (this.roles = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userContactService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUserContact[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserContact[]>) => response.body)
      )
      .subscribe((res: IUserContact[]) => (this.usercontacts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(groupMembers: IGroupMembers) {
    this.editForm.patchValue({
      id: groupMembers.id,
      code: groupMembers.code,
      name: groupMembers.name,
      description: groupMembers.description,
      roles: groupMembers.roles
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const groupMembers = this.createFromForm();
    if (groupMembers.id !== undefined) {
      this.subscribeToSaveResponse(this.groupMembersService.update(groupMembers));
    } else {
      this.subscribeToSaveResponse(this.groupMembersService.create(groupMembers));
    }
  }

  private createFromForm(): IGroupMembers {
    const entity = {
      ...new GroupMembers(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      roles: this.editForm.get(['roles']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupMembers>>) {
    result.subscribe((res: HttpResponse<IGroupMembers>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackRolesById(index: number, item: IRoles) {
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
