/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectSubgoalIdComponent } from 'app/entities/project-subgoal-id/project-subgoal-id.component';
import { ProjectSubgoalIdService } from 'app/entities/project-subgoal-id/project-subgoal-id.service';
import { ProjectSubgoalId } from 'app/shared/model/project-subgoal-id.model';

describe('Component Tests', () => {
  describe('ProjectSubgoalId Management Component', () => {
    let comp: ProjectSubgoalIdComponent;
    let fixture: ComponentFixture<ProjectSubgoalIdComponent>;
    let service: ProjectSubgoalIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectSubgoalIdComponent],
        providers: []
      })
        .overrideTemplate(ProjectSubgoalIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectSubgoalIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectSubgoalIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectSubgoalId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectSubgoalIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
