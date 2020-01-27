/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectBusinessgoalIdDeleteDialogComponent } from 'app/entities/project-businessgoal-id/project-businessgoal-id-delete-dialog.component';
import { ProjectBusinessgoalIdService } from 'app/entities/project-businessgoal-id/project-businessgoal-id.service';

describe('Component Tests', () => {
  describe('ProjectBusinessgoalId Management Delete Component', () => {
    let comp: ProjectBusinessgoalIdDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectBusinessgoalIdDeleteDialogComponent>;
    let service: ProjectBusinessgoalIdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectBusinessgoalIdDeleteDialogComponent]
      })
        .overrideTemplate(ProjectBusinessgoalIdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectBusinessgoalIdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectBusinessgoalIdService);
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
