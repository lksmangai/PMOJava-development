/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectRolesDetailComponent } from 'app/entities/project-roles/project-roles-detail.component';
import { ProjectRoles } from 'app/shared/model/project-roles.model';

describe('Component Tests', () => {
  describe('ProjectRoles Management Detail Component', () => {
    let comp: ProjectRolesDetailComponent;
    let fixture: ComponentFixture<ProjectRolesDetailComponent>;
    const route = ({ data: of({ projectRoles: new ProjectRoles(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectRolesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectRolesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectRolesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectRoles).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
