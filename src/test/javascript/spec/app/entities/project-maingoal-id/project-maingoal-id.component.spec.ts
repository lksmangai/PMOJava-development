/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectMaingoalIdComponent } from 'app/entities/project-maingoal-id/project-maingoal-id.component';
import { ProjectMaingoalIdService } from 'app/entities/project-maingoal-id/project-maingoal-id.service';
import { ProjectMaingoalId } from 'app/shared/model/project-maingoal-id.model';

describe('Component Tests', () => {
  describe('ProjectMaingoalId Management Component', () => {
    let comp: ProjectMaingoalIdComponent;
    let fixture: ComponentFixture<ProjectMaingoalIdComponent>;
    let service: ProjectMaingoalIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectMaingoalIdComponent],
        providers: []
      })
        .overrideTemplate(ProjectMaingoalIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectMaingoalIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectMaingoalIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectMaingoalId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectMaingoalIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
