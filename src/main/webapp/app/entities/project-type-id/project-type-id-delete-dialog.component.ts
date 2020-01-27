import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectTypeId } from 'app/shared/model/project-type-id.model';
import { ProjectTypeIdService } from './project-type-id.service';

@Component({
  selector: 'jhi-project-type-id-delete-dialog',
  templateUrl: './project-type-id-delete-dialog.component.html'
})
export class ProjectTypeIdDeleteDialogComponent {
  projectTypeId: IProjectTypeId;

  constructor(
    protected projectTypeIdService: ProjectTypeIdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectTypeIdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectTypeIdListModification',
        content: 'Deleted an projectTypeId'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-type-id-delete-popup',
  template: ''
})
export class ProjectTypeIdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectTypeId }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectTypeIdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.projectTypeId = projectTypeId;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-type-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-type-id', { outlets: { popup: null } }]);
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
