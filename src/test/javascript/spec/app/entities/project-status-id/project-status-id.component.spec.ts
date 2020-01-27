/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectStatusIdComponent } from 'app/entities/project-status-id/project-status-id.component';
import { ProjectStatusIdService } from 'app/entities/project-status-id/project-status-id.service';
import { ProjectStatusId } from 'app/shared/model/project-status-id.model';

describe('Component Tests', () => {
  describe('ProjectStatusId Management Component', () => {
    let comp: ProjectStatusIdComponent;
    let fixture: ComponentFixture<ProjectStatusIdComponent>;
    let service: ProjectStatusIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectStatusIdComponent],
        providers: []
      })
        .overrideTemplate(ProjectStatusIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectStatusIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectStatusIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectStatusId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectStatusIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
