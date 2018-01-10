/*  1:   */ package com.asinfo.as2.dao.presupuesto;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.dao.AbstractDaoAS2;
/*  4:   */ import com.asinfo.as2.entities.presupuesto.DetallePresupuesto;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.Map;
/*  7:   */ import javax.ejb.Stateless;
/*  8:   */ import javax.persistence.EntityManager;
/*  9:   */ import javax.persistence.TypedQuery;
/* 10:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 11:   */ import javax.persistence.criteria.CriteriaQuery;
/* 12:   */ import javax.persistence.criteria.Expression;
/* 13:   */ import javax.persistence.criteria.Predicate;
/* 14:   */ import javax.persistence.criteria.Root;
/* 15:   */ 
/* 16:   */ @Stateless
/* 17:   */ public class DetallePresupuestoDao
/* 18:   */   extends AbstractDaoAS2<DetallePresupuesto>
/* 19:   */ {
/* 20:   */   public DetallePresupuestoDao()
/* 21:   */   {
/* 22:33 */     super(DetallePresupuesto.class);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List<DetallePresupuesto> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 26:   */   {
/* 27:43 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 28:44 */     CriteriaQuery<DetallePresupuesto> criteriaQuery = criteriaBuilder.createQuery(DetallePresupuesto.class);
/* 29:45 */     Root<DetallePresupuesto> from = criteriaQuery.from(DetallePresupuesto.class);
/* 30:   */     
/* 31:47 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 32:48 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 33:   */     
/* 34:50 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 35:   */     
/* 36:52 */     CriteriaQuery<DetallePresupuesto> select = criteriaQuery.select(from);
/* 37:53 */     TypedQuery<DetallePresupuesto> typedQuery = this.em.createQuery(select);
/* 38:54 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 39:   */     
/* 40:56 */     return typedQuery.getResultList();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public List<DetallePresupuesto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 44:   */   {
/* 45:66 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 46:67 */     CriteriaQuery<DetallePresupuesto> criteriaQuery = criteriaBuilder.createQuery(DetallePresupuesto.class);
/* 47:68 */     Root<DetallePresupuesto> from = criteriaQuery.from(DetallePresupuesto.class);
/* 48:   */     
/* 49:70 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 50:71 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 51:   */     
/* 52:73 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 53:   */     
/* 54:75 */     CriteriaQuery<DetallePresupuesto> select = criteriaQuery.select(from);
/* 55:76 */     TypedQuery<DetallePresupuesto> typedQuery = this.em.createQuery(select);
/* 56:   */     
/* 57:78 */     return typedQuery.getResultList();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public DetallePresupuesto cargarDetalle(int idDetallePresupuesto)
/* 61:   */   {
/* 62:88 */     return null;
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.presupuesto.DetallePresupuestoDao
 * JD-Core Version:    0.7.0.1
 */