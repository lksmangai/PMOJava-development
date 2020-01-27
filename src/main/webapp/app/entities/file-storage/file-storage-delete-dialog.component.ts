import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFileStorage } from 'app/shared/model/file-storage.model';
import { FileStorageService } from './file-storage.service';

@Component({
  selector: 'jhi-file-storage-delete-dialog',
  templateUrl: './file-storage-delete-dialog.component.html'
})
export class FileStorageDeleteDialogComponent {
  fileStorage: IFileStorage;

  constructor(
    protected fileStorageService: FileStorageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fileStorageService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fileStorageListModification',
        content: 'Deleted an fileStorage'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-file-storage-delete-popup',
  template: ''
})
export class FileStorageDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fileStorage }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FileStorageDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fileStorage = fileStorage;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/file-storage', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/file-storage', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
