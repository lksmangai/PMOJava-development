/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectBucketIdUpdateComponent } from 'app/entities/project-bucket-id/project-bucket-id-update.component';
import { ProjectBucketIdService } from 'app/entities/project-bucket-id/project-bucket-id.service';
import { ProjectBucketId } from 'app/shared/model/project-bucket-id.model';

describe('Component Tests', () => {
  describe('ProjectBucketId Management Update Component', () => {
    let comp: ProjectBucketIdUpdateComponent;
    let fixture: ComponentFixture<ProjectBucketIdUpdateComponent>;
    let service: ProjectBucketIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectBucketIdUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectBucketIdUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectBucketIdUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectBucketIdService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectBucketId(123);
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
        const entity = new ProjectBucketId();
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
