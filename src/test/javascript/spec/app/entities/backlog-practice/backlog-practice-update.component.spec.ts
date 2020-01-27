/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { BacklogPracticeUpdateComponent } from 'app/entities/backlog-practice/backlog-practice-update.component';
import { BacklogPracticeService } from 'app/entities/backlog-practice/backlog-practice.service';
import { BacklogPractice } from 'app/shared/model/backlog-practice.model';

describe('Component Tests', () => {
  describe('BacklogPractice Management Update Component', () => {
    let comp: BacklogPracticeUpdateComponent;
    let fixture: ComponentFixture<BacklogPracticeUpdateComponent>;
    let service: BacklogPracticeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [BacklogPracticeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BacklogPracticeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BacklogPracticeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BacklogPracticeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BacklogPractice(123);
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
        const entity = new BacklogPractice();
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
