import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITagType } from 'app/shared/model/tag-type.model';

@Component({
  selector: 'jhi-tag-type-detail',
  templateUrl: './tag-type-detail.component.html'
})
export class TagTypeDetailComponent implements OnInit {
  tagType: ITagType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tagType }) => {
      this.tagType = tagType;
    });
  }

  previousState() {
    window.history.back();
  }
}
