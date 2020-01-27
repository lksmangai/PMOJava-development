/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { SkillTableDetailComponent } from 'app/entities/skill-table/skill-table-detail.component';
import { SkillTable } from 'app/shared/model/skill-table.model';

describe('Component Tests', () => {
  describe('SkillTable Management Detail Component', () => {
    let comp: SkillTableDetailComponent;
    let fixture: ComponentFixture<SkillTableDetailComponent>;
    const route = ({ data: of({ skillTable: new SkillTable(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillTableDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SkillTableDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SkillTableDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.skillTable).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
