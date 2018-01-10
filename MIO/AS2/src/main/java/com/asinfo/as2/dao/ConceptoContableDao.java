/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.ConceptoContable;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import javax.ejb.Stateless;
/*  7:   */ import javax.persistence.EntityManager;
/*  8:   */ import javax.persistence.TypedQuery;
/*  9:   */ import javax.persistence.criteria.CriteriaBuilder;
/* 10:   */ import javax.persistence.criteria.CriteriaQuery;
/* 11:   */ import javax.persistence.criteria.Expression;
/* 12:   */ import javax.persistence.criteria.JoinType;
/* 13:   */ import javax.persistence.criteria.Predicate;
/* 14:   */ import javax.persistence.criteria.Root;
/* 15:   */ 
/* 16:   */ @Stateless
/* 17:   */ public class ConceptoContableDao
/* 18:   */   extends AbstractDaoAS2<ConceptoContable>
/* 19:   */ {
/* 20:   */   public ConceptoContableDao()
/* 21:   */   {
/* 22:34 */     super(ConceptoContable.class);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List<ConceptoContable> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 26:   */   {
/* 27:41 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 28:42 */     CriteriaQuery<ConceptoContable> criteriaQuery = criteriaBuilder.createQuery(ConceptoContable.class);
/* 29:43 */     Root<ConceptoContable> from = criteriaQuery.from(ConceptoContable.class);
/* 30:   */     
/* 31:45 */     from.fetch("cuentaContableDebe", JoinType.LEFT);
/* 32:46 */     from.fetch("cuentaContableHaber", JoinType.LEFT);
/* 33:   */     
/* 34:48 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 35:49 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 36:50 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 37:   */     
/* 38:52 */     CriteriaQuery<ConceptoContable> select = criteriaQuery.select(from);
/* 39:   */     
/* 40:54 */     TypedQuery<ConceptoContable> typedQuery = this.em.createQuery(select);
/* 41:55 */     agregarPaginacion(startIndex, pageSize, typedQuery);
/* 42:   */     
/* 43:57 */     return typedQuery.getResultList();
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.ConceptoContableDao
 * JD-Core Version:    0.7.0.1
 */