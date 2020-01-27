/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Qnow1TestModule } from '../../../test.module';
import { GroupMembersComponent } from 'app/entities/group-members/group-members.component';
import { GroupMembersService } from 'app/entities/group-members/group-members.service';
import { GroupMembers } from 'app/shared/model/group-members.model';

describe('Component Tests', () => {
  describe('GroupMembers Management Component', () => {
    let comp: GroupMembersComponent;
    let fixture: ComponentFixture<GroupMembersComponent>;
    let service: GroupMembersService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Qnow1TestModule],
        declarations: [GroupMembersComponent],
        providers: []
      })
        .overrideTemplate(GroupMembersComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupMembersComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupMembersService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GroupMembers(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.groupMembers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
