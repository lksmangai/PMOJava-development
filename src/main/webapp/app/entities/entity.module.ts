import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'qnow-user',
        loadChildren: './qnow-user/qnow-user.module#AppQnowUserModule'
      },
      {
        path: 'user-contact',
        loadChildren: './user-contact/user-contact.module#AppUserContactModule'
      },
      {
        path: 'city',
        loadChildren: './city/city.module#AppCityModule'
      },
      {
        path: 'state-master',
        loadChildren: './state-master/state-master.module#AppStateMasterModule'
      },
      {
        path: 'country',
        loadChildren: './country/country.module#AppCountryModule'
      },
      {
        path: 'im-employee',
        loadChildren: './im-employee/im-employee.module#AppImEmployeeModule'
      },
      {
        path: 'department',
        loadChildren: './department/department.module#AppDepartmentModule'
      },
      {
        path: 'skills',
        loadChildren: './skills/skills.module#AppSkillsModule'
      },
      {
        path: 'employee-status',
        loadChildren: './employee-status/employee-status.module#AppEmployeeStatusModule'
      },
      {
        path: 'skill-category',
        loadChildren: './skill-category/skill-category.module#AppSkillCategoryModule'
      },
      {
        path: 'im-projects',
        loadChildren: './im-projects/im-projects.module#AppImProjectsModule'
      },
      {
        path: 'project-type-id',
        loadChildren: './project-type-id/project-type-id.module#AppProjectTypeIdModule'
      },
      {
        path: 'project-status-id',
        loadChildren: './project-status-id/project-status-id.module#AppProjectStatusIdModule'
      },
      {
        path: 'project-board-id',
        loadChildren: './project-board-id/project-board-id.module#AppProjectBoardIdModule'
      },
      {
        path: 'company',
        loadChildren: './company/company.module#AppCompanyModule'
      },
      {
        path: 'project-cost-center-id',
        loadChildren: './project-cost-center-id/project-cost-center-id.module#AppProjectCostCenterIdModule'
      },
      {
        path: 'project-bucket-id',
        loadChildren: './project-bucket-id/project-bucket-id.module#AppProjectBucketIdModule'
      },
      {
        path: 'project-maingoal-id',
        loadChildren: './project-maingoal-id/project-maingoal-id.module#AppProjectMaingoalIdModule'
      },
      {
        path: 'project-subgoal-id',
        loadChildren: './project-subgoal-id/project-subgoal-id.module#AppProjectSubgoalIdModule'
      },
      {
        path: 'project-businessgoal-id',
        loadChildren: './project-businessgoal-id/project-businessgoal-id.module#AppProjectBusinessgoalIdModule'
      },
      {
        path: 'project-initiative-id',
        loadChildren: './project-initiative-id/project-initiative-id.module#AppProjectInitiativeIdModule'
      },
      {
        path: 'group-members',
        loadChildren: './group-members/group-members.module#AppGroupMembersModule'
      },
      {
        path: 'roles',
        loadChildren: './roles/roles.module#AppRolesModule'
      },
      {
        path: 'project-allocation',
        loadChildren: './project-allocation/project-allocation.module#AppProjectAllocationModule'
      },
      {
        path: 'skill-expertise',
        loadChildren: './skill-expertise/skill-expertise.module#AppSkillExpertiseModule'
      },
      {
        path: 'skill-table',
        loadChildren: './skill-table/skill-table.module#AppSkillTableModule'
      },
      {
        path: 'im-timesheet',
        loadChildren: './im-timesheet/im-timesheet.module#AppImTimesheetModule'
      },
      {
        path: 'opportunity-priority-id',
        loadChildren: './opportunity-priority-id/opportunity-priority-id.module#AppOpportunityPriorityIdModule'
      },
      {
        path: 'backlog-practice',
        loadChildren: './backlog-practice/backlog-practice.module#AppBacklogPracticeModule'
      },
      {
        path: 'project-theme',
        loadChildren: './project-theme/project-theme.module#AppProjectThemeModule'
      },
      {
        path: 'project-class',
        loadChildren: './project-class/project-class.module#AppProjectClassModule'
      },
      {
        path: 'project-vertical',
        loadChildren: './project-vertical/project-vertical.module#AppProjectVerticalModule'
      },
      {
        path: 'project-roles',
        loadChildren: './project-roles/project-roles.module#AppProjectRolesModule'
      },
      {
        path: 'file-storage',
        loadChildren: './file-storage/file-storage.module#AppFileStorageModule'
      },
      {
        path: 'project-tag',
        loadChildren: './project-tag/project-tag.module#AppProjectTagModule'
      },
      {
        path: 'tag-type',
        loadChildren: './tag-type/tag-type.module#AppTagTypeModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEntityModule {}
