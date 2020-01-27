import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectStatusId } from 'app/shared/model/project-status-id.model';
import { ProjectStatusIdService } from './project-status-id.service';

@Component({
  selector: 'jhi-project-status-id-delete-dialog',
  templateUrl: './project-status-id-delete-dialog.component.html'
})
export class ProjectStatusIdDeleteDialogComponent {
  projectStatusId: IProjectStatusId;

  constructor(
    protected projectStatusIdService: ProjectStatusIdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectStatusIdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectStatusIdListModification',
        content: 'Deleted an projectStatusId'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-status-id-delete-popup',
  template: ''
})
export class ProjectStatusIdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectStatusId }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectStatusIdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.projectStatusId = projectStatusId;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-status-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-status-id', { outlets: { popup: null } }]);
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
