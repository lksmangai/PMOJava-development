/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectStatusIdDeleteDialogComponent } from 'app/entities/project-status-id/project-status-id-delete-dialog.component';
import { ProjectStatusIdService } from 'app/entities/project-status-id/project-status-id.service';

describe('Component Tests', () => {
  describe('ProjectStatusId Management Delete Component', () => {
    let comp: ProjectStatusIdDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectStatusIdDeleteDialogComponent>;
    let service: ProjectStatusIdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectStatusIdDeleteDialogComponent]
      })
        .overrideTemplate(ProjectStatusIdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectStatusIdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectStatusIdService);
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
