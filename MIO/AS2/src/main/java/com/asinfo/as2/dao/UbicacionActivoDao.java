/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.UbicacionActivo;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import javax.ejb.Stateless;
/*   7:    */ import javax.persistence.EntityManager;
/*   8:    */ import javax.persistence.TypedQuery;
/*   9:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  10:    */ import javax.persistence.criteria.CriteriaQuery;
/*  11:    */ import javax.persistence.criteria.Expression;
/*  12:    */ import javax.persistence.criteria.JoinType;
/*  13:    */ import javax.persistence.criteria.Predicate;
/*  14:    */ import javax.persistence.criteria.Root;
/*  15:    */ 
/*  16:    */ @Stateless
/*  17:    */ public class UbicacionActivoDao
/*  18:    */   extends AbstractDaoAS2<UbicacionActivo>
/*  19:    */ {
/*  20:    */   public UbicacionActivoDao()
/*  21:    */   {
/*  22: 41 */     super(UbicacionActivo.class);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public List<UbicacionActivo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  26:    */   {
/*  27: 52 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  28: 53 */     CriteriaQuery<UbicacionActivo> criteriaQuery = criteriaBuilder.createQuery(UbicacionActivo.class);
/*  29: 54 */     Root<UbicacionActivo> from = criteriaQuery.from(UbicacionActivo.class);
/*  30: 55 */     from.fetch("departamento", JoinType.LEFT);
/*  31: 56 */     from.fetch("sucursal", JoinType.LEFT);
/*  32:    */     
/*  33: 58 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  34: 59 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  35:    */     
/*  36: 61 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  37:    */     
/*  38: 63 */     CriteriaQuery<UbicacionActivo> select = criteriaQuery.select(from);
/*  39: 64 */     TypedQuery<UbicacionActivo> typedQuery = this.em.createQuery(select);
/*  40: 65 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  41:    */     
/*  42: 67 */     return typedQuery.getResultList();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<UbicacionActivo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  46:    */   {
/*  47: 79 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  48: 80 */     CriteriaQuery<UbicacionActivo> criteriaQuery = criteriaBuilder.createQuery(UbicacionActivo.class);
/*  49: 81 */     Root<UbicacionActivo> from = criteriaQuery.from(UbicacionActivo.class);
/*  50: 82 */     from.fetch("departamento", JoinType.LEFT);
/*  51: 83 */     from.fetch("sucursal", JoinType.LEFT);
/*  52:    */     
/*  53: 85 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  54: 86 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  55:    */     
/*  56: 88 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  57:    */     
/*  58: 90 */     CriteriaQuery<UbicacionActivo> select = criteriaQuery.select(from);
/*  59: 91 */     TypedQuery<UbicacionActivo> typedQuery = this.em.createQuery(select);
/*  60:    */     
/*  61: 93 */     return typedQuery.getResultList();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public UbicacionActivo cargarDetalle(int idUbicacionActivo)
/*  65:    */   {
/*  66:104 */     return null;
/*  67:    */   }
/*  68:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.UbicacionActivoDao
 * JD-Core Version:    0.7.0.1
 */