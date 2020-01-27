import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISkills } from 'app/shared/model/skills.model';
import { SkillsService } from './skills.service';

@Component({
  selector: 'jhi-skills-delete-dialog',
  templateUrl: './skills-delete-dialog.component.html'
})
export class SkillsDeleteDialogComponent {
  skills: ISkills;

  constructor(protected skillsService: SkillsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.skillsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'skillsListModification',
        content: 'Deleted an skills'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-skills-delete-popup',
  template: ''
})
export class SkillsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skills }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SkillsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.skills = skills;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/skills', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/skills', { outlets: { popup: null } }]);
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
