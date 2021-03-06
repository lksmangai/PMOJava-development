/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { BacklogPracticeDeleteDialogComponent } from 'app/entities/backlog-practice/backlog-practice-delete-dialog.component';
import { BacklogPracticeService } from 'app/entities/backlog-practice/backlog-practice.service';

describe('Component Tests', () => {
  describe('BacklogPractice Management Delete Component', () => {
    let comp: BacklogPracticeDeleteDialogComponent;
    let fixture: ComponentFixture<BacklogPracticeDeleteDialogComponent>;
    let service: BacklogPracticeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [BacklogPracticeDeleteDialogComponent]
      })
        .overrideTemplate(BacklogPracticeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BacklogPracticeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BacklogPracticeService);
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
