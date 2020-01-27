/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectClassUpdateComponent } from 'app/entities/project-class/project-class-update.component';
import { ProjectClassService } from 'app/entities/project-class/project-class.service';
import { ProjectClass } from 'app/shared/model/project-class.model';

describe('Component Tests', () => {
  describe('ProjectClass Management Update Component', () => {
    let comp: ProjectClassUpdateComponent;
    let fixture: ComponentFixture<ProjectClassUpdateComponent>;
    let service: ProjectClassService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectClassUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectClassUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectClassUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectClassService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectClass(123);
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
        const entity = new ProjectClass();
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
