/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectMaingoalIdDetailComponent } from 'app/entities/project-maingoal-id/project-maingoal-id-detail.component';
import { ProjectMaingoalId } from 'app/shared/model/project-maingoal-id.model';

describe('Component Tests', () => {
  describe('ProjectMaingoalId Management Detail Component', () => {
    let comp: ProjectMaingoalIdDetailComponent;
    let fixture: ComponentFixture<ProjectMaingoalIdDetailComponent>;
    const route = ({ data: of({ projectMaingoalId: new ProjectMaingoalId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectMaingoalIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectMaingoalIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectMaingoalIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectMaingoalId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
