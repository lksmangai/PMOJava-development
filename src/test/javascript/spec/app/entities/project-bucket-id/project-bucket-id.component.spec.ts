/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectBucketIdComponent } from 'app/entities/project-bucket-id/project-bucket-id.component';
import { ProjectBucketIdService } from 'app/entities/project-bucket-id/project-bucket-id.service';
import { ProjectBucketId } from 'app/shared/model/project-bucket-id.model';

describe('Component Tests', () => {
  describe('ProjectBucketId Management Component', () => {
    let comp: ProjectBucketIdComponent;
    let fixture: ComponentFixture<ProjectBucketIdComponent>;
    let service: ProjectBucketIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectBucketIdComponent],
        providers: []
      })
        .overrideTemplate(ProjectBucketIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectBucketIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectBucketIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectBucketId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectBucketIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
