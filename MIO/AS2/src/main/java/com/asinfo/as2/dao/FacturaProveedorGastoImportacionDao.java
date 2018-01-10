/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.FacturaProveedorGastoImportacion;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.TypedQuery;
/*  9:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 10:   */ import javax.persistence.criteria.CriteriaQuery;
/* 11:   */ import javax.persistence.criteria.Expression;
/* 12:   */ import javax.persistence.criteria.Predicate;
/* 13:   */ import javax.persistence.criteria.Root;
/* 14:   */ 
/* 15:   */ @Stateless
/* 16:   */ public class FacturaProveedorGastoImportacionDao
/* 17:   */   extends AbstractDaoAS2<FacturaProveedorGastoImportacion>
/* 18:   */ {
/* 19:   */   public FacturaProveedorGastoImportacionDao()
/* 20:   */   {
/* 21:38 */     super(FacturaProveedorGastoImportacion.class);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public List<FacturaProveedorGastoImportacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 25:   */   {
/* 26:48 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 27:49 */     CriteriaQuery<FacturaProveedorGastoImportacion> criteriaQuery = criteriaBuilder.createQuery(FacturaProveedorGastoImportacion.class);
/* 28:50 */     Root<FacturaProveedorGastoImportacion> from = criteriaQuery.from(FacturaProveedorGastoImportacion.class);
/* 29:   */     
/* 30:52 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 31:53 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 32:   */     
/* 33:55 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 34:   */     
/* 35:57 */     CriteriaQuery<FacturaProveedorGastoImportacion> select = criteriaQuery.select(from);
/* 36:58 */     TypedQuery<FacturaProveedorGastoImportacion> typedQuery = this.em.createQuery(select);
/* 37:59 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 38:   */     
/* 39:61 */     return typedQuery.getResultList();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public List<FacturaProveedorGastoImportacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 43:   */   {
/* 44:72 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 45:73 */     CriteriaQuery<FacturaProveedorGastoImportacion> criteriaQuery = criteriaBuilder.createQuery(FacturaProveedorGastoImportacion.class);
/* 46:74 */     Root<FacturaProveedorGastoImportacion> from = criteriaQuery.from(FacturaProveedorGastoImportacion.class);
/* 47:   */     
/* 48:76 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 49:77 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 50:   */     
/* 51:79 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 52:   */     
/* 53:81 */     CriteriaQuery<FacturaProveedorGastoImportacion> select = criteriaQuery.select(from);
/* 54:82 */     TypedQuery<FacturaProveedorGastoImportacion> typedQuery = this.em.createQuery(select);
/* 55:   */     
/* 56:84 */     return typedQuery.getResultList();
/* 57:   */   }
/* 58:   */   
/* 59:   */   public FacturaProveedorGastoImportacion cargarDetalle(int idFacturaProveedorGastoImportacion)
/* 60:   */   {
/* 61:93 */     return null;
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.FacturaProveedorGastoImportacionDao
 * JD-Core Version:    0.7.0.1
 */