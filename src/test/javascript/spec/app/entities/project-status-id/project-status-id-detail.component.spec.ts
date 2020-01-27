/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectStatusIdDetailComponent } from 'app/entities/project-status-id/project-status-id-detail.component';
import { ProjectStatusId } from 'app/shared/model/project-status-id.model';

describe('Component Tests', () => {
  describe('ProjectStatusId Management Detail Component', () => {
    let comp: ProjectStatusIdDetailComponent;
    let fixture: ComponentFixture<ProjectStatusIdDetailComponent>;
    const route = ({ data: of({ projectStatusId: new ProjectStatusId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectStatusIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectStatusIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectStatusIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectStatusId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
