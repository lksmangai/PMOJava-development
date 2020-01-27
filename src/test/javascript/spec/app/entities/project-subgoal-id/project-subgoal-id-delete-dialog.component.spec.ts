/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectSubgoalIdDeleteDialogComponent } from 'app/entities/project-subgoal-id/project-subgoal-id-delete-dialog.component';
import { ProjectSubgoalIdService } from 'app/entities/project-subgoal-id/project-subgoal-id.service';

describe('Component Tests', () => {
  describe('ProjectSubgoalId Management Delete Component', () => {
    let comp: ProjectSubgoalIdDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectSubgoalIdDeleteDialogComponent>;
    let service: ProjectSubgoalIdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectSubgoalIdDeleteDialogComponent]
      })
        .overrideTemplate(ProjectSubgoalIdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectSubgoalIdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectSubgoalIdService);
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
