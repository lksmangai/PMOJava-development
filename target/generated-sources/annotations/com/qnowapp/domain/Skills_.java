package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Skills.class)
public abstract class Skills_ {

	public static volatile SingularAttribute<Skills, String> skillName;
	public static volatile SingularAttribute<Skills, SkillCategory> skillCategoryId;
	public static volatile SingularAttribute<Skills, Skills> parentSkillsId;
	public static volatile SingularAttribute<Skills, String> skillCode;
	public static volatile SingularAttribute<Skills, Long> id;

	public static final String SKILL_NAME = "skillName";
	public static final String SKILL_CATEGORY_ID = "skillCategoryId";
	public static final String PARENT_SKILLS_ID = "parentSkillsId";
	public static final String SKILL_CODE = "skillCode";
	public static final String ID = "id";

}

