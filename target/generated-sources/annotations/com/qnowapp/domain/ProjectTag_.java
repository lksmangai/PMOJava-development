package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProjectTag.class)
public abstract class ProjectTag_ {

	public static volatile SingularAttribute<ProjectTag, String> code;
	public static volatile SingularAttribute<ProjectTag, String> flag;
	public static volatile SingularAttribute<ProjectTag, String> color;
	public static volatile SingularAttribute<ProjectTag, String> name;
	public static volatile SingularAttribute<ProjectTag, TagType> tagType;
	public static volatile SingularAttribute<ProjectTag, ImProjects> imProjects;
	public static volatile SingularAttribute<ProjectTag, String> description;
	public static volatile SingularAttribute<ProjectTag, Long> id;
	public static volatile SingularAttribute<ProjectTag, ImEmployee> imEmployee;

	public static final String CODE = "code";
	public static final String FLAG = "flag";
	public static final String COLOR = "color";
	public static final String NAME = "name";
	public static final String TAG_TYPE = "tagType";
	public static final String IM_PROJECTS = "imProjects";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String IM_EMPLOYEE = "imEmployee";

}

