/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { SkillCategoryUpdateComponent } from 'app/entities/skill-category/skill-category-update.component';
import { SkillCategoryService } from 'app/entities/skill-category/skill-category.service';
import { SkillCategory } from 'app/shared/model/skill-category.model';

describe('Component Tests', () => {
  describe('SkillCategory Management Update Component', () => {
    let comp: SkillCategoryUpdateComponent;
    let fixture: ComponentFixture<SkillCategoryUpdateComponent>;
    let service: SkillCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillCategoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SkillCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkillCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SkillCategory(123);
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
        const entity = new SkillCategory();
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
