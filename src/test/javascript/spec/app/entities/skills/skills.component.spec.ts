/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { SkillsComponent } from 'app/entities/skills/skills.component';
import { SkillsService } from 'app/entities/skills/skills.service';
import { Skills } from 'app/shared/model/skills.model';

describe('Component Tests', () => {
  describe('Skills Management Component', () => {
    let comp: SkillsComponent;
    let fixture: ComponentFixture<SkillsComponent>;
    let service: SkillsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillsComponent],
        providers: []
      })
        .overrideTemplate(SkillsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkillsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Skills(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.skills[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
