package com.gmb.conexion;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;


public class GmbConexion<T, PK extends Serializable> {

	@PersistenceContext(unitName = "PostgresDS")
	public EntityManager entityMgr;
 
	//@PersistenceContext(unitName = "AS2DS") 
	public EntityManager entityMgr2;
 
	protected Class<T> entityClass;

	public T create(T t) {
		// em.persist(t);
		try {
			entityMgr.getTransaction().begin();
			entityMgr.persist(t);
			entityMgr.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.toString());
			entityMgr.getTransaction().rollback();
		}

		return t;
	}

	public T read(PK id) {
		return entityMgr.find(entityClass, id);
	}

	public T update(T t) {
		return entityMgr.merge(t);
	}

}