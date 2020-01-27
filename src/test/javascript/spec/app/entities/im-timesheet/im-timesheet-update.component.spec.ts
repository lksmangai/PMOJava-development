/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ImTimesheetUpdateComponent } from 'app/entities/im-timesheet/im-timesheet-update.component';
import { ImTimesheetService } from 'app/entities/im-timesheet/im-timesheet.service';
import { ImTimesheet } from 'app/shared/model/im-timesheet.model';

describe('Component Tests', () => {
  describe('ImTimesheet Management Update Component', () => {
    let comp: ImTimesheetUpdateComponent;
    let fixture: ComponentFixture<ImTimesheetUpdateComponent>;
    let service: ImTimesheetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ImTimesheetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImTimesheetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImTimesheetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImTimesheetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ImTimesheet(123);
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
        const entity = new ImTimesheet();
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
