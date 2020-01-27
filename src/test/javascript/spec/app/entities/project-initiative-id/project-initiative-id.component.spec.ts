/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectInitiativeIdComponent } from 'app/entities/project-initiative-id/project-initiative-id.component';
import { ProjectInitiativeIdService } from 'app/entities/project-initiative-id/project-initiative-id.service';
import { ProjectInitiativeId } from 'app/shared/model/project-initiative-id.model';

describe('Component Tests', () => {
  describe('ProjectInitiativeId Management Component', () => {
    let comp: ProjectInitiativeIdComponent;
    let fixture: ComponentFixture<ProjectInitiativeIdComponent>;
    let service: ProjectInitiativeIdService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectInitiativeIdComponent],
        providers: []
      })
        .overrideTemplate(ProjectInitiativeIdComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectInitiativeIdComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectInitiativeIdService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectInitiativeId(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectInitiativeIds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
