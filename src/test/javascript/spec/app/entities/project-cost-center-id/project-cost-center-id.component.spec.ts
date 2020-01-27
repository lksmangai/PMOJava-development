/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectCostCenterIdComponent } from 'app/entities/project-cost-center-id/project-cost-center-id.component';
import { ProjectCostCenterIdService } from 'app/entities/project-cost-center-id/project-cost-center-id.service';
import { ProjectCostCenterId } from 'app/shared/model/project-cost-center-id.model';

describe('Component Tests', () => {
  describe('ProjectCostCenterId Management Component', () => {
    let comp: ProjectCostCenterIdComponent;
    let fixture: ComponentFixture<ProjectCostCenterIdComponent>;
    let service: ProjectCostCenterIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectCostCenterIdComponent],
        providers: []
      })
        .overrideTemplate(ProjectCostCenterIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectCostCenterIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectCostCenterIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectCostCenterId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectCostCenterIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
