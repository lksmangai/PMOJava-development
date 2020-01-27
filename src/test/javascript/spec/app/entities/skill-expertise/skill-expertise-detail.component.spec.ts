/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { SkillExpertiseDetailComponent } from 'app/entities/skill-expertise/skill-expertise-detail.component';
import { SkillExpertise } from 'app/shared/model/skill-expertise.model';

describe('Component Tests', () => {
  describe('SkillExpertise Management Detail Component', () => {
    let comp: SkillExpertiseDetailComponent;
    let fixture: ComponentFixture<SkillExpertiseDetailComponent>;
    const route = ({ data: of({ skillExpertise: new SkillExpertise(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillExpertiseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SkillExpertiseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SkillExpertiseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.skillExpertise).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
