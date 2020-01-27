import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectBucketId } from 'app/shared/model/project-bucket-id.model';
import { ProjectBucketIdService } from './project-bucket-id.service';

@Component({
  selector: 'jhi-project-bucket-id-delete-dialog',
  templateUrl: './project-bucket-id-delete-dialog.component.html'
})
export class ProjectBucketIdDeleteDialogComponent {
  projectBucketId: IProjectBucketId;

  constructor(
    protected projectBucketIdService: ProjectBucketIdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectBucketIdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectBucketIdListModification',
        content: 'Deleted an projectBucketId'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-bucket-id-delete-popup',
  template: ''
})
export class ProjectBucketIdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectBucketId }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectBucketIdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.projectBucketId = projectBucketId;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-bucket-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-bucket-id', { outlets: { popup: null } }]);
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
