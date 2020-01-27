/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { RolesComponent } from 'app/entities/roles/roles.component';
import { RolesService } from 'app/entities/roles/roles.service';
import { Roles } from 'app/shared/model/roles.model';

describe('Component Tests', () => {
  describe('Roles Management Component', () => {
    let comp: RolesComponent;
    let fixture: ComponentFixture<RolesComponent>;
    let service: RolesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [RolesComponent],
        providers: []
      })
        .overrideTemplate(RolesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RolesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RolesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Roles(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.roles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
