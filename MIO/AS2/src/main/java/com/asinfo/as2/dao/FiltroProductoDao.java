/*  1:   */ package com.asinfo.as2.dao;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.entities.FiltroProducto;
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
/* 17:   */ public class FiltroProductoDao
/* 18:   */   extends AbstractDaoAS2<FiltroProducto>
/* 19:   */ {
/* 20:   */   public FiltroProductoDao()
/* 21:   */   {
/* 22:29 */     super(FiltroProducto.class);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public List<FiltroProducto> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 26:   */   {
/* 27:35 */     CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
/* 28:36 */     CriteriaQuery<FiltroProducto> criteriaQuery = criteriaBuilder.createQuery(FiltroProducto.class);
/* 29:37 */     Root<FiltroProducto> from = criteriaQuery.from(FiltroProducto.class);
/* 30:38 */     from.fetch("atributo1", JoinType.LEFT);
/* 31:39 */     from.fetch("atributo2", JoinType.LEFT);
/* 32:40 */     from.fetch("atributo3", JoinType.LEFT);
/* 33:41 */     from.fetch("atributo4", JoinType.LEFT);
/* 34:42 */     from.fetch("atributo5", JoinType.LEFT);
/* 35:43 */     from.fetch("atributo6", JoinType.LEFT);
/* 36:44 */     from.fetch("atributo7", JoinType.LEFT);
/* 37:45 */     from.fetch("atributo8", JoinType.LEFT);
/* 38:46 */     from.fetch("atributo9", JoinType.LEFT);
/* 39:47 */     from.fetch("atributo10", JoinType.LEFT);
/* 40:   */     
/* 41:49 */     List<Expression<?>> empresiones = obtenerExpresiones(filters, criteriaBuilder, from);
/* 42:50 */     criteriaQuery.where((Predicate[])empresiones.toArray(new Predicate[empresiones.size()]));
/* 43:   */     
/* 44:52 */     agregarOrdenamiento(sortField, sortOrder, criteriaBuilder, criteriaQuery, from);
/* 45:   */     
/* 46:54 */     CriteriaQuery<FiltroProducto> select = criteriaQuery.select(from);
/* 47:   */     
/* 48:56 */     TypedQuery<FiltroProducto> typedQuery = this.em.createQuery(select);
/* 49:   */     
/* 50:58 */     return typedQuery.getResultList();
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.dao.FiltroProductoDao
 * JD-Core Version:    0.7.0.1
 */