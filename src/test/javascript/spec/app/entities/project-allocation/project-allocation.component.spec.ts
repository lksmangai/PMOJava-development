/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectAllocationComponent } from 'app/entities/project-allocation/project-allocation.component';
import { ProjectAllocationService } from 'app/entities/project-allocation/project-allocation.service';
import { ProjectAllocation } from 'app/shared/model/project-allocation.model';

describe('Component Tests', () => {
  describe('ProjectAllocation Management Component', () => {
    let comp: ProjectAllocationComponent;
    let fixture: ComponentFixture<ProjectAllocationComponent>;
    let service: ProjectAllocationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectAllocationComponent],
        providers: []
      })
        .overrideTemplate(ProjectAllocationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectAllocationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectAllocationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectAllocation(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectAllocations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
