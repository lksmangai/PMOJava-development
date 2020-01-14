package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Roles.class)
public abstract class Roles_ {

	public static volatile SetAttribute<Roles, GroupMembers> groupMembers;
	public static volatile SingularAttribute<Roles, String> code;
	public static volatile SingularAttribute<Roles, String> name;
	public static volatile SingularAttribute<Roles, String> description;
	public static volatile SingularAttribute<Roles, Long> id;

	public static final String GROUP_MEMBERS = "groupMembers";
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

