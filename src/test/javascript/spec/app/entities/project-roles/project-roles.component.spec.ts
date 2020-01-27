/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectRolesComponent } from 'app/entities/project-roles/project-roles.component';
import { ProjectRolesService } from 'app/entities/project-roles/project-roles.service';
import { ProjectRoles } from 'app/shared/model/project-roles.model';

describe('Component Tests', () => {
  describe('ProjectRoles Management Component', () => {
    let comp: ProjectRolesComponent;
    let fixture: ComponentFixture<ProjectRolesComponent>;
    let service: ProjectRolesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectRolesComponent],
        providers: []
      })
        .overrideTemplate(ProjectRolesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectRolesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectRolesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectRoles(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectRoles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
