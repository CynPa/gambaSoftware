/*   1:    */ package com.asinfo.as2.dao;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ import javax.ejb.Stateless;
/*   9:    */ import javax.persistence.EntityManager;
/*  10:    */ import javax.persistence.TypedQuery;
/*  11:    */ import javax.persistence.criteria.CriteriaBuilder;
/*  12:    */ import javax.persistence.criteria.CriteriaQuery;
/*  13:    */ import javax.persistence.criteria.Expression;
/*  14:    */ import javax.persistence.criteria.Path;
/*  15:    */ import javax.persistence.criteria.Predicate;
/*  16:    */ import javax.persistence.criteria.Root;
/*  17:    */ 
/*  18:    */ @Stateless
/*  19:    */ public class MotivoAjusteInventarioDao
/*  20:    */   extends AbstractDaoAS2<MotivoAjusteInventario>
/*  21:    */ {
/*  22:    */   public MotivoAjusteInventarioDao()
/*  23:    */   {
/*  24: 39 */     super(MotivoAjusteInventario.class);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public List<MotivoAjusteInventario> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  28:    */   {
/*  29: 50 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  30: 51 */     CriteriaQuery<MotivoAjusteInventario> criteriaQuery = criteriaBuilder.createQuery(MotivoAjusteInventario.class);
/*  31: 52 */     Root<MotivoAjusteInventario> from = criteriaQuery.from(MotivoAjusteInventario.class);
/*  32:    */     
/*  33: 54 */     List<Expression<?>> expresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/*  34: 55 */     criteriaQuery.where((Predicate[])expresiones.toArray(new Predicate[expresiones.size()]));
/*  35:    */     
/*  36: 57 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  37:    */     
/*  38: 59 */     CriteriaQuery<MotivoAjusteInventario> select = criteriaQuery.select(from);
/*  39: 60 */     TypedQuery<MotivoAjusteInventario> typedQuery = this.em.createQuery(select);
/*  40: 61 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/*  41:    */     
/*  42: 63 */     return typedQuery.getResultList();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public MotivoAjusteInventario cargarDetalle(int idMotivoAjusteInventario)
/*  46:    */   {
/*  47: 73 */     MotivoAjusteInventario motivo = (MotivoAjusteInventario)buscarPorId(Integer.valueOf(idMotivoAjusteInventario));
/*  48: 74 */     return motivo;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<MotivoAjusteInventario> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  52:    */   {
/*  53: 80 */     filters = agregarFiltrosOrganizacion(filters);
/*  54:    */     
/*  55: 82 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/*  56: 83 */     CriteriaQuery<MotivoAjusteInventario> criteriaQuery = criteriaBuilder.createQuery(MotivoAjusteInventario.class);
/*  57: 84 */     Root<MotivoAjusteInventario> from = criteriaQuery.from(MotivoAjusteInventario.class);
/*  58:    */     
/*  59: 86 */     Predicate conjunction = criteriaBuilder.conjunction();
/*  60: 87 */     Predicate disjunction = criteriaBuilder.disjunction();
/*  61: 89 */     for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();)
/*  62:    */     {
/*  63: 90 */       String filterProperty = (String)it.next();
/*  64: 92 */       if ((filterProperty != null) && (!filterProperty.isEmpty()))
/*  65:    */       {
/*  66: 93 */         String filterValue = (String)filters.get(filterProperty);
/*  67: 95 */         if (filterProperty.startsWith("idOrganizacion")) {
/*  68: 96 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/*  69: 97 */         } else if (filterProperty.startsWith("activo")) {
/*  70: 98 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Boolean.valueOf(Boolean.parseBoolean(filterValue))));
/*  71: 99 */         } else if (filterProperty.equals("idMotivoAjusteInventario")) {
/*  72:100 */           conjunction.getExpressions().add(criteriaBuilder.equal(from.get(filterProperty), Integer.valueOf(Integer.parseInt(filterValue))));
/*  73:    */         } else {
/*  74:102 */           disjunction.getExpressions().add(criteriaBuilder
/*  75:103 */             .like(criteriaBuilder.lower(from.get(filterProperty).as(String.class)), "%" + filterValue + "%"));
/*  76:    */         }
/*  77:    */       }
/*  78:    */     }
/*  79:108 */     if (disjunction.getExpressions().size() > 0) {
/*  80:109 */       conjunction.getExpressions().add(disjunction);
/*  81:    */     }
/*  82:112 */     if (conjunction.getExpressions().size() > 0) {
/*  83:113 */       criteriaQuery.where(conjunction);
/*  84:    */     }
/*  85:116 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/*  86:    */     
/*  87:118 */     CriteriaQuery<MotivoAjusteInventario> select = criteriaQuery.select(from);
/*  88:119 */     TypedQuery<MotivoAjusteInventario> typedQuery = this.em.createQuery(select);
/*  89:    */     
/*  90:121 */     return typedQuery.getResultList();
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.MotivoAjusteInventarioDao
 * JD-Core Version:    0.7.0.1
 */