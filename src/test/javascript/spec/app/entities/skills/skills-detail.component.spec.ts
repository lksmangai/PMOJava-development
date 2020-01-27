/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { SkillsDetailComponent } from 'app/entities/skills/skills-detail.component';
import { Skills } from 'app/shared/model/skills.model';

describe('Component Tests', () => {
  describe('Skills Management Detail Component', () => {
    let comp: SkillsDetailComponent;
    let fixture: ComponentFixture<SkillsDetailComponent>;
    const route = ({ data: of({ skills: new Skills(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SkillsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SkillsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.skills).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
