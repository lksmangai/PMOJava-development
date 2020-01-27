/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { EmployeeStatusUpdateComponent } from 'app/entities/employee-status/employee-status-update.component';
import { EmployeeStatusService } from 'app/entities/employee-status/employee-status.service';
import { EmployeeStatus } from 'app/shared/model/employee-status.model';

describe('Component Tests', () => {
  describe('EmployeeStatus Management Update Component', () => {
    let comp: EmployeeStatusUpdateComponent;
    let fixture: ComponentFixture<EmployeeStatusUpdateComponent>;
    let service: EmployeeStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [EmployeeStatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmployeeStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmployeeStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmployeeStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeStatus(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmployeeStatus();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
