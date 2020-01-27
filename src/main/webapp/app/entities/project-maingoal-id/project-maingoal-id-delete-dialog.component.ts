import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectMaingoalId } from 'app/shared/model/project-maingoal-id.model';
import { ProjectMaingoalIdService } from './project-maingoal-id.service';

@Component({
  selector: 'jhi-project-maingoal-id-delete-dialog',
  templateUrl: './project-maingoal-id-delete-dialog.component.html'
})
export class ProjectMaingoalIdDeleteDialogComponent {
  projectMaingoalId: IProjectMaingoalId;

  constructor(
    protected projectMaingoalIdService: ProjectMaingoalIdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectMaingoalIdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectMaingoalIdListModification',
        content: 'Deleted an projectMaingoalId'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-maingoal-id-delete-popup',
  template: ''
})
export class ProjectMaingoalIdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectMaingoalId }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectMaingoalIdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.projectMaingoalId = projectMaingoalId;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-maingoal-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-maingoal-id', { outlets: { popup: null } }]);
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
