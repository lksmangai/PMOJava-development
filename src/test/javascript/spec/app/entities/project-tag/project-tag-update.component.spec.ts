/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectTagUpdateComponent } from 'app/entities/project-tag/project-tag-update.component';
import { ProjectTagService } from 'app/entities/project-tag/project-tag.service';
import { ProjectTag } from 'app/shared/model/project-tag.model';

describe('Component Tests', () => {
  describe('ProjectTag Management Update Component', () => {
    let comp: ProjectTagUpdateComponent;
    let fixture: ComponentFixture<ProjectTagUpdateComponent>;
    let service: ProjectTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectTagUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectTag(123);
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
        const entity = new ProjectTag();
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
