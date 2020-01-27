/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectBoardIdDeleteDialogComponent } from 'app/entities/project-board-id/project-board-id-delete-dialog.component';
import { ProjectBoardIdService } from 'app/entities/project-board-id/project-board-id.service';

describe('Component Tests', () => {
  describe('ProjectBoardId Management Delete Component', () => {
    let comp: ProjectBoardIdDeleteDialogComponent;
    let fixture: ComponentFixture<ProjectBoardIdDeleteDialogComponent>;
    let service: ProjectBoardIdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectBoardIdDeleteDialogComponent]
      })
        .overrideTemplate(ProjectBoardIdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectBoardIdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectBoardIdService);
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
