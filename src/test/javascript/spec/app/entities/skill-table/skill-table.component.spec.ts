/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { SkillTableComponent } from 'app/entities/skill-table/skill-table.component';
import { SkillTableService } from 'app/entities/skill-table/skill-table.service';
import { SkillTable } from 'app/shared/model/skill-table.model';

describe('Component Tests', () => {
  describe('SkillTable Management Component', () => {
    let comp: SkillTableComponent;
    let fixture: ComponentFixture<SkillTableComponent>;
    let service: SkillTableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [SkillTableComponent],
        providers: []
      })
        .overrideTemplate(SkillTableComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SkillTableComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SkillTableService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SkillTable(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.skillTables[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
