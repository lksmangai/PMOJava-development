/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectTypeIdComponent } from 'app/entities/project-type-id/project-type-id.component';
import { ProjectTypeIdService } from 'app/entities/project-type-id/project-type-id.service';
import { ProjectTypeId } from 'app/shared/model/project-type-id.model';

describe('Component Tests', () => {
  describe('ProjectTypeId Management Component', () => {
    let comp: ProjectTypeIdComponent;
    let fixture: ComponentFixture<ProjectTypeIdComponent>;
    let service: ProjectTypeIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectTypeIdComponent],
        providers: []
      })
        .overrideTemplate(ProjectTypeIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectTypeIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectTypeIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectTypeId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectTypeIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
