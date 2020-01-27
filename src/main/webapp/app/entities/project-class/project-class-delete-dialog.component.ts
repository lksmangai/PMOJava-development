import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectClass } from 'app/shared/model/project-class.model';
import { ProjectClassService } from './project-class.service';

@Component({
  selector: 'jhi-project-class-delete-dialog',
  templateUrl: './project-class-delete-dialog.component.html'
})
export class ProjectClassDeleteDialogComponent {
  projectClass: IProjectClass;

  constructor(
    protected projectClassService: ProjectClassService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectClassService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectClassListModification',
        content: 'Deleted an projectClass'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-class-delete-popup',
  template: ''
})
export class ProjectClassDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectClass }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectClassDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.projectClass = projectClass;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-class', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-class', { outlets: { popup: null } }]);
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
