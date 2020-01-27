/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectTagComponent } from 'app/entities/project-tag/project-tag.component';
import { ProjectTagService } from 'app/entities/project-tag/project-tag.service';
import { ProjectTag } from 'app/shared/model/project-tag.model';

describe('Component Tests', () => {
  describe('ProjectTag Management Component', () => {
    let comp: ProjectTagComponent;
    let fixture: ComponentFixture<ProjectTagComponent>;
    let service: ProjectTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectTagComponent],
        providers: []
      })
        .overrideTemplate(ProjectTagComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectTagComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectTagService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectTag(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectTags[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
