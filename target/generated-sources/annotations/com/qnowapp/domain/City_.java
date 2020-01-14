package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(City.class)
public abstract class City_ {

	public static volatile SingularAttribute<City, String> cityName;
	public static volatile SingularAttribute<City, String> cityCode;
	public static volatile SingularAttribute<City, Long> id;

	public static final String CITY_NAME = "cityName";
	public static final String CITY_CODE = "cityCode";
	public static final String ID = "id";

}

