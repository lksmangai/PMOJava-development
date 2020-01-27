/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ImProjectsComponent } from 'app/entities/im-projects/im-projects.component';
import { ImProjectsService } from 'app/entities/im-projects/im-projects.service';
import { ImProjects } from 'app/shared/model/im-projects.model';

describe('Component Tests', () => {
  describe('ImProjects Management Component', () => {
    let comp: ImProjectsComponent;
    let fixture: ComponentFixture<ImProjectsComponent>;
    let service: ImProjectsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ImProjectsComponent],
        providers: []
      })
        .overrideTemplate(ImProjectsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImProjectsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImProjectsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ImProjects(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.imProjects[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
