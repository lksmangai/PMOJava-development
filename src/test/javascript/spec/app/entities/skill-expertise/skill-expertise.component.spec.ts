/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { SkillExpertiseComponent } from 'app/entities/skill-expertise/skill-expertise.component';
import { SkillExpertiseService } from 'app/entities/skill-expertise/skill-expertise.service';
import { SkillExpertise } from 'app/shared/model/skill-expertise.model';

describe('Component Tests', () => {
  describe('SkillExpertise Management Component', () => {
    let comp: SkillExpertiseComponent;
    let fixture: ComponentFixture<SkillExpertiseComponent>;
    let service: SkillExpertiseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillExpertiseComponent],
        providers: []
      })
        .overrideTemplate(SkillExpertiseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkillExpertiseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillExpertiseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SkillExpertise(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.skillExpertises[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
