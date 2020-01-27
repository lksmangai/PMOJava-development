/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectVerticalComponent } from 'app/entities/project-vertical/project-vertical.component';
import { ProjectVerticalService } from 'app/entities/project-vertical/project-vertical.service';
import { ProjectVertical } from 'app/shared/model/project-vertical.model';

describe('Component Tests', () => {
  describe('ProjectVertical Management Component', () => {
    let comp: ProjectVerticalComponent;
    let fixture: ComponentFixture<ProjectVerticalComponent>;
    let service: ProjectVerticalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectVerticalComponent],
        providers: []
      })
        .overrideTemplate(ProjectVerticalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectVerticalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectVerticalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectVertical(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectVerticals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
