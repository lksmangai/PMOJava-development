/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectVerticalUpdateComponent } from 'app/entities/project-vertical/project-vertical-update.component';
import { ProjectVerticalService } from 'app/entities/project-vertical/project-vertical.service';
import { ProjectVertical } from 'app/shared/model/project-vertical.model';

describe('Component Tests', () => {
  describe('ProjectVertical Management Update Component', () => {
    let comp: ProjectVerticalUpdateComponent;
    let fixture: ComponentFixture<ProjectVerticalUpdateComponent>;
    let service: ProjectVerticalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectVerticalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectVerticalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectVerticalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectVerticalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectVertical(123);
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
        const entity = new ProjectVertical();
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
