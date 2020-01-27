/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectBusinessgoalIdDetailComponent } from 'app/entities/project-businessgoal-id/project-businessgoal-id-detail.component';
import { ProjectBusinessgoalId } from 'app/shared/model/project-businessgoal-id.model';

describe('Component Tests', () => {
  describe('ProjectBusinessgoalId Management Detail Component', () => {
    let comp: ProjectBusinessgoalIdDetailComponent;
    let fixture: ComponentFixture<ProjectBusinessgoalIdDetailComponent>;
    const route = ({ data: of({ projectBusinessgoalId: new ProjectBusinessgoalId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectBusinessgoalIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectBusinessgoalIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectBusinessgoalIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectBusinessgoalId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
