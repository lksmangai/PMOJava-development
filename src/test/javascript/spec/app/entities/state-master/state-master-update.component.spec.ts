/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { StateMasterUpdateComponent } from 'app/entities/state-master/state-master-update.component';
import { StateMasterService } from 'app/entities/state-master/state-master.service';
import { StateMaster } from 'app/shared/model/state-master.model';

describe('Component Tests', () => {
  describe('StateMaster Management Update Component', () => {
    let comp: StateMasterUpdateComponent;
    let fixture: ComponentFixture<StateMasterUpdateComponent>;
    let service: StateMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [StateMasterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StateMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StateMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StateMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StateMaster(123);
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
        const entity = new StateMaster();
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
