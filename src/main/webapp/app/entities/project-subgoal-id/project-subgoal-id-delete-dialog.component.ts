import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectSubgoalId } from 'app/shared/model/project-subgoal-id.model';
import { ProjectSubgoalIdService } from './project-subgoal-id.service';

@Component({
  selector: 'jhi-project-subgoal-id-delete-dialog',
  templateUrl: './project-subgoal-id-delete-dialog.component.html'
})
export class ProjectSubgoalIdDeleteDialogComponent {
  projectSubgoalId: IProjectSubgoalId;

  constructor(
    protected projectSubgoalIdService: ProjectSubgoalIdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectSubgoalIdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectSubgoalIdListModification',
        content: 'Deleted an projectSubgoalId'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-subgoal-id-delete-popup',
  template: ''
})
export class ProjectSubgoalIdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectSubgoalId }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectSubgoalIdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.projectSubgoalId = projectSubgoalId;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-subgoal-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-subgoal-id', { outlets: { popup: null } }]);
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
