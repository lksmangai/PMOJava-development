import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFileStorage } from 'app/shared/model/file-storage.model';

@Component({
  selector: 'jhi-file-storage-detail',
  templateUrl: './file-storage-detail.component.html'
})
export class FileStorageDetailComponent implements OnInit {
  fileStorage: IFileStorage;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fileStorage }) => {
      this.fileStorage = fileStorage;
    });
  }

  previousState() {
    window.history.back();
  }
}
