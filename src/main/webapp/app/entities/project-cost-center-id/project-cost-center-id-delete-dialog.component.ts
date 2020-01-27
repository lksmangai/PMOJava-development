import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectCostCenterId } from 'app/shared/model/project-cost-center-id.model';
import { ProjectCostCenterIdService } from './project-cost-center-id.service';

@Component({
  selector: 'jhi-project-cost-center-id-delete-dialog',
  templateUrl: './project-cost-center-id-delete-dialog.component.html'
})
export class ProjectCostCenterIdDeleteDialogComponent {
  projectCostCenterId: IProjectCostCenterId;

  constructor(
    protected projectCostCenterIdService: ProjectCostCenterIdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectCostCenterIdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectCostCenterIdListModification',
        content: 'Deleted an projectCostCenterId'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-cost-center-id-delete-popup',
  template: ''
})
export class ProjectCostCenterIdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectCostCenterId }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectCostCenterIdDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.projectCostCenterId = projectCostCenterId;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-cost-center-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-cost-center-id', { outlets: { popup: null } }]);
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
