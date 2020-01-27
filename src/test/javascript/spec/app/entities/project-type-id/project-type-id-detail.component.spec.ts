/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectTypeIdDetailComponent } from 'app/entities/project-type-id/project-type-id-detail.component';
import { ProjectTypeId } from 'app/shared/model/project-type-id.model';

describe('Component Tests', () => {
  describe('ProjectTypeId Management Detail Component', () => {
    let comp: ProjectTypeIdDetailComponent;
    let fixture: ComponentFixture<ProjectTypeIdDetailComponent>;
    const route = ({ data: of({ projectTypeId: new ProjectTypeId(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectTypeIdDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectTypeIdDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectTypeIdDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectTypeId).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
