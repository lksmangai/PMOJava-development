import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISkillTable } from 'app/shared/model/skill-table.model';
import { SkillTableService } from './skill-table.service';

@Component({
  selector: 'jhi-skill-table-delete-dialog',
  templateUrl: './skill-table-delete-dialog.component.html'
})
export class SkillTableDeleteDialogComponent {
  skillTable: ISkillTable;

  constructor(
    protected skillTableService: SkillTableService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.skillTableService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'skillTableListModification',
        content: 'Deleted an skillTable'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-skill-table-delete-popup',
  template: ''
})
export class SkillTableDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skillTable }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SkillTableDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.skillTable = skillTable;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/skill-table', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/skill-table', { outlets: { popup: null } }]);
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
