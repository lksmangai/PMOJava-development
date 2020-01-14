package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GroupMembers.class)
public abstract class GroupMembers_ {

	public static volatile SingularAttribute<GroupMembers, String> code;
	public static volatile SetAttribute<GroupMembers, Roles> roles;
	public static volatile SingularAttribute<GroupMembers, String> name;
	public static volatile SingularAttribute<GroupMembers, String> description;
	public static volatile SetAttribute<GroupMembers, UserContact> userContacts;
	public static volatile SingularAttribute<GroupMembers, Long> id;

	public static final String CODE = "code";
	public static final String ROLES = "roles";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String USER_CONTACTS = "userContacts";
	public static final String ID = "id";

}

