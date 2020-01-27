import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectBusinessgoalId } from 'app/shared/model/project-businessgoal-id.model';
import { ProjectBusinessgoalIdService } from './project-businessgoal-id.service';

@Component({
  selector: 'jhi-project-businessgoal-id-delete-dialog',
  templateUrl: './project-businessgoal-id-delete-dialog.component.html'
})
export class ProjectBusinessgoalIdDeleteDialogComponent {
  projectBusinessgoalId: IProjectBusinessgoalId;

  constructor(
    protected projectBusinessgoalIdService: ProjectBusinessgoalIdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectBusinessgoalIdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectBusinessgoalIdListModification',
        content: 'Deleted an projectBusinessgoalId'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-businessgoal-id-delete-popup',
  template: ''
})
export class ProjectBusinessgoalIdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectBusinessgoalId }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectBusinessgoalIdDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.projectBusinessgoalId = projectBusinessgoalId;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-businessgoal-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-businessgoal-id', { outlets: { popup: null } }]);
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
