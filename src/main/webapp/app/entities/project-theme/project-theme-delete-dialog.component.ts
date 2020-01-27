import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectTheme } from 'app/shared/model/project-theme.model';
import { ProjectThemeService } from './project-theme.service';

@Component({
  selector: 'jhi-project-theme-delete-dialog',
  templateUrl: './project-theme-delete-dialog.component.html'
})
export class ProjectThemeDeleteDialogComponent {
  projectTheme: IProjectTheme;

  constructor(
    protected projectThemeService: ProjectThemeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.projectThemeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'projectThemeListModification',
        content: 'Deleted an projectTheme'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-project-theme-delete-popup',
  template: ''
})
export class ProjectThemeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ projectTheme }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProjectThemeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.projectTheme = projectTheme;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/project-theme', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/project-theme', { outlets: { popup: null } }]);
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
