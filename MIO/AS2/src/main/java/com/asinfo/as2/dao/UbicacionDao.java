/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.Ubicacion;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.Query;
/*  9:   */ import javax.persistence.TypedQuery;
/* 10:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 11:   */ import javax.persistence.criteria.CriteriaQuery;
/* 12:   */ import javax.persistence.criteria.Expression;
/* 13:   */ import javax.persistence.criteria.JoinType;
/* 14:   */ import javax.persistence.criteria.Predicate;
/* 15:   */ import javax.persistence.criteria.Root;
/* 16:   */ 
/* 17:   */ @Stateless
/* 18:   */ public class UbicacionDao
/* 19:   */   extends AbstractDaoAS2<Ubicacion>
/* 20:   */ {
/* 21:   */   public UbicacionDao()
/* 22:   */   {
/* 23:41 */     super(Ubicacion.class);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public List<Ubicacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 27:   */   {
/* 28:46 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 29:47 */     CriteriaQuery<Ubicacion> criteriaQuery = criteriaBuilder.createQuery(Ubicacion.class);
/* 30:48 */     Root<Ubicacion> from = criteriaQuery.from(Ubicacion.class);
/* 31:   */     
/* 32:50 */     from.fetch("sucursal", JoinType.LEFT);
/* 33:51 */     from.fetch("departamento", JoinType.LEFT);
/* 34:   */     
/* 35:53 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 36:54 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/* 37:   */     
/* 38:56 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 39:   */     
/* 40:58 */     CriteriaQuery<Ubicacion> select = criteriaQuery.select(from);
/* 41:59 */     TypedQuery<Ubicacion> typedQuery = this.em.createQuery(select);
/* 42:   */     
/* 43:61 */     return typedQuery.getResultList();
/* 44:   */   }
/* 45:   */   
/* 46:   */   public Ubicacion obtenerUbicacionPorDireccion1(String direccion1, int idOrganizacion)
/* 47:   */   {
/* 48:71 */     StringBuffer sql = new StringBuffer();
/* 49:72 */     sql.append("SELECT u FROM Ubicacion u WHERE u.direccion1 = :direccion1 AND u.idOrganizacion = :idOrganizacion");
/* 50:   */     
/* 51:74 */     Query query = this.em.createQuery(sql.toString());
/* 52:75 */     query.setParameter("direccion1", direccion1);
/* 53:76 */     query.setParameter("idOrganizacion", Integer.valueOf(idOrganizacion));
/* 54:   */     
/* 55:78 */     return (Ubicacion)query.getSingleResult();
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.UbicacionDao
 * JD-Core Version:    0.7.0.1
 */