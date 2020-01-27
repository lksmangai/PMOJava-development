/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectAllocationDetailComponent } from 'app/entities/project-allocation/project-allocation-detail.component';
import { ProjectAllocation } from 'app/shared/model/project-allocation.model';

describe('Component Tests', () => {
  describe('ProjectAllocation Management Detail Component', () => {
    let comp: ProjectAllocationDetailComponent;
    let fixture: ComponentFixture<ProjectAllocationDetailComponent>;
    const route = ({ data: of({ projectAllocation: new ProjectAllocation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectAllocationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectAllocationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectAllocationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectAllocation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
