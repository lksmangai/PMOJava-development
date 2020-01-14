package com.qnowapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(FileStorage.class)
public abstract class FileStorage_ {

	public static volatile SingularAttribute<FileStorage, String> filename;
	public static volatile SingularAttribute<FileStorage, ImProjects> imProjects;
	public static volatile SingularAttribute<FileStorage, String> caption;
	public static volatile SingularAttribute<FileStorage, Long> id;
	public static volatile SingularAttribute<FileStorage, UserContact> userContact;

	public static final String FILENAME = "filename";
	public static final String IM_PROJECTS = "imProjects";
	public static final String CAPTION = "caption";
	public static final String ID = "id";
	public static final String USER_CONTACT = "userContact";

}

