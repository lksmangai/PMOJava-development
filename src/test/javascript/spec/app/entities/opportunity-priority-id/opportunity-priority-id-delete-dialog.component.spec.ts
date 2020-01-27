/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { OpportunityPriorityIdDeleteDialogComponent } from 'app/entities/opportunity-priority-id/opportunity-priority-id-delete-dialog.component';
import { OpportunityPriorityIdService } from 'app/entities/opportunity-priority-id/opportunity-priority-id.service';

describe('Component Tests', () => {
  describe('OpportunityPriorityId Management Delete Component', () => {
    let comp: OpportunityPriorityIdDeleteDialogComponent;
    let fixture: ComponentFixture<OpportunityPriorityIdDeleteDialogComponent>;
    let service: OpportunityPriorityIdService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [OpportunityPriorityIdDeleteDialogComponent]
      })
        .overrideTemplate(OpportunityPriorityIdDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OpportunityPriorityIdDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OpportunityPriorityIdService);
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
