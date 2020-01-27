/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ImProjectsUpdateComponent } from 'app/entities/im-projects/im-projects-update.component';
import { ImProjectsService } from 'app/entities/im-projects/im-projects.service';
import { ImProjects } from 'app/shared/model/im-projects.model';

describe('Component Tests', () => {
  describe('ImProjects Management Update Component', () => {
    let comp: ImProjectsUpdateComponent;
    let fixture: ComponentFixture<ImProjectsUpdateComponent>;
    let service: ImProjectsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ImProjectsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImProjectsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImProjectsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImProjectsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ImProjects(123);
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
        const entity = new ImProjects();
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
