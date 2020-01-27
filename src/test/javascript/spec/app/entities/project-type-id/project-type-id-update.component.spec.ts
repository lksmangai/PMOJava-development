/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectTypeIdUpdateComponent } from 'app/entities/project-type-id/project-type-id-update.component';
import { ProjectTypeIdService } from 'app/entities/project-type-id/project-type-id.service';
import { ProjectTypeId } from 'app/shared/model/project-type-id.model';

describe('Component Tests', () => {
  describe('ProjectTypeId Management Update Component', () => {
    let comp: ProjectTypeIdUpdateComponent;
    let fixture: ComponentFixture<ProjectTypeIdUpdateComponent>;
    let service: ProjectTypeIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectTypeIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectTypeIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectTypeIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectTypeIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectTypeId(123);
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
        const entity = new ProjectTypeId();
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
