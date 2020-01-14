package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StateMaster.class)
public abstract class StateMaster_ {

	public static volatile SingularAttribute<StateMaster, String> stateName;
	public static volatile SingularAttribute<StateMaster, String> stateCode;
	public static volatile SingularAttribute<StateMaster, Long> id;

	public static final String STATE_NAME = "stateName";
	public static final String STATE_CODE = "stateCode";
	public static final String ID = "id";

}

