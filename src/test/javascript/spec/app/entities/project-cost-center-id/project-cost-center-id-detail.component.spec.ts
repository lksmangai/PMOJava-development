/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectCostCenterIdDetailComponent } from 'app/entities/project-cost-center-id/project-cost-center-id-detail.component';
import { ProjectCostCenterId } from 'app/shared/model/project-cost-center-id.model';

describe('Component Tests', () => {
  describe('ProjectCostCenterId Management Detail Component', () => {
    let comp: ProjectCostCenterIdDetailComponent;
    let fixture: ComponentFixture<ProjectCostCenterIdDetailComponent>;
    const route = ({ data: of({ projectCostCenterId: new ProjectCostCenterId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectCostCenterIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectCostCenterIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectCostCenterIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectCostCenterId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
