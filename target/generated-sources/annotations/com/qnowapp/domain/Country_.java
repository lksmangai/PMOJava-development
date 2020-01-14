package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Country.class)
public abstract class Country_ {

	public static volatile SingularAttribute<Country, String> countryCode;
	public static volatile SingularAttribute<Country, Long> id;
	public static volatile SingularAttribute<Country, String> countryName;

	public static final String COUNTRY_CODE = "countryCode";
	public static final String ID = "id";
	public static final String COUNTRY_NAME = "countryName";

}

