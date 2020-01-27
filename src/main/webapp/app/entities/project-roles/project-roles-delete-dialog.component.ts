import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectRoles } from 'app/shared/model/project-roles.model';
import { ProjectRolesService } from './project-roles.service';

@Component({
  selector: 'jhi-project-roles-delete-dialog',
  templateUrl: './project-roles-delete-dialog.component.html'
})
export class ProjectRolesDeleteDialogComponent {
  projectRoles: IProjectRoles;

  constructor(
    protected projectRolesService: ProjectRolesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectRolesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectRolesListModification',
        content: 'Deleted an projectRoles'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-roles-delete-popup',
  template: ''
})
export class ProjectRolesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectRoles }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectRolesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.projectRoles = projectRoles;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-roles', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-roles', { outlets: { popup: null } }]);
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
