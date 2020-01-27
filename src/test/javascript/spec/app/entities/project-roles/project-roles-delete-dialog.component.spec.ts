/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectRolesDeleteDialogComponent } from 'app/entities/project-roles/project-roles-delete-dialog.component';
import { ProjectRolesService } from 'app/entities/project-roles/project-roles.service';

describe('Component Tests', () => {
  describe('ProjectRoles Management Delete Component', () => {
    let comp: ProjectRolesDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectRolesDeleteDialogComponent>;
    let service: ProjectRolesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectRolesDeleteDialogComponent]
      })
        .overrideTemplate(ProjectRolesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectRolesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectRolesService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
