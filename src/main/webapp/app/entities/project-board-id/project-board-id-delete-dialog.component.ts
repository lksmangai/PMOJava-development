import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectBoardId } from 'app/shared/model/project-board-id.model';
import { ProjectBoardIdService } from './project-board-id.service';

@Component({
  selector: 'jhi-project-board-id-delete-dialog',
  templateUrl: './project-board-id-delete-dialog.component.html'
})
export class ProjectBoardIdDeleteDialogComponent {
  projectBoardId: IProjectBoardId;

  constructor(
    protected projectBoardIdService: ProjectBoardIdService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectBoardIdService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectBoardIdListModification',
        content: 'Deleted an projectBoardId'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-board-id-delete-popup',
  template: ''
})
export class ProjectBoardIdDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectBoardId }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectBoardIdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.projectBoardId = projectBoardId;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-board-id', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-board-id', { outlets: { popup: null } }]);
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
