/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectSubgoalIdDetailComponent } from 'app/entities/project-subgoal-id/project-subgoal-id-detail.component';
import { ProjectSubgoalId } from 'app/shared/model/project-subgoal-id.model';

describe('Component Tests', () => {
  describe('ProjectSubgoalId Management Detail Component', () => {
    let comp: ProjectSubgoalIdDetailComponent;
    let fixture: ComponentFixture<ProjectSubgoalIdDetailComponent>;
    const route = ({ data: of({ projectSubgoalId: new ProjectSubgoalId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectSubgoalIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectSubgoalIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectSubgoalIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectSubgoalId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
