import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectVertical } from 'app/shared/model/project-vertical.model';
import { ProjectVerticalService } from './project-vertical.service';

@Component({
  selector: 'jhi-project-vertical-delete-dialog',
  templateUrl: './project-vertical-delete-dialog.component.html'
})
export class ProjectVerticalDeleteDialogComponent {
  projectVertical: IProjectVertical;

  constructor(
    protected projectVerticalService: ProjectVerticalService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectVerticalService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectVerticalListModification',
        content: 'Deleted an projectVertical'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-vertical-delete-popup',
  template: ''
})
export class ProjectVerticalDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectVertical }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectVerticalDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.projectVertical = projectVertical;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-vertical', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-vertical', { outlets: { popup: null } }]);
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
