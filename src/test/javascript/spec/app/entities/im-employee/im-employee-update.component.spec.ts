/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ImEmployeeUpdateComponent } from 'app/entities/im-employee/im-employee-update.component';
import { ImEmployeeService } from 'app/entities/im-employee/im-employee.service';
import { ImEmployee } from 'app/shared/model/im-employee.model';

describe('Component Tests', () => {
  describe('ImEmployee Management Update Component', () => {
    let comp: ImEmployeeUpdateComponent;
    let fixture: ComponentFixture<ImEmployeeUpdateComponent>;
    let service: ImEmployeeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ImEmployeeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImEmployeeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImEmployeeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImEmployeeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ImEmployee(123);
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
        const entity = new ImEmployee();
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
