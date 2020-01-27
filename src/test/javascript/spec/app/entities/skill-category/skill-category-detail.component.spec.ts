/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { SkillCategoryDetailComponent } from 'app/entities/skill-category/skill-category-detail.component';
import { SkillCategory } from 'app/shared/model/skill-category.model';

describe('Component Tests', () => {
  describe('SkillCategory Management Detail Component', () => {
    let comp: SkillCategoryDetailComponent;
    let fixture: ComponentFixture<SkillCategoryDetailComponent>;
    const route = ({ data: of({ skillCategory: new SkillCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SkillCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SkillCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.skillCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
