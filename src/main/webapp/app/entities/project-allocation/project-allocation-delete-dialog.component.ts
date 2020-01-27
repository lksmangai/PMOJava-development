import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectAllocation } from 'app/shared/model/project-allocation.model';
import { ProjectAllocationService } from './project-allocation.service';

@Component({
  selector: 'jhi-project-allocation-delete-dialog',
  templateUrl: './project-allocation-delete-dialog.component.html'
})
export class ProjectAllocationDeleteDialogComponent {
  projectAllocation: IProjectAllocation;

  constructor(
    protected projectAllocationService: ProjectAllocationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectAllocationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectAllocationListModification',
        content: 'Deleted an projectAllocation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-allocation-delete-popup',
  template: ''
})
export class ProjectAllocationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectAllocation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectAllocationDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.projectAllocation = projectAllocation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-allocation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-allocation', { outlets: { popup: null } }]);
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
