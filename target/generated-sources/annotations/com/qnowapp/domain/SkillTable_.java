package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SkillTable.class)
public abstract class SkillTable_ {

	public static volatile SingularAttribute<SkillTable, Skills> skills;
	public static volatile SingularAttribute<SkillTable, SkillExpertise> skillExpertise;
	public static volatile SingularAttribute<SkillTable, ImProjects> imProjects;
	public static volatile SingularAttribute<SkillTable, Long> id;
	public static volatile SingularAttribute<SkillTable, UserContact> userContact;

	public static final String SKILLS = "skills";
	public static final String SKILL_EXPERTISE = "skillExpertise";
	public static final String IM_PROJECTS = "imProjects";
	public static final String ID = "id";
	public static final String USER_CONTACT = "userContact";

}

