/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { ProjectThemeComponent } from 'app/entities/project-theme/project-theme.component';
import { ProjectThemeService } from 'app/entities/project-theme/project-theme.service';
import { ProjectTheme } from 'app/shared/model/project-theme.model';

describe('Component Tests', () => {
  describe('ProjectTheme Management Component', () => {
    let comp: ProjectThemeComponent;
    let fixture: ComponentFixture<ProjectThemeComponent>;
    let service: ProjectThemeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [ProjectThemeComponent],
        providers: []
      })
        .overrideTemplate(ProjectThemeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectThemeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectThemeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProjectTheme(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.projectThemes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
