/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Qnow1TestModule } from '../../../test.module';
import { EmployeeStatusDeleteDialogComponent } from 'app/entities/employee-status/employee-status-delete-dialog.component';
import { EmployeeStatusService } from 'app/entities/employee-status/employee-status.service';

describe('Component Tests', () => {
  describe('EmployeeStatus Management Delete Component', () => {
    let comp: EmployeeStatusDeleteDialogComponent;
    let fixture: ComponentFixture<EmployeeStatusDeleteDialogComponent>;
    let service: EmployeeStatusService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [EmployeeStatusDeleteDialogComponent]
      })
        .overrideTemplate(EmployeeStatusDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmployeeStatusDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeStatusService);
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
