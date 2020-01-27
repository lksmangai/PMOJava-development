/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { SkillCategoryComponent } from 'app/entities/skill-category/skill-category.component';
import { SkillCategoryService } from 'app/entities/skill-category/skill-category.service';
import { SkillCategory } from 'app/shared/model/skill-category.model';

describe('Component Tests', () => {
  describe('SkillCategory Management Component', () => {
    let comp: SkillCategoryComponent;
    let fixture: ComponentFixture<SkillCategoryComponent>;
    let service: SkillCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillCategoryComponent],
        providers: []
      })
        .overrideTemplate(SkillCategoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkillCategoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillCategoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SkillCategory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.skillCategories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
