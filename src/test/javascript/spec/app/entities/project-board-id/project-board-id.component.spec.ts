/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectBoardIdComponent } from 'app/entities/project-board-id/project-board-id.component';
import { ProjectBoardIdService } from 'app/entities/project-board-id/project-board-id.service';
import { ProjectBoardId } from 'app/shared/model/project-board-id.model';

describe('Component Tests', () => {
  describe('ProjectBoardId Management Component', () => {
    let comp: ProjectBoardIdComponent;
    let fixture: ComponentFixture<ProjectBoardIdComponent>;
    let service: ProjectBoardIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectBoardIdComponent],
        providers: []
      })
        .overrideTemplate(ProjectBoardIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectBoardIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectBoardIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectBoardId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectBoardIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
