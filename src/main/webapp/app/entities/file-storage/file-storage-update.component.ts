import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFileStorage, FileStorage } from 'app/shared/model/file-storage.model';
import { FileStorageService } from './file-storage.service';
import { IUserContact } from 'app/shared/model/user-contact.model';
import { UserContactService } from 'app/entities/user-contact';
import { IImProjects } from 'app/shared/model/im-projects.model';
import { ImProjectsService } from 'app/entities/im-projects';

@Component({
  selector: 'jhi-file-storage-update',
  templateUrl: './file-storage-update.component.html'
})
export class FileStorageUpdateComponent implements OnInit {
  fileStorage: IFileStorage;
  isSaving: boolean;

  usercontacts: IUserContact[];

  improjects: IImProjects[];

  editForm = this.fb.group({
    id: [],
    filename: [],
    caption: [],
    userContact: [],
    imProjects: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected fileStorageService: FileStorageService,
    protected userContactService: UserContactService,
    protected imProjectsService: ImProjectsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fileStorage }) => {
      this.updateForm(fileStorage);
      this.fileStorage = fileStorage;
    });
    this.userContactService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUserContact[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserContact[]>) => response.body)
      )
      .subscribe((res: IUserContact[]) => (this.usercontacts = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.imProjectsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IImProjects[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImProjects[]>) => response.body)
      )
      .subscribe((res: IImProjects[]) => (this.improjects = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(fileStorage: IFileStorage) {
    this.editForm.patchValue({
      id: fileStorage.id,
      filename: fileStorage.filename,
      caption: fileStorage.caption,
      userContact: fileStorage.userContact,
      imProjects: fileStorage.imProjects
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fileStorage = this.createFromForm();
    if (fileStorage.id !== undefined) {
      this.subscribeToSaveResponse(this.fileStorageService.update(fileStorage));
    } else {
      this.subscribeToSaveResponse(this.fileStorageService.create(fileStorage));
    }
  }

  private createFromForm(): IFileStorage {
    const entity = {
      ...new FileStorage(),
      id: this.editForm.get(['id']).value,
      filename: this.editForm.get(['filename']).value,
      caption: this.editForm.get(['caption']).value,
      userContact: this.editForm.get(['userContact']).value,
      imProjects: this.editForm.get(['imProjects']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFileStorage>>) {
    result.subscribe((res: HttpResponse<IFileStorage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackUserContactById(index: number, item: IUserContact) {
    return item.id;
  }

  trackImProjectsById(index: number, item: IImProjects) {
    return item.id;
  }
}
