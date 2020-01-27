/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectRolesUpdateComponent } from 'app/entities/project-roles/project-roles-update.component';
import { ProjectRolesService } from 'app/entities/project-roles/project-roles.service';
import { ProjectRoles } from 'app/shared/model/project-roles.model';

describe('Component Tests', () => {
  describe('ProjectRoles Management Update Component', () => {
    let comp: ProjectRolesUpdateComponent;
    let fixture: ComponentFixture<ProjectRolesUpdateComponent>;
    let service: ProjectRolesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectRolesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectRolesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectRolesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectRolesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectRoles(123);
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
        const entity = new ProjectRoles();
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
