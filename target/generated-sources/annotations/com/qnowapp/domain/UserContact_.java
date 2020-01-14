package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserContact.class)
public abstract class UserContact_ {

	public static volatile SingularAttribute<UserContact, String> teamName;
	public static volatile SingularAttribute<UserContact, QnowUser> qnowUser;
	public static volatile SingularAttribute<UserContact, String> waPostal;
	public static volatile SingularAttribute<UserContact, StateMaster> haState;
	public static volatile SingularAttribute<UserContact, String> primaryRole;
	public static volatile SingularAttribute<UserContact, City> waCity;
	public static volatile SingularAttribute<UserContact, String> permentAddress;
	public static volatile SingularAttribute<UserContact, Long> id;
	public static volatile SingularAttribute<UserContact, Country> waCountry;
	public static volatile SingularAttribute<UserContact, String> ucNote;
	public static volatile SetAttribute<UserContact, GroupMembers> groupMembers;
	public static volatile SingularAttribute<UserContact, String> initiative;
	public static volatile SingularAttribute<UserContact, String> homePhone;
	public static volatile SingularAttribute<UserContact, String> secondaryRole;
	public static volatile SingularAttribute<UserContact, String> haPostal;
	public static volatile SingularAttribute<UserContact, String> haLine1;
	public static volatile SingularAttribute<UserContact, City> haCity;
	public static volatile SingularAttribute<UserContact, String> haLine2;
	public static volatile SingularAttribute<UserContact, String> technology;
	public static volatile SingularAttribute<UserContact, Country> haCountry;
	public static volatile SingularAttribute<UserContact, StateMaster> waState;
	public static volatile SingularAttribute<UserContact, String> waLine1;
	public static volatile SingularAttribute<UserContact, String> workPhone;
	public static volatile SingularAttribute<UserContact, String> cellPhone;
	public static volatile SingularAttribute<UserContact, String> waLine2;
	public static volatile SetAttribute<UserContact, ImEmployee> imEmployees;

	public static final String TEAM_NAME = "teamName";
	public static final String QNOW_USER = "qnowUser";
	public static final String WA_POSTAL = "waPostal";
	public static final String HA_STATE = "haState";
	public static final String PRIMARY_ROLE = "primaryRole";
	public static final String WA_CITY = "waCity";
	public static final String PERMENT_ADDRESS = "permentAddress";
	public static final String ID = "id";
	public static final String WA_COUNTRY = "waCountry";
	public static final String UC_NOTE = "ucNote";
	public static final String GROUP_MEMBERS = "groupMembers";
	public static final String INITIATIVE = "initiative";
	public static final String HOME_PHONE = "homePhone";
	public static final String SECONDARY_ROLE = "secondaryRole";
	public static final String HA_POSTAL = "haPostal";
	public static final String HA_LINE1 = "haLine1";
	public static final String HA_CITY = "haCity";
	public static final String HA_LINE2 = "haLine2";
	public static final String TECHNOLOGY = "technology";
	public static final String HA_COUNTRY = "haCountry";
	public static final String WA_STATE = "waState";
	public static final String WA_LINE1 = "waLine1";
	public static final String WORK_PHONE = "workPhone";
	public static final String CELL_PHONE = "cellPhone";
	public static final String WA_LINE2 = "waLine2";
	public static final String IM_EMPLOYEES = "imEmployees";

}

