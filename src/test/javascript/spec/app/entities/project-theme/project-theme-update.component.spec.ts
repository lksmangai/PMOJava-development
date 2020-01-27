/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectThemeUpdateComponent } from 'app/entities/project-theme/project-theme-update.component';
import { ProjectThemeService } from 'app/entities/project-theme/project-theme.service';
import { ProjectTheme } from 'app/shared/model/project-theme.model';

describe('Component Tests', () => {
  describe('ProjectTheme Management Update Component', () => {
    let comp: ProjectThemeUpdateComponent;
    let fixture: ComponentFixture<ProjectThemeUpdateComponent>;
    let service: ProjectThemeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectThemeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectThemeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectThemeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectThemeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectTheme(123);
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
        const entity = new ProjectTheme();
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
