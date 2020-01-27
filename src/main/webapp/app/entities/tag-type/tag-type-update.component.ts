import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITagType, TagType } from 'app/shared/model/tag-type.model';
import { TagTypeService } from './tag-type.service';

@Component({
  selector: 'jhi-tag-type-update',
  templateUrl: './tag-type-update.component.html'
})
export class TagTypeUpdateComponent implements OnInit {
  tagType: ITagType;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [],
    name: [],
    description: []
  });

  constructor(protected tagTypeService: TagTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tagType }) => {
      this.updateForm(tagType);
      this.tagType = tagType;
    });
  }

  updateForm(tagType: ITagType) {
    this.editForm.patchValue({
      id: tagType.id,
      code: tagType.code,
      name: tagType.name,
      description: tagType.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tagType = this.createFromForm();
    if (tagType.id !== undefined) {
      this.subscribeToSaveResponse(this.tagTypeService.update(tagType));
    } else {
      this.subscribeToSaveResponse(this.tagTypeService.create(tagType));
    }
  }

  private createFromForm(): ITagType {
    const entity = {
      ...new TagType(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITagType>>) {
    result.subscribe((res: HttpResponse<ITagType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
