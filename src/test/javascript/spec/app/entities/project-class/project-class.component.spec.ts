/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectClassComponent } from 'app/entities/project-class/project-class.component';
import { ProjectClassService } from 'app/entities/project-class/project-class.service';
import { ProjectClass } from 'app/shared/model/project-class.model';

describe('Component Tests', () => {
  describe('ProjectClass Management Component', () => {
    let comp: ProjectClassComponent;
    let fixture: ComponentFixture<ProjectClassComponent>;
    let service: ProjectClassService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectClassComponent],
        providers: []
      })
        .overrideTemplate(ProjectClassComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectClassComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectClassService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectClass(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectClasses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
