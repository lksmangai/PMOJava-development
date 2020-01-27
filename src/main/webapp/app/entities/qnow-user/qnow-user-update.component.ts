import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IQnowUser, QnowUser } from 'app/shared/model/qnow-user.model';
import { QnowUserService } from './qnow-user.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-qnow-user-update',
  templateUrl: './qnow-user-update.component.html'
})
export class QnowUserUpdateComponent implements OnInit {
  qnowUser: IQnowUser;
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    user: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected qnowUserService: QnowUserService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ qnowUser }) => {
      this.updateForm(qnowUser);
      this.qnowUser = qnowUser;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(qnowUser: IQnowUser) {
    this.editForm.patchValue({
      id: qnowUser.id,
      user: qnowUser.user
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const qnowUser = this.createFromForm();
    if (qnowUser.id !== undefined) {
      this.subscribeToSaveResponse(this.qnowUserService.update(qnowUser));
    } else {
      this.subscribeToSaveResponse(this.qnowUserService.create(qnowUser));
    }
  }

  private createFromForm(): IQnowUser {
    const entity = {
      ...new QnowUser(),
      id: this.editForm.get(['id']).value,
      user: this.editForm.get(['user']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQnowUser>>) {
    result.subscribe((res: HttpResponse<IQnowUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
