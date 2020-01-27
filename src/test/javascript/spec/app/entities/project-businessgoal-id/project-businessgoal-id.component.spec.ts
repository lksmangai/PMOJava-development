/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectBusinessgoalIdComponent } from 'app/entities/project-businessgoal-id/project-businessgoal-id.component';
import { ProjectBusinessgoalIdService } from 'app/entities/project-businessgoal-id/project-businessgoal-id.service';
import { ProjectBusinessgoalId } from 'app/shared/model/project-businessgoal-id.model';

describe('Component Tests', () => {
  describe('ProjectBusinessgoalId Management Component', () => {
    let comp: ProjectBusinessgoalIdComponent;
    let fixture: ComponentFixture<ProjectBusinessgoalIdComponent>;
    let service: ProjectBusinessgoalIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectBusinessgoalIdComponent],
        providers: []
      })
        .overrideTemplate(ProjectBusinessgoalIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectBusinessgoalIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectBusinessgoalIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectBusinessgoalId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectBusinessgoalIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
