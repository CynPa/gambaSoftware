/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import javax.ejb.Stateless;
/*   7:    */ import javax.persistence.EntityManager;
/*   8:    */ import javax.persistence.TypedQuery;
/*   9:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  10:    */ import javax.persistence.criteria.CriteriaQuery;
/*  11:    */ import javax.persistence.criteria.Expression;
/*  12:    */ import javax.persistence.criteria.JoinType;
/*  13:    */ import javax.persistence.criteria.Path;
/*  14:    */ import javax.persistence.criteria.Predicate;
/*  15:    */ import javax.persistence.criteria.Root;
/*  16:    */ import javax.persistence.criteria.Selection;
/*  17:    */ 
/*  18:    */ @Stateless
/*  19:    */ public class MotivoBajaActivoDao
/*  20:    */   extends AbstractDaoAS2<MotivoBajaActivo>
/*  21:    */ {
/*  22:    */   public MotivoBajaActivoDao()
/*  23:    */   {
/*  24: 42 */     super(MotivoBajaActivo.class);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public List<MotivoBajaActivo> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  28:    */   {
/*  29: 53 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  30: 54 */     CriteriaQuery<MotivoBajaActivo> criteriaQuery = criteriaBuilder.createQuery(MotivoBajaActivo.class);
/*  31: 55 */     Root<MotivoBajaActivo> from = criteriaQuery.from(MotivoBajaActivo.class);
/*  32: 56 */     from.fetch("documento", JoinType.LEFT);
/*  33: 57 */     from.fetch("cuentaContableMotivoBajaActivo", JoinType.LEFT);
/*  34:    */     
/*  35: 59 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  36: 60 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  37: 61 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  38:    */     
/*  39: 63 */     CriteriaQuery<MotivoBajaActivo> select = criteriaQuery.select(from);
/*  40: 64 */     TypedQuery<MotivoBajaActivo> typedQuery = this.em.createQuery(select);
/*  41: 65 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  42:    */     
/*  43: 67 */     return typedQuery.getResultList();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public List<MotivoBajaActivo> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  47:    */   {
/*  48: 79 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  49: 80 */     CriteriaQuery<MotivoBajaActivo> criteriaQuery = criteriaBuilder.createQuery(MotivoBajaActivo.class);
/*  50: 81 */     Root<MotivoBajaActivo> from = criteriaQuery.from(MotivoBajaActivo.class);
/*  51:    */     
/*  52: 83 */     Path<Integer> pathIdMotivoBajaActivo = from.get("idMotivoBajaActivo");
/*  53: 84 */     Path<String> pathCodigo = from.get("codigo");
/*  54: 85 */     Path<String> pathNombre = from.get("nombre");
/*  55:    */     
/*  56: 87 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  57: 88 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  58: 89 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  59:    */     
/*  60: 91 */     CriteriaQuery<MotivoBajaActivo> select = criteriaQuery.multiselect(new Selection[] { pathIdMotivoBajaActivo, pathNombre, pathCodigo });
/*  61: 92 */     TypedQuery<MotivoBajaActivo> typedQuery = this.em.createQuery(select);
/*  62:    */     
/*  63: 94 */     return typedQuery.getResultList();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int contarPorCriterio(Map<String, String> filters)
/*  67:    */   {
/*  68:106 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  69:107 */     CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
/*  70:    */     
/*  71:109 */     Root<MotivoBajaActivo> from = criteriaQuery.from(MotivoBajaActivo.class);
/*  72:110 */     criteriaQuery.select(criteriaBuilder.count(from));
/*  73:    */     
/*  74:112 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  75:113 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/*  76:    */     
/*  77:115 */     return ((Long)this.em.createQuery(criteriaQuery).getSingleResult()).intValue();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public MotivoBajaActivo cargarDetalle(int idMotivoBajaActivo)
/*  81:    */   {
/*  82:125 */     return null;
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.MotivoBajaActivoDao
 * JD-Core Version:    0.7.0.1
 */